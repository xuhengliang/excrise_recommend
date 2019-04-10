package exam.controller.student;

import exam.model.Knowledge;
import exam.model.Question;
import exam.model.StudentKnowledge;
import exam.model.StudentQuestion;
import exam.model.role.Student;
import exam.service.KnowledgeService;
import exam.service.QuestionService;
import exam.service.StudentKnowledgeService;
import exam.service.StudentQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生个性化推荐试题模块
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.student.RecommendController")
@RequestMapping("/student/recommend")
public class RecommendController {

    @Resource
    private KnowledgeService knowledgeService;
    @Resource
    private QuestionService questionService;
    @Resource
    private StudentQuestionService  studentQuestionService;
    @Resource
    private StudentKnowledgeService studentKnowledgeService;

    /**
     * 更新失分信息
     * @param request
     */
    @RequestMapping("/auto-update")
    @ResponseBody
    public void autoUpdate(HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        StudentKnowledge studentKnowledge = null;
        // 试题信息
        List<StudentQuestion> studentQuestions = studentQuestionService.queryQuestionRecordByStudentId(student.getId());
        System.out.println(studentQuestions.size());

        if (CollectionUtils.isNotEmpty(studentQuestions)){
            for (StudentQuestion studentQuestion : studentQuestions) {
                if (studentQuestion != null) {
                    if (studentQuestion.getQid() > 0) {
                        // 进行失分率的计算
                        if (studentQuestion.getTotalCount() > 0) {
                            // 有历史记录
                            if (studentQuestion.getFailCount() > 0) {
                                studentKnowledge = new StudentKnowledge();
                                // 初始化失分率为100
                                studentKnowledge.setLosingScore(100);
                                // 查询知识点信息
                                List<Knowledge> knowledgeList = knowledgeService.findByQid(studentQuestion.getQid());
                                if (CollectionUtils.isNotEmpty(knowledgeList)) {
                                    for (Knowledge knowledge : knowledgeList) {
                                        studentKnowledge.setKid(knowledge.getId());
                                        studentKnowledge.setSid(student.getId());
                                        // 计算失分率
                                        studentKnowledge.setLosingScore(studentQuestion.getFailCount() /studentQuestion.getTotalCount() * 100);
                                        // 失分信息入库
                                        studentKnowledgeService.insertOrUpdateStudentKnowledge(studentKnowledge);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 个性化习题推荐
     * @param kid
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/auto-recommend")
    public String autoRecommend(Integer kid, HttpServletRequest request, HttpServletResponse response, Model model) {
        Question question = new Question();
        StudentKnowledge studentKnowledge = new StudentKnowledge();
        Student student = (Student) request.getSession().getAttribute("student");
        studentKnowledge.setSid(student.getId());
        List<StudentKnowledge> studentKnowledgeList = null;
        List<Question>  questions = new ArrayList<>();
        if (kid == null) {
            // 根据用户推荐
            studentKnowledgeList = studentKnowledgeService.findStudentKnowledge(studentKnowledge);
        }else {
            // 根据用户以及知识点进行推荐
            studentKnowledge.setKid(kid);
            studentKnowledgeList = studentKnowledgeService.findStudentKnowledge(studentKnowledge);
        }
        // 获得用户已经做过的习题信息
        List<StudentQuestion> studentQuestions = studentQuestionService.queryQuestionRecordByStudentId(student.getId());
        List<Integer> qidList = new ArrayList<>();
        List<Integer> kidList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(studentQuestions)){
            for (StudentQuestion studentQuestion : studentQuestions) {
                qidList.add(studentQuestion.getQid());
            }
            if (CollectionUtils.isNotEmpty(studentKnowledgeList)) {
                // 通过kid到关联习题信息
                List<Question>  kQuestions = new ArrayList<>();
                for (StudentKnowledge knowledge : studentKnowledgeList) {
                    kidList.add(knowledge.getKid());
                    kQuestions = questionService.findQuestionListByKid(kid);
                    for (Question kQuestion : kQuestions) {
                        if (!qidList.contains(kQuestion.getId())){
                            questions.add(kQuestion);
                        }
                    }
                }
                if (CollectionUtils.isEmpty(kQuestions)){
                    // 没有题目推荐 因为所有的知识点暂时还没有与题目关联
                    model.addAttribute("result", "0");
                    model.addAttribute("message", "暂时没有题目推荐 因为该知识点暂时还没有与题目关联");
                } else if (CollectionUtils.isEmpty(questions)) {
                    // 全部做过了 一样的全部返回
                    int random = (int)(Math.random() * questions.size());
                    question = questions.get(random);
                }
            } else if (kid != null){
                // 找不到用户对应知识点失分率计算的记录 说明用户对该还没有做过该知识点的题目
                // 随机返回一道该知识点的题目
                questions = questionService.findQuestionListByKid(kid);
                if (CollectionUtils.isEmpty(questions)){
                    // 没有题目推荐 因为该知识点暂时还没有与题目关联
                    model.addAttribute("result", "0");
                    model.addAttribute("message", "暂时没有题目推荐 因为该知识点暂时还没有与题目关联");
                } else {
                    int random = (int)(Math.random() * questions.size());
                    question = questions.get(random);
                }
            } else {
                System.out.println(question + ":" + "142");
                // 是通过用户查找失分记录 如果没有说明用户还没有做过题目
                questions = questionService.findAll();
                if (CollectionUtils.isEmpty(questions)){
                    model.addAttribute("result", "0");
                    model.addAttribute("message", "暂时没有题目推荐 因为目前试题库空空如也");
                } else {
                    int random = (int)(Math.random() * questions.size());
                    question = questions.get(random);
                }
            }
        } else {
            System.out.println(question + ":" + "156");
            // 用户没有做过习题   随机返回一道题目
            questions = questionService.findAll();
            if (CollectionUtils.isEmpty(questions)){
                model.addAttribute("result", "0");
                model.addAttribute("message", "暂时没有题目推荐 因为目前试题库空空如也");
            } else {
                int random = (int)(Math.random() * questions.size());
                question = questions.get(random);
            }
        }
        model.addAttribute("result", "1");

        model.addAttribute("question", question);
        return "student/recommend";
    }
}
