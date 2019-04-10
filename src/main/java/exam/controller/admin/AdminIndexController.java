package exam.controller.admin;

import exam.model.role.Manager;
import exam.service.ManagerService;
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
 * 管理员首页
 * @Author 许恒亮
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminIndexController {
	
	@Resource
	private ManagerService managerService;

	@RequestMapping("/index")
	public String index() {
		return "admin/index";
	}

	@RequestMapping("/password")
	public String password() {
		return "admin/password";
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
        Manager manager = (Manager) request.getSession().getAttribute("admin");
        if (manager.getPassword().equals(password)) {
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
        Manager manager = (Manager) request.getSession().getAttribute("admin");
        if (!checkPassword(oldPassword, newPassword, manager)) {
            return "error";
        }
        managerService.updatePassword(manager.getId(), newPassword);
        manager.setPassword(newPassword);
        manager.setModified(true);
        model.addAttribute("message", "密码修改成功");
        model.addAttribute("url", request.getContextPath() + "/admin/index");
        return "success";
    }

    private boolean checkPassword(String oldPassword, String newPassword, Manager manager) {
        if (!manager.getPassword().equals(oldPassword)) {
            return false;
        }
        if (!DataUtil.isValid(newPassword) || !newPassword.matches("^\\w{4,10}$")) {
            return false;
        }
        return true;
    }
	
}
