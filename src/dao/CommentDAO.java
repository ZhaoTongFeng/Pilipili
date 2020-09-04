package dao;

import java.util.List;

import bean.Comment;
import bean.User;

public interface CommentDAO{
	
	public void update(Comment comment);
	public void add(Comment comment);
	public Comment get(Integer id);
	public void delete(Comment comment);
	
	//全部
	public Long count();
	public List<Comment> List(Integer start,Integer count);
	
	//某个用户写的评论
	public Long countUser(Integer user_id);
	public List<Comment> listUser(Integer start,Integer count,Integer user_id);
	

	//VIDEO的评论数
	public Long countVideo(Integer video_id);
	//Video下的一级评论
	public List<Comment> videoList(Integer start,Integer count,Integer comment_video_id);
	
	//comment的二级评论
	public List<Comment> commentList(Integer start,Integer count,Integer comment_comment_id);
}