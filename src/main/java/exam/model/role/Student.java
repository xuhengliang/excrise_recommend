package exam.model.role;

import exam.model.Clazz;

import java.io.Serializable;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class Student implements Serializable {

	private static final long serialVersionUID = -4447237686888091087L;
	
	private String id;
	private String name;
	private String password;
	private Clazz clazz;
	private boolean modified;
	
	public String getId() {
		return id;
	}
	public Student setId(String id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Student setName(String name) {
		this.name = name;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public Student setPassword(String password) {
		this.password = password;
		return this;
	}
	public Clazz getClazz() {
		return clazz;
	}
	public Student setClazz(Clazz clazz) {
		this.clazz = clazz;
		return this;
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
}
