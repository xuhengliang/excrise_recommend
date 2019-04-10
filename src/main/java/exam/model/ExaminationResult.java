package exam.model;

import exam.dto.MarkedQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class ExaminationResult {

	private int id;
	private int examId;
	private String examTitle;
	private String studentId;
	private int point;
	private Date time;
	
	private List<MarkedQuestion> markedQuestions = new ArrayList<MarkedQuestion>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getExamTitle() {
		return examTitle;
	}
	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public List<MarkedQuestion> getMarkedQuestions() {
		return Collections.unmodifiableList(markedQuestions);
	}
	
	public void addMarkedQuestion(MarkedQuestion question) {
		this.markedQuestions.add(question);
	}
	
}
