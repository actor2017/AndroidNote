package com.ly.hihifriend.utils.tencentim;

import com.tencent.imsdk.ext.group.TIMGroupPendencyItem;

/**
 * Created by valxehuang on 2018/7/30.
 */

public class GroupApplyInfo {
    private int                  status;
    private TIMGroupPendencyItem pendencyItem;

    public GroupApplyInfo(TIMGroupPendencyItem pendency) {
        this.pendencyItem = pendency;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getStatus() {
        return status;
    }

    public TIMGroupPendencyItem getPendencyItem() {
        return pendencyItem;
    }

}
