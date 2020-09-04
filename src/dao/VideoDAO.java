package dao;

import java.util.List;

import bean.Video;

public interface VideoDAO{
	public Long count();
	public void add(Video video);
	public Video get(Integer id);
	public void delete(Video video);
	public void update(Video video);
	//全部
	public List<Video> list(int start,int count);
	
	//最新
	public List<Video> listNew(int start,int count);
	
	//某个用户的视频
	public List<Video> listUser(Integer start,Integer count,Integer user_id);
	public Long countUser(Integer user_id);
	
	//根据category来选择video，可以用来分类和播放页推荐
	public List<Video> categoryList(int start,int count,int category_id);
	public Long categoryCount(int category_id); 
	
	//全局搜索title或者introduction
	public List<Video> search(int start,int count,String content);
	public Long countSearch(String content); 
	
	//指定类型中搜索title或者introduction
	public List<Video> searchWithCategory(int start,int count,Integer category_id,String content);
	public Long countSearchWithCategory(Integer category_id,String content); 	
	
	//收藏夹
	public List<Video> markList(int start,int count,Integer[] ids);
	
	//审核
	public List<Video> auditList(int start,int count);	
	public Long auditCount();	
}