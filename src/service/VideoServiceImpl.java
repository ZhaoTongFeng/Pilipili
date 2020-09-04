package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.Video;
import dao.VideoDAO;




@Service
public class VideoServiceImpl implements VideoService{
	@Autowired
	private VideoDAO videoDAO;


	@Override
	public Video get(Integer id) {
		
		return videoDAO.get(id);
	}
	@Override
	public void delete(Video video) {
		
		videoDAO.delete(video);
	}
	@Override
	public void update(Video video) {
		
		videoDAO.update(video);
	}
	public List<Video> list(int start, int count) {
		
		return videoDAO.list(start, count);
	}
	@Override
	public void add(Video video) {
		
		videoDAO.add(video);
	}
	@Override
	public Long count() {
		
		return videoDAO.count();
	}
	@Override
	public List<Video> categoryList(int start, int count, int category_id) {
		// TODO Auto-generated method stub
		return videoDAO.categoryList(start, count, category_id);
	}
	@Override
	public Long categoryCount(int category_id) {
		// TODO Auto-generated method stub
		return videoDAO.categoryCount(category_id);
	}
	@Override
	public List<Video> search(int start, int count, String content) {
		// TODO Auto-generated method stub
		return videoDAO.search(start, count, content);
	}
	@Override
	public Long countSearch(String content) {
		// TODO Auto-generated method stub
		return videoDAO.countSearch(content);
	}
	@Override
	public List<Video> searchWithCategory(int start, int count, Integer category_id, String content) {
		// TODO Auto-generated method stub
		return videoDAO.searchWithCategory(start, count, category_id, content);
	}
	@Override
	public Long countSearchWithCategory(Integer category_id, String content) {
		// TODO Auto-generated method stub
		return videoDAO.countSearchWithCategory(category_id, content);
	}
	
	@Override
	public List<Video> listUser(Integer start, Integer count, Integer user_id) {
		// TODO Auto-generated method stub
		return videoDAO.listUser(start, count, user_id);
	}
	@Override
	public Long countUser(Integer user_id) {
		// TODO Auto-generated method stub
		return videoDAO.countUser(user_id);
	}
	@Override
	public List<Video> markList(int start, int count, Integer[] ids) {
		// TODO Auto-generated method stub
		return videoDAO.markList(start, count, ids);
	}
	@Override
	public List<Video> auditList(int start, int count) {
		// TODO Auto-generated method stub
		return videoDAO.auditList(start, count);
	}
	@Override
	public Long auditCount() {
		// TODO Auto-generated method stub
		return videoDAO.auditCount();
	}
	@Override
	public List<Video> listNew(int start, int count) {
		// TODO Auto-generated method stub
		return videoDAO.listNew(start, count);
	}
	
	@Override
	public void marked(Video video, int user_id, boolean isMark) {
		if(isMark){
			video.setNum_mark(video.getNum_mark()-1);
			video.setPeople_mark(video.getPeople_mark().replace(","+String.valueOf(user_id), ""));
		}else{
			video.setNum_mark(video.getNum_mark()+1);
			video.setPeople_mark(video.getPeople_mark()+","+String.valueOf(user_id));
		}
	}
	
}
