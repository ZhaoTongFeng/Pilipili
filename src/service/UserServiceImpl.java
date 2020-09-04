package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import bean.User;
import dao.UserDAO;




@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;


	@Override
	public User get(Integer id) {
		
		return userDAO.get(id);
	}


	@Override
	public void delete(User user) {
		
		userDAO.delete(user);
	}


	@Override
	public void update(User user) {
		
		userDAO.update(user);
	}

	

	
	

	//@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public List<User> list(int start, int count) {
		return userDAO.list(start, count);
	}
	
	@Override
	public void add(User user) {
		
		userDAO.add(user);
	}


	@Override
	public User getByEmail(String email) {
		
		return userDAO.getByEmail(email);
	}


	@Override
	public Long count() {
		
		return userDAO.count();
	}


	@Override
	public boolean CheckMarkVideo(User user, int video_id) {
		//检查当前用户是否已经收藏此视频
    	String[] videoIds = user.getVideo_mark().split(",");
    	boolean isMark = false;
		for(int i=0;i<videoIds.length;i++){
			if((int)Integer.valueOf(videoIds[i])== (int)video_id){
				isMark = true;
				break;
			}
		}
		return isMark;
	}


	@Override
	public boolean MarkVideo(User user, int video_id) {
		boolean isMark = this.CheckMarkVideo(user, video_id);
		if(isMark){

			user.setNum_mark(user.getNum_mark()-1);
			user.setVideo_mark(user.getVideo_mark().replace(","+String.valueOf(video_id), ""));
		}else{

			user.setNum_mark(user.getNum_mark()+1);
			user.setVideo_mark(user.getVideo_mark()+","+String.valueOf(video_id));
		}
		return isMark;
	}
	
}
