package com.fengyu.liveyoukube.bean;

public class ChannelInfo {

    private String channelID;
    private String channelName;
    private String channelNum;
    private String channelSC;
    private String mediaAddr;

    public ChannelInfo(String channelID, String channelName, String channelNum, String channelSC, String mediaAddr) {
        // TODO Auto-generated constructor stub
        this.channelID = channelID;
        this.channelName = channelName;
        this.channelNum = channelNum;
        this.channelSC = channelSC;
        this.mediaAddr = mediaAddr;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannleSC(String channelSC) {
        this.channelSC = channelSC;
    }

    public String getChannelSC() {
        return channelSC;
    }

    public void setMediaAddr(String mediaAddr) {
        this.mediaAddr = mediaAddr;
    }

    public String getMediaAddr() {
        return mediaAddr;
    }

}
