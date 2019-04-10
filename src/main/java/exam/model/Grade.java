package exam.model;

import exam.util.json.JSON;
import exam.util.json.JSONAble;
import exam.util.json.JSONObject;

import java.io.Serializable;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class Grade implements Serializable, JSONAble {

	private static final long serialVersionUID = -3270807327813730922L;
	
	private int id;
	private int grade;
	
	public Grade(int id) {
		this.id = id;
	}
	
	public Grade() {}
	
	public Grade(int id, int grade) {
		this.id = id;
		this.grade = grade;
	}
	
	public JSON getJSON() {
		JSONObject object = new JSONObject();
		object.addElement("id", String.valueOf(id))
			.addElement("grade", String.valueOf(grade));
		return object;
	}
	
	@Override
	public String toString() {
		return "Grade [id=" + id + ", grade=" + grade + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
