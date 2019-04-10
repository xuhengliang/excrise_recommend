package exam.dao.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface GenerateKeyCallback {

	void setParameters(PreparedStatement ps, Object param) throws SQLException;
	
}
