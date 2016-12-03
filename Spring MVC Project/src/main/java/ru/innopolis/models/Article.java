package ru.innopolis.models;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */

/*Модель статьи*/
public class Article {
    private int id;
    private String title;
    private String content;
    private String preview;
    private String userLogin;
    private int userId;
    private String ts;
    private boolean checking;
    private boolean isModeration;

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Article(int id, int userId, String userLogin, String title, String content, String preview, String ts, boolean checking, boolean isModeration) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.userLogin = userLogin;
        this.userId = userId;
        this.ts = ts;
        this.checking = checking;
        this.isModeration = isModeration;
    }

    public Article(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Article(int id, int userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public String getTs() {
        return ts;
    }

    public boolean getChecking() {
        return checking;
    }

    public boolean getModeration() {
        return isModeration;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", userId='" + userId + '\'' +
                ", ts='" + ts + '\'' +
                ", checking=" + checking +
                ", isModeration=" + isModeration +
                '}';
    }
}
