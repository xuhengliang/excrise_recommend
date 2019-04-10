package exam.dao;

import exam.dao.base.BaseDao;
import exam.model.Clazz;

import java.util.List;

public interface ClazzDao extends BaseDao<Clazz> {

	List<Clazz> findClazzOnly(Clazz clazz);

}
