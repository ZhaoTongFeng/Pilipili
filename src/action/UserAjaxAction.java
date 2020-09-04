package action;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.dom4j.CDATA;
import org.hibernate.usertype.UserCollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import bean.Message;
import bean.User;
import service.MessageService;
import service.UserService;
import util.ErrMap;
import util.FileUtil;
//注意：每次更新了数据库的User，还要重新设置session的USER

@Controller("userAjaxAction")
@ParentPackage("json-default")
@Scope("prototype")
public class UserAjaxAction extends ActionSupport {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	private File doc;
	private String docFileName;
	private String docContentType;

	private ErrMap data = new ErrMap();
	private User user;

	private String name;
	private String email;
	private String passwd;
	private String message;
	private String balance;

	private String exp;

	private int month = 0;

	// 签到
	@Action(value = "checkIn", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String checkIn() {
		this.IsLogin();
		Date current = new Date();
		if (user.getCheckInTime() == null) {

		} else {
			long offset = current.getTime() - user.getCheckInTime().getTime();
			if (offset < (24 * 60 * 60 * 1000)) {
				data.putErr("今日已签", MyConfig.ERR_CONDITION);
			}
		}
		if (data.isErr()) {
			return "succ";
		}
		user.setCheckInTime(current);

		user.setExp(user.getExp() + MyConfig.EXP_CHECK_IN);
		user.setBalance(user.getBalance() + MyConfig.BALANCE_CHECK_IN);
		userService.update(user);
		
		data.put("exp", user.getExp());
		data.put("grade", user.getGrade());
		data.put("balance", user.getBalance());
		data.put("code", 0);
		return "succ";
	}

	// 充值积分
	@Action(value = "recharge", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String Recharge() {
		this.IsLogin();
		int num = 0;
		System.out.println(balance);
		if (balance == null) {
			data.putErr("balance为null", MyConfig.ERR_NULL);
		}
		try {
			num = Integer.parseInt(balance);
		} catch (NumberFormatException e) {
			data.putErr("balance不是整型", MyConfig.ERR_TYPE);
			e.printStackTrace();
		}
		if (data.isErr()) {
			return "succ";
		}

		user.setBalance(user.getBalance() + num);
		user.setExp(user.getExp() + (num * MyConfig.EXP_PER_BALANCE));

		// 保存
		userService.update(user);
		this.UpdateSessionUser(user);

		// 消息
		messageService.add(new Message(String.format(MyConfig.MSG_BALANCE, num), null, user));
		// 返回：增长的经验，和当前的余额
		data.put("code", 0);

		data.put("balance", user.getBalance());
		return "succ";
	}

	// 积分兑换VIP
	@Action(value = "addVipTime", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String SetLevel() {
		System.out.println(month);
		if (month == 0) {
			data.putErr("请选择充值类型", MyConfig.ERR_DEFAULT);
		}
		this.IsLogin();		
		//取出月份对应的价格
		List<Map<Integer, Integer>> vipPrices = MyConfig.VIP_MONTH_PRICE;
		int balance = 0;
		for (Map<Integer, Integer> map : vipPrices) {
			for (int m : map.keySet()) {
				if (m == month) {
					balance = map.get(m);
				}
			}
		}
		if (balance == 0) {
			data.putErr("充值时长不在范围内", MyConfig.ERR_OUT_RANGE);
		}
		// 判断积分是否足够
		if (user.getBalance() < balance) {
			data.putErr("积分不足", MyConfig.ERR_CONDITION);
		}
		if (data.isErr()) {return "succ";}
		
		
		// 扣积分，增加时长，设置成VIP
		user.setBalance(user.getBalance() - balance);

		Date enDate;
		Calendar cal = Calendar.getInstance();
		if (user.getVipTime() == null) {
			// 首次充值
			Date date = new Date();
			// 设置起点时间
			cal.setTime(date);
			cal.add(Calendar.MONTH, month);
			enDate = cal.getTime();
			user.setVipTime(enDate);
		} else {
			// 续费
			cal.setTime(user.getVipTime());
			cal.add(Calendar.MONTH, month);
			enDate = cal.getTime();
			user.setVipTime(enDate);
		}
		user.setExp(user.getExp()+(int)month*MyConfig.EXP_PRE_VIP_MONTH);
		
		user.setLevel(MyConfig.LEVEL_VIP);
		// 保存更新
		this.UpdateSessionUser(user);
		userService.update(user);
		
		// 返回到期时间
		messageService.add(new Message(String.format(MyConfig.MSG_VIP, user.getVipTime()), null, user));
		data.put("code", 0);
		data.put("balance", user.getBalance());
		data.put("vipTime", user.getVipTime());

		return "succ";
	}

	// 上传头像
	@Action(value = "uploadImg", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String uploadImg() {

		this.IsLogin();
		if (doc == null) {
			data.putErr("没有选择图片", MyConfig.ERR_DEFAULT);
		}
		if (data.isErr()) {return "succ";}

		String root = ServletActionContext.getServletContext().getRealPath("");
		String filename = FileUtil.getRandomName(docFileName);// 名字
		File ImageFile = user.getPath(root, filename);// 完整路径
		try {
			// 保存,设置,更新
			FileUtils.copyFile(doc, ImageFile);
			String src = user.getSrc(filename);
			user.setImg(src);
			userService.update(user);
			this.UpdateSessionUser(user);
			data.put("code", 0);
			data.put("src", src);// 返回图片路径
		} catch (IOException e) {
			data.putErr("IOError:文件保存失败",MyConfig.ERR_IO);
			e.printStackTrace();
		}

		return "succ";
	}

	// 更改用户信息
	@Action(value = "updateInfo", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String update() {
		this.IsLogin();
		this.verifyNameAndPasswd();
		if (data.isErr()) {return "succ";}
		
		user.setPasswd(passwd);
		user.setName(name);
		
		userService.update(user);
		this.UpdateSessionUser(user);
		data.put("code", 0);
		return "succ";
	}

	// 登录
	@Action(value = "login", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String login() {
		User dbuser = userService.getByEmail(email);
		if (dbuser == null) {
			data.putErr("用户不存在", MyConfig.ERR_NO_USER);
		} else if (!dbuser.getPasswd().equals(passwd)) {
			data.putErr("密码不正确", MyConfig.ERR_CONDITION);
		} else if (dbuser.getLevel() == MyConfig.LEVEL_BAN) {
			data.putErr("你已被永久封禁，请联系客服解封", MyConfig.ERR_CONDITION);
		} else {
			data.put("code", 0);
			this.UpdateSessionUser(dbuser);
		}
		return "succ";
	}
	

	// 注册
	@Action(value = "register", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String register() {

		User dbuser = userService.getByEmail(email);
		if (dbuser != null) {
			data.putErr("用户已存在", MyConfig.ERR_EXIST_USER);
		}
		this.verifyNameAndPasswd();
		if (data.isErr()) {return "succ";}

		// 注册成功并直接登录
		
		User user = new User(name, email, passwd);
		userService.add(user);
		this.UpdateSessionUser(user);
		data.put("code", 0);
		return "succ";
	}

	// 检查是否登录
	private boolean IsLogin() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		if (user == null) {
			data.putErr("未登录", MyConfig.ERR_NO_LOGIN);
			return false;
		} else {
			return true;
		}
	}
	
	public void UpdateSessionUser(User u) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("user", u);
	}

	// 检查用户名和密码
	private void verifyNameAndPasswd() {
		if (name.length() > 10 || name.length() < 1) {
			data.putErr("名字长度：1-10", MyConfig.ERR_CONDITION);
		}
		if (passwd.length() < 6 || passwd.length() > 20) {
			data.putErr("密码长度：6-20", MyConfig.ERR_CONDITION);
		}
	}



	/**************************************
	 * Setter&Getter
	 * 
	 **************************************/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSON(serialize = false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JSON(serialize = false)
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JSON(serialize = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JSON(serialize = false)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public ErrMap getData() {
		return data;
	}

	public void setData(ErrMap data) {
		this.data = data;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
