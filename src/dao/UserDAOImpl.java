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

import bean.User;


@Repository("userDAO")
public class UserDAOImpl extends HibernateTemplate implements UserDAO{
    @Resource(name="sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    };
    
    @Override
	public void add(User user){
		//System.out.println(save(user));
    	save(user);
	}
	
    @Override
	public User get(Integer id){
		return (User)get(User.class,id);
	}
	
    @Override
	public void delete(User user){
		super.delete(user);
	}
    @Override
	public void update(User user){
		super.update(user);
	}
	@Override
	public User getByEmail(String email) {
		List<User> users = find("from User where email='"+email+"'");
		System.out.println(users);
		if(users.size()==0){
			return null;
		}else{
			return users.get(0);
		}
	}
	@Transactional
	public List<User> list(int start, int count) {
		String hql = "from User order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<User> list = (List<User>)query.list();
		return list;
	}

	@Transactional
	public Long count() {
		
		String hql = "select count(*) from User";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long)query.iterate().next();
	}
	
	
}
