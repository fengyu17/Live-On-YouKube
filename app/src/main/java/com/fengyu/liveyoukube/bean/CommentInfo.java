package com.fengyu.liveyoukube.bean;

public class CommentInfo {

    private String commentId;
    private String content;
    private String userName;
    private String sourceName;
    private String published;

    public CommentInfo(String commentId, String content, String userName, String sourceName, String published) {
        // TODO Auto-generated constructor stub
        this.commentId = commentId;
        this.content = content;
        this.userName = userName;
        this.sourceName = sourceName;
        this.published = published;
    }

    public void setcommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getcommentId() {
        return commentId;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getcontent() {
        return content;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getuserName() {
        return userName;
    }

    public void setsourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getsourceName() {
        return sourceName;
    }

    public void setpublished(String published) {
        this.published = published;
    }

    public String getpublished() {
        return published;
    }

}
