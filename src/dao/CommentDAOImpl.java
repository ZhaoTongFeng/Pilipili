package dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bean.User;
import bean.Comment;
import bean.Comment;


@Repository("commentDAO")
public class CommentDAOImpl extends HibernateTemplate implements CommentDAO{
    @Resource(name="sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    };
    
    @Override
	public void add(Comment comment){
		save(comment);
	}
	@Override
	public Comment get(Integer id) {
		// TODO Auto-generated method stub
		return (Comment)get(Comment.class,id);
	}

	
    @Override
	public void delete(Comment comment){
		super.delete(comment);
	}
	@Override
	public void update(Comment comment) {
		super.update(comment);
	}



    @Transactional
	public List<Comment> videoList(Integer start, Integer count, Integer comment_video_id) {
		String hql = "from Comment where comment_video_id=? and comment_comment_id=0 order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		query.setInteger(0, comment_video_id);
		
		List<Comment> list = (List<Comment>)query.list();
		return list;
	}

	@Transactional
	public List<Comment> commentList(Integer start, Integer count, Integer comment_comment_id) {
		String hql = "from Comment where comment_comment_id=? order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		query.setInteger(0, comment_comment_id);
		List<Comment> list = (List<Comment>)query.list();
		return list;
	}

	@Transactional
	public List<Comment> listUser(Integer start, Integer count, Integer user_id) {
		String hql = "from Comment where comment_user_id=" + String.valueOf(user_id) + " order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Comment> list = (List<Comment>)query.list();
		return list;
	}
	
	@Transactional
	public Long countUser(Integer user_id) {
		String hql = "select count(*) from Comment where comment_user_id=" + String.valueOf(user_id);
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long)query.iterate().next();
	}
	
	@Transactional
	public List<Comment> List(Integer start, Integer count) {
		String hql = "from Comment order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Comment> list = (List<Comment>)query.list();
		return list;
	}

	@Transactional
	public Long count() {
		String hql = "select count(*) from Comment";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long)query.iterate().next();
	}

	@Transactional
	public Long countVideo(Integer video_id) {
		String hql = "select count(*) from Comment where comment_video_id=?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, video_id);
		return (Long)query.iterate().next();
	}


}
