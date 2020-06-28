package com.nkay.swyt.database.manager;

/**
 * FileName: FXfjControlAdapter
 * Author: zhangwenping
 * Dtae: 2017/4/26
 * Email: httputils@qq.com
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.FjgkSceneCheck;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * 风险现场检查管理
 */
public class FJGKSceneCheckMgr {
    private Context context;

    public FJGKSceneCheckMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取风险现场检查列表信息
     * @return
     */
    public ArrayList<FjgkSceneCheck> getAllFjgkCheck(int pageIndex, int pageSize,String editText){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        StringBuffer sb = new StringBuffer("select f.SCENE_CHECK_ID,f.CHECK_NAME,f.CHECK_DATE,d.DEPARTMENT_NAME,s.code_name ,f.Id,u.USER_NAME from ");
        sb.append(" FJGK_SCECE_CHECK f ");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT d on f.DUTY_DEPT = d.DEPARTMENT_MANAGEMENT_ID ");
        sb.append(" left join SYSTEM_CODE_MASTER s on  s.code_type = '005' and s.code_1 = f.CHECK_STATUS ");
        sb.append(" left join COMPANY_COLLIERY_USER u on f.DUTY_PERSON = u.USER_ID ");
        sb.append(" where (f.DETY_MEMBER like '%"+userId+"%' or f.DUTY_PERSON = '"+userId+"' )");
        sb.append(" and (f.CHECK_NAME like '%"+editText+"%' or d.DEPARTMENT_NAME like '%"+editText+"%' or u.USER_NAME like '%"+editText+"%') ORDER BY f.CHECK_STATUS ,f.CHECK_DATE DESC limit '"+pageSize+"' offset '"+pageIndex*pageSize+"' ");
        Cursor cursor = null;
        ArrayList<FjgkSceneCheck> fjgkSceneCheckList = new ArrayList<FjgkSceneCheck>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                FjgkSceneCheck fjgkSceneCheck = new FjgkSceneCheck();
                fjgkSceneCheck.setSceneCheckId(cursor.getString(0));
                fjgkSceneCheck.setCheckName(cursor.getString(1));
                fjgkSceneCheck.setCheckDate(cursor.getString(2));
                fjgkSceneCheck.setDutyDeptName(cursor.getString(3));
                fjgkSceneCheck.setCheckStatusName(cursor.getString(4));
                fjgkSceneCheck.setId(cursor.getInt(5));
                fjgkSceneCheck.setDutyPersonName(cursor.getString(6));
                fjgkSceneCheckList.add(fjgkSceneCheck);
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
        return fjgkSceneCheckList;
    }


    public ArrayList<FjgkSceneCheck> getFjgkCheckNum(){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");

        StringBuffer sb = new StringBuffer("select f.SCENE_CHECK_ID,f.CHECK_NAME,f.CHECK_DATE,d.DEPARTMENT_NAME,s.code_name , f.Id from ");
        sb.append(" FJGK_SCECE_CHECK f ");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT d on f.DUTY_DEPT = d.DEPARTMENT_MANAGEMENT_ID ");
        sb.append("left join SYSTEM_CODE_MASTER s on  s.code_type = '005' and s.code_1 = f.CHECK_STATUS ");
        sb.append(" where (f.DETY_MEMBER like '%"+userId+"%' or f.DUTY_PERSON = '"+userId+"' ) and  f.CHECK_STATUS = '01' ");

        Cursor cursor = null;
        ArrayList<FjgkSceneCheck> fjgkSceneCheckList = new ArrayList<FjgkSceneCheck>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                FjgkSceneCheck fjgkSceneCheck = new FjgkSceneCheck();
                fjgkSceneCheck.setSceneCheckId(cursor.getString(0));
                fjgkSceneCheck.setCheckName(cursor.getString(1));
                fjgkSceneCheck.setCheckDate(cursor.getString(2));
                fjgkSceneCheck.setDutyDeptName(cursor.getString(3));
                fjgkSceneCheck.setCheckStatusName(cursor.getString(4));
                fjgkSceneCheck.setId(cursor.getInt(5));
                fjgkSceneCheckList.add(fjgkSceneCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (cursor!=null) {
                cursor.close();
            }
        }
        return fjgkSceneCheckList;
    }


}