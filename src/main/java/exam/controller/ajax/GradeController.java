package exam.controller.ajax;

import exam.model.Grade;
import exam.service.GradeService;
import exam.util.DataUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ajax
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/grade")
public class GradeController {
	
	@Resource
	private GradeService gradeService;

	/**
	 * 异步拉去年级信息
	 * @param response
	 */
	@RequestMapping("/ajax")
	@ResponseBody
	public void ajax(HttpServletResponse response) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		List<Grade> grades = gradeService.findAll();
		json.addElement("result", "1");
		for(Grade grade : grades) {
			array.addObject(grade.getJSON());
		}
		json.addElement("data", array);
		DataUtil.writeJSON(json, response);
	}
	
}
