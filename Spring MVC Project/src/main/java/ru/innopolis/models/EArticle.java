package ru.innopolis.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Alexander Chuvashov on 01.12.2016.
 */

@Entity
@Table(name = "articles")
public class EArticle {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name="articles_id_seq",
            sequenceName="articles_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="articles_id_seq")
    private int id;

    @Column(name = "userId")
    private int userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "preview")
    private String preview;

    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts;

    public void setTs(Date ts) {
        this.ts = ts;
    }

    @Column(name = "isPublish")
    private boolean isPublish;

    @Column(name = "isModeration")
    private boolean isModeration;

    @Column(name = "isRemove")
    private boolean isRemove;

    public EArticle(int id, int userId, String title, String content, String preview, Timestamp ts, boolean isPublish, boolean isModeration, boolean isRemove) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.userId = userId;
        this.ts = ts;
        this.isPublish = isPublish;
        this.isModeration = isModeration;
        this.isRemove = isRemove;
    }

    public EArticle() {
        super();
    }

    public EArticle(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public EArticle(int id, int userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public EArticle(int id, int userId, String title, String content, String preview, Timestamp ts) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.ts = ts;
    }

    public EArticle(int id, String title, String preview, String content) {
        this.id = id;
        this.preview = preview;
        this.title = title;
        this.content = content;
    }

    public EArticle(int userId, String title, String content, String preview, Date ts ) {
        this.userId = userId;
        this.preview = preview;
        this.title = title;
        this.content = content;
        this.ts = ts;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Date getTs() {
        return ts;
    }

    public boolean getIsPublish() {
        return isPublish;
    }

    public boolean getIsModeration() {
        return isModeration;
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
                ", userId='" + userId + '\'' +
                ", ts='" + ts + '\'' +
                ", isPublish=" + isPublish +
                ", isModeration=" + isModeration +
                ", isRemove=" + isRemove +
                '}';
    }

    public void setIsRemove(boolean isRemove) {
        this.isRemove = isRemove;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsModeration(boolean isModeration) {
        this.isModeration = isModeration;
    }

    public void setIsPublish(boolean isPublish) {
        this.isPublish = isPublish;
    }
}
