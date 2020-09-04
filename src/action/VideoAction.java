package action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bean.*;
import service.CategoryService;
import service.CommentService;
import service.VideoService;
import util.FileUtil;
 

@Controller("videoAction")
@Namespace("/")
@ParentPackage("struts-default")
public class VideoAction {
	@Autowired
	private VideoService videoService;
	@Autowired
	private CategoryService categoryService;
	
	
	
	private int id = 0;
	private Video video;
	private User user;
	
	private List<Video> videos;
	private List<Category> categories;
	
	
	
	
    //跳转：上传页面
	public String UploadVideo(){
		setUserBySession();
		if(user!=null){
			categories = categoryService.list();
			return "succ"; 
		}else{
			return "sign_in";
		}
	}
	//跳转：审核页面
	public String audit(){		
		setUserBySession();
		if(user==null){		
			return "sign_in";
		}else{
			if(user.getLevel()!=MyConfig.LEVEL_ADMIN){
				return "index";
			}
		}
	
		//待审核的视频
		videos = videoService.auditList(0, 10);
		
		if(id==0){
			
			if(videos.size()==0){
				return "list";
			}else{
				video = videos.get(0);
			}
		}else{
			video = videoService.get(id);
		}
		return "succ"; 
	}
	
	
	//跳转：显示页面
	public String Video(){		
		setUserBySession();
		//推荐视频
		video = videoService.get(id);
		if(video==null){
			return "defend";
		}
		videos = videoService.categoryList(0, 10, video.getCategory().getId());		
		if(id==0){
			if(videos.size()!=0){
				video=videos.get(0);
			}
		}else{
			
		}
		if(user==null){			
			if(video.getLevel()==MyConfig.LEVEL_PUBLIC){
				if(video.getIsVip()){
					return "sign_in";
				}else{
					//不登录只能看公开的普通视频	
				}
			}else{
				return "sign_in";
			}
		}else{
			if(user.getLevel()==MyConfig.LEVEL_ADMIN){
				//管理员可以查看任意video，包括用户私有的
				
			}else{
				if((int)video.getUser().getId()==(int)user.getId()){
					//自己的视频不用检测公开权限
				}else{
					if(video.getLevel()==MyConfig.LEVEL_PUBLIC){
						if(video.getIsVip()){

							if(user.isVIP()){
								//当前用户是VIP可以看别人的公开的VIP视频
							}else{
								return "vip";
							}
						}else{
							//登陆了和上面一样看公开的普通视频
						}
					}else{
						return "sign_in";
					}
				}
			}
		}

		
		//增加观看次数
		video.setNum_view(video.getNum_view()+1);
		videoService.update(video);

		return "succ"; 
	}
	
	//跳转：修改页面
	public String updateVideo(){
		setUserBySession();
		if(user==null){
			System.out.println("没登录");
			return "sign_in";
		}
		if(id==0){
			System.out.println("id=0");
			return "sign_in";
		}
		categories = categoryService.list();
		Video dbvideo = videoService.get(id);
//		if(dbvideo.getUser().getId()!=user.getId()){
//			System.out.println("不是自己的");
//			System.out.println(dbvideo.getUser().getId());
//			System.out.println(user.getId());
//			return "sign_in";
//		}
		video = dbvideo;
		return "succ";
	}
	
	//验证是否登录
	public void setUserBySession(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User)session.getAttribute("user");
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

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}