package exam.dao;

import exam.dao.base.BaseDao;
import exam.model.Exam;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;

import java.util.List;

public interface ExamDao extends BaseDao<Exam> {

	public List<Exam> execute(CallableStatementCreator creator, CallableStatementCallback<List<Exam>> callback);
	
}
