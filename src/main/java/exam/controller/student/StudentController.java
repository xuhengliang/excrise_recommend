package exam.controller.student;

import exam.model.role.Student;
import exam.service.StudentService;
import exam.util.DataUtil;
import exam.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 学生首页
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.student.StudentController")
@RequestMapping("/student")
public class StudentController {
	
	@Resource
	private StudentService studentService;
	
	@RequestMapping("/index")
	public String index() {
		return "student/index";
	}
	
	@RequestMapping("/password")
	public String password() {
		return "student/password";
	}

    /**
     * 确认密码
     * @param password
     * @param request
     * @param response
     */
    @RequestMapping("/password/check")
    @ResponseBody
    public void check(String password, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        Student student = (Student) request.getSession().getAttribute("student");
        if (student.getPassword().equals(password)) {
            json.addElement("result", "1");
        } else {
            json.addElement("result", "0");
        }
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
        Student student = (Student) request.getSession().getAttribute("student");
        if (!checkPassword(oldPassword, newPassword, student)) {
            return "error";
        }
        studentService.updatePassword(student.getId(), newPassword);
        student.setPassword(newPassword);
        student.setModified(true);
        model.addAttribute("message", "密码修改成功");
        model.addAttribute("url", request.getContextPath() + "/student/index");
        return "success";
    }
    
    private boolean checkPassword(String oldPassword, String newPassword, Student student) {
        if (!student.getPassword().equals(oldPassword)) {
            return false;
        }
        if (!DataUtil.isValid(newPassword) || !newPassword.matches("^\\w{4,10}$")) {
            return false;
        }
        return true;
    }
	
	
}
