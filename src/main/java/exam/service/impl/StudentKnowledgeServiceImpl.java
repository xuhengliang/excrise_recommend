package exam.service.impl;

import exam.dao.StudentKnowledgeDao;
import exam.dao.base.BaseDao;
import exam.model.StudentKnowledge;
import exam.service.StudentKnowledgeService;
import exam.service.base.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
@Service("studentKnowledgeService")
public class StudentKnowledgeServiceImpl extends BaseServiceImpl<StudentKnowledge> implements StudentKnowledgeService {
    private StudentKnowledgeDao studentKnowledgeDao;

    @Resource(name = "studentKnowledgeDao")
    @Override
    protected void setBaseDao(BaseDao<StudentKnowledge> baseDao) {
        super.baseDao = baseDao;
        this.studentKnowledgeDao = (StudentKnowledgeDao) baseDao;
    }
    @Override
    public void insertOrUpdateStudentKnowledge(StudentKnowledge studentKnowledge) {
        String sql = studentKnowledgeDao.getSql() + " where kid = ? and sid = ?";
        List<StudentKnowledge> studentKnowledges = studentKnowledgeDao.queryBySQL(sql,new Object[]{studentKnowledge.getKid(),studentKnowledge.getSid()});
        String updateInsertSql = "";
        if (CollectionUtils.isNotEmpty(studentKnowledges) && studentKnowledges.size() == 1) {
            //update
            updateInsertSql = "update set student_knowledge losing_score = ? where kid = ? and sid = ?";
        }else {
            //insert
            updateInsertSql = "insert into student_knowledge(losing_score,kid,sid) values(?,?,?)";
        }
        studentKnowledgeDao.executeSql(updateInsertSql, new Object[] {studentKnowledge.getLosingScore(),studentKnowledge.getKid(),studentKnowledge.getSid()});
    }

    @Override
    public List<StudentKnowledge> findStudentKnowledge(StudentKnowledge studentKnowledge) {
        return studentKnowledgeDao.findByEntity(studentKnowledge);
    }

}
