package com.nkay.swyt.database.manager;

/**
 * Created by Dell on 2017/4/24.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.CompanyCollierUser;

import java.util.ArrayList;

/**
 * 初始化数据管理类
 */
public class CompanyUserMgr {
    private Context context;

    public CompanyUserMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取全部联系人信息
     * @return ArrayList<CompanyCollierUser>
     */
    public ArrayList<CompanyCollierUser> getAllUser(){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select u.USER_NAME,u.MOBILE_PHONE,d.DEPARTMENT_NAME from ");
        sb.append(" COMPANY_COLLIERY_USER u,");
        sb.append(" COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT d ");
        sb.append(" where u.DEPARYMENT_ID = d.DEPARTMENT_MANAGEMENT_ID and u.del_flg = 0 ");

        Cursor cursor = null;
        ArrayList<CompanyCollierUser> userList = new ArrayList<CompanyCollierUser>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                CompanyCollierUser companyCollierUser = new CompanyCollierUser();
                companyCollierUser.setUserName(cursor.getString(0));
                companyCollierUser.setMobilePhone(cursor.getString(1));
                companyCollierUser.setDeptName(cursor.getString(2));
                userList.add(companyCollierUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (cursor!=null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return userList;
    }

}
