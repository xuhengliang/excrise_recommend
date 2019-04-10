package exam.model;

import exam.util.json.JSON;
import exam.util.json.JSONObject;

import java.io.Serializable;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class Knowledge implements Serializable {

    private static final long serialVersionUID = 196561496982830813L;
    private int id;
    private String knowledgeName;
    private int pid;
    private String tid;

    public JSON getJSON() {
        JSONObject object = new JSONObject();
        object.addElement("id", String.valueOf(id))
                .addElement("knowledgeName", String.valueOf(knowledgeName))
                .addElement("pid", String.valueOf(pid));
        return object;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
