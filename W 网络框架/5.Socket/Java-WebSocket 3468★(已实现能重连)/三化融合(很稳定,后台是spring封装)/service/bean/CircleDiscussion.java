package com.liaoin.microservice.bean.entity.circle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaoin.microservice.bean.entity.backstage.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 圈子评论实体类
 *
 * @author 张权立
 * @date 2018/7/9 19:34
 */
@Document
@ApiModel("圈子评论实体类")
public class CircleDiscussion implements Serializable {

    @Id
    @ApiModelProperty("系统标识")
    private String id;
    @Field
    @ApiModelProperty("文本内容")
    private String content;
    @DBRef
    @ApiModelProperty("评论用户")
    private User discussUser;
    @DBRef
    @ApiModelProperty("回复用户")
    private User replyUser;
    @Field
    @ApiModelProperty("评论时间")
    private Date discussTime;

    @Field
    @ApiModelProperty("圈子消息编号")
    private String circleMessageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getDiscussUser() {
        return discussUser;
    }

    public void setDiscussUser(User discussUser) {
        this.discussUser = discussUser;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User replyUser) {
        this.replyUser = replyUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getDiscussTime() {
        return discussTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }

    public String getCircleMessageId() {
        return circleMessageId;
    }

    public void setCircleMessageId(String circleMessageId) {
        this.circleMessageId = circleMessageId;
    }
}
