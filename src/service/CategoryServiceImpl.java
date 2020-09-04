package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import bean.Category;
import dao.CategoryDAO;


@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Override
	public Category get(Integer id) {
		
		return categoryDAO.get(id);
	}
	@Override
	public void delete(Category category) {
		
		categoryDAO.delete(category);
	}
	@Override
	public void update(Category category) {
		
		categoryDAO.update(category);
	}
	public List<Category> list() {
		return categoryDAO.list();
	}

	public void add(Category category) {
		
		categoryDAO.add(category);
	}
	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return categoryDAO.count();
	}

}
