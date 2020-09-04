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
//ע�⣺ÿ�θ��������ݿ��User����Ҫ��������session��USER

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

	// ǩ��
	@Action(value = "checkIn", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String checkIn() {
		this.IsLogin();
		Date current = new Date();
		if (user.getCheckInTime() == null) {

		} else {
			long offset = current.getTime() - user.getCheckInTime().getTime();
			if (offset < (24 * 60 * 60 * 1000)) {
				data.putErr("������ǩ", MyConfig.ERR_CONDITION);
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

	// ��ֵ����
	@Action(value = "recharge", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String Recharge() {
		this.IsLogin();
		int num = 0;
		System.out.println(balance);
		if (balance == null) {
			data.putErr("balanceΪnull", MyConfig.ERR_NULL);
		}
		try {
			num = Integer.parseInt(balance);
		} catch (NumberFormatException e) {
			data.putErr("balance��������", MyConfig.ERR_TYPE);
			e.printStackTrace();
		}
		if (data.isErr()) {
			return "succ";
		}

		user.setBalance(user.getBalance() + num);
		user.setExp(user.getExp() + (num * MyConfig.EXP_PER_BALANCE));

		// ����
		userService.update(user);
		this.UpdateSessionUser(user);

		// ��Ϣ
		messageService.add(new Message(String.format(MyConfig.MSG_BALANCE, num), null, user));
		// ���أ������ľ��飬�͵�ǰ�����
		data.put("code", 0);

		data.put("balance", user.getBalance());
		return "succ";
	}

	// ���ֶһ�VIP
	@Action(value = "addVipTime", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String SetLevel() {
		System.out.println(month);
		if (month == 0) {
			data.putErr("��ѡ���ֵ����", MyConfig.ERR_DEFAULT);
		}
		this.IsLogin();		
		//ȡ���·ݶ�Ӧ�ļ۸�
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
			data.putErr("��ֵʱ�����ڷ�Χ��", MyConfig.ERR_OUT_RANGE);
		}
		// �жϻ����Ƿ��㹻
		if (user.getBalance() < balance) {
			data.putErr("���ֲ���", MyConfig.ERR_CONDITION);
		}
		if (data.isErr()) {return "succ";}
		
		
		// �ۻ��֣�����ʱ�������ó�VIP
		user.setBalance(user.getBalance() - balance);

		Date enDate;
		Calendar cal = Calendar.getInstance();
		if (user.getVipTime() == null) {
			// �״γ�ֵ
			Date date = new Date();
			// �������ʱ��
			cal.setTime(date);
			cal.add(Calendar.MONTH, month);
			enDate = cal.getTime();
			user.setVipTime(enDate);
		} else {
			// ����
			cal.setTime(user.getVipTime());
			cal.add(Calendar.MONTH, month);
			enDate = cal.getTime();
			user.setVipTime(enDate);
		}
		user.setExp(user.getExp()+(int)month*MyConfig.EXP_PRE_VIP_MONTH);
		
		user.setLevel(MyConfig.LEVEL_VIP);
		// �������
		this.UpdateSessionUser(user);
		userService.update(user);
		
		// ���ص���ʱ��
		messageService.add(new Message(String.format(MyConfig.MSG_VIP, user.getVipTime()), null, user));
		data.put("code", 0);
		data.put("balance", user.getBalance());
		data.put("vipTime", user.getVipTime());

		return "succ";
	}

	// �ϴ�ͷ��
	@Action(value = "uploadImg", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String uploadImg() {

		this.IsLogin();
		if (doc == null) {
			data.putErr("û��ѡ��ͼƬ", MyConfig.ERR_DEFAULT);
		}
		if (data.isErr()) {return "succ";}

		String root = ServletActionContext.getServletContext().getRealPath("");
		String filename = FileUtil.getRandomName(docFileName);// ����
		File ImageFile = user.getPath(root, filename);// ����·��
		try {
			// ����,����,����
			FileUtils.copyFile(doc, ImageFile);
			String src = user.getSrc(filename);
			user.setImg(src);
			userService.update(user);
			this.UpdateSessionUser(user);
			data.put("code", 0);
			data.put("src", src);// ����ͼƬ·��
		} catch (IOException e) {
			data.putErr("IOError:�ļ�����ʧ��",MyConfig.ERR_IO);
			e.printStackTrace();
		}

		return "succ";
	}

	// �����û���Ϣ
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

	// ��¼
	@Action(value = "login", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String login() {
		User dbuser = userService.getByEmail(email);
		if (dbuser == null) {
			data.putErr("�û�������", MyConfig.ERR_NO_USER);
		} else if (!dbuser.getPasswd().equals(passwd)) {
			data.putErr("���벻��ȷ", MyConfig.ERR_CONDITION);
		} else if (dbuser.getLevel() == MyConfig.LEVEL_BAN) {
			data.putErr("���ѱ����÷��������ϵ�ͷ����", MyConfig.ERR_CONDITION);
		} else {
			data.put("code", 0);
			this.UpdateSessionUser(dbuser);
		}
		return "succ";
	}
	

	// ע��
	@Action(value = "register", results = { @Result(name = "succ", type = "json", params = { "root", "data" }) })
	public String register() {

		User dbuser = userService.getByEmail(email);
		if (dbuser != null) {
			data.putErr("�û��Ѵ���", MyConfig.ERR_EXIST_USER);
		}
		this.verifyNameAndPasswd();
		if (data.isErr()) {return "succ";}

		// ע��ɹ���ֱ�ӵ�¼
		
		User user = new User(name, email, passwd);
		userService.add(user);
		this.UpdateSessionUser(user);
		data.put("code", 0);
		return "succ";
	}

	// ����Ƿ��¼
	private boolean IsLogin() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		if (user == null) {
			data.putErr("δ��¼", MyConfig.ERR_NO_LOGIN);
			return false;
		} else {
			return true;
		}
	}
	
	public void UpdateSessionUser(User u) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("user", u);
	}

	// ����û���������
	private void verifyNameAndPasswd() {
		if (name.length() > 10 || name.length() < 1) {
			data.putErr("���ֳ��ȣ�1-10", MyConfig.ERR_CONDITION);
		}
		if (passwd.length() < 6 || passwd.length() > 20) {
			data.putErr("���볤�ȣ�6-20", MyConfig.ERR_CONDITION);
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
