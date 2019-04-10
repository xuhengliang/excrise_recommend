package exam.controller.teacher;

import exam.model.Question;
import exam.model.QuestionType;
import exam.model.page.PageBean;
import exam.model.role.Teacher;
import exam.service.QuestionService;
import exam.util.DataUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 老师操作试题
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.teacher.QuestionController")
@RequestMapping("/teacher/question")
public class QuestionController {
	
	@Resource
	private QuestionService questionService;
	@Value("#{properties['question.pageSize']}")
	private int pageSize;
	@Value("#{properties['question.pageNumber']}")
	private int pageNumber;

	/**
	 * 单选
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/singles")
	public String singlesHelper(HttpServletRequest request, Model model) throws IOException {
		return singles("1", null, request, model);
	}

	@RequestMapping("/singles/{pn}")
	public String singles(@PathVariable String pn, String search, HttpServletRequest request, Model model) {
		return questions(DataUtil.getPageCode(pn), search, QuestionType.SINGLE, request, model);
	}

	/**
	 * 多选
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/multis")
	public String multisHelper(HttpServletRequest request, Model model) {
		return multis("1", null, request, model);
	}
	
	@RequestMapping("/multis/{pn}")
	public String multis(@PathVariable String pn, String search, HttpServletRequest request, Model model) {
		return questions(DataUtil.getPageCode(pn), search, QuestionType.MULTI, request, model);
	}

	/**
	 * 判断
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/judges")
	public String judgesHelper(HttpServletRequest request, Model model) {
		return judges("1", null, request, model);
	}
	
	@RequestMapping("/judges/{pn}")
	public String judges(@PathVariable String pn, String search, HttpServletRequest request, Model model) {
		return questions(DataUtil.getPageCode(pn), search, QuestionType.JUDGE, request, model);
	}

	/**
	 * 保存试题
	 * @param id
	 * @param title
	 * @param optionA
	 * @param optionB
	 * @param optionC
	 * @param optionD
	 * @param answer
	 * @param point
	 * @param type
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(Integer id, String title, String optionA, String optionB, String optionC, String optionD,
			String answer, Integer point, String type, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (!DataUtil.isValid(point) || !DataUtil.isValid(title, answer, type)
				|| (QuestionType.valueOf(type) != QuestionType.JUDGE && !DataUtil.isValid(optionA, 
						optionB, optionC, optionD))) {
			json.addElement("result", "0");
		} else {
			Question question = new Question();
			question.setType(QuestionType.valueOf(type));
			question.setAnswer(answer);
			question.setId(id);
			question.setOptionA(optionA);
			question.setOptionB(optionB);
			question.setOptionC(optionC);
			question.setOptionD(optionD);
			question.setPoint(point);
			question.setTitle(title);
			question.setTeacher((Teacher) request.getSession().getAttribute("teacher"));
			questionService.saveOrUpdate(question);
			json.addElement("result", "1");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 删除试题
	 * @param id
	 * @param response
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void delete(@PathVariable("id") int id, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		//首先检查此题是否被试卷引用
		if (questionService.isUsedByExam(id)) {
			json.addElement("result", "0").addElement("message", "此题目被试卷引用，无法删除");
		} else {
			questionService.delete(id);
			json.addElement("result", "1").addElement("message", "删除成功");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 异步拉去试题信息
	 * @param type
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ajax")
	@ResponseBody
	public void list(String type, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (!DataUtil.isValid(type)) {
			json.addElement("result", "0");
		} else {
			QuestionType qt = QuestionType.valueOf(type);
			String tid = ((Teacher) request.getSession().getAttribute("teacher")).getId(); 
			List<Question> questions = qt == QuestionType.SINGLE ? questionService.getSingles(tid) :
				(qt == QuestionType.MULTI ? questionService.getMultis(tid) : questionService.getJudges(tid));
			JSONArray array = new JSONArray();
			for (Question q : questions) {
				array.addObject(q.getJSON());
			}
			json.addElement("result", "1").addElement("data", array);
		}
		DataUtil.writeJSON(json, response);
	}
	
	private String questions(int pageCode, String search, QuestionType type, HttpServletRequest request, Model model) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String where = "where tid = '" + teacher.getId() + "' and type = '" + type.name() + "'";
		if (DataUtil.isValid(search)) {
			where += " and title like '%" + search + "%'"; 
		}
		PageBean<Question> pageBean = questionService.pageSearch(pageCode, pageSize, pageNumber, where, null, null);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("search", search);
		model.addAttribute("type", type.name());
		return "teacher/question_list";
	}

	/**
	 * 正确率
	 * @param qid
	 * @param response
	 */
	@RequestMapping("/rate/{qid}")
	@ResponseBody
	public void rate(@PathVariable Integer qid, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (!DataUtil.isValid(qid)) {
			json.addElement("result", "0").addElement("messgae", "参数非法");
		} else {
			Double rate = questionService.articulationScore(qid);
			json.addElement("result", "1").addElement("rate", rate == null ? "无考试记录!" : (rate * 100) + "%");
		}
		DataUtil.writeJSON(json, response);
	}
	
}
