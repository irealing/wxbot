package cn.fuser.vx.wxbot;

import cn.fuser.vx.wxbot.auth.BaseResponse;
import cn.fuser.vx.wxbot.auth.SyncCheckKey;
import com.alibaba.fastjson.annotation.JSONField;

public class InitReply {
    @JSONField(name = "BaseResponse")
    private BaseResponse baseResponse;
    @JSONField(name = "User")
    private User user;
    @JSONField(name = "SyncKey")
    private SyncCheckKey syncCheckKey;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SyncCheckKey getSyncCheckKey() {
        return syncCheckKey;
    }

    public void setSyncCheckKey(SyncCheckKey syncCheckKey) {
        this.syncCheckKey = syncCheckKey;
    }
}
