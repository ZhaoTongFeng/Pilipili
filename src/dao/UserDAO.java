package dao;

import java.util.List;

import bean.User;

public interface UserDAO{
	
	public void add(User user);
	public User get(Integer id);
	public User getByEmail(String email);
	public void delete(User user);
	public void update(User user);
	
	//È«²¿
	public Long count();
	public List<User> list(int start,int count);

}