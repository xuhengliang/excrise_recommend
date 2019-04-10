package exam.service;

import exam.model.Knowledge;
import exam.service.base.BaseService;

import java.util.List;

/**
 * @Author 许恒亮
 * @Time 2019/4/6 23:39
 * @Version 1.0
 */
public interface KnowledgeService extends BaseService<Knowledge> {
    void saveKnowledge(String knowledgeName,int pid,String tid);
    void updateKnowledgeName(int id,String knowledgeName,int pid);
    List<Knowledge>  findLinked(int qid,String tid);
    List<Knowledge>  findNotLinked(int qid,String tid);
    void removeLink(int qid, String tid,int[] kids);
    void insertLink(int qid, String tid,int[] kids);
    List<Knowledge> findByQid(int qid);
}
