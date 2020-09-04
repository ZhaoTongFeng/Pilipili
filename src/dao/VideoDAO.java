package dao;

import java.util.List;

import bean.Video;

public interface VideoDAO{
	public Long count();
	public void add(Video video);
	public Video get(Integer id);
	public void delete(Video video);
	public void update(Video video);
	//ȫ��
	public List<Video> list(int start,int count);
	
	//����
	public List<Video> listNew(int start,int count);
	
	//ĳ���û�����Ƶ
	public List<Video> listUser(Integer start,Integer count,Integer user_id);
	public Long countUser(Integer user_id);
	
	//����category��ѡ��video��������������Ͳ���ҳ�Ƽ�
	public List<Video> categoryList(int start,int count,int category_id);
	public Long categoryCount(int category_id); 
	
	//ȫ������title����introduction
	public List<Video> search(int start,int count,String content);
	public Long countSearch(String content); 
	
	//ָ������������title����introduction
	public List<Video> searchWithCategory(int start,int count,Integer category_id,String content);
	public Long countSearchWithCategory(Integer category_id,String content); 	
	
	//�ղؼ�
	public List<Video> markList(int start,int count,Integer[] ids);
	
	//���
	public List<Video> auditList(int start,int count);	
	public Long auditCount();	
}