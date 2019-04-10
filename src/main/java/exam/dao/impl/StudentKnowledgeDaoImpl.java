package exam.dao.impl;

import exam.dao.StudentKnowledgeDao;
import exam.dao.base.BaseDaoImpl;
import exam.model.StudentKnowledge;
import exam.util.DataUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("studentKnowledgeDao")
public class StudentKnowledgeDaoImpl extends BaseDaoImpl<StudentKnowledge> implements StudentKnowledgeDao {

    private static RowMapper<StudentKnowledge> rowMapper;
    private static String sql = "select * from student_knowledge";
    private static String countSql = "select count(*) from student_knowledge";
    private static final int FACTOR = 40;

    static {
        rowMapper = new RowMapper<StudentKnowledge>() {
            public StudentKnowledge mapRow(ResultSet rs, int rowNum) throws SQLException {
                StudentKnowledge studentKnowledge = new StudentKnowledge();
                studentKnowledge.setId(rs.getInt("id"));
                studentKnowledge.setKid(rs.getInt("kid"));
                studentKnowledge.setSid(rs.getString("sid"));
                studentKnowledge.setLosingScore(rs.getInt("losing_score"));
                return studentKnowledge;
            }
        };
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
    public RowMapper<StudentKnowledge> getRowMapper() {
        return rowMapper;
    }

    @Override
    public List<StudentKnowledge> findByEntity(StudentKnowledge entity) {
        StringBuilder sqlBuilder = new StringBuilder(sql).append(" where 1 = 1");
        if(entity != null) {
            if(DataUtil.isValid(entity.getId())) {
                sqlBuilder.append(" and id = ").append(entity.getId());
            }
            if(DataUtil.isValid(entity.getSid())) {
                sqlBuilder.append(" and sid = ").append(entity.getSid());
            }
            if(DataUtil.isValid(entity.getKid())) {
                sqlBuilder.append(" and kid = ").append(entity.getKid());
            }
            // 实验证明失分率小于40的习题推荐最为有效
            sqlBuilder.append(" and losing_score <= ").append(FACTOR);

        }
        return jdbcTemplate.query(sqlBuilder.toString(), rowMapper);
    }
}
