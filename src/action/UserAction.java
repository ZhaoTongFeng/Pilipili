package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import bean.User;
import service.UserService;

/**************************************
 * UserAction
 * //注意：每次更新了数据库的User，还要重新设置session的USER
 **************************************/

public class UserAction extends ActionSupport{
	
	@Autowired
	private UserService userService;
	
	private User user;
	private List<User> users;
	
	
	private int id;
	private int level;
	

	//积分充值数额
	private int[] balances = {30,68,128,512,1024,2048};
	
	//VIP价格
	private List<Map<Integer, Integer>> vipPrices = new ArrayList<Map<Integer,Integer>>();
	

	
	//跳转：VIP中心
	public String VIPCenter(){
		vipPrices = MyConfig.VIP_MONTH_PRICE;
		if(isLogin()){
			return "succ";
		}else{
			return "sign_in";
		}
	}	
	
	//跳转：积分中心
	public String BalanceCenter(){
		if(isLogin()){
			return "succ";
		}else{
			return "sign_in";
		}
	}
	
	//操作：注销，跳转:主页
	public String sign_out(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("user");
		return "succ";
	}
	
	//跳转:登录页面
	public String sign_in(){
		return "succ"; 
	}
	
	//跳转：注册页面
	public String sign_up(){
		return "succ"; 
	}
	
	//跳转：管理员主页
	public String AdminHome(){
		if(isLogin()&&user.getLevel()==2){
			return "succ";
		}else{
			return "sign_in";
		}		
	}
	
	//跳转用户主页
	public String UserHome(){
		if(isLogin()){
			return "succ";
		}else{
			return "sign_in";
		}
	}
	
	//操作：调整权限（测试用），跳转：主页
	public String SetLevel(){
		if(isLogin()){
			user.setLevel(level);
			
			userService.update(user);
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("user", user);
		}
		return "succ"; 
	}
	
	
	
	
	//验证是否登录
	public boolean isLogin(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User)session.getAttribute("user");
		if(user==null){
			return false;
		}else{
			return true;
		}
	}
	
	
	
	
	/**************************************
	 * Setter&Getter
	 * 
	 **************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}




	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}

	public int[] getBalances() {
		return balances;
	}

	public void setBalances(int[] balances) {
		this.balances = balances;
	}

	public List<Map<Integer, Integer>> getVipPrices() {
		return vipPrices;
	}

	public void setVipPrices(List<Map<Integer, Integer>> vipPrices) {
		this.vipPrices = vipPrices;
	}


	
}




//result type=chain、dispatcher、redirect(redirect-action)
//dispatcher 为默认跳转类型，用于返回一个视图资源(如:jsp)
//redirect 类型用于重定向到一个页面，另一个action或一个网址。redirect-action
//chain 用于把相关的几个action连接起来，共同完成一个功能。