package service;

import java.util.List;

import bean.Category;


public interface CategoryService {
	public Long count();
	public void add(Category category);
	public Category get(Integer id);
	public void delete(Category category);
	public void update(Category category);
	public List<Category> list();

}
