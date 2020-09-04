package dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.Message;
import bean.Message;


@Repository("messageDAO")
public class MessageDAOImpl extends HibernateTemplate implements MessageDAO{
    @Resource(name="sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    };
    
    @Override
	public void add(Message message){
		save(message);
	}
	
    @Override
	public Message get(Integer id){
		return (Message)get(Message.class,id);
	}
	
    @Override
	public void delete(Message message){
		super.delete(message);
	}
    @Override
	public void update(Message message){
		super.update(message);
	}


	@Transactional
	public Long count() {
		String hql = "select count(*) from Message";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long)query.iterate().next();
	}
	
	@Transactional
	public List<Message> listUser(Integer start, Integer count, Integer user_id) {
		String hql = "from Message where message_user_to_id=? order by id desc ";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, user_id);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Message> list = (List<Message>)query.list();
		return list;
	}
	@Transactional
	public Long countUser(Integer user_id) {
		String hql = "select count(*) from Message where message_user_to_id=?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, user_id);
		return (Long)query.iterate().next();
	}
}
