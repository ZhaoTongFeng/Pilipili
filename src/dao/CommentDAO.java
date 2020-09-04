package dao;

import java.util.List;

import bean.Comment;
import bean.User;

public interface CommentDAO{
	
	public void update(Comment comment);
	public void add(Comment comment);
	public Comment get(Integer id);
	public void delete(Comment comment);
	
	//ȫ��
	public Long count();
	public List<Comment> List(Integer start,Integer count);
	
	//ĳ���û�д������
	public Long countUser(Integer user_id);
	public List<Comment> listUser(Integer start,Integer count,Integer user_id);
	

	//VIDEO��������
	public Long countVideo(Integer video_id);
	//Video�µ�һ������
	public List<Comment> videoList(Integer start,Integer count,Integer comment_video_id);
	
	//comment�Ķ�������
	public List<Comment> commentList(Integer start,Integer count,Integer comment_comment_id);
}