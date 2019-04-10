package exam.service.base;

import exam.dao.base.BaseDao;
import exam.model.page.PageBean;

import java.util.HashMap;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	protected BaseDao<T> baseDao;
	
	protected abstract void setBaseDao(BaseDao<T> baseDao);
	
	public List<T> findAll() {
		throw new UnsupportedOperationException();
	}
	
	public void delete(Object id) {
		throw new UnsupportedOperationException();
	}
	
	public void saveOrUpdate(T entity) {
		throw new UnsupportedOperationException();
	}
	
	public List<T> find(T entity) {
		return baseDao.find(entity);
	}
	
	public PageBean<T> pageSearch(int pageCode, int pageSize, int pageNumber,
			String where, List<Object> params, HashMap<String, String> orderbys) {
		return baseDao.pageSearch(pageCode, pageSize, pageNumber, where, params, orderbys);
	}
	
}
