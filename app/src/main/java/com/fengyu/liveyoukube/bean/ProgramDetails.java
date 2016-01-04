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
public class ProgramDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String coverImg;
    private String name;
    private String released;
    private String score;
    private String genre;
    private String area;
    private String director;
    private String actors;
    private String intro;
    private String programID;


    public ProgramDetails(String coverImg, String name, String released, String score, String genre, String area, String director, String actors, String intro, String programID) {
        this.coverImg = coverImg;
        this.name = name;
        this.released = released;
        this.score = score;
        this.genre = genre;
        this.area = area;
        this.director = director;
        this.actors = actors;
        this.intro = intro;
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

    public void setScore(String score) {
        this.score = score;
    }

    public String getRating() {
        return score;
    }


    public void setReleased(String released) {
        this.released = released;
    }

    public String getReleased() {
        return released;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
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

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getActors() {
        return actors;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getProgramID() {
        return programID;
    }

}
