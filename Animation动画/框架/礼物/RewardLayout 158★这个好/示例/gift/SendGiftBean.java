package com.ly.hihifriend.gift;

/**
 * Created by zhangyf on 2017/3/30.
 */

public class SendGiftBean extends BaseGiftBean {

    private int userId;//用户id
    private int giftId;//礼物id
    private String headUrl;//头像地址
    private String userName;//用户名称
    private String giftName;//礼物名称
    private String giftImg;//礼物本地图片也可以定义为远程url
    private long giftStayTime = 3000;//礼物持续时间
    private int giftSendSize = 1;//单次礼物数目

//    /**
//     * 礼物计数
//     */
//    private int giftCount;
//    /**
//     * 礼物刷新时间
//     */
//    private long latestRefreshTime;
//    /**
//     * 当前index
//     */
//    private int currentIndex;

    public SendGiftBean() {
    }

    /**
     * @param userId
     * @param giftId
     * @param headUrl 头像url
     * @param userName 用户昵称
     * @param giftName 礼物名称
     * @param giftImg 礼物图片url
     * @param giftSendSize 发送几个礼物
     */
    public SendGiftBean(int userId, int giftId, String headUrl, String userName, String giftName, String giftImg, int giftSendSize) {
        this.userId = userId;
        this.giftId = giftId;
        this.headUrl = headUrl;
        this.userName = userName;
        this.giftName = giftName;
        this.giftImg = giftImg;
        this.giftSendSize = giftSendSize;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftImg() {
        return giftImg;
    }

    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    @Override
    public int getTheGiftId() {
        return giftId;
    }

    @Override
    public void setTheGiftId(int gid) {
        this.giftId = gid;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public int getTheUserId() {
        return userId;
    }

    @Override
    public void setTheUserId(int uid) {
        this.userId = uid;
    }

    @Override
    public int getTheSendGiftSize() {
        return giftSendSize;
    }

    @Override
    public void setTheSendGiftSize(int size) {
        giftSendSize = size;
    }

    @Override
    public long getTheGiftStay() {
        return giftStayTime;
    }

    @Override
    public void setTheGiftStay(long stay) {
        giftStayTime = stay;
    }

    @Override
    public String toString() {
        return "SendGiftBean{" +
                "userId=" + userId +
                ", giftId=" + giftId +
                ", headUrl='" + headUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", giftName='" + giftName + '\'' +
                ", giftImg='" + giftImg + '\'' +
                ", giftStayTime=" + giftStayTime +
                ", giftSendSize=" + giftSendSize +
                '}';
    }
}
