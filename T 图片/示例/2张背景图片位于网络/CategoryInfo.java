package cn.itheima.redboy.bean;

import java.util.List;

/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2017
 * Company    : 公司名称
 * Author     : 董小雷
 * Date       : 2017/3/9 on 10:14.
 */
public class CategoryInfo {

    /**
     * 访问网络后获取的gson:
     *
     * {"message":"分类查询成功","status":200,"categories":[{"desc":"妈妈内衣 祛纹纤体",
     * "iconUrl":"category\/icon_category_mon_red.png,category\/icon_category_mon_white.png",
     * "id":22,"isLeaf":0,"name":"孕妈专区","parentId":1},{"desc":"奶粉辅食 婴幼儿营养",
     * "iconUrl":"category\/icon_category_yinyou_red.png,category\/icon_category_yinyou_white
     * .png","id":23,"isLeaf":0,"name":"婴幼食品","parentId":1},{"desc":"尿裤 喂养用品 纸巾",
     * "iconUrl":"category\/icon_category_baby_red.png,category\/icon_category_baby_white.png",
     * "id":24,"isLeaf":0,"name":"宝宝用品","parentId":1},{"desc":"婴儿床椅 婴儿车",
     * "iconUrl":"category\/icon_category_toy_red.png,category\/icon_category_toy_white.png",
     * "id":25,"isLeaf":0,"name":"玩具童车","parentId":1},{"desc":"童鞋 婴幼儿服饰",
     * "iconUrl":"category\/icon_category_sleep_red.png,category\/icon_category_sleep_white.png",
     * "id":26,"isLeaf":0,"name":"寝具服饰","parentId":1}]}
     */

    public String message;
    public int status;
    public List<CategoriesBean> categories;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean {
        /**
         * desc : 妈妈内衣 祛纹纤体
         * iconUrl : category/icon_category_mon_red.png,category/icon_category_mon_white.png
         * id : 22
         * isLeaf : 0
         * name : 孕妈专区
         * parentId : 1
         */

        public String desc;
        public String iconUrl;
        public int id;
        public int isLeaf;
        public String name;
        public int parentId;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsLeaf() {
            return isLeaf;
        }

        public void setIsLeaf(int isLeaf) {
            this.isLeaf = isLeaf;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }
}
