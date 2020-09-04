package dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import action.MyConfig;
import bean.Video;


@Repository("videoDAO")
public class VideoDAOImpl extends HibernateTemplate implements VideoDAO{
    @Resource(name="sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    };
	@Transactional
	public Long count() {
		String hql = "select count(*) from Video";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long)query.iterate().next();
	}
    @Override
	public void add(Video video){
		save(video);
	}
	
    @Override
	public Video get(Integer id){
		return (Video)get(Video.class,id);
	}
	
    @Override
	public void delete(Video video){
		super.delete(video);
	}
    @Override
	public void update(Video video){
		super.update(video);
	}

	@Transactional
	public List<Video> list(int start, int count) {
		String hql = "from Video order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Video> list = (List<Video>)query.list();
		return list;
	}
	@Transactional
	public List<Video> categoryList(int start, int count, int category_id) {
		String hql = "from Video where level=? and video_category_id=? order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		query.setInteger(0, MyConfig.LEVEL_PUBLIC);
		query.setInteger(1, category_id);
		List<Video> list = (List<Video>)query.list();
		return list;
	}
	
	@Transactional
	public Long categoryCount(int category_id) {
		String hql = "select count(*) from Video where level=? and video_category_id=?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, MyConfig.LEVEL_PUBLIC);
		query.setInteger(1, category_id);
		return (Long)query.iterate().next();
	}
	@Transactional
	public List<Video> search(int start, int count, String content) {
		String hql = "from Video where (title like ? or introduction like ?) and level=? order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, "%"+content+"%");
		query.setString(1, "%"+content+"%");
		query.setInteger(2, MyConfig.LEVEL_PUBLIC);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Video> list = (List<Video>)query.list();
		return list;
	}
	@Transactional
	public Long countSearch(String content) {
		String hql = "select count(*) from Video where (title like ? or introduction like ?) and level=?";

		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, "%"+content+"%");
		query.setString(1, "%"+content+"%");
		query.setInteger(2, MyConfig.LEVEL_PUBLIC);
		return (Long)query.iterate().next();
	}
	@Transactional
	public List<Video> searchWithCategory(int start, int count, Integer category_id, String content) {
		String hql = "from Video where video_category_id=? and (title like ? or introduction like ?) and level=? order by id desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, "%"+category_id+"%");
		query.setString(1, "%"+content+"%");
		query.setString(2, "%"+content+"%");
		query.setInteger(3, MyConfig.LEVEL_PUBLIC);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Video> list = (List<Video>)query.list();
		return list;
	}
	@Transactional
	public Long countSearchWithCategory(Integer category_id, String content) {
		String hql = "select count(*) from Video where video_category_id=? and (title like ? or introduction like ?) and level=?";
		System.out.println(hql);
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, "%"+category_id+"%");
		query.setString(1, "%"+content+"%");
		query.setString(2, "%"+content+"%");
		query.setInteger(3, MyConfig.LEVEL_PUBLIC);
		return (Long)query.iterate().next();
	}
	@Transactional
	public List<Video> listUser(Integer start, Integer count, Integer user_id) {
		String hql = "from Video where video_user_id=? order by id desc ";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, user_id);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Video> list = (List<Video>)query.list();
		return list;
	}
	@Transactional
	public Long countUser(Integer user_id) {
		String hql = "select count(*) from Video where video_user_id=?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, user_id);
		return (Long)query.iterate().next();
	}
	@Transactional
	public List<Video> markList(int start, int count, Integer[] ids) {
		String hql = "from Video where level=? and id in (:alist)";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		query.setInteger(0, MyConfig.LEVEL_PUBLIC);
		query.setParameterList("alist", ids);
		return (List<Video>)query.list();
	}
	
	@Transactional
	public List<Video> auditList(int start, int count) {
		String hql = "from Video where level=? order by id";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		query.setInteger(0, MyConfig.LEVEL_AUDIT);
		return (List<Video>)query.list();
	}
	
	@Transactional
	public Long auditCount() {
		String hql = "select count(*) from Video where level=?";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger(0, MyConfig.LEVEL_AUDIT);
		return (Long)query.iterate().next();
	}
	@Override
	public List<Video> listNew(int start, int count) {
		String hql = "from Video where level=? order by id desc";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		query.setInteger(0, MyConfig.LEVEL_PUBLIC);
		return (List<Video>)query.list();
	}
}
