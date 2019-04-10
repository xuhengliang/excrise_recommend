package exam.controller.teacher;

import exam.dto.ClassDTO;
import exam.model.role.Teacher;
import exam.service.TeacherService;
import exam.util.DataUtil;
import exam.util.json.JSONArray;
import exam.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * 老师首页
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;
	
	@RequestMapping("/index")
	public String index() {
		return "teacher/index";
	}

    @RequestMapping("/password")
    public String password() {
        return "teacher/password";
    }

    /**
     * 校验密码
     * @param password
     * @param request
     * @param response
     */
    @RequestMapping("/password/check")
    @ResponseBody
    public void check(String password, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (teacher.getPassword().equals(password)) {
            json.addElement("result", "1");
        } else {
            json.addElement("result", "0");
        }
        DataUtil.writeJSON(json, response);
    }

    /**
     * 班级列表
     * @param session
     * @param response
     */
    @RequestMapping("/classes")
    @ResponseBody
    public void classes(HttpSession session, HttpServletResponse response) {
    	JSONObject json = new JSONObject();
    	Teacher teacher = (Teacher) session.getAttribute("teacher");
    	List<ClassDTO> dtoes = teacherService.getClassesWithMajorAndGrade(teacher.getId());
    	JSONArray array = new JSONArray();
    	for (ClassDTO dto : dtoes) {
    		array.addObject(dto.getJSON());
    	}
    	json.addElement("result", "1").addElement("data", array);
    	DataUtil.writeJSON(json, response);
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/password/modify")
    public String modifyPassword(String oldPassword, String newPassword, HttpServletRequest request, Model model) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (!checkPassword(oldPassword, newPassword, teacher)) {
            return "error";
        }
        teacherService.updatePassword(teacher.getId(), newPassword);
        teacher.setPassword(newPassword);
        teacher.setModified(true);
        model.addAttribute("message", "密码修改成功");
        model.addAttribute("url", request.getContextPath() + "/teacher/index");
        return "success";
    }

    /**
     * 检查旧密码和新密码
     * @param oldPassword 必须和session里面保存的密码一致
     * @param newPassword 必须是4-10，由数字、字母、下划线组成
     * @param teacher
     * @return 通过返回true
     */
    private boolean checkPassword(String oldPassword, String newPassword, Teacher teacher) {
        if (!teacher.getPassword().equals(oldPassword)) {
            return false;
        }
        if (!DataUtil.isValid(newPassword) || !newPassword.matches("^\\w{4,10}$")) {
            return false;
        }
        return true;
    }
	
}
