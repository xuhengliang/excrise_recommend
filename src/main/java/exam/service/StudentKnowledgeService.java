package exam.service;

import exam.model.StudentKnowledge;
import exam.service.base.BaseService;

import java.util.List;

/**
 * @Author 许恒亮
 * @Time 2019/4/7 17:36
 * @Version 1.0
 */
public interface StudentKnowledgeService  extends BaseService<StudentKnowledge> {
    void insertOrUpdateStudentKnowledge(StudentKnowledge studentKnowledge);
    List<StudentKnowledge> findStudentKnowledge(StudentKnowledge studentKnowledge);
}
