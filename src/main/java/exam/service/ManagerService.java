package exam.service;

import exam.model.role.Manager;

public interface ManagerService {

	/**
	 * 用户登录
	 */
	Manager login(String name, String password);
	
	/**
	 * 修改密码
	 * @param id 管理员id
	 * @param password 未经过加密的新密码
	 */
	void updatePassword(int id, String password);
	
}
