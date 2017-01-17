package bbabics.testdatabaseapplication.data;

/**
 * Created by bbabics on 2017. 01. 17..
 */

public class Comment {
    private long id;
    private String comment;

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return comment;
    }
}
