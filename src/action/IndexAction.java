package action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import bean.Category;
import bean.User;
import bean.Video;
import service.CategoryService;
import service.UserService;
import service.VideoService;
import util.FileUtil;

/**************************************
 * Index
 * 
 **************************************/
public class IndexAction extends ActionSupport{
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private VideoService videoService;
	
	//当前用户
	private User user;
	
	//分类
	private List<Category> categories;
	
	//最新视频（假装把这个当成推荐数据）
	private List<Video> videos;
	
	//分类视频（有少个分类就装多少个）
	private ArrayList<ArrayList<Video>> videos_;

	
	//跳转：主页
	public String index(){
		categories = categoryService.list();
		videos_ = new ArrayList<ArrayList<Video>>();
		for(Category category: categories){
			videos_.add((ArrayList<Video>)videoService.categoryList(0, 12, category.getId()));
		}
		videos = videoService.listNew(0, 12);
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		return "show"; 
	}

	
	
	
	
	/**************************************
	 * Setter&Getter
	 * 
	 **************************************/
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
	public ArrayList<ArrayList<Video>> getVideos_() {
		return videos_;
	}
	public void setVideos_(ArrayList<ArrayList<Video>> videos_) {
		this.videos_ = videos_;
	}
	public VideoService getVideoService() {
		return videoService;
	}
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
}