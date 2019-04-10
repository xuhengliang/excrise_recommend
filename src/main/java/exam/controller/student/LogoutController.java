package exam.controller.student;

import exam.model.role.Student;
import exam.session.SessionContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 学生退出登录
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.student.LogoutController")
public class LogoutController {
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	@RequestMapping("/student/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Student student = (Student) session.getAttribute("student");
		SessionContainer.loginStudents.remove(student.getId());
		session.invalidate();
		return "redirect:/login";
	}
	
}
