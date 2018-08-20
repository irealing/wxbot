package cn.fuser.vx.wxbot;

import cn.fuser.vx.wxbot.exchange.SyncCheckKey;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class SyncRet {
    @JSONField(name = "AddMsgCount")
    private int addMsgCount;
    @JSONField(name = "AddMsgList")
    private List<Message> msgList;
    @JSONField(name = "SyncCheckKey")
    private SyncCheckKey syncCheckKey;
    @JSONField(name = "SyncKey")
    private SyncCheckKey syncKey;

    public int getAddMsgCount() {
        return addMsgCount;
    }

    public void setAddMsgCount(int addMsgCount) {
        this.addMsgCount = addMsgCount;
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }

    public SyncCheckKey getSyncCheckKey() {
        return syncCheckKey;
    }

    public void setSyncCheckKey(SyncCheckKey syncCheckKey) {
        this.syncCheckKey = syncCheckKey;
    }

    public SyncCheckKey getSyncKey() {
        return syncKey;
    }

    public void setSyncKey(SyncCheckKey syncKey) {
        this.syncKey = syncKey;
    }
}
