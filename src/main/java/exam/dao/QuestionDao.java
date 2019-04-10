package exam.dao;

import exam.dao.base.BaseDao;
import exam.model.Question;

import java.util.List;

public interface QuestionDao extends BaseDao<Question> {
    List<Question> findAll();
    List<Question> findByEntity(Question entity);
}
