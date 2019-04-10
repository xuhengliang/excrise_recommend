package exam.session;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public final class SessionContainer {
	
	private SessionContainer() {}
	
	/**
	 * 维持已登录的学生
	 * key -> 学生id
	 * value -> 学生的session
	 */
	public static final ConcurrentMap<String, HttpSession> loginStudents = new ConcurrentHashMap<>();
	/**
	 * 维持已登录的教师
	 */
	public static final ConcurrentMap<String, HttpSession> loginTeachers = new ConcurrentHashMap<>();
	/**
	 * 由于admin仅有一个账户，故一个Boolean类型应该就可以了
	 */
	public static volatile HttpSession adminSession;
	
}
