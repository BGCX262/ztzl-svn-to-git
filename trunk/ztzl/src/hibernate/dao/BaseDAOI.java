package hibernate.dao;

import hibernate.PageInfo;

import java.util.List;

public interface BaseDAOI {
	// 增加
	public void save(Object obj);

	// 删除
	public void delete(Object obj);

	// 修改
	public Object merge(Object obj);
	
	//更新
	public void update(Object obj);

	// 自动保存
	public void uporsave(Object obj);
	
	//批量保存
	@SuppressWarnings("unchecked")
	public boolean save(List list);
	//批量更新
	@SuppressWarnings("unchecked")
	public boolean saveorupdate(List list);

	// 根据主键取得唯一对象
	public Object findByID(String clazz, Long id);

	// 根据现有对象取得相同对象
	public List<Object> findByExample(Object obj);

	// 根据属性取得对象
	public List<Object> findByProperty(String clazz, String property, Object provalue);

	// 根据多属性组合取得对象
	public List<Object> findByComplex(String clazz, String[] property, Object[] value);

	// 取得所有对象
	public List<Object> findAll(String queryString);
	
	//取得数据分页
	public PageInfo findAll(String queryString, int pageNo, int pageCount);
	
	//取得数据记录数
	public int getCount(String queryString);
}
