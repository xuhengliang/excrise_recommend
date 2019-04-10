package exam.dao.impl;

import exam.dao.StudentQuestionDao;
import exam.dao.base.BaseDaoImpl;
import exam.model.StudentQuestion;
import exam.util.DataUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("studentQuestionDao")
public class StudentQuestionDaoImpl extends BaseDaoImpl<StudentQuestion>  implements StudentQuestionDao {

    private static RowMapper<StudentQuestion> rowMapper;
    private static String sql = "select * from student_question";
    private static String countSql = "select count(*) from student_question";

    static {
        rowMapper = new RowMapper<StudentQuestion>() {
            public StudentQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
                StudentQuestion studentQuestion = new StudentQuestion();
                studentQuestion.setId(rs.getInt("id"));
                studentQuestion.setQid(rs.getInt("qid"));
                studentQuestion.setSid(rs.getString("sid"));
                studentQuestion.setFailCount(rs.getInt("fail_count"));
                studentQuestion.setTotalCount(rs.getInt("total_count"));
                return studentQuestion;
            }
        };
    }
    @Override
    public List<StudentQuestion> find(StudentQuestion entity) {
        StringBuilder sqlBuilder = new StringBuilder(sql).append(" where 1 = 1");
        if(entity != null) {
            if(DataUtil.isValid(entity.getId())) {
                sqlBuilder.append(" and id = ").append(entity.getId());
            }
            if(DataUtil.isValid(entity.getQid())) {
                sqlBuilder.append(" and qid = ").append(entity.getQid());
            }
            if(DataUtil.isValid(entity.getSid())) {
                sqlBuilder.append(" and sid = ").append(entity.getSid());
            }
        }
        return jdbcTemplate.query(sqlBuilder.toString(), rowMapper);
    }
    @Override
    public String getCountSql() {
        return countSql;
    }

    @Override
    public String getSql() {
        return sql;
    }

    @Override
    public RowMapper<StudentQuestion> getRowMapper() {
        return rowMapper;
    }
}
