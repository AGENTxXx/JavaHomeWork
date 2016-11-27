package ru.innopolis.modal;

/**
 * Created by Alexander Chuvashov on 24.11.2016.
 */
/*Класс сущности "Статья"*/
public class Article {
    private String id;
    private String title;
    private String content;
    private String authorLogin;
    private String authorId;
    private String ts;
    private boolean checking;
    private boolean publish;

    public String getTs() {
        return ts;
    }

    public boolean getChecking() {
        return checking;
    }

    public boolean getPublish() {
        return publish;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Article(String id, String authorId, String authorLogin, String title, String content, String ts, boolean checking, boolean publish) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorLogin = authorLogin;
        this.authorId = authorId;
        this.ts = ts;
        this.checking = checking;
        this.publish = publish;
    }
}
