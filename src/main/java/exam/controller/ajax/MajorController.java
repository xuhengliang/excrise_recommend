package exam.controller.ajax;

import exam.model.Major;
import exam.service.MajorService;
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
 * @Time 2019/4/6 19:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/major")
public class MajorController {

	@Resource
	private MajorService majorService;

	/**
	 * 异步拉取专业信息
	 * @param grade
	 * @param response
	 */
	@RequestMapping("/ajax")
	@ResponseBody
	public void ajax(String grade, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		List<Major> majors = null;;
		if (!DataUtil.isValid(grade)) {
			majors = majorService.findAll();
		} else if (!DataUtil.isNumber(grade)) {
			json.addElement("result", "0").addElement("message", "年级格式非法");
		} else {
			majors = majorService.findByGrade(Integer.parseInt(grade));
		}
		if (majors != null) {
			json.addElement("result", "1");
			JSONArray array = new JSONArray();
			for (Major major : majors) {
				array.addObject(major.getJSON());
			}
			json.addElement("data", array);
		}
		DataUtil.writeJSON(json, response);
	}
	
}
