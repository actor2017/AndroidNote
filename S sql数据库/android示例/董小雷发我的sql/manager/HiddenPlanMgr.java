package com.nkay.swyt.database.manager;

/**
 * Created by Dell on 2017/4/27.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.HiddenPlan;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * 隐患现场检查管理
 */
public class HiddenPlanMgr {
    private Context context;

    public HiddenPlanMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取隐患检查列表信息
     *
     * @return
     */
    public ArrayList<HiddenPlan> getAllHiddenPlan(int pageIndex, int pageSize,String editText) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        StringBuffer sb = new StringBuffer("SELECT p.HIDDEN_CHECK_PLANID,p.PLAN_NAME,p.CHECK_TIME,s1.code_name,dept.DEPARTMENT_NAME,p.Id , user.USER_NAME FROM YHPC_HIDDEN_PLAN p ");
        sb.append(" LEFT JOIN SYSTEM_CODE_MASTER s1 ON p.TASK_STATUS = s1.code_1 AND s1.code_type = '016' ");
        sb.append(" LEFT JOIN COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept ON p.DEPARTMENT = dept.DEPARTMENT_MANAGEMENT_ID ");
        sb.append(" LEFT JOIN COMPANY_COLLIERY_USER user ON p.DEPARTMENT_MAN = user.USER_ID ");
        sb.append(" where (p.DEPARTMENT_PERSONS like '%"+userId+"%' or p.DEPARTMENT_MAN = '"+userId+"') and (p.PLAN_NAME like '%" + editText + "%' or dept.DEPARTMENT_NAME like '%" + editText + "%'  or user.USER_NAME like '%" + editText + "%')  ");
        sb.append(" ORDER BY  p.TASK_STATUS , p.CHECK_TIME DESC limit '" + pageSize + "' offset '" + pageIndex * pageSize + "' ");
        Cursor cursor = null;
        ArrayList<HiddenPlan> hiddenPlanList = new ArrayList<HiddenPlan>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                HiddenPlan hiddenPlan = new HiddenPlan();
                hiddenPlan.setHiddenCheckPlanid(cursor.getString(0));
                hiddenPlan.setPlanName(cursor.getString(1));
                hiddenPlan.setCheckTime(cursor.getString(2));
                hiddenPlan.setTaskStatusName(cursor.getString(3));
                hiddenPlan.setDepartmentName(cursor.getString(4));
                hiddenPlan.setId(cursor.getInt(5));
                hiddenPlan.setDepartmentManName(cursor.getString(6));
                hiddenPlanList.add(hiddenPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return hiddenPlanList;
    }


    /**
     * 获取隐患排查列表的未检查个数的方法
     * @return
     */
    public ArrayList<HiddenPlan> getHiddenPlanNum() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        StringBuffer sb = new StringBuffer("SELECT p.HIDDEN_CHECK_PLANID,p.PLAN_NAME,p.CHECK_TIME,s1.code_name,dept.DEPARTMENT_NAME,p.Id , user.USER_NAME FROM YHPC_HIDDEN_PLAN p ");
        sb.append(" LEFT JOIN SYSTEM_CODE_MASTER s1 ON p.TASK_STATUS = s1.code_1 AND s1.code_type = '016' ");
        sb.append(" LEFT JOIN COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept ON p.DEPARTMENT = dept.DEPARTMENT_MANAGEMENT_ID ");
        sb.append(" LEFT JOIN COMPANY_COLLIERY_USER user ON p.DEPARTMENT_MAN = user.USER_ID ");
        sb.append(" where (p.DEPARTMENT_PERSONS like '%"+userId+"%' or p.DEPARTMENT_MAN = '"+userId+"') and p.TASK_STATUS = '01' ");
        Cursor cursor = null;
        ArrayList<HiddenPlan> hiddenPlanList = new ArrayList<HiddenPlan>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                HiddenPlan hiddenPlan = new HiddenPlan();
                hiddenPlan.setHiddenCheckPlanid(cursor.getString(0));
                hiddenPlan.setPlanName(cursor.getString(1));
                hiddenPlan.setCheckTime(cursor.getString(2));
                hiddenPlan.setTaskStatusName(cursor.getString(3));
                hiddenPlan.setDepartmentName(cursor.getString(4));
                hiddenPlan.setId(cursor.getInt(5));
                hiddenPlan.setDepartmentManName(cursor.getString(6));
                hiddenPlanList.add(hiddenPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return hiddenPlanList;
    }
}
