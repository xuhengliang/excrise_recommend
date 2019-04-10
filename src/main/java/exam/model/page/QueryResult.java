package exam.model.page;

import java.util.List;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class QueryResult<T> {

	private List<T> records;
	private int recordCount;
	
	public QueryResult(List<T> records, int recordCount) {
		this.records = records;
		this.recordCount = recordCount;
	}
	
	public QueryResult() {}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
}
