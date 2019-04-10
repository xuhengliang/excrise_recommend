package exam.controller.admin;

import exam.model.page.PageBean;
import exam.model.role.Student;
import exam.service.StudentService;
import exam.util.DataUtil;
import exam.util.StringUtil;
import exam.util.json.JSON;
import exam.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员学生操作模块
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/student")
public class AdminStudentController {
	
	@Resource
	private StudentService studentService;
	@Value("#{properties['student.pageSize']}")
	private int pageSize;
	@Value("#{properties['student.pageNumber']}")
	private int pageNumber;

	/**
	 * 学生列表
	 * @param pn
	 * @param search
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(String pn, String search, Model model) {
		int pageCode = DataUtil.getPageCode(pn);
		String where = null;
		if(DataUtil.isValid(search)) {
			where = " where s.name like '%" + search + "%'";
		}
		PageBean<Student> pageBean = studentService.pageSearch(pageCode, pageSize, pageNumber, where, null, null);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("search", search);
		return "admin/student_list";
	}

	/**
	 * 添加学生
	 * @param id
	 * @param clazz
	 * @param name
	 * @param response
	 */
	@RequestMapping("/add")
	@ResponseBody
	public void add(String id, String clazz, String name, HttpServletResponse response) {
		JSON json = new JSONObject();
		name = StringUtil.htmlEncode(name);
		if(!DataUtil.isNumber(id, clazz)) {
			json.addElement("result", "0").addElement("message", "数据格式非法");
		}else if(!DataUtil.isValid(name)) {
			json.addElement("result", "0").addElement("message", "请输入学生姓名");
		}else {
			studentService.saveStudent(id, name, StringUtil.md5("1234"), Integer.parseInt(clazz));
			json.addElement("result", "1").addElement("message", "学生添加成功");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 编辑学生
	 * @param id
	 * @param name
	 * @param clazz
	 * @param response
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public void edit(String id, String name, String clazz, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		name = StringUtil.htmlEncode(name);
		if(!DataUtil.isNumber(clazz) || !DataUtil.isValid(id, name)) {
			json.addElement("result", "0").addElement("message", "格式非法");
		}else {
			studentService.updateStudent(Integer.parseInt(clazz), name, id);
			json.addElement("result", "1").addElement("message", "修改成功");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 删除学生
	 * @param sid
	 * @param response
	 */
	@RequestMapping("/delete/{sid}")
	@ResponseBody
	public void delete(@PathVariable String sid, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		studentService.delete(sid);
		json.addElement("result", "1").addElement("message", "删除成功");
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 确认学生是否存在
	 * @param id
	 * @param response
	 */
	@RequestMapping("/check")
	@ResponseBody
	public void check(String id, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if(!DataUtil.isNumber(id)) {
			json.addElement("result", "0").addElement("message", "格式非法");
		}else if(studentService.isExisted(id)) {
			json.addElement("result", "1").addElement("exist", "1");
		}else {
			json.addElement("result", "1").addElement("exist", "0");
		}
		DataUtil.writeJSON(json, response);
	}
	
}
