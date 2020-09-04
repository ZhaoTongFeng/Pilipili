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
	
	//��������
    @Action(value="goodComment",results={@Result(name="success",type="json",params={"root","data"})})
    public String goodComment() {
    	data.put("code", -1);
    	CheckIsLogin();
    	CheckComment();
    	if((Integer) data.get("code")!=-1){
    		return "success";
    	}
		//��鵱ǰ�û��Ƿ��Ѿ������
    	boolean isGood = GoodUtil.setGood(comment, user.getId());
		System.out.println(comment.getPeople_good());
		//���棬�����ص�ǰ������
		messageService.add(new Message(String.format(MyConfig.MSG_GOOD_COMMENT, user.getName(),comment.getContent()),user,comment.getUser()));
		commentService.update(comment);
    	data.put("code", 0);
    	data.put("num_good", comment.getNum_good());
    	data.put("good", !isGood);
    	return "success";
    }

    
	//��������������
	@Action(value="addComment",results={@Result(name="success",type="json",params={"root","data"})})
	public String insert(){
		data.put("code", -1);
		CheckIsLogin();
    	if((Integer) data.get("code")!=-1){
    		return "success";
    	}
    	if(content.length()==0){
			data.put("code", MyConfig.ERR_CONDITION);
    		String errmsg = "���۲���Ϊ��";
    		data.put("errmsg", errmsg);
			return "success";
    	}
		//����
    	
    	
		Comment newComment = new Comment(content,user);
		newComment.setTime_create(new Date());
		Video video = videoService.get(video_id);
		
		if(video==null){
			data.put("code", MyConfig.ERR_NULL);
    		String errmsg = "��VIDEO������";
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
		
		//����
		
		data.put("code", 0);
		data.put("newComment", newComment);
		data.put("num_comment", video.getNum_comment());
		
		return "success";
	}
	
	
	//��������ȡ��������
	@Action(value="getMoreComment",results={@Result(name="success",type="json",params={"root","data"})})
	public String getMoreComment() {
		data.put("code", -1);
		//Ч��
		if(page==-1||video_id==0){
			
			return "success";
		}
		//��ҳ��ȡ����
		int count = 10;
		int start = page*count;
		comments = commentService.videoList(start, count, video_id);
		//����
		data.put("data", comments);
		data.put("code", 0);
		return "success";
	}
	
	//��������ȡ���۵�������
	@Action(value="getChildComment",results={@Result(name="success",type="json",params={"root","data"})})
	public String getChildComment() {
		data.put("code", -1);
		//Ч��
		if(page==-1||comment_id==0){
			return "success";
		}
		//��ҳ��ȡ����
		int count = 10;
		int start = page*count;
		comments = commentService.commentList(start, count, comment_id);
		comment = commentService.get(comment_id);
		data.put("comments", comments);//���ػظ��б�
		data.put("comment", comment);//��������
		data.put("code", 0);
		return "success";
	}
	
    private void CheckIsLogin(){
    	//����Ƿ��¼
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		if(user==null){
    		data.put("code", MyConfig.ERR_NO_LOGIN);
    		String errmsg = "δ��¼";
    		data.put("errmsg", errmsg);
		}
    }
    
    private void CheckComment(){
    	comment = commentService.get(id);
    	if(comment==null){
			data.put("code", MyConfig.ERR_NULL);
			System.out.println(id);
    		String errmsg = "�����۲�����";
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
