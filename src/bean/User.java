package bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import action.MyConfig;

//H
@Entity
@Table(name="user")

//Spring
@Component("User")
public class User implements Serializable{
	public User(){
		
	}
    public User(String name, String email, String passwd) {
		super();
		this.name = name;
		this.email = email;
		this.passwd = passwd;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable=false)  
	private Integer id;
    
    @Column(nullable=false)
    private String name="�û�";
    
    @Column(nullable=false)
    
    private String email;
    
    @Column(nullable=false)
    private String passwd;
    
    //ͷ��   
    @Column
    private String img = "file/image/system/pay.png";		
    
    //Ȩ��
    @Column(columnDefinition="INT default 1",nullable=false)
    private Integer level = 0;
    
    //VIP����ʱ��
    @Column
    private Date vipTime = new Date();
    
    //VIP״̬
    @Column
    private Boolean isVip;	    
    //ǩ��ʱ��
    @Column
    private Date checkInTime;
    
    //ע��ʱ��
    @Column
    private Date registerTime = new Date();
    
    
    //��������
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_comment = 0;	
    
    //���ۿ�����
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_view = 0;	
    //��������
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer income = 0;	
    
    //�Լ���ֵ�Ľ��
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer balance = 0;	
    

    //���飬�ȼ�
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer exp = 0;	
    
    @Column(columnDefinition="INT default 1",nullable=false)
    private Integer grade = 1;	
    

    //�ղص�videoID
    @Column
    private String video_mark="0";
    //�ղ���
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_mark = 0;
    
    //������
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_good = 0;
    
    public boolean isVIP(){
    	if(vipTime==null){
    		return false;
    	}else{
        	Date current = new Date();
        	if(current.getTime()<=vipTime.getTime()){
        		return true;
        	}else{
        		return false;
        	}
    	}

    }
    
    //����ͼƬ�ļ�ʵ�ʱ���ľ���·��
    public File getPath(String root,String filename){
        String destPath = String.format(MyConfig.PATH_USER_IMG, root,id);
        File dir = new File(destPath);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        return new File(dir,filename);
    }
    
    
    public String getSrc(String filename){
    	String src = String.format(MyConfig.SRC_USER_IMG, id,filename);
    	return src;
    }
    

    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		//��ֵ+����
		this.exp = exp;
		if(exp>MyConfig.EXP_PER_LEVEL){
			this.grade+=(int)(exp/MyConfig.EXP_PER_LEVEL);
			this.exp = exp%MyConfig.EXP_PER_LEVEL;
		}
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Date getVipTime() {
		return vipTime;
	}
	public void setVipTime(Date vipTime) {
		this.vipTime = vipTime;
	}
	public Date getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Integer getNum_comment() {
		return num_comment;
	}
	public void setNum_comment(Integer num_comment) {
		this.num_comment = num_comment;
	}
	public Integer getNum_view() {
		return num_view;
	}
	public void setNum_view(Integer num_view) {
		this.num_view = num_view;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getNum_mark() {
		return num_mark;
	}
	public void setNum_mark(Integer num_mark) {
		this.num_mark = num_mark;
	}
	public String getVideo_mark() {
		return video_mark;
	}
	public void setVideo_mark(String video_mark) {
		this.video_mark = video_mark;
	}
	public Boolean getIsVip() {
		return isVIP();
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}
	public Integer getNum_good() {
		return num_good;
	}
	public void setNum_good(Integer num_good) {
		this.num_good = num_good;
	}
	
}
