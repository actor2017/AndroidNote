package cn.itcast.mobilesafe12;

import android.test.AndroidTestCase;

import java.util.Random;

import cn.itcast.mobilesafe12.db.dao.BlackNumberDao;

/**
 * Created by Kevin.
 * 本java写在androidTest/java/cn.itcast.mobilesafe12/目录下
 * 用于测试对数据库的各种操作是否正确,因为如果不Test而是生成app,会很繁琐
 * 例:
 *  测试添加100条数据:★★★★★右击testAdd()→Run★★★★★
 *
 * <p>
 * 黑名单数据库单元测试
 */

public class BlackNumberTest extends AndroidTestCase {

    public void testAdd() {
        //BlackNumberDao.getInstance(getContext()).add("110", 0);

        Random random = new Random();
        //插入100条假数据
        for (int i = 0; i < 100; i++) {
            int mode = random.nextInt(3);//0,1,2添加拦截模式:电话,短信,电话+短信

            if (i < 10) {
                BlackNumberDao.getInstance(getContext()).add("1341234567" + i, mode);
            } else {
                BlackNumberDao.getInstance(getContext()).add("135123456" + i, mode);
            }
        }
    }

    public void testDelete() {
        BlackNumberDao.getInstance(getContext()).delete("110");
    }

    public void testUpdate() {
        BlackNumberDao.getInstance(getContext()).update("110", 1);
    }

    public void testFind() {
        //返回是否找到
        boolean exist = BlackNumberDao.getInstance(getContext()).find("110");

        //断言, 参1:期望值, 参2:实际值
        assertEquals(true, exist);
    }

}
