package com.yys.land.bean;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 作者：win
 * 时间：2019/4/11
 * 注释：
 */
@Entity
public class GatherBean implements Serializable {
    @Id
    private long id;
    /**
     * 坐标信息
     */
    private String coordinate;
    /**
     * 面积
     */
    private String area;
    /**
     * 周长
     */
    private String length;
    /**
     * 照片
     */
    private String photos;
    /**
     * 保存时间
     */
    private String time;
    /**
     * 标题
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "GatherBean{" +
                "id=" + id +
                ", coordinate='" + coordinate + '\'' +
                ", area='" + area + '\'' +
                ", length='" + length + '\'' +
                ", photos='" + photos + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
