package service;

import java.util.List;

import bean.Category;
import bean.Comment;


public interface CommentService {
	public Long count();
	public void update(Comment comment);
	public void add(Comment comment);
	public Comment get(Integer id);
	public void delete(Comment comment);
	
	//翻页获取某个用户写的评论
	public List<Comment> listUser(Integer start,Integer count,Integer user_id);
	public Long countUser(Integer user_id);
	
	//VIDEO的评论数
	public Long countVideo(Integer video_id);
	
	//翻页获取全部
	public List<Comment> list(Integer start,Integer count);
	
	//翻页获取Video下的一级评论
	public List<Comment> videoList(Integer start,Integer count,Integer comment_video_id);
	
	//翻页获取comment的二级评论
	public List<Comment> commentList(Integer start,Integer count,Integer comment_comment_id);
	

}
