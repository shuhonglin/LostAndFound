package com.moonlight.nasa.lostandfound.entity;

import java.io.Serializable;

/**
 * Created by NaSa on 2015/7/16.
 */
public class Good_Item implements Serializable {

    private int userId;
    private int goodId;
    private String nickName;
    private String goodDate;
    private String goodPlatform;
    private String userImageUrl;
    private String goodDescription;
    private String[] goodImageUrls;
    private String goodLocation;

    private int transmitNum;
    private int likeNum;
    private int leavewordNum;


    public Good_Item(){};

    /**
     *
     * @param userId  用户Id
     * @param goodId  物品Id
     * @param nickName  用户名
     * @param goodDate  公告发布日期
     * @param goodPlatform  公告发布平台
     * @param userImageUrl  用户头像
     * @param goodDescription  物品描述
     * @param goodImageUrls  物品图片
     * @param goodLocation  公告发布位置
     * @param transmitNum  转发数
     * @param likeNum  点赞数
     * @param leavewordNum  留言数
     */
    public Good_Item(int userId, int goodId, String nickName, String goodDate, String goodPlatform, String userImageUrl,
                     String goodDescription, String[] goodImageUrls, String goodLocation, int transmitNum,
                     int likeNum, int leavewordNum) {
        this.userId = userId;
        this.goodId = goodId;
        this.nickName = nickName;
        this.goodDate = goodDate;
        this.goodPlatform = goodPlatform;
        this.userImageUrl = userImageUrl;
        this.goodDescription = goodDescription;
        this.goodImageUrls = goodImageUrls;
        this.goodLocation = goodLocation;
        this.transmitNum = transmitNum;
        this.leavewordNum = leavewordNum;
        this.likeNum = likeNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGoodDate() {
        return goodDate;
    }

    public void setGoodDate(String goodDate) {
        this.goodDate = goodDate;
    }

    public String getGoodPlatform() {
        return goodPlatform;
    }

    public void setGoodPlatform(String goodPlatform) {
        this.goodPlatform = goodPlatform;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public String[] getGoodImageUrls() {
        return goodImageUrls;
    }

    public void setGoodImageUrls(String[] goodImageUrls) {
        this.goodImageUrls = goodImageUrls;
    }

    public String getGoodLocation() {
        return goodLocation;
    }

    public void setGoodLocation(String goodLocation) {
        this.goodLocation = goodLocation;
    }

    public int getTransmitNum() {
        return transmitNum;
    }

    public void setTransmitNum(int transmitNum) {
        this.transmitNum = transmitNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getLeavewordNum() {
        return leavewordNum;
    }

    public void setLeavewordNum(int leavewordNum) {
        this.leavewordNum = leavewordNum;
    }

    @Override
    public String toString() {
        return "userId: "+this.userId+
                "goodId: "+this.goodId+
                "nickName: "+this.nickName+
                "goodDate: "+this.goodDate+
                "goodPlatform: "+this.goodPlatform+
                "userImageUrl: "+this.userImageUrl+
                "goodDescription: "+this.goodDescription+
                "goodImageUrls: "+this.goodImageUrls+
                "goodLocation: "+this.goodLocation+
                "transmitNum: "+this.transmitNum+
                "leavewordNum: "+this.leavewordNum+
                "likeNum: "+this.likeNum;
    }
}
