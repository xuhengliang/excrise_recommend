package exam.dao;

import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import exam.dao.base.BaseDao;
import exam.model.ExaminationResult;

public interface ExaminationResultDao extends BaseDao<ExaminationResult> {


	public <T> T query(String sql, ResultSetExtractor<T> resultSetExtractor);

	public <T> List<T> query(String sql, RowMapper<T> rowMapper);
	
}
