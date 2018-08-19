package cn.fuser.vx.wxbot;

import com.alibaba.fastjson.annotation.JSONField;

public class Message {
    @JSONField(name = "AppInfo")
    private AppInfo appInfo;
    @JSONField(name = "AppMsgType")
    private int appMsgType;
    @JSONField(name = "Content")
    private String content;
    @JSONField(name = "CreateTime")
    private long createTime;
    @JSONField(name = "EncryFileName")
    private String encryFileName;
    @JSONField(name = "FileName")
    private String fileName;
    @JSONField(name = "FileSize")
    private String fileSize;
    @JSONField(name = "ForwardFlag")
    private int forwardFlag;
    @JSONField(name = "FromUserName")
    private String fromUserName;
    @JSONField(name = "HasProductId")
    private int hasProductId;
    @JSONField(name = "ImgHeight")
    private int imgHeight;
    @JSONField(name = "ImgStatus")
    private int imgStatus;
    @JSONField(name = "ImgWidth")
    private int imgWidth;
    @JSONField(name = "MediaId")
    private String mediaId;
    @JSONField(name = "MsgId")
    private String msgId;
    @JSONField(name = "MsgType")
    private int msgType;
    @JSONField(name = "NewMsgId")
    private long newMsgId;
    @JSONField(name = "OriContent")
    private String oriContent;
    @JSONField(name = "PlayLength")
    private int playLength;
    @JSONField(name = "Status")
    private int status;
    @JSONField(name = "StatusNotifyCode")
    private int statusnotifycode;
    @JSONField(name = "StatusNotifyUserName")
    private String statusnotifyusername;
    @JSONField(name = "SubMsgType")
    private int submsgtype;
    @JSONField(name = "Ticket")
    private String ticket;
    @JSONField(name = "ToUserName")
    private String tousername;
    @JSONField(name = "Url")
    private String url;
    @JSONField(name = "VoiceLength")
    private int voicelength;

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public int getAppMsgType() {
        return appMsgType;
    }

    public void setAppMsgType(int appMsgType) {
        this.appMsgType = appMsgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getEncryFileName() {
        return encryFileName;
    }

    public void setEncryFileName(String encryFileName) {
        this.encryFileName = encryFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getForwardFlag() {
        return forwardFlag;
    }

    public void setForwardFlag(int forwardFlag) {
        this.forwardFlag = forwardFlag;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getHasProductId() {
        return hasProductId;
    }

    public void setHasProductId(int hasProductId) {
        this.hasProductId = hasProductId;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getImgStatus() {
        return imgStatus;
    }

    public void setImgStatus(int imgStatus) {
        this.imgStatus = imgStatus;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public long getNewMsgId() {
        return newMsgId;
    }

    public void setNewMsgId(long newMsgId) {
        this.newMsgId = newMsgId;
    }

    public String getOriContent() {
        return oriContent;
    }

    public void setOriContent(String oriContent) {
        this.oriContent = oriContent;
    }

    public int getPlayLength() {
        return playLength;
    }

    public void setPlayLength(int playLength) {
        this.playLength = playLength;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusnotifycode() {
        return statusnotifycode;
    }

    public void setStatusnotifycode(int statusnotifycode) {
        this.statusnotifycode = statusnotifycode;
    }

    public String getStatusnotifyusername() {
        return statusnotifyusername;
    }

    public void setStatusnotifyusername(String statusnotifyusername) {
        this.statusnotifyusername = statusnotifyusername;
    }

    public int getSubmsgtype() {
        return submsgtype;
    }

    public void setSubmsgtype(int submsgtype) {
        this.submsgtype = submsgtype;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVoicelength() {
        return voicelength;
    }

    public void setVoicelength(int voicelength) {
        this.voicelength = voicelength;
    }
}
