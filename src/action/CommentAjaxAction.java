package action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import bean.Comment;
import bean.Message;
import bean.User;
import bean.Video;
import service.CommentService;
import service.MessageService;
import service.VideoService;
import util.ErrMap;
import util.GoodUtil;

@Controller("commentAjaxAction")
@ParentPackage("json-default")
@Scope("prototype")
@Namespace("/comment")
public class CommentAjaxAction extends ActionSupport{
	@Autowired
	private CommentService commentService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private MessageService messageService;	
	
	private String content;
	private int video_id = 0;
	private int comment_id = 0;

	private int page=-1;
	private List<Comment> comments;
	
	private User user;
	
	private ErrMap data = new ErrMap();
	
	private int id;
	private Comment comment;
	
	//点赞评论
    @Action(value="goodComment",results={@Result(name="success",type="json",params={"root","data"})})
    public String goodComment() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckComment();
    	if((Integer) data.get("code")!=-1){
    		return "success";
    	}
		//检查当前用户是否已经点过赞
    	boolean isGood = GoodUtil.setGood(comment, user.getId());
		System.out.println(comment.getPeople_good());
		//保存，并返回当前点赞数
		messageService.add(new Message(String.format(MyConfig.MSG_GOOD_COMMENT, user.getName(),comment.getContent()),user,comment.getUser()));
		commentService.update(comment);
    	data.put("code", 0);
    	data.put("num_good", comment.getNum_good());
    	data.put("good", !isGood);
    	return "success";
    }

    
	//操作：插入评论
	@Action(value="addComment",results={@Result(name="success",type="json",params={"root","data"})})
	public String insert(){
		data.put("code", -1);
		CheckIsLogin();
    	if((Integer) data.get("code")!=-1){
    		return "success";
    	}
    	if(content.length()==0){
			data.put("code", MyConfig.ERR_CONDITION);
    		String errmsg = "评论不能为空";
    		data.put("errmsg", errmsg);
			return "success";
    	}
		//保存
    	
    	
		Comment newComment = new Comment(content,user);
		newComment.setTime_create(new Date());
		Video video = videoService.get(video_id);
		
		if(video==null){
			data.put("code", MyConfig.ERR_NULL);
    		String errmsg = "此VIDEO不存在";
    		data.put("errmsg", errmsg);
			return "success";
		}
		

		newComment.setVideo(video);
		

		if(comment_id!=0){
			comment = commentService.get(comment_id);
			newComment.setComment_comment_id(comment_id);
			comment.setNum_reply(comment.getNum_reply()+1);
			data.put("isReply", true);
			commentService.update(comment);
			data.put("comment", comment);
			messageService.add(new Message(String.format(MyConfig.MSG_REPLY, user.getName(),comment.getContent(),newComment.getContent()),user,comment.getUser()));
		}else{
			data.put("isReply", false);
			messageService.add(new Message(String.format(MyConfig.MSG_COMMENT, user.getName(),video.getTitle(),newComment.getContent()),user,video.getUser()));
		}
		commentService.add(newComment);
		
		
		video.setNum_comment(video.getNum_comment()+1);
		videoService.update(video);
		
		//返回
		
		data.put("code", 0);
		data.put("newComment", newComment);
		data.put("num_comment", video.getNum_comment());
		
		return "success";
	}
	
	
	//操作：获取更多评论
	@Action(value="getMoreComment",results={@Result(name="success",type="json",params={"root","data"})})
	public String getMoreComment() {
		data.put("code", -1);
		//效验
		if(page==-1||video_id==0){
			
			return "success";
		}
		//翻页获取评论
		int count = 10;
		int start = page*count;
		comments = commentService.videoList(start, count, video_id);
		//返回
		data.put("data", comments);
		data.put("code", 0);
		return "success";
	}
	
	//操作：获取评论的子评论
	@Action(value="getChildComment",results={@Result(name="success",type="json",params={"root","data"})})
	public String getChildComment() {
		data.put("code", -1);
		//效验
		if(page==-1||comment_id==0){
			return "success";
		}
		//翻页获取评论
		int count = 10;
		int start = page*count;
		comments = commentService.commentList(start, count, comment_id);
		comment = commentService.get(comment_id);
		data.put("comments", comments);//返回回复列表
		data.put("comment", comment);//返回评论
		data.put("code", 0);
		return "success";
	}
	
    private void CheckIsLogin(){
    	//检查是否登录
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		if(user==null){
    		data.put("code", MyConfig.ERR_NO_LOGIN);
    		String errmsg = "未登录";
    		data.put("errmsg", errmsg);
		}
    }
    
    private void CheckComment(){
    	comment = commentService.get(id);
    	if(comment==null){
			data.put("code", MyConfig.ERR_NULL);
			System.out.println(id);
    		String errmsg = "此评论不存在";
    		data.put("errmsg", errmsg);
    	}
    }
	
	
	
	
	
	/**************************************
	 * Setter&Getter
	 * 
	 **************************************/
	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public VideoService getVideoService() {
		return videoService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getVideo_id() {
		return video_id;
	}

	public void setVideo_id(int video_id) {
		this.video_id = video_id;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}




	public MessageService getMessageService() {
		return messageService;
	}


	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


	public ErrMap getData() {
		return data;
	}


	public void setData(ErrMap data) {
		this.data = data;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
