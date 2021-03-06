package exam.model;

import java.io.Serializable;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class StudentQuestion implements Serializable {

    private static final long serialVersionUID = 3414280336596100035L;
    private int id;
    private String sid;
    private int qid;
    private int totalCount;
    private int failCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    @Override
    public String toString() {
        return "StudentQuestion{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", qid=" + qid +
                ", totalCount=" + totalCount +
                ", failCount=" + failCount +
                '}';
    }
}
