package model;

/**
 * Created by khoa.nguyen on 2/29/2016.
 */
public class Post {
    private int id;
    public String content;

    public Post() {
        super();
    }

    public Post(String content) {
        super();
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return this.content;
    }
}
