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
    //类型ID
    private int ca_id=0;
    private int id = 0;
    
    private Video video;
    private User user;

    
    
    private ErrMap data = new ErrMap();
    
    private List<File> doc;//临时路径
    private List<String> docFileName;//文件名
    private List<String> docContentType;//文件类型
    
    private Boolean isVip;

    private Boolean hasImage = false;
    private Boolean hasVideo = false;
    
    //审核
    private Boolean opt;
    @Action(value="audit",results={@Result(name="success",type="json",params={"root","data"})})
    public String audit() {
    	CheckIsLogin();
    	CheckVideo();
    	if(opt==null||id==0){
    		data.putErr("参数不正确", MyConfig.ERR_DEFAULT);
    	}
    	if(data.isErr()){return "success";}
    	if(opt){
    		//通过
    		video.setLevel(MyConfig.LEVEL_PUBLIC);
    		messageService.add(new Message(String.format(MyConfig.MSG_AUDIT_SUCC, video.getTitle()),null,video.getUser()));
    	}else{
    		//驳回
    		video.setLevel(MyConfig.LEVEL_FAIL);
    		video.setNum_audit_fail(video.getNum_audit_fail()+1);
    		messageService.add(new Message(String.format(MyConfig.MSG_AUDIT_FAIL, video.getTitle()),null,video.getUser()));
    	}
		//保存,获得下一条待审核的视频ID
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
    
    
    
    //权限变更
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
    		data.put("str", "取消审核");
    		video.setNunm_audit_total(video.getNunm_audit_total()+1);
    		video.setTime_audit(new Date());
    	}else if(level==MyConfig.LEVEL_AUDIT){
    		video.setLevel(MyConfig.LEVEL_PRIVATE);
    		data.put("str", "发布");
    		video.setNunm_audit_total(video.getNunm_audit_total()-1);
    	}else if(level==MyConfig.LEVEL_PUBLIC){
    		video.setLevel(MyConfig.LEVEL_PRIVATE);
    		data.put("str", "发布");
    	}else if(level==MyConfig.LEVEL_FAIL){
    		
    		video.setLevel(MyConfig.LEVEL_AUDIT);
    		data.put("str", "取消审核");
    		
    		video.setNunm_audit_total(video.getNunm_audit_total()+1);
    		video.setTime_audit(new Date());
    	}else{
    		return "success";
    	}
		//保存，并返回当前点赞数
		videoService.update(video);
    	data.put("code", 0);
    	data.put("oldLevel", level);
    	return "success";
    }
    
    
    //点赞
    @Action(value="goodVideo",results={@Result(name="success",type="json",params={"root","data"})})
    public String goodVideo() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
		//检查当前用户是否已经点过赞
    	boolean isGood = GoodUtil.setGood(video, user.getId());

    	messageService.add(new Message(String.format(MyConfig.MSG_GOOD, user.getName(),video.getTitle()),user,video.getUser()));
		//保存，并返回当前点赞数
		videoService.update(video);
    	data.put("code", 0);
    	data.put("num_good", video.getNum_good());
    	data.put("good", !isGood);
    	return "success";
    }
    
    
    //投币
    @Action(value="giveCoin",results={@Result(name="success",type="json",params={"root","data"})})
    public String GiveCoin() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
    	if((int)user.getId()==(int)video.getUser().getId()){
    		data.putErr("不能给自己投币", MyConfig.ERR_CONDITION);
    	}
    	int addon = 1;    	
    	if(user.getBalance()<addon){
    		data.putErr("余额不足", MyConfig.ERR_CONDITION);
    	}
    	if(data.isErr()){return "success";}
    	

    	video.setBalance(video.getBalance()+addon);
    	User user_video = video.getUser();
    	user_video.setBalance(user_video.getBalance()+addon);
    	user.setBalance(user.getBalance()-addon);
    	
		//保存，并返回当前点赞数
		videoService.update(video);
		userService.update(user);
		userService.update(user_video);
		
    	data.put("code", 0);
    	data.put("balance", user.getBalance());
    	data.put("balance_video", video.getBalance());
    	data.put("coin", addon);
    	return "success";
    }
    
    
    
    
    
    //收藏
    @Action(value="mark",results={@Result(name="success",type="json",params={"root","data"})})
    public String MarkVideo() {
    	
    	//data.put("code", -1);
    	CheckIsLogin();
    	CheckVideo();
    	if(data.isErr()){return "success";}
		//双向关联
    	boolean isMark = userService.MarkVideo(user, video.getId());
    	videoService.marked(video, user.getId(), isMark);
		//保存
    	messageService.add(new Message(String.format(MyConfig.MSG_MARK, user.getName(),video.getTitle()),user,video.getUser()));
		videoService.update(video);
		userService.update(user);
    	data.put("code", 0);
    	data.put("num_mark", video.getNum_mark());
    	data.put("mark", !isMark);
    	return "success";
    }
    
    
    
    
    
    
    
//    //上传视频
    @Action(value="upload",results={@Result(name="success",type="json",params={"root","data"})})
    public String upload() {
    	data.put("code", -1);
    	//1.效验
    	CheckIsLogin();
    	//检查文件
    	if(doc==null){return "success";}
    	CheckVideoFile(doc.get(0));
    	CheckImgFile(doc.get(1));
    	
    	CheckVideoFormat(docContentType.get(0));

    	CheckCategory(video);
    	
    	if(data.isErr()){return "success";}
    	video.setUser(user);
    	
		//保存并获取ID
		videoService.add(video);
		
		//2.保存视频和图片
		SaveVideo(doc.get(0),docFileName.get(0));
		if(data.isErr()){return "success";}
    	SaveImage(video,doc.get(1), docFileName.get(1));
    	if(data.isErr()){return "success";}

    	
        //3.最后更新数据，返回id，JS跳转编辑页面
        videoService.update(video);
        data.put("code", 0);
        data.put("id", video.getId());
        return "success";
    }

    
    
    //修改
    @Action(value="update",results={@Result(name="success",type="json",params={"root","data"})})
    public String wqedsadeqw(){
    	data.put("code", -1);
    	CheckIsLogin();
		Video dbvideo = videoService.get(video.getId());
		if(dbvideo==null){
			data.putErr("此VIDEO不存在", MyConfig.ERR_NULL);
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

        //3.最后更新数据，返回id，JS跳转编辑页面
        videoService.update(dbvideo);
        data.put("code", 0);
        data.put("video", dbvideo);
        return "success";
    	
    }

    //这两个检测文件是为空的函数是无效的
    private void CheckImgFile(File file){
    	if(file.length()==0){
    		data.putErr("封面图片不能为空", MyConfig.ERR_NULL);
    	}
    }
    private void CheckVideoFile(File file){
    	if(file.length()==0){
    		data.putErr("视频不能为空", MyConfig.ERR_NULL);
    	}
    }
    private void CheckVideoFormat(String type){
    	if(!MyConfig.FORMAT_SUPPORT.contains(type)){
    		data.putErr("视频格式不支持", MyConfig.ERR_NULL);
    	}
    }

    private void CheckCategory(Video v){
    	//检查类型
    	Category category = categoryService.get(ca_id);
    	if(category==null){
    		data.putErr("类型不能为空", MyConfig.ERR_NULL);
    	}else{
    		v.setCategory(category);
    	}
    }
    
    private void CheckIsLogin(){
    	//检查是否登录，如果登录了设置user
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		if(user==null){
			data.putErr("未登录", MyConfig.ERR_NO_LOGIN);
		}
    }
    public void CheckVideo(){
    	video = videoService.get(id);
    	if(video==null){
    		data.putErr("此视频不存在", MyConfig.ERR_NULL);
    	}
    }
    //检查是不是自己的
    public void CheckIsSelf(){
    	if((int)video.getUser().getId()!=(int)user.getId()){
    		data.putErr("无修改权限", MyConfig.ERR_LEVEL);
    	}
    }
    
    //根路径：...项目名/，括号里面可以直接进行拼接，但这里不在这儿拼接
    private String getRoot(){
    	return ServletActionContext.getServletContext().getRealPath("");
    }

    private void SaveVideo(File file,String name){
    	
    	String root = getRoot();
    	String videoName = FileUtil.getRandomName(name);
    	File videoFile = video.getPath(root,videoName);
        try {
        	//成功设置SRC
			FileUtils.copyFile(file, videoFile);
	    	video.setSrc(video.getSrc(videoName));
		} catch (IOException e) {
			//失败回滚，还需要删除本地文件，这里懒得删除了
			videoService.delete(video);
    		data.put("code", MyConfig.ERR_IO);
    		String errmsg = "服务器异常：视频文件保存失败";
    		data.put("errmsg", errmsg);
    		e.printStackTrace();
		}
    }
    
    
    private void SaveVideo(Video v,File file,String name){
    	String root = getRoot();
        String VideoName = FileUtil.getRandomName(name);//视频名称
        File VideoFile = v.getPath(root, VideoName);//视频完整路径
        try{
        	FileUtils.copyFile(file, VideoFile);
        	v.setSrc(v.getSrc(VideoName));
        }catch(IOException e){
        	//videoService.delete(video);
        	data.putErr("服务器异常：视频文件保存失败", MyConfig.ERR_IO);
    		e.printStackTrace();
        }
    }
    
    private void SaveImage(Video v,File file,String name){
    	String root = getRoot();
        String ImageName = FileUtil.getRandomName(name);//封面名称
        File ImageFile = v.getImagePath(root,ImageName);//封面完整路径
        try{
        	FileUtils.copyFile(file, ImageFile);
        	v.setImg(v.getImageSrc(ImageName));
        }catch(IOException e){
        	//videoService.delete(video);
        	data.putErr("服务器异常：封面文件保存失败", MyConfig.ERR_IO);
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