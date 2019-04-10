package exam.service.impl;

import exam.dao.KnowledgeDao;
import exam.dao.base.BaseDao;
import exam.model.Knowledge;
import exam.service.KnowledgeService;
import exam.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
@Service("knowledgeService")
public class KnowledgeServiceImpl extends BaseServiceImpl<Knowledge>  implements KnowledgeService {
    private KnowledgeDao knowledgeDao;

    @Resource(name = "knowledgeDao")
    @Override
    protected void setBaseDao(BaseDao<Knowledge> baseDao) {
        super.baseDao = baseDao;
        this.knowledgeDao = (KnowledgeDao) baseDao;
    }

    @Override
    public void saveKnowledge(String knowledgeName, int pid, String tid) {
        knowledgeDao.executeSql("insert into knowledge values(null, ?, ?, ?)",
                new Object[] {knowledgeName, pid, tid});
    }


    @Override
    public void updateKnowledgeName(int id, String knowledgeName, int pid) {
        String sql = "update knowledge set knowledge_name = ?, pid = ? where id = ?";
        knowledgeDao.executeSql(sql, new Object[]{knowledgeName, pid, id});
    }

    @Override
    public List<Knowledge> findLinked(int qid,String tid) {
        String sql = knowledgeDao.getSql() + " where tid = " + tid + " and id in (select kid from question_knowledge where qid = " + qid + ")";
        return knowledgeDao.queryBySQL(sql);
    }

    @Override
    public List<Knowledge> findNotLinked(int qid,String tid) {
        String sql = knowledgeDao.getSql() + " where tid = " + tid + " and id not in (select kid from question_knowledge where qid = " + qid +")";
        return knowledgeDao.queryBySQL(sql);
    }

    @Override
    public void removeLink(int qid, String tid,int[] kids) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("delete from question_knowledge where tid = ? and qid = ? and kid in(");
        for (int i = 0 ; i < kids.length ;i++ ) {
            if (i == kids.length - 1) {
                stringBuffer.append(kids[i]);
                stringBuffer.append(")");
            } else {
                stringBuffer.append(kids[i]);
                stringBuffer.append(",");
            }
        }
        knowledgeDao.executeSql(stringBuffer.toString(),
                new Object[] {tid, qid});
    }

    @Override
    public void insertLink(int qid,String tid,int[] kids) {
        for (int i = 0 ; i < kids.length ;i++ ) {
            String sql = "insert into question_knowledge(qid,tid,kid) values(?,?," + kids[i] + ")";
            knowledgeDao.executeSql(sql, new Object[] {qid,tid});
        }

    }

    @Override
    public List<Knowledge> findByQid(int qid) {
        if (qid <= 0 ) {
            return null;
        }
        String sql = knowledgeDao.getSql() + " where id in (select kid from question_knowledge where qid = " + qid +")";
        return knowledgeDao.queryBySQL(sql);

    }



    @Override
    public void delete(Object id) {
        String sql = "delete from knowledge where id = " + id;
        knowledgeDao.executeSql(sql);
    }

}
