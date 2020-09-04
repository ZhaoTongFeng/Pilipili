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
 * //ע�⣺ÿ�θ��������ݿ��User����Ҫ��������session��USER
 **************************************/

public class UserAction extends ActionSupport{
	
	@Autowired
	private UserService userService;
	
	private User user;
	private List<User> users;
	
	
	private int id;
	private int level;
	

	//���ֳ�ֵ����
	private int[] balances = {30,68,128,512,1024,2048};
	
	//VIP�۸�
	private List<Map<Integer, Integer>> vipPrices = new ArrayList<Map<Integer,Integer>>();
	

	
	//��ת��VIP����
	public String VIPCenter(){
		vipPrices = MyConfig.VIP_MONTH_PRICE;
		if(isLogin()){
			return "succ";
		}else{
			return "sign_in";
		}
	}	
	
	//��ת����������
	public String BalanceCenter(){
		if(isLogin()){
			return "succ";
		}else{
			return "sign_in";
		}
	}
	
	//������ע������ת:��ҳ
	public String sign_out(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("user");
		return "succ";
	}
	
	//��ת:��¼ҳ��
	public String sign_in(){
		return "succ"; 
	}
	
	//��ת��ע��ҳ��
	public String sign_up(){
		return "succ"; 
	}
	
	//��ת������Ա��ҳ
	public String AdminHome(){
		if(isLogin()&&user.getLevel()==2){
			return "succ";
		}else{
			return "sign_in";
		}		
	}
	
	//��ת�û���ҳ
	public String UserHome(){
		if(isLogin()){
			return "succ";
		}else{
			return "sign_in";
		}
	}
	
	//����������Ȩ�ޣ������ã�����ת����ҳ
	public String SetLevel(){
		if(isLogin()){
			user.setLevel(level);
			
			userService.update(user);
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("user", user);
		}
		return "succ"; 
	}
	
	
	
	
	//��֤�Ƿ��¼
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




//result type=chain��dispatcher��redirect(redirect-action)
//dispatcher ΪĬ����ת���ͣ����ڷ���һ����ͼ��Դ(��:jsp)
//redirect ���������ض���һ��ҳ�棬��һ��action��һ����ַ��redirect-action
//chain ���ڰ���صļ���action������������ͬ���һ�����ܡ�