package cn.fuser.vx.wxbot;

import com.alibaba.fastjson.annotation.JSONField;

public class RecommendInfo {
    @JSONField(name = "Alias")
    private String alias;
    @JSONField(name = "AttrStatus")
    private int attrStatus;
    @JSONField(name = "City")
    private String city;
    @JSONField(name = "Content")
    private String content;
    @JSONField(name = "NickName")
    private String nickName;
    @JSONField(name = "OpCode")
    private int opCode;
    @JSONField(name = "Province")
    private String province;
    @JSONField(name = "QQNum")
    private int qqNum;
    @JSONField(name = "Scene")
    private int scene;
    @JSONField(name = "Sex")
    private int sex;
    @JSONField(name = "Signature")
    private String signature;
    @JSONField(name = "Ticket")
    private String ticket;
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

    public int getAttrStatus() {
        return attrStatus;
    }

    public void setAttrStatus(int attrStatus) {
        this.attrStatus = attrStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getQqNum() {
        return qqNum;
    }

    public void setQqNum(int qqNum) {
        this.qqNum = qqNum;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
