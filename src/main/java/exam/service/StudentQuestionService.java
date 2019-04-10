package exam.service;

import exam.model.StudentQuestion;
import exam.service.base.BaseService;

import java.util.List;

/**
 * @Author 许恒亮
 * @Time 2019/4/7 17:27
 * @Version 1.0
 */
public interface StudentQuestionService extends BaseService<StudentQuestion> {

    StudentQuestion queryQuestionRecord(int id, String studentId);
    // 获得用户已经做过的习题信息
    List<StudentQuestion> queryQuestionRecordByStudentId(String studentId);

    void insertOrUpdateQuestionRecord(StudentQuestion studentQuestion);

}
