package action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import service.CategoryService;
import service.CommentService;
import service.UserService;
import service.VideoService;

@Controller("publicAjaxAction")
@ParentPackage("json-default")
@Scope("prototype")
public class PublicAjaxAction extends ActionSupport{

	@Autowired
	private UserService userService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;
	
	
	
	
	
	//参数
	private String type;
	private int page = 0;
	
	//返回值(JSON)
	private Map<String,Object> data = new HashMap<>();


	@Action(value="listapi",results={@Result(name="success",type="json",params={"root","data"})})
	public String list(){
		data.put("code", -1);
		System.out.println(type);
		
		if(type==null){
			data.put("code", MyConfig.ERR_PARAMS_MISS);
			String errmsg = "缺少参数type，type有效值：v,u,c";
			System.out.println(errmsg);
			data.put("errmsg", errmsg);
			return "success";
		}
		
		int count = 20;
		int start = page*count;		
		if(type.equals("v")){
			data.put("data", videoService.list(start,count));

		}else if(type.equals("u")){
			data.put("data", userService.list(start,count));

		}else if(type.equals("c")){
			data.put("data", commentService.list(start, count));

		}else if(type.equals("c")){
			data.put("data", categoryService.list());
		}
		else{
			data.put("code", MyConfig.ERR_OUT_RANGE);
			String errmsg = "type不在有效范围";
			data.put("errmsg", errmsg);
		}
		data.put("code", 0);
		return "success";
	}

	
	public CategoryService getCategoryService() {
		return categoryService;
	}


	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
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


	public VideoService getVideoService() {
		return videoService;
	}


	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}


	public CommentService getCommentService() {
		return commentService;
	}


	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}


	public Map<String, Object> getData() {
		return data;
	}


	public void setData(Map<String, Object> data) {
		this.data = data;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}

	
	


}
