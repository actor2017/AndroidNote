package cn.itcast.mobilesafe12;

import android.os.Environment;
import android.test.AndroidTestCase;

import cn.itcast.mobilesafe12.utils.MD5Utils;

/**
 * Created by Kevin.
 * 本java写在androidTest/java/cn.itcast.mobilesafe12/目录下
 *
 > MD5 单向加密算法

 > 能够将任意字符串或者文件加密成32位字符串

 只能加密, 不能解密

 不同数据计算出来的md5基本不一样
 所以: 如果两个数据md5一致, 就可以基本判定这两个数据一致

 md5-->数字指纹

 如果两个不同数据算出的md5一样, 就称作哈希碰撞, 但是概率极低

 md5破解:
 在线破解服务器提前计算出海量常用密码的md5,保存在服务器上,然后进行查询

 md5加盐
 md5("123456")->e10adc3949ba59abbe56e057f20f883e
 md5(abc+"123456"+id)->e10adc3949ba59abbe56e057f20f883e ->abc12345612321312
 *
 */

public class Md5Test extends AndroidTestCase {

    //此方法名称必须以test开头
    public void testMd5() {
        //e10adc3949ba59abbe56e057f20f883e
        //0-f的字符,16进制, 16的32次方个数据

        //1.测试String的MD5值
        String md5 = MD5Utils.getMd5("kfjadsfuweqoiruoiqweurio");
        System.out.println("md5:" + md5);
        System.out.println("md5 length:" + md5.length());

        //2.测试文件的MD5值
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/err11.log";
        String fileMd5 = MD5Utils.getFileMd5(path);
        System.out.println("file md5:" + fileMd5);
    }

}
