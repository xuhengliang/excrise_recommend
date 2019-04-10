package exam.dao;

import exam.dao.base.BaseDao;
import exam.model.role.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface TeacherDao extends BaseDao<Teacher> {

	public <T> List<T> query(String sql, RowMapper<T> rowMapper);
	
}
