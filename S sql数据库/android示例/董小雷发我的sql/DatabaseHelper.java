
package com.nkay.swyt.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nkay.swyt.model.BzhCheckGroup;
import com.nkay.swyt.model.BzhCheckMajorDetail;
import com.nkay.swyt.model.BzhMajorCheckContent;
import com.nkay.swyt.model.BzhPlan;
import com.nkay.swyt.model.BzhPlanMajor;
import com.nkay.swyt.model.CollieryClasses;
import com.nkay.swyt.model.CompanyCollierUser;
import com.nkay.swyt.model.CompanyColliery;
import com.nkay.swyt.model.CompanycollierDepartmentManagement;
import com.nkay.swyt.model.FjgkSceneCheck;
import com.nkay.swyt.model.FjgkSceneCheckDanger;
import com.nkay.swyt.model.GtAccessory;
import com.nkay.swyt.model.GtAddress;
import com.nkay.swyt.model.GtCheckAssign;
import com.nkay.swyt.model.GtCheckDanger;
import com.nkay.swyt.model.GtDangerAccept;
import com.nkay.swyt.model.GtDangerReform1;
import com.nkay.swyt.model.GtDangerSupervise;
import com.nkay.swyt.model.GtExalt;
import com.nkay.swyt.model.GtPostpone;
import com.nkay.swyt.model.GtWarnInfo;
import com.nkay.swyt.model.HiddenPlan;
import com.nkay.swyt.model.HiddenPlanDetail;
import com.nkay.swyt.model.SortTable;
import com.nkay.swyt.model.SystemCodeMaster;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库操作管理工具类
 * <p>
 * 我们需要自定义一个类继承自ORMlite给我们提供的OrmLiteSqliteOpenHelper，创建一个构造方法，重写两个方法onCreate()和onUpgrade()
 * 在onCreate()方法中使用TableUtils类中的createTable()方法初始化数据表
 * 在onUpgrade()方法中我们可以先删除所有表，然后调用onCreate()方法中的代码重新创建表
 * <p>
 * 我们需要对这个类进行单例，保证整个APP中只有一个SQLite Connection对象
 * <p>
 * 这个类通过一个Map集合来管理APP中所有的DAO，只有当第一次调用这个DAO类时才会创建这个对象（并存入Map集合中）
 * 其他时候都是直接根据实体类的路径从Map集合中取出DAO对象直接调用
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // 数据库名称

    public static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/swyt.db";

//    public static final String DATABASE_NAME = "swyt.db";


    // 本类的单例实例
    private static DatabaseHelper instance;


    public static final int DATABASE_VERSION = 1;

    // 存储APP中所有的DAO对象的Map集合
    @SuppressWarnings("rawtypes")
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    // 获取本类单例对象的方法
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    // 私有的构造方法
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 根据传入的DAO的路径获取到这个DAO的单例对象（要么从daos这个Map中获取，要么新创建一个并存入daos）
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override // 创建数据库时调用的方法
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CompanyColliery.class);
            TableUtils.createTable(connectionSource, SystemCodeMaster.class);
            TableUtils.createTable(connectionSource, CollieryClasses.class);
            TableUtils.createTable(connectionSource, CompanycollierDepartmentManagement.class);
            TableUtils.createTable(connectionSource, CompanyCollierUser.class);
//            TableUtils.createTable(connectionSource, FjgkDangerList.class);
            TableUtils.createTable(connectionSource, FjgkSceneCheck.class);
            TableUtils.createTable(connectionSource, FjgkSceneCheckDanger.class);
            TableUtils.createTable(connectionSource, HiddenPlan.class);
            TableUtils.createTable(connectionSource, HiddenPlanDetail.class);
//            TableUtils.createTable(connectionSource, HiddenStock.class);
            TableUtils.createTable(connectionSource, GtCheckDanger.class);
            TableUtils.createTable(connectionSource, GtAccessory.class);
            TableUtils.createTable(connectionSource, BzhPlan.class);
            TableUtils.createTable(connectionSource, BzhPlanMajor.class);
            TableUtils.createTable(connectionSource, BzhCheckGroup.class);
            TableUtils.createTable(connectionSource, BzhCheckMajorDetail.class);
//            TableUtils.createTable(connectionSource, CheckMajorProject.class);
            TableUtils.createTable(connectionSource, SortTable.class);
            TableUtils.createTable(connectionSource, GtExalt.class);
            TableUtils.createTable(connectionSource, GtPostpone.class);
            TableUtils.createTable(connectionSource, GtAddress.class);
            TableUtils.createTable(connectionSource, GtCheckAssign.class);
            TableUtils.createTable(connectionSource, GtDangerReform1.class);
            TableUtils.createTable(connectionSource, GtDangerSupervise.class);
            TableUtils.createTable(connectionSource, GtDangerAccept.class);
            TableUtils.createTable(connectionSource, GtWarnInfo.class);
            TableUtils.createTable(connectionSource, BzhMajorCheckContent.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override // 数据库版本更新时调用的方法
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CompanyColliery.class,true);
            TableUtils.dropTable(connectionSource, SystemCodeMaster.class,true);
            TableUtils.dropTable(connectionSource, CollieryClasses.class,true);
            TableUtils.dropTable(connectionSource, CompanycollierDepartmentManagement.class,true);
            TableUtils.dropTable(connectionSource, CompanyCollierUser.class,true);
//            TableUtils.createTable(connectionSource, FjgkDangerList.class);
            TableUtils.dropTable(connectionSource, FjgkSceneCheck.class,true);
            TableUtils.dropTable(connectionSource, FjgkSceneCheckDanger.class,true);
            TableUtils.dropTable(connectionSource, HiddenPlan.class,true);
            TableUtils.dropTable(connectionSource, HiddenPlanDetail.class,true);
//            TableUtils.createTable(connectionSource, HiddenStock.class);
            TableUtils.dropTable(connectionSource, GtCheckDanger.class,true);
            TableUtils.dropTable(connectionSource, GtAccessory.class,true);
            TableUtils.dropTable(connectionSource, BzhPlan.class,true);
            TableUtils.dropTable(connectionSource, BzhPlanMajor.class,true);
            TableUtils.dropTable(connectionSource, BzhCheckGroup.class,true);
            TableUtils.dropTable(connectionSource, BzhCheckMajorDetail.class,true);
//            TableUtils.createTable(connectionSource, CheckMajorProject.class);
            TableUtils.dropTable(connectionSource, SortTable.class,true);
            TableUtils.dropTable(connectionSource, GtExalt.class,true);
            TableUtils.dropTable(connectionSource, GtPostpone.class,true);
            TableUtils.dropTable(connectionSource, GtAddress.class,true);
            TableUtils.dropTable(connectionSource, GtCheckAssign.class,true);
            TableUtils.dropTable(connectionSource, GtDangerReform1.class,true);
            TableUtils.dropTable(connectionSource, GtDangerSupervise.class,true);
            TableUtils.dropTable(connectionSource, GtDangerAccept.class,true);
            TableUtils.dropTable(connectionSource, GtWarnInfo.class,true);
            TableUtils.dropTable(connectionSource, BzhMajorCheckContent.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 释放资源
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}