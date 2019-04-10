package exam.model;

import exam.util.json.JSON;
import exam.util.json.JSONAble;
import exam.util.json.JSONObject;

import java.io.Serializable;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class Clazz implements Serializable, JSONAble {

	private static final long serialVersionUID = 3193377452706700152L;
	
	private int id;
	private int cno;
	private Major major;
	private Grade grade;
	
	public Clazz(int id) {
		this.id = id;
	}
	
	public Clazz() {}
	
	@Override
	public String toString() {
		return "Clazz [id=" + id + ", cno=" + cno + ", major=" + major
				+ ", grade=" + grade + "]";
	}

	public JSON getJSON() {
		JSONObject json = new JSONObject();
		json.addElement("id", String.valueOf(id)).addElement("cno", String.valueOf(cno));
		if(major != null && grade != null) {
			json.addElement("major", this.major.getJSON()).addElement("grade", this.grade.getJSON());
		}
		return json;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
}
