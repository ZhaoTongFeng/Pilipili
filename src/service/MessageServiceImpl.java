package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import bean.Message;
import dao.MessageDAO;
import dao.MessageDAO;


@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	private MessageDAO messageDAO;
	
	@Override
	public Message get(Integer id) {
		
		return messageDAO.get(id);
	}
	@Override
	public void delete(Message message) {
		
		messageDAO.delete(message);
	}
	@Override
	public void update(Message message) {
		
		messageDAO.update(message);
	}

	public void add(Message message) {
		if(message.getFromUser()==null){
			
		}else{
			if((int)message.getFromUser().getId()==(int)message.getToUser().getId()){
				return ;
			}
		}
		messageDAO.add(message);
	}
	
	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return messageDAO.count();
	}
	
	@Override
	public List<Message> listUser(Integer start, Integer count, Integer user_id) {
		// TODO Auto-generated method stub
		return messageDAO.listUser(start, count, user_id);
	}
	
	@Override
	public Long countUser(Integer user_id) {
		// TODO Auto-generated method stub
		return messageDAO.countUser(user_id);
	}
}
