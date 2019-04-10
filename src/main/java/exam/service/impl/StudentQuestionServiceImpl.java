package exam.service.impl;

import exam.dao.StudentQuestionDao;
import exam.dao.base.BaseDao;
import exam.model.StudentQuestion;
import exam.service.StudentQuestionService;
import exam.service.base.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
@Service("studentQuestionService")
public class StudentQuestionServiceImpl extends BaseServiceImpl<StudentQuestion> implements StudentQuestionService {
    private StudentQuestionDao studentQuestionDao;

    @Resource(name = "studentQuestionDao")
    @Override
    protected void setBaseDao(BaseDao<StudentQuestion> baseDao) {
        super.baseDao = baseDao;
        this.studentQuestionDao = (StudentQuestionDao) baseDao;
    }


    @Override
    public void insertOrUpdateQuestionRecord(StudentQuestion studentQuestion) {
        if (studentQuestion != null && studentQuestion.getId() > 0) {
            List<StudentQuestion> questions = studentQuestionDao.queryBySQL("select * from student_question where qid = " + studentQuestion.getQid() + " and sid = " + studentQuestion.getSid());
            if (CollectionUtils.isNotEmpty(questions)) {
                studentQuestionDao.executeSql("update student_question set total_count = ?,fail_count = ? where qid = ? and sid = ?",
                        new Object[] {studentQuestion.getTotalCount(), studentQuestion.getFailCount(), studentQuestion.getQid(), studentQuestion.getSid()});
            }
        }else {
            studentQuestionDao.executeSql("insert into student_question(total_count,fail_count,qid,sid) values(?,?,?,?)",
                    new Object[] {studentQuestion.getTotalCount(), studentQuestion.getFailCount(), studentQuestion.getQid(), studentQuestion.getSid()});
        }
    }
    @Override
    public StudentQuestion queryQuestionRecord(int id, String studentId) {
        StudentQuestion studentQuestion = new StudentQuestion();
        studentQuestion.setQid(id);
        studentQuestion.setSid(studentId);
        List<StudentQuestion> studentQuestions = studentQuestionDao.find(studentQuestion);
        return CollectionUtils.isEmpty(studentQuestions) ? null : studentQuestions.get(0);
    }

    @Override
    public List<StudentQuestion> queryQuestionRecordByStudentId(String studentId) {
        if (StringUtils.isEmpty(studentId)) {
            return null;
        }
        StudentQuestion studentQuestion = new StudentQuestion();
        studentQuestion.setSid(studentId);
        List<StudentQuestion> studentQuestions = studentQuestionDao.find(studentQuestion);
        return studentQuestions;
    }
}
