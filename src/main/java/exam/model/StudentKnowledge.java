package exam.model;

import java.io.Serializable;

/**
 * @Author 许恒亮
 * @Version 1.0
 */
public class StudentKnowledge implements Serializable {

    private static final long serialVersionUID = -44075508311116582L;
    private int id;
    private String sid;
    private int kid;
    private int losingScore;

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

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public int getLosingScore() {
        return losingScore;
    }

    public void setLosingScore(int losingScore) {
        this.losingScore = losingScore;
    }
}
