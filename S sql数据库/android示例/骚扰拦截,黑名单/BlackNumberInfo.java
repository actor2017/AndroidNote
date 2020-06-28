package cn.itcast.mobilesafe12.bean;

import java.io.Serializable;

/**
 * Created by Kevin.
 * 本例,写在bean里面:
 *          黑名单条目对象,记录number,mode.然后把对象存入list集合
 *          获取的时候直接遍历list集合,得到本对象,然后调用number,mode

 //intent可以传递序列化后的对象, 注意: 传递的对象及成员变量的类型对象, 全都要实现Serializable接口
 */

public class BlackNumberInfo implements Serializable{

    public String number;
    public int mode;

    //用于System.out.println();
    @Override
    public String toString() {
        return "BlackNumberInfo{" +
                "number='" + number + '\'' +
                ", mode=" + mode +
                '}';
    }
}
