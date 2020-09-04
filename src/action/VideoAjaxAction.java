package action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.annotations.Check;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.SavepointManager;

import bean.*;
import service.CategoryService;
import service.MessageService;
import service.UserService;
import service.VideoService;
import util.ErrMap;
import util.FileUtil;
import util.GoodUtil;
 

@SuppressWarnings("serial")
@Controller("videoAjaxAction")
@ParentPackage("json-default")
@Scope("prototype")
@Namespace("/video")
public class VideoAjaxAction {
	@Autowired
	private VideoService videoService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
    //����ID
    private int ca_id=0;
    private int id = 0;
    
    private Video video;
    private User user;

    
    
    private ErrMap data = new ErrMap();
    
    private List<File> doc;//��ʱ·��
    private List<String> docFileName;//�ļ���
    private List<String> docContentType;//�ļ�����
    
    private Boolean isVip;

    private Boolean hasImage = false;
    private Boolean hasVideo = false;
    
    //���
    private Boolean opt;
    @Action(value="audit",results={@Result(name="success",type="json",params={"root","data"})})
    public String audit() {
    	CheckIsLogin();
    	CheckVideo();
    	if(opt==null||id==0){
    		data.putErr("��������ȷ", MyConfig.ERR_DEFAULT);
    	}
    	if(data.isErr()){return "success";}
    	if(opt){
    		//ͨ��
    		video.setLevel(MyConfig.LEVEL_PUBLIC);
    		messageService.add(new Message(String.format(MyConfig.MSG_AUDIT_SUCC, video.getTitle()),null,video.getUser()));
    	}else{
    		//����
    		video.setLevel(MyConfig.LEVEL_FAIL);
    		video.setNum_audit_fail(video.getNum_audit_fail()+1);
    		messageService.add(new Message(String.format(MyConfig.MSG_AUDIT_FAIL, video.getTitle()),null,video.getUser()));
    	}
		//����,�����һ������˵���ƵID
    	data.put("code", 0);
		videoService.update(video);
		List<Video> list = videoService.auditList(0, 1);
		if(list.size()==0){
			data.put("id", 0);
		}else{
			data.put("id", list.get(0).getId());
		}
    	return "success";
    }
    
    
    
    //Ȩ�ޱ��
    @Action(value="updateLevel",results={@Result(name="success",type="json",params={"root","data"})})
    public String updateLevel() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
    	CheckIsSelf();
    	if(data.isErr()){return "success";}
    	int level = video.getLevel();
    	if(level==MyConfig.LEVEL_PRIVATE){
    		video.setLevel(MyConfig.LEVEL_AUDIT);
    		data.put("str", "ȡ�����");
    		video.setNunm_audit_total(video.getNunm_audit_total()+1);
    		video.setTime_audit(new Date());
    	}else if(level==MyConfig.LEVEL_AUDIT){
    		video.setLevel(MyConfig.LEVEL_PRIVATE);
    		data.put("str", "����");
    		video.setNunm_audit_total(video.getNunm_audit_total()-1);
    	}else if(level==MyConfig.LEVEL_PUBLIC){
    		video.setLevel(MyConfig.LEVEL_PRIVATE);
    		data.put("str", "����");
    	}else if(level==MyConfig.LEVEL_FAIL){
    		
    		video.setLevel(MyConfig.LEVEL_AUDIT);
    		data.put("str", "ȡ�����");
    		
    		video.setNunm_audit_total(video.getNunm_audit_total()+1);
    		video.setTime_audit(new Date());
    	}else{
    		return "success";
    	}
		//���棬�����ص�ǰ������
		videoService.update(video);
    	data.put("code", 0);
    	data.put("oldLevel", level);
    	return "success";
    }
    
    
    //����
    @Action(value="goodVideo",results={@Result(name="success",type="json",params={"root","data"})})
    public String goodVideo() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
		//��鵱ǰ�û��Ƿ��Ѿ������
    	boolean isGood = GoodUtil.setGood(video, user.getId());

    	messageService.add(new Message(String.format(MyConfig.MSG_GOOD, user.getName(),video.getTitle()),user,video.getUser()));
		//���棬�����ص�ǰ������
		videoService.update(video);
    	data.put("code", 0);
    	data.put("num_good", video.getNum_good());
    	data.put("good", !isGood);
    	return "success";
    }
    
    
    //Ͷ��
    @Action(value="giveCoin",results={@Result(name="success",type="json",params={"root","data"})})
    public String GiveCoin() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
    	if((int)user.getId()==(int)video.getUser().getId()){
    		data.putErr("���ܸ��Լ�Ͷ��", MyConfig.ERR_CONDITION);
    	}
    	int addon = 1;    	
    	if(user.getBalance()<addon){
    		data.putErr("����", MyConfig.ERR_CONDITION);
    	}
    	if(data.isErr()){return "success";}
    	

    	video.setBalance(video.getBalance()+addon);
    	User user_video = video.getUser();
    	user_video.setBalance(user_video.getBalance()+addon);
    	user.setBalance(user.getBalance()-addon);
    	
		//���棬�����ص�ǰ������
		videoService.update(video);
		userService.update(user);
		userService.update(user_video);
		
    	data.put("code", 0);
    	data.put("balance", user.getBalance());
    	data.put("balance_video", video.getBalance());
    	data.put("coin", addon);
    	return "success";
    }
    
    
    
    
    
    //�ղ�
    @Action(value="mark",results={@Result(name="success",type="json",params={"root","data"})})
    public String MarkVideo() {
    	
    	//data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
		//˫�����
    	boolean isMark = userService.MarkVideo(user, video.getId());
    	videoService.marked(video, user.getId(), isMark);
		//����
    	messageService.add(new Message(String.format(MyConfig.MSG_MARK, user.getName(),video.getTitle()),user,video.getUser()));
		videoService.update(video);
		userService.update(user);
    	data.put("code", 0);
    	data.put("num_mark", video.getNum_mark());
    	data.put("mark", !isMark);
    	return "success";
    }
    
    
    
    
    
    
    
//    //�ϴ���Ƶ
    @Action(value="upload",results={@Result(name="success",type="json",params={"root","data"})})
    public String upload() {
    	data.put("code", -1);
    	//1.Ч��
    	CheckIsLogin();
    	//����ļ�
    	if(doc==null){return "success";}
    	CheckVideoFile(doc.get(0));
    	CheckImgFile(doc.get(1));
    	
    	CheckVideoFormat(docContentType.get(0));

    	CheckCategory(video);
    	
    	if(data.isErr()){return "success";}
    	video.setUser(user);
    	
		//���沢��ȡID
		videoService.add(video);
		
		//2.������Ƶ��ͼƬ
		SaveVideo(doc.get(0),docFileName.get(0));
		if(data.isErr()){return "success";}
    	SaveImage(video,doc.get(1), docFileName.get(1));
    	if(data.isErr()){return "success";}

    	
        //3.���������ݣ�����id��JS��ת�༭ҳ��
        videoService.update(video);
        data.put("code", 0);
        data.put("id", video.getId());
        return "success";
    }

    
    
    //�޸�
    @Action(value="update",results={@Result(name="success",type="json",params={"root","data"})})
    public String wqedsadeqw(){
    	data.put("code", -1);
    	CheckIsLogin();
		Video dbvideo = videoService.get(video.getId());
		if(dbvideo==null){
			data.putErr("��VIDEO������", MyConfig.ERR_NULL);
			return "success";
		}
    	CheckCategory(dbvideo);
    	if(data.isErr()){return "success";}
    	dbvideo.setIsVip(isVip);
    	dbvideo.setTitle(video.getTitle());
    	dbvideo.setIntroduction(video.getIntroduction());
    	
    	
    	System.out.println(doc);
    	if(hasImage&&hasVideo){
    		CheckVideoFormat(docContentType.get(0));
    		if(data.isErr()){return "success";}
    		SaveVideo(dbvideo,doc.get(0), docFileName.get(0));
    		if(data.isErr()){return "success";}
    		SaveImage(dbvideo,doc.get(1), docFileName.get(1));
    	}else if(hasImage){
    		SaveImage(dbvideo,doc.get(0), docFileName.get(0));
    	}else if(hasVideo){
    		CheckVideoFormat(docContentType.get(0));
    		if(data.isErr()){return "success";}
    		SaveVideo(dbvideo,doc.get(0), docFileName.get(0));
    	}
    	
    	if(data.isErr()){return "success";}

        //3.���������ݣ�����id��JS��ת�༭ҳ��
        videoService.update(dbvideo);
        data.put("code", 0);
        data.put("video", dbvideo);
        return "success";
    	
    }

    //����������ļ���Ϊ�յĺ�������Ч��
    private void CheckImgFile(File file){
    	if(file.length()==0){
    		data.putErr("����ͼƬ����Ϊ��", MyConfig.ERR_NULL);
    	}
    }
    private void CheckVideoFile(File file){
    	if(file.length()==0){
    		data.putErr("��Ƶ����Ϊ��", MyConfig.ERR_NULL);
    	}
    }
    private void CheckVideoFormat(String type){
    	if(!MyConfig.FORMAT_SUPPORT.contains(type)){
    		data.putErr("��Ƶ��ʽ��֧��", MyConfig.ERR_NULL);
    	}
    }

    private void CheckCategory(Video v){
    	//�������
    	Category category = categoryService.get(ca_id);
    	if(category==null){
    		data.putErr("���Ͳ���Ϊ��", MyConfig.ERR_NULL);
    	}else{
    		v.setCategory(category);
    	}
    }
    
    private void CheckIsLogin(){
    	//����Ƿ��¼�������¼������user
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		if(user==null){
			data.putErr("δ��¼", MyConfig.ERR_NO_LOGIN);
		}
    }
    public void CheckVideo(){
    	video = videoService.get(id);
    	if(video==null){
    		data.putErr("����Ƶ������", MyConfig.ERR_NULL);
    	}
    }
    //����ǲ����Լ���
    public void CheckIsSelf(){
    	if((int)video.getUser().getId()!=(int)user.getId()){
    		data.putErr("���޸�Ȩ��", MyConfig.ERR_LEVEL);
    	}
    }
    
    //��·����...��Ŀ��/�������������ֱ�ӽ���ƴ�ӣ������ﲻ�����ƴ��
    private String getRoot(){
    	return ServletActionContext.getServletContext().getRealPath("");
    }

    private void SaveVideo(File file,String name){
    	
    	String root = getRoot();
    	String videoName = FileUtil.getRandomName(name);
    	File videoFile = video.getPath(root,videoName);
        try {
        	//�ɹ�����SRC
			FileUtils.copyFile(file, videoFile);
	    	video.setSrc(video.getSrc(videoName));
		} catch (IOException e) {
			//ʧ�ܻع�������Ҫɾ�������ļ�����������ɾ����
			videoService.delete(video);
    		data.put("code", MyConfig.ERR_IO);
    		String errmsg = "�������쳣����Ƶ�ļ�����ʧ��";
    		data.put("errmsg", errmsg);
    		e.printStackTrace();
		}
    }
    
    
    private void SaveVideo(Video v,File file,String name){
    	String root = getRoot();
        String VideoName = FileUtil.getRandomName(name);//��Ƶ����
        File VideoFile = v.getPath(root, VideoName);//��Ƶ����·��
        try{
        	FileUtils.copyFile(file, VideoFile);
        	v.setSrc(v.getSrc(VideoName));
        }catch(IOException e){
        	//videoService.delete(video);
        	data.putErr("�������쳣����Ƶ�ļ�����ʧ��", MyConfig.ERR_IO);
    		e.printStackTrace();
        }
    }
    
    private void SaveImage(Video v,File file,String name){
    	String root = getRoot();
        String ImageName = FileUtil.getRandomName(name);//��������
        File ImageFile = v.getImagePath(root,ImageName);//��������·��
        try{
        	FileUtils.copyFile(file, ImageFile);
        	v.setImg(v.getImageSrc(ImageName));
        }catch(IOException e){
        	//videoService.delete(video);
        	data.putErr("�������쳣�������ļ�����ʧ��", MyConfig.ERR_IO);
    		e.printStackTrace();
        }
    }
    
    

    
    
    
    
    
	/**************************************
	 * Setter&Getter
	 * 
	 **************************************/
	public VideoService getVideoService() {
		return videoService;
	}
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public int getCa_id() {
		return ca_id;
	}
	public void setCa_id(int ca_id) {
		this.ca_id = ca_id;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public List<File> getDoc() {
		return doc;
	}
	public void setDoc(List<File> doc) {
		this.doc = doc;
	}
	public List<String> getDocFileName() {
		return docFileName;
	}
	public void setDocFileName(List<String> docFileName) {
		this.docFileName = docFileName;
	}
	public List<String> getDocContentType() {
		return docContentType;
	}
	public void setDocContentType(List<String> docContentType) {
		this.docContentType = docContentType;
	}

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


	public boolean isOpt() {
		return opt;
	}



	public Boolean getOpt() {
		return opt;
	}


	public void setOpt(Boolean opt) {
		this.opt = opt;
	}


	public void setOpt(boolean opt) {
		this.opt = opt;
	}



	public Boolean getIsVip() {
		return isVip;
	}



	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}



	public Boolean getHasImage() {
		return hasImage;
	}



	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}



	public Boolean getHasVideo() {
		return hasVideo;
	}



	public void setHasVideo(Boolean hasVideo) {
		this.hasVideo = hasVideo;
	}



	public ErrMap getData() {
		return data;
	}



	public void setData(ErrMap data) {
		this.data = data;
	}



	public MessageService getMessageService() {
		return messageService;
	}



	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}





	
}