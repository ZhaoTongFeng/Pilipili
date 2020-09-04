package dao;

import java.util.List;

import bean.Category;

public interface CategoryDAO{
	public Long count();
	public void add(Category category);
	public Category get(Integer id);
	public void delete(Category category);
	public void update(Category category);
	
	//È«²¿
	public List<Category> list();
}