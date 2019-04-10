package exam.controller.student;

import exam.dto.ExaminationAnswer;
import exam.dto.MarkedQuestion;
import exam.model.*;
import exam.model.page.PageBean;
import exam.model.role.Student;
import exam.service.ExamService;
import exam.service.ExaminationResultService;
import exam.service.QuestionService;
import exam.service.StudentQuestionService;
import exam.util.DataUtil;
import exam.util.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学生参加考试操作模块
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.student.ExamController")
@RequestMapping("/student/exam")
public class ExamController {
	
	@Resource
	private ExamService examService;
	@Resource
	private ExaminationResultService examinationResultService;
	@Resource
	private QuestionService questionService;
	@Autowired
	private StudentQuestionService studentQuestionService;

	@Value("#{properties['student.exam.pageSize']}")
	private int pageSize;
	@Value("#{properties['student.exam.pageNumber']}")
	private int pageNumber;

	/**
	 * 试卷列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String listHelper(Model model, HttpServletRequest request) {
		return list("1", model, request);
	}

	/**
	 * 个性化推荐的习题只提交一道题目
	 * @param id
	 * @param anwser
	 * @param request
	 * @return
	 */
	@RequestMapping("/submit-one")
	public String submitOne(int id,String anwser,HttpServletRequest request) {

		Question condition = new Question();
		condition.setId(id);
		List<Question> questions = questionService.findByEntity(condition);
		Question question = new Question();
		if(CollectionUtils.isNotEmpty(questions)){
			question = questions.get(0);
		}
		int type = question.getType().ordinal();
		/**
		 *
		 SINGLE,
		 MULTI,
		 JUDGE
		 */

		Student student = (Student) request.getSession().getAttribute("student");
		StudentQuestion studentQuestion = studentQuestionService.queryQuestionRecord(id, student.getId());
		if (studentQuestion == null) {
			studentQuestion = new StudentQuestion();
			studentQuestion.setQid(id);
			studentQuestion.setSid(student.getId());
		}
		studentQuestion.setTotalCount(studentQuestion.getTotalCount() + 1);
		System.out.println(question.getAnswer() + ":" );

		switch (type){
			case 0 :
				// SINGLE
				if (question.getAnswer().equals(anwser)) {
					//正确
				}else {
					//错误
					studentQuestion.setFailCount(studentQuestion.getFailCount() + 1);
				}
				break;
			case 1 :
				// MULTI
				if (question.getAnswer().equals(anwser)) {
					//正确
				}else {
					//错误
					studentQuestion.setFailCount(studentQuestion.getFailCount() + 1);
				}
				break;
			case 2 :
				// JUDGE
				if (question.getAnswer().equals(anwser)) {
					//正确
				}else {
					//错误
					studentQuestion.setFailCount(studentQuestion.getFailCount() + 1);
				}
				break;
			default:
				// 类型不支持
				return "error";
		}
		// 记录
		studentQuestionService.insertOrUpdateQuestionRecord(studentQuestion);
		// 继续推荐下一题
		return "student/recommend/auto-recommend";
	}

	/**
	 * 试卷列表
	 * @param pn
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list/{pn}")
	public String list(@PathVariable String pn, Model model, HttpServletRequest request) {
		Student student = (Student) request.getSession().getAttribute("student");
		int pageCode = DataUtil.getPageCode(pn);
		PageBean<Exam> pageBean = examService.pageSearchByStudent(pageCode, pageSize, pageNumber, student.getId());
		model.addAttribute("pageBean", pageBean);
		return "student/exam_list";
	}

	/**
	 * 考试
	 * @param eid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/{eid}")
	public String take(@PathVariable Integer eid, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sid = ((Student) session.getAttribute("student")).getId();
		if (examService.hasJoined(eid, sid)) {
			model.addAttribute("message", "您已参加过此次考试");
			return "error";
		}
		Exam exam = new Exam();
		exam.setId(eid);
		Exam result = examService.findWithQuestions(exam);
		if (result == null) {
			return "error";
		}
		if (result.getStatus() == ExamStatus.RUNNED) {
			model.addAttribute("message", "很抱歉，此考试已关闭");
			return "error";
		}
		model.addAttribute("exam", result);
		model.addAttribute("eid", eid);
		//把题目缓存进入session，这样可以避免批卷时再次访问数据库
		session.setAttribute("exam", result);
		return "student/exam_take";
	}

	/**
	 * 提交考试信息
	 * @param result
	 * @param request
	 * @param response
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public void submit(String result, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (!DataUtil.isValid(result)) {
			json.addElement("result", "0").addElement("message", "交卷失败，参数非法");
		} else {
			HttpSession session = request.getSession();
			ExaminationAnswer ea = DataUtil.parseAnswers(result);
			if (!examService.isUseful(ea.getExamId())) {
				json.addElement("result", "0").addElement("message", "抱歉，此试题已停止运行或被删除");
			} else {
				Exam exam = (Exam) session.getAttribute("exam");
				String studentId = ((Student) session.getAttribute("student")).getId();
				ExaminationResult er = markExam(ea, exam, studentId);
				examinationResultService.saveOrUpdate(er);
				session.removeAttribute("exam");
				json.addElement("result", "1").addElement("point", String.valueOf(er.getPoint()));
			}
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 批卷
	 * @param ea
	 * @param exam
	 * @param sid
	 * @return
	 */
	public ExaminationResult markExam(ExaminationAnswer ea, Exam exam, String sid) {
		ExaminationResult er = new ExaminationResult();
		er.setExamId(exam.getId());
		er.setStudentId(sid);
		er.setExamTitle(exam.getTitle());
		er.setTime(new Date());
		//计算总分
		int sum = 0;
		Map<Integer, String> answers = ea.getAnswers();
		sum += markHelper(exam.getSingleQuestions(), answers, er);
		sum += markHelper(exam.getMultiQuestions(), answers, er);
		sum += markHelper(exam.getJudgeQuestions(), answers, er);
		er.setPoint(sum);
		return er;
	}

	/**
	 * 辅助批卷-批阅一道题
	 * @param questions
	 * @param answers
	 * @param er
	 * @return
	 */
	private int markHelper(List<Question> questions, Map<Integer, String> answers, ExaminationResult er) {
		int point = 0;
		MarkedQuestion mq = null;
		String wa = null;
		for (Question q : questions) {
			StudentQuestion studentQuestion = studentQuestionService.queryQuestionRecord(q.getId(), er.getStudentId());
			if (studentQuestion == null) {
				studentQuestion = new StudentQuestion();
				studentQuestion.setQid(q.getId());
				studentQuestion.setSid(er.getStudentId());
			}
			studentQuestion.setTotalCount(studentQuestion.getTotalCount() + 1);
			mq = new MarkedQuestion();
			mq.setQuestionId(q.getId());
			wa = answers.get(q.getId());
			if (q.getAnswer().equals(wa)) {
				// 该题做对了
				mq.setRight(true);
				point += q.getPoint();
			} else {
				// 该题做错了
				studentQuestion.setFailCount(studentQuestion.getFailCount() + 1);
				mq.setRight(false);
			}
			// 记录
			studentQuestionService.insertOrUpdateQuestionRecord(studentQuestion);
			mq.setRight(q.getAnswer().equals(wa));
			mq.setWrongAnswer(wa);
		}
		er.addMarkedQuestion(mq);
		return point;
	}

}
