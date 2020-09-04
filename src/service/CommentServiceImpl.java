package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.Comment;
import dao.CommentDAO;




@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDAO commentDAO;

	@Override
	public Comment get(Integer id) {
		// TODO Auto-generated method stub
		return commentDAO.get(id);
	}

	@Override
	public void delete(Comment comment) {
		
		commentDAO.delete(comment);
	}

	@Override
	public void add(Comment comment) {
		
		commentDAO.add(comment);
	}

	@Override
	public List<Comment> videoList(Integer start, Integer count, Integer comment_video_id) {
		// TODO Auto-generated method stub
		return commentDAO.videoList(start, count, comment_video_id);
	}

	@Override
	public List<Comment> commentList(Integer start, Integer count, Integer comment_comment_id) {
		// TODO Auto-generated method stub
		return commentDAO.commentList(start, count, comment_comment_id);
	}



	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return commentDAO.count();
	}

	@Override
	public List<Comment> listUser(Integer start, Integer count, Integer user_id) {
		// TODO Auto-generated method stub
		return commentDAO.listUser(start, count, user_id);
	}
	
	@Override
	public Long countUser(Integer user_id) {
		// TODO Auto-generated method stub
		return commentDAO.countUser(user_id);
	}

	@Override
	public List<Comment> list(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return commentDAO.List(start, count);
	}

	@Override
	public void update(Comment comment) {
		// TODO Auto-generated method stub
		commentDAO.update(comment);
	}

	@Override
	public Long countVideo(Integer video_id) {
		// TODO Auto-generated method stub
		return commentDAO.countVideo(video_id);
	}


	
}
