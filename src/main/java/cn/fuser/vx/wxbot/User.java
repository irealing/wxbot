package cn.fuser.vx.wxbot;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
    /**
     * 用户对象
     */
    @JSONField(name = "AppAccountFlag")
    private int appAccountFlg;
    @JSONField(name = "ContactFlag")
    private int contactFlag;
    @JSONField(name = "HeadImgFlag")
    private int headImgFlag;
    @JSONField(name = "HeadImgUrl")
    private String headImgUrl;
    @JSONField(name = "HideInputBarFlag")
    private int hideInputBarFlag;
    @JSONField(name = "NickName")
    private String nickName;
    @JSONField(name = "PYInitial")
    private String pyInitial;
    @JSONField(name = "PYQuanPin")
    private String pyQuanPin;
    @JSONField(name = "RemarkName")
    private String remarkName;
    @JSONField(name = "RemarkPYInitial")
    private String remarkPYInitial;
    @JSONField(name = "Sex")
    private int sex;
    @JSONField(name = "Signature")
    private String signature;
    @JSONField(name = "StarFriend")
    private String starFriend;
    @JSONField(name = "Uin")
    private long uin;
    @JSONField(name = "UserName")
    private String userName;
    @JSONField(name = "VerifyFlag")
    private int verifyFlag;
    @JSONField(name = "WebWxPluginSwitch")
    private int webWxPluginSwitch;

    public int getAppAccountFlg() {
        return appAccountFlg;
    }

    public void setAppAccountFlg(int appAccountFlg) {
        this.appAccountFlg = appAccountFlg;
    }

    public int getContactFlag() {
        return contactFlag;
    }

    public void setContactFlag(int contactFlag) {
        this.contactFlag = contactFlag;
    }

    public int getHeadImgFlag() {
        return headImgFlag;
    }

    public void setHeadImgFlag(int headImgFlag) {
        this.headImgFlag = headImgFlag;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public int getHideInputBarFlag() {
        return hideInputBarFlag;
    }

    public void setHideInputBarFlag(int hideInputBarFlag) {
        this.hideInputBarFlag = hideInputBarFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPyInitial() {
        return pyInitial;
    }

    public void setPyInitial(String pyInitial) {
        this.pyInitial = pyInitial;
    }

    public String getPyQuanPin() {
        return pyQuanPin;
    }

    public void setPyQuanPin(String pyQuanPin) {
        this.pyQuanPin = pyQuanPin;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getRemarkPYInitial() {
        return remarkPYInitial;
    }

    public void setRemarkPYInitial(String remarkPYInitial) {
        this.remarkPYInitial = remarkPYInitial;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStarFriend() {
        return starFriend;
    }

    public void setStarFriend(String starFriend) {
        this.starFriend = starFriend;
    }

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVerifyFlag() {
        return verifyFlag;
    }

    public void setVerifyFlag(int verifyFlag) {
        this.verifyFlag = verifyFlag;
    }

    public int getWebWxPluginSwitch() {
        return webWxPluginSwitch;
    }

    public void setWebWxPluginSwitch(int webWxPluginSwitch) {
        this.webWxPluginSwitch = webWxPluginSwitch;
    }
}
