package com.ly.hihifriend.utils.tencentim;


/**
 * Created by valexhuang on 2018/8/17.
 */

public interface IUIKitCallBack<T> {

    public void onSuccess(T data);

    public void onError(String module, int errCode, String errMsg);
}
