package exam.util.json;

import exam.util.DataUtil;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class JSONArray extends JSON {
	

	@Override
	public JSONArray addObject(JSON object) {
		if (json != null) {
			json.append(object).append(",");
		}
		return this;
	}

	@Override
	public JSON addElement(String key, String value) {
		if (DataUtil.isValid(key) && value != null) {
			json.append("{\"").append(key).append("\":\"").append(value).append("\"},");
		}
		return this;
	}

	@Override
	public JSON addElement(String key, JSON object) {
		if (DataUtil.isValid(key) && object != null) {
			json.append("{\"").append(key).append("\":").append(object).append("},");
		}
		return this;
	}

	@Override
	protected char getBrace() {
		return '[';
	}

}
