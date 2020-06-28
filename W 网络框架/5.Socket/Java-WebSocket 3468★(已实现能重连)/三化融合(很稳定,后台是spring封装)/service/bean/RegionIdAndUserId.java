package com.liaoin.microservice.bean.entity.circle;

import java.util.Objects;

/**
 * 区域编号和用户编号
 *
 * @author 张权立
 * @date 2018/7/13 11:07
 */
public class RegionIdAndUserId {

    private Integer regionId;
    private Integer userId;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegionIdAndUserId that = (RegionIdAndUserId) o;
        return Objects.equals(regionId, that.regionId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, userId);
    }

    @Override
    public String toString() {
        return "RegionIdAndUserId{" +
                "regionId=" + regionId +
                ", userId=" + userId +
                '}';
    }
}
