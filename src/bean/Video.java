package bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import action.MyConfig;
import util.GoodAPI;


@Entity
@Table(name="video")
@Component("Video")
public class Video implements Serializable,GoodAPI{
	public Video(){
		
	}
	public Video(String title,String introduction,User user){
		this.title = title;
		this.introduction = introduction;
		this.user = user;

	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable=false)  
	private Integer id;
	
	//��Ƶ����ID
    @Column
    private Integer vid;
    
    //��ǰ״̬��˽�У���������ˣ����ʧ��
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer level = 0;
    
	@Column(nullable=false)
    private String title;
	
	//���
    @Column
    private String introduction;
    
	//�Ƿ���VIP��Ƶ
	@Column(nullable=false)
    private Boolean isVip=false;
	

    
    //�ۿ���
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_view = 0;
    
    //����Ƶ������
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer balance = 0;
    
    //������
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_comment = 0;
    
    //�ղص�videoID
    @Column
    private String people_mark="0";
    //�ղ���
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_mark = 0;
    
    //����
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_good = 0;
    
    //�����˵�ID
    @Column
    private String people_good="0";
    
    //ͼƬ����
    @Column
    private String img;
    
    //��Ƶ����
    @Column
    private String src;
    
    //����ʱ��
    @Column
    private Date time_create = new Date();    
    
    //��˴���
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer nunm_audit_total = 0;
    
    //ʧ�ܴ���
    @Column(columnDefinition="INT default 0",nullable=false)
    private Integer num_audit_fail = 0;    
    
    //�˴�����ύʱ��
    @Column
    private Date time_audit = new Date();  
    
    

    
    @ManyToOne
    //@JoinTable(name="user",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="video_user_id"))
    @JoinColumn(name="video_user_id")
	@Autowired
    private User user;
    
    @ManyToOne
    @JoinColumn(name="video_category_id")
	@Autowired
    private Category category;
    
    

    
    
    
    //������Ƶ�ļ�ʵ�ʱ���ľ���·��
    public File getPath(String root,String filename){
        String destPath = String.format(MyConfig.PATH_VIDEO, root,id);
        File dir = new File(destPath);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        return new File(dir,filename);
    }
    
    public String getSrc(String filename){
    	String src = String.format(MyConfig.SRC_VIDEO, id,filename);
    	return src;
    }
    
    public File getImagePath(String root,String filename){
    	String destPath = String.format(MyConfig.PATH_VIDEO_IMG, root,id);
        File dir = new File(destPath);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        return new File(dir,filename);
    }
    public String getImageSrc(String filename){
    	String src = String.format(MyConfig.SRC_VIDEO_IMG, id,filename);
    	return src;
    }
    
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getNum_view() {
		return num_view;
	}

	public void setNum_view(Integer num_view) {
		this.num_view = num_view;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	


	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Date getTime_create() {
		return time_create;
	}
	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}

	public Integer getNum_good() {
		return num_good;
	}
	public void setNum_good(Integer num_good) {
		this.num_good = num_good;
	}
	public String getPeople_good() {
		return people_good;
	}
	public void setPeople_good(String people_good) {
		this.people_good = people_good;
	}
	public String getPeople_mark() {
		return people_mark;
	}
	public void setPeople_mark(String people_mark) {
		this.people_mark = people_mark;
	}
	public Integer getNum_mark() {
		return num_mark;
	}
	public void setNum_mark(Integer num_mark) {
		this.num_mark = num_mark;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Boolean getIsVip() {
		return isVip;
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}
	public Integer getNum_comment() {
		return num_comment;
	}
	public void setNum_comment(Integer num_comment) {
		this.num_comment = num_comment;
	}
	public Integer getNunm_audit_total() {
		return nunm_audit_total;
	}
	public void setNunm_audit_total(Integer nunm_audit_total) {
		this.nunm_audit_total = nunm_audit_total;
	}
	public Integer getNum_audit_fail() {
		return num_audit_fail;
	}
	public void setNum_audit_fail(Integer num_audit_fail) {
		this.num_audit_fail = num_audit_fail;
	}
	public Date getTime_audit() {
		return time_audit;
	}
	public void setTime_audit(Date time_audit) {
		this.time_audit = time_audit;
	}	
	
}
