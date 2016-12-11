package ru.innopolis.models;

import java.sql.Timestamp;

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
    private Timestamp ts;
    private boolean isPublish;
    private boolean isModeration;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Article(int id, int userId, String userLogin, String title, String content, String preview, Timestamp ts, boolean isPublish, boolean isModeration) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.userLogin = userLogin;
        this.userId = userId;
        this.ts = ts;
        this.isPublish = isPublish;
        this.isModeration = isModeration;
    }

    public Article(int id, int userId, String title, String content, String preview, Timestamp ts, boolean isPublish, boolean isModeration) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.userLogin = userLogin;
        this.userId = userId;
        this.ts = ts;
        this.isPublish = isPublish;
        this.isModeration = isModeration;
    }

    public Article(int id, int userId, String title, String content, String preview, Timestamp ts) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.ts = ts;
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

    public Article(int id, String title, String preview, String content) {
        this.id = id;
        this.preview = preview;
        this.title = title;
        this.content = content;
    }

    public Timestamp getTs() {
        return ts;
    }

    public boolean getIsPublish() {
        return isPublish;
    }

    public boolean getIsModeration() {
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
                ", isPublish=" + isPublish +
                ", isModeration=" + isModeration +
                '}';
    }
}
