package exam.controller.admin;

import exam.model.Clazz;
import exam.model.page.PageBean;
import exam.model.role.Teacher;
import exam.service.ClazzService;
import exam.service.TeacherService;
import exam.util.DataUtil;
import exam.util.StringUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员老师操作模块
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/teacher")
public class AdminTeacherController {
	
	@Resource
	private TeacherService teacherService;
	@Resource
	private ClazzService clazzService;
	@Value("#{properties['teacher.pageSize']}")
	private int pageSize;
	@Value("#{properties['teacher.pageNumber']}")
	private int pageNumber;

	/**
	 * 老师列表
	 * @param pn
	 * @param id
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(String pn, String id, String name, Model model) {
		int pageCode = DataUtil.getPageCode(pn);
		String where = " where 1 = 1 ";
		List<Object> params = new ArrayList<Object>(1);
		if(DataUtil.isValid(id)) {
			where += "and id = ?";
			params.add(id);
		}else if(DataUtil.isValid(name)) {
			where += "and name like '%" + name + "%'";
		}
		PageBean<Teacher> pageBean = teacherService.pageSearch(pageCode, pageSize, pageNumber, where, params, null);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("name", name);
		return "admin/teacher_list";
	}

	/**
	 * 添加老师
	 * @param id
	 * @param name
	 * @param response
	 */
	@RequestMapping("/add")
	@ResponseBody
	public void add(String id, String name, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		name = StringUtil.htmlEncode(name);
		if(!DataUtil.isValid(id, name)) {
			json.addElement("result", "0").addElement("message", "格式非法");
		}else if(teacherService.isExist(id)) {
			json.addElement("result", "0").addElement("message", "此教师已存在");
		}else {
			teacherService.saveTeacher(id, name, StringUtil.md5("1234"));
			json.addElement("result", "1").addElement("message", "保存成功");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 编辑老师
	 * @param id
	 * @param name
	 * @param response
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public void edit(String id, String name, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		name = StringUtil.htmlEncode(name);
		if(!DataUtil.isValid(id, name)) {
			json.addElement("result", "0").addElement("message", "格式非法");
		}else {
			teacherService.updateName(id, name);
			json.addElement("result", "1").addElement("message", "保存成功");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 班级列表
	 * @param tid
	 * @param response
	 */
	@RequestMapping("/clazz/list")
	@ResponseBody
	public void clazz(String tid, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if(!DataUtil.isValid(tid)) {
			json.addElement("result", "0").addElement("message", "数据格式非法");
		}else {
			List<Clazz> clazzs = clazzService.findByTeacher(tid);
			JSONArray array = new JSONArray();
			for(Clazz c : clazzs) {
				array.addObject(c.getJSON());
			}
			json.addElement("result", "1").addElement("data", array);
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 添加班级
	 * @param ids
	 * @param tid
	 * @param response
	 */
	@RequestMapping("/clazz/save")
	@ResponseBody
	public void clazzSave(String ids, String tid, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if(!DataUtil.isValid(ids, tid)) {
			json.addElement("result", "0").addElement("message", "格式非法");
		}else {
			teacherService.updateTeachClazzs(ids, tid);
			json.addElement("result", "1").addElement("message", "修改成功");
		}
		DataUtil.writeJSON(json, response);
	}

	/**
	 * 删除老师
	 * @param tid
	 * @param response
	 */
	@RequestMapping("/delete/{tid}")
	public void delete(@PathVariable String tid, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		teacherService.delete(tid);
		json.addElement("result", "1").addElement("message", "删除成功");
		DataUtil.writeJSON(json, response);
	}
	
}
