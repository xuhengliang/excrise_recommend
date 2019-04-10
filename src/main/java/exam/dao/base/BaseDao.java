package exam.dao.base;

import exam.model.page.PageBean;
import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;
import java.util.List;

public interface BaseDao<T> {

	void executeSql(String sql, Object[] params);
	
	List<T> find(T entity);
	
	void executeSql(String sql);
	
	List<T> queryBySQL(String sql);
	
	List<T> queryBySQL(String sql, Object...params);
	
	Object queryForObject(String sql, Class<?> clazz);
	
	public int[] batchUpdate(String...sqls);

    int getKeyHelper(final String sql, final GenerateKeyCallback callback, final Object param);

	String getCountSql();

	String getSql();

	RowMapper<T> getRowMapper();
	
	PageBean<T> pageSearch(int pageCode, int pageSize, int pageNumber, String where,
			List<Object> params, HashMap<String, String> orderbys);

}
