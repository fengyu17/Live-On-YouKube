/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-8-12 上午9:06:27
 */
package com.fengyu.liveyoukube.bean;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class ProgramInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String coverImg;
    private String name;
    //    private String time;
    private String rating;
    //    private String category;
//    private String director;
//    private String actor;
//    private String area;
//    private String intro;
    private String playLink;
    private String programID;

    /*
        public ProgramInfo(String coverImg, String name, String time, String rating, String category, String area, String director, String actor, String intro, String programID) {
            this.coverImg = coverImg;
            this.name = name;
            this.time = time;
            this.rating = rating;
            this.category = category;
            this.area = area;
            this.director = director;
            this.actor = actor;
            this.intro = intro;
            this.programID = programID;
        }
    */

    public ProgramInfo(String coverImg, String name, String rating, String programID) {
        this.coverImg = coverImg;
        this.name = name;
        this.rating = rating;
        this.programID = programID;
    }

    public ProgramInfo(String coverImg, String name, String rating, String playLink, String programID) {
        this.coverImg = coverImg;
        this.name = name;
        this.rating = rating;
        this.playLink = playLink;
        this.programID = programID;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setPlayLink(String playLink) {
        this.playLink = playLink;
    }

    public String getPlayLink() {
        return playLink;
    }

    /*
             public void setTime(String time) {
                 this.time = time;
             }

             public String getTime() {
                 return time;
             }

             public void setCategory(String category) {
                 this.category = category;
             }

             public String getCategory() {
                 return category;
             }

             public void setArea(String area) {
                 this.area = area;
             }

             public String getArea() {
                 return area;
             }

             public void setDirector(String director) {
                 this.director = director;
             }

             public String getDirector() {
                 return director;
             }

             public void setActor(String actor) {
                 this.actor = actor;
             }

             public String getActor() {
                 return actor;
             }

             public void setIntro(String intro) {
                 this.intro = intro;
             }

             public String getIntro() {
                 return intro;
             }
         */
    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getProgramID() {
        return programID;
    }

}
