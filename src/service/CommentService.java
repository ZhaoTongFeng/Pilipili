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
	
	//��ҳ��ȡĳ���û�д������
	public List<Comment> listUser(Integer start,Integer count,Integer user_id);
	public Long countUser(Integer user_id);
	
	//VIDEO��������
	public Long countVideo(Integer video_id);
	
	//��ҳ��ȡȫ��
	public List<Comment> list(Integer start,Integer count);
	
	//��ҳ��ȡVideo�µ�һ������
	public List<Comment> videoList(Integer start,Integer count,Integer comment_video_id);
	
	//��ҳ��ȡcomment�Ķ�������
	public List<Comment> commentList(Integer start,Integer count,Integer comment_comment_id);
	

}
