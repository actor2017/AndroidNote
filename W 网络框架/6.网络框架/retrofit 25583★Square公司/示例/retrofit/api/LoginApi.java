package com.ly.hihifriend.network.api;

import com.ly.hihifriend.info.BaseInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Description: 登录
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/15 on 9:30
 */
public interface LoginApi {

    /**
     * rxjava返回io.reactivex.Observable, retrofit返回retrofit2.Call
     */
    @GET("/LLGA/api/appDoor/login")
    Call<BaseInfo> login(@Query("username") String username, @Query("password") String password);
}
