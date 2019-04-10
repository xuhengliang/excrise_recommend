package exam.dao.impl;

import exam.dao.KnowledgeDao;
import exam.dao.base.BaseDaoImpl;
import exam.model.Knowledge;
import exam.util.DataUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("knowledgeDao")
public class KnowledgeDaoImpl extends BaseDaoImpl<Knowledge> implements KnowledgeDao {
    private static RowMapper<Knowledge> rowMapper;
    private static String sql = "select * from knowledge";
    private static String countSql = "select count(id) from knowledge";

    @Override
    public String getCountSql() {
        return countSql;
    }

    @Override
    public String getSql() {
        return sql;
    }
    static {
        rowMapper = new RowMapper<Knowledge>() {
            public Knowledge mapRow(ResultSet rs, int rowNum) throws SQLException {
                Knowledge knowledge = new Knowledge();
                knowledge.setId(rs.getInt("id"));
                knowledge.setKnowledgeName(rs.getString("knowledge_name"));
                knowledge.setPid(rs.getInt("pid"));
                return knowledge;
            }
        };
    }
    @Override
    public List<Knowledge> find(Knowledge entity) {
        StringBuilder sqlBuilder = new StringBuilder(sql).append(" where 1 = 1");
        if(entity != null) {
            if(DataUtil.isValid(entity.getId())) {
                sqlBuilder.append(" and id = ").append(entity.getId());
            }
            if(DataUtil.isValid(entity.getKnowledgeName())) {
                sqlBuilder.append(" and knowledge_name = ").append(entity.getKnowledgeName());
            }
            if(DataUtil.isValid(entity.getPid())) {
                sqlBuilder.append(" and pid = ").append(entity.getPid());
            }
            if(DataUtil.isValid(entity.getTid())) {
                sqlBuilder.append(" and tid = ").append(entity.getTid());
            }
        }
        return jdbcTemplate.query(sqlBuilder.toString(), rowMapper);
    }
    @Override
    public RowMapper<Knowledge> getRowMapper() {
        return rowMapper;
    }
}
