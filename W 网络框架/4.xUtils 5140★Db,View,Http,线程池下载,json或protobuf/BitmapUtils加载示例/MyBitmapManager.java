package cn.itcast.googleplay12.manager;

import com.lidroid.xutils.BitmapUtils;

import cn.itcast.googleplay12.utils.UiUtils;

/**
 * Created by zhengping on 2017/4/6,10:22.
 * 示例:MyBitmapManager.getInstance().getBitmapUtils().display(iv, url);
 */

public class MyBitmapManager {

    private MyBitmapManager(){}

    private static MyBitmapManager instance ;

    public static MyBitmapManager getInstance() {//饿汉,饿汉式
        if(instance == null) {
            instance = new MyBitmapManager();
        }
        return instance;
    }

	//因为BitmapUtils是LRUCache管理图片,是一个容器,所以必须写成单例
    private BitmapUtils bitmapUtils;

    public BitmapUtils getBitmapUtils() {
        if(bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(UiUtils.getContext());
        }
        return bitmapUtils;
    }
}
