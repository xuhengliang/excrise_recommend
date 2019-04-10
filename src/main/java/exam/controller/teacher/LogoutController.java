package exam.controller.teacher;

import exam.model.role.Teacher;
import exam.session.SessionContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 老师退出登录
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller("exam.controller.teacher")
public class LogoutController {
	/**
	 * 退出系统
	 * @param request
	 * @return
	 */
	@RequestMapping("/teacher/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		SessionContainer.loginTeachers.remove(teacher.getId());
		session.invalidate();
		return "redirect:/login";
	}

}
