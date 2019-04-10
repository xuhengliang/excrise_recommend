package exam.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class ExaminationAnswer {

	private int examId;
	//保存结果，key:题目id，value：考生给出的答案
	private Map<Integer, String> answers = new HashMap<Integer, String>();
	
	/**
	 * 添加一道题目
	 * @param id 题目id
	 * @param answer 考生的答案
	 */
	public void addQuestion(int id, String answer) {
		this.answers.put(id, answer);
	}
	
	public Map<Integer, String> getAnswers() {
		return Collections.unmodifiableMap(answers);
	}

	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	
}
