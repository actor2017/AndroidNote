package com.liaoin.microservice.bean.entity.circle;

import io.swagger.annotations.ApiModelProperty;

/**
 * 推送消息实体类
 *
 * @author 张权立
 * @date 2018/7/12 18:01
 */
public class PushMessage {

    @ApiModelProperty("推送类型：1=点赞，2=评论")
    private String type;
    @ApiModelProperty("推送内容")
    private Object message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
