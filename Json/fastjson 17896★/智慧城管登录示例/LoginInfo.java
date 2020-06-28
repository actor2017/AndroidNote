package com.kuchuanyun.wisdomcitymanagement.gson;

import java.util.List;

/**
 * Description: 登录信息
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/9/6 on 10:10.
 * <p>
 * {"errCode":0,"errMsg":"成功","data":{"no":"1001","name":"系统管理员1","mobile":"13888888888",
 * "perms":["sys:dict:view","sys:user:view","sys:menu:view","cms:case:view","cms:video:view",
 * "cms:video:edit","sys:log:view","cms:wsareareport:view","sys:role:view","cms:ckfinder:view",
 * "sys:area:view","cms:caseReport:view","cms:caseAreaReport:view","sys:office:view",
 * "cms:casetype:view","cms:writ:view","cms:wsreport:view","sys:menu:edit","sys:dict:edit",
 * "sys:user:edit","sys:role:edit","cms:ckfinder:upload","sys:area:edit","cms:ckfinder:edit",
 * "cms:case:edit","sys:office:edit","cms:writ:edit","cms:casetype:edit","sys:office:print"],
 * "userId":"1","username":"prince"}}
 */

public class LoginInfo {

    /**
     * errCode : 0
     * errMsg : 成功
     * data : {"no":"1001","name":"系统管理员1","mobile":"13888888888","perms":["sys:dict:view",
     * "sys:user:view","sys:menu:view","cms:case:view","cms:video:view","cms:video:edit",
     * "sys:log:view","cms:wsareareport:view","sys:role:view","cms:ckfinder:view",
     * "sys:area:view","cms:caseReport:view","cms:caseAreaReport:view","sys:office:view",
     * "cms:casetype:view","cms:writ:view","cms:wsreport:view","sys:menu:edit","sys:dict:edit",
     * "sys:user:edit","sys:role:edit","cms:ckfinder:upload","sys:area:edit","cms:ckfinder:edit",
     * "cms:case:edit","sys:office:edit","cms:writ:edit","cms:casetype:edit","sys:office:print"],
     * "userId":"1","username":"prince"}
     */

    public int errCode;
    public String errMsg;
    public DataBean data;

    public static class DataBean {
        /**
         * no : 1001
         * name : 系统管理员1
         * mobile : 13888888888
         * perms : ["sys:dict:view","sys:user:view","sys:menu:view","cms:case:view",
         * "cms:video:view","cms:video:edit","sys:log:view","cms:wsareareport:view",
         * "sys:role:view","cms:ckfinder:view","sys:area:view","cms:caseReport:view",
         * "cms:caseAreaReport:view","sys:office:view","cms:casetype:view","cms:writ:view",
         * "cms:wsreport:view","sys:menu:edit","sys:dict:edit","sys:user:edit","sys:role:edit",
         * "cms:ckfinder:upload","sys:area:edit","cms:ckfinder:edit","cms:case:edit",
         * "sys:office:edit","cms:writ:edit","cms:casetype:edit","sys:office:print"]
         * userId : 1
         * username : prince
         */

        public String no;
        public String name;
        public String mobile;
        public String userId;
        public String username;
        public List<String> perms;
    }
}
