package exam.controller.ajax;

import exam.model.Clazz;
import exam.model.Grade;
import exam.model.Major;
import exam.service.ClazzService;
import exam.util.DataUtil;
import exam.util.json.JSON;
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
@RequestMapping("/clazz")
public class ClazzController {

	@Resource
	private ClazzService clazzService;

	/**
	 * 异步拉去班级信息
	 * @param grade
	 * @param major
	 * @param response
	 */
	@RequestMapping("/ajax")
	@ResponseBody
	public void ajax(String grade, String major, HttpServletResponse response) {
		JSON json = new JSONObject();
		if(!DataUtil.isNumber(grade, major)) {
			json.addElement("result", "0").addElement("message", "格式非法");
		}else {
			Clazz clazz = new Clazz();
			clazz.setMajor(new Major(Integer.parseInt(major)));
			clazz.setGrade(new Grade(Integer.parseInt(grade)));
			List<Clazz> clazzs = clazzService.findClazzOnly(clazz);
			json.addElement("result", "1");
			JSONArray array = new JSONArray();
			for(Clazz c : clazzs) {
				array.addObject(c.getJSON());
			}
			json.addElement("data", array);
		}
		DataUtil.writeJSON(json, response);
	}
	
}
