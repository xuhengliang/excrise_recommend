package exam.controller.admin;

import exam.session.SessionContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 管理员退出登录
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminLogoutController {
	/**
	 * 注销 - 退出系统
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		SessionContainer.adminSession = null;
		session.invalidate();
		return "login";
	}
	
}
