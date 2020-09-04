package service;

import java.util.List;

import bean.User;

public interface UserService {
	public Long count();
	public void add(User user);
	public User get(Integer id);
	public User getByEmail(String email);
	public void delete(User user);
	public void update(User user);
	public List<User> list(int start,int count);
	
	public boolean CheckMarkVideo(User user,int video_id);
	public boolean MarkVideo(User user,int video_id);

}
