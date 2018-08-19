package cn.fuser.vx.wxbot;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通讯录
 */
public class Member {
    @JSONField(name = "Alias")
    private String alias;
    @JSONField(name = "AppAccountFlag")
    private int appAccountFlag;
    @JSONField(name = "AttrStatus")
    private long attrStatus;
    @JSONField(name = "ChatRoomId")
    private int chatRoomId;
    @JSONField(name = "City")
    private String city;
    @JSONField(name = "ContactFlag")
    private int contactFlag;
    @JSONField(name = "DisplayName")
    private String displayName;
    @JSONField(name = "EncryChatRoomId")
    private String encryChatRoomId;
    @JSONField(name = "HeadImgUrl")
    private String headImgUrl;
    @JSONField(name = "HideInputBarFlag")
    private int hideInputBarFlag;
    @JSONField(name = "IsOwner")
    private int isOwner;
    @JSONField(name = "KeyWord")
    private String keyWord;
    @JSONField(name = "MemberCount")
    private int memberCount;
    @JSONField(name = "NickName")
    private String nickName;
    @JSONField(name = "OwnerUin")
    private int ownerUin;
    @JSONField(name = "PYInitial")
    private String pYInitial;
    @JSONField(name = "PYQuanPin")
    private String pYQuanPin;
    @JSONField(name = "Province")
    private String province;
    @JSONField(name = "RemarkName")
    private String remarkName;
    @JSONField(name = "RemarkPYInitial")
    private String remarkPYInitial;
    @JSONField(name = "RemarkPYQuanPin")
    private String remarkPYQuanPin;
    @JSONField(name = "Sex")
    private int sex;
    @JSONField(name = "Signature")
    private String signature;
    @JSONField(name = "SnsFlag")
    private int snsFlag;
    @JSONField(name = "StarFriend")
    private int starFriend;
    @JSONField(name = "Statues")
    private int statues;
    @JSONField(name = "Uin")
    private int uin;
    @JSONField(name = "UniFriend")
    private int uniFriend;
    @JSONField(name = "UserName")
    private String userName;
    @JSONField(name = "VerifyFlag")
    private int verifyFlag;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getAppAccountFlag() {
        return appAccountFlag;
    }

    public void setAppAccountFlag(int appAccountFlag) {
        this.appAccountFlag = appAccountFlag;
    }

    public long getAttrStatus() {
        return attrStatus;
    }

    public void setAttrStatus(long attrStatus) {
        this.attrStatus = attrStatus;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getContactFlag() {
        return contactFlag;
    }

    public void setContactFlag(int contactFlag) {
        this.contactFlag = contactFlag;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEncryChatRoomId() {
        return encryChatRoomId;
    }

    public void setEncryChatRoomId(String encryChatRoomId) {
        this.encryChatRoomId = encryChatRoomId;
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

    public int getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOwnerUin() {
        return ownerUin;
    }

    public void setOwnerUin(int ownerUin) {
        this.ownerUin = ownerUin;
    }

    public String getpYInitial() {
        return pYInitial;
    }

    public void setpYInitial(String pYInitial) {
        this.pYInitial = pYInitial;
    }

    public String getpYQuanPin() {
        return pYQuanPin;
    }

    public void setpYQuanPin(String pYQuanPin) {
        this.pYQuanPin = pYQuanPin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getRemarkPYQuanPin() {
        return remarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String remarkPYQuanPin) {
        this.remarkPYQuanPin = remarkPYQuanPin;
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

    public int getSnsFlag() {
        return snsFlag;
    }

    public void setSnsFlag(int snsFlag) {
        this.snsFlag = snsFlag;
    }

    public int getStarFriend() {
        return starFriend;
    }

    public void setStarFriend(int starFriend) {
        this.starFriend = starFriend;
    }

    public int getStatues() {
        return statues;
    }

    public void setStatues(int statues) {
        this.statues = statues;
    }

    public int getUin() {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    public int getUniFriend() {
        return uniFriend;
    }

    public void setUniFriend(int uniFriend) {
        this.uniFriend = uniFriend;
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
}
