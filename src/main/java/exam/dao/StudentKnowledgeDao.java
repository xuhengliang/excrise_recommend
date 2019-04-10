package exam.dao;

import exam.dao.base.BaseDao;
import exam.model.StudentKnowledge;

import java.util.List;

public interface StudentKnowledgeDao extends BaseDao<StudentKnowledge>  {

    List<StudentKnowledge> findByEntity(StudentKnowledge studentKnowledge);
}
