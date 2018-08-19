package cn.fuser.vx.wxbot;

import com.alibaba.fastjson.annotation.JSONField;

public class AppInfo {
    @JSONField(name = "AppID")
    private String appID;
    @JSONField(name = "Type")
    private int type;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
