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
	
	
	
	
    //��ת���ϴ�ҳ��
	public String UploadVideo(){
		setUserBySession();
		if(user!=null){
			categories = categoryService.list();
			return "succ"; 
		}else{
			return "sign_in";
		}
	}
	//��ת�����ҳ��
	public String audit(){		
		setUserBySession();
		if(user==null){		
			return "sign_in";
		}else{
			if(user.getLevel()!=MyConfig.LEVEL_ADMIN){
				return "index";
			}
		}
	
		//����˵���Ƶ
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
	
	
	//��ת����ʾҳ��
	public String Video(){		
		setUserBySession();
		//�Ƽ���Ƶ
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
					//����¼ֻ�ܿ���������ͨ��Ƶ	
				}
			}else{
				return "sign_in";
			}
		}else{
			if(user.getLevel()==MyConfig.LEVEL_ADMIN){
				//����Ա���Բ鿴����video�������û�˽�е�
				
			}else{
				if((int)video.getUser().getId()==(int)user.getId()){
					//�Լ�����Ƶ���ü�⹫��Ȩ��
				}else{
					if(video.getLevel()==MyConfig.LEVEL_PUBLIC){
						if(video.getIsVip()){

							if(user.isVIP()){
								//��ǰ�û���VIP���Կ����˵Ĺ�����VIP��Ƶ
							}else{
								return "vip";
							}
						}else{
							//��½�˺�����һ������������ͨ��Ƶ
						}
					}else{
						return "sign_in";
					}
				}
			}
		}

		
		//���ӹۿ�����
		video.setNum_view(video.getNum_view()+1);
		videoService.update(video);

		return "succ"; 
	}
	
	//��ת���޸�ҳ��
	public String updateVideo(){
		setUserBySession();
		if(user==null){
			System.out.println("û��¼");
			return "sign_in";
		}
		if(id==0){
			System.out.println("id=0");
			return "sign_in";
		}
		categories = categoryService.list();
		Video dbvideo = videoService.get(id);
//		if(dbvideo.getUser().getId()!=user.getId()){
//			System.out.println("�����Լ���");
//			System.out.println(dbvideo.getUser().getId());
//			System.out.println(user.getId());
//			return "sign_in";
//		}
		video = dbvideo;
		return "succ";
	}
	
	//��֤�Ƿ��¼
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