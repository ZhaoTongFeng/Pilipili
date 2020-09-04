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

import bean.Category;


@Repository("categoryDAO")
public class CategoryDAOImpl extends HibernateTemplate implements CategoryDAO{
    @Resource(name="sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    };
    
    @Override
	public void add(Category user){
		save(user);
	}
	
    @Override
	public Category get(Integer id){
		return (Category)get(Category.class,id);
	}
	
    @Override
	public void delete(Category user){
		super.delete(user);
	}
    @Override
	public void update(Category user){
		super.update(user);
	}

	@Transactional
	public List<Category> list() {
		return find("from Category");
	}

	@Transactional
	public Long count() {
		String hql = "select count(*) from Category";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long)query.iterate().next();
	}
}
