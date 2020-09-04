package dao;

import java.util.List;

import bean.Message;

public interface MessageDAO{
	public Long count();
	public void add(Message message);
	public Message get(Integer id);
	public void delete(Message message);
	public void update(Message message);
	
	//取出自己受到的Message
	public List<Message> listUser(Integer start,Integer count,Integer user_id);
	public Long countUser(Integer user_id);
}