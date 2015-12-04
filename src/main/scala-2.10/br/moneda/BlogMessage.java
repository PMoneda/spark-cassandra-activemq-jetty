package br.moneda;

/**
 * Created by philippe on 02/12/15.
 */
public class BlogMessage {
    private String author;
    private String blog;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "BlogMessage{" +
                "author='" + author + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
