package com.nkay.swyt.database.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhPlan;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * 质量标准化检查管理
 */
public class BzhPlanMgr {
    private Context context;

    public BzhPlanMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取质量标准化检查信息
     *
     * @return
     */
    public ArrayList<BzhPlan> getAllBzhPlan(int pageIndex, int pageSize,String editText) {
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT DISTINCT BZH_PLAN.QUALITYPLAN_ID,BZH_PLAN.CHECKED_SECTION,BZH_PLAN.CHECK_COMMENTS,BZH_PLAN.PLAN_NAME,BZH_PLAN.CHECK_YEAR||'-'||BZH_PLAN.CHECK_MONTH as planTime,s.code_name as checkTypeName,s2.code_name as isExecuteName FROM BZH_PLAN ");
        sb.append(" left join SYSTEM_CODE_MASTER s on s.code_type = '000' and s.code_1 = BZH_PLAN.CHECK_TYPE ");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type = '016' and s2.code_1 = BZH_PLAN.IS_EXECUTE ");
        sb.append("left join BZH_PLANMAJOR s3 on s3.QUALITYPLAN_ID = BZH_PLAN.QUALITYPLAN_ID ");
        sb.append(" where (s3.CHECK_LEAD_ID = '" + userId + "' or s3.CHECK_VICE_ID = '" + userId + "' or s3.CHECK_MEMBER_ID like '%" + userId + "%') and (BZH_PLAN.PLAN_NAME like '%"+editText+"%' or s.code_name like '%"+editText+"%') ");
        sb.append(" ORDER BY BZH_PLAN.IS_EXECUTE,BZH_PLAN.update_datatime limit '" + pageSize + "' offset '" + pageIndex * pageSize + "'");
        Cursor cursor = null;
        ArrayList<BzhPlan> planList = new ArrayList<BzhPlan>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhPlan plan = new BzhPlan();
                plan.setQualityplanId(cursor.getString(0));
                plan.setCheckedSection(cursor.getString(1));
                plan.setCheckComments(cursor.getString(2));
                plan.setPlanName(cursor.getString(3));
                plan.setCheckYearMonth(cursor.getString(4));
                plan.setCheckTypeName(cursor.getString(5));
                plan.setIsExecuteName(cursor.getString(6));
                planList.add(plan);
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
        return planList;
    }



    public ArrayList<BzhPlan> selectNotCkeckNum(){
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        StringBuffer sb = new StringBuffer("SELECT DISTINCT BZH_PLAN.QUALITYPLAN_ID FROM BZH_PLAN ");
        sb.append("left join BZH_PLANMAJOR s3 on s3.QUALITYPLAN_ID = BZH_PLAN.QUALITYPLAN_ID ");
        sb.append(" where BZH_PLAN.IS_EXECUTE = '01' and (s3.CHECK_LEAD_ID = '" + userId + "' or s3.CHECK_VICE_ID = '" + userId + "' or s3.CHECK_MEMBER_ID like '%" + userId + "%')");
        Cursor cursor = null;
        ArrayList<BzhPlan> bzhPlans = new ArrayList<BzhPlan>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                BzhPlan bzhPlan = new BzhPlan();
                bzhPlan.setQualityplanId(cursor.getString(0));
                bzhPlans.add(bzhPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (cursor!=null) {
                cursor.close();
            }
        }
        return bzhPlans;
    }

}
