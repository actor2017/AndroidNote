package com.liaoin.microservice.bean.entity.circle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaoin.microservice.bean.entity.backstage.Region;
import com.liaoin.microservice.bean.entity.backstage.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 圈子点赞实体类
 *
 * @author 张权立
 * @date 2018/7/12 18:12
 */
@Document
@ApiModel("圈子点赞实体类")
public class CircleLike {

    @Id
    @ApiModelProperty("系统标识")
    private String id;

    @Field
    @ApiModelProperty("点赞用户编号")
    private Integer userId;
    @Field
    @ApiModelProperty("点赞用户昵称")
    private String nickname;
    @Field
    @ApiModelProperty("点赞用户头像")
    private String imagesUrl;
    @DBRef
    @ApiModelProperty("点赞用户")
    private User likeUser;

    @Field
    @ApiModelProperty("点赞用户所属区域编号")
    private Integer regionId;
    @Field
    @ApiModelProperty("点赞用户所属区域名称")
    private String regionName;
    @Field
    @ApiModelProperty("点赞时间")
    private Date likeTime;
    @DBRef
    @ApiModelProperty("点赞用户所属区域")
    private Region region;

    @Field
    @ApiModelProperty("圈子消息编号")
    private String circleMessageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return likeUser == null ? null : likeUser.getNickname();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImagesUrl() {
        return likeUser == null ? null : likeUser.getImagesUrl();
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return region == null ? null : region.getName();
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getLikeTime() {
        return likeTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public String getCircleMessageId() {
        return circleMessageId;
    }

    public void setCircleMessageId(String circleMessageId) {
        this.circleMessageId = circleMessageId;
    }

    public User getLikeUser() {
        return likeUser;
    }

    public void setLikeUser(User likeUser) {
        this.likeUser = likeUser;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
