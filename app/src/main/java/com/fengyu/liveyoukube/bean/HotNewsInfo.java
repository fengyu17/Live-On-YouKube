package com.fengyu.liveyoukube.bean;

import java.io.Serializable;

public class HotNewsInfo implements Serializable {

    private String id;
    private String thumbnail;
    private String title;
    private String view_count;
    private String published;
    private String link;
    private String tag;
    private String up_count;
    private String down_count;
    private String category;

    public HotNewsInfo(String id, String thumbnail, String title, String view_count, String published, String link, String tag, String up_count, String down_count, String category) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.view_count = view_count;
        this.published = published;
        this.link = link;
        this.tag = tag;
        this.up_count = up_count;
        this.down_count = down_count;
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getView_count() {
        return view_count;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getPublished() {
        return published;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setUp_count(String up_count) {
        this.up_count = up_count;
    }

    public String getUp_count() {
        return up_count;
    }

    public void setDown_count(String down_count) {
        this.down_count = down_count;
    }

    public String getDown_count() {
        return down_count;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
