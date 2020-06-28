package com.nkay.swyt.database.manager;

/**
 * Created by Dell on 2017/4/27.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtExalt;

import java.util.ArrayList;

/**
 * 隐患提级管理
 */
public class GtExaltMgr {
    private Context context;

    public GtExaltMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取隐患提级信息
     * @return
     */
    public ArrayList<GtExalt> getGtExaltByHiddenId(String assignHiddenId){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.USER_NAME as exaltPersonName,s2.USER_NAME as exaltPersonOverName,s3.code_name as exaltDangerClassesName,s4.code_name as lastExaltDangerClassesName ");
        sb.append(" ,GT_EXALT.EXALT_TIME,GT_EXALT.EXALT_REASON,GT_EXALT.`STATUS`,GT_EXALT.EXALT_TIME_OVER,GT_EXALT.EXALT_OPINION from GT_EXALT  ");
        //提级申请人
        sb.append(" left join COMPANY_COLLIERY_USER s1 on s1.USER_ID = GT_EXALT.EXALT_PERSON ");
        //提级审核人
        sb.append(" left join COMPANY_COLLIERY_USER s2 on s2.USER_ID = GT_EXALT.EXALT_PERSON_OVER ");
        //提级等级
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='006' and s3.code_1 = GT_EXALT.EXALT_DANGER_CLASSES ");
        //提级前等级
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='006' and s4.code_1 = GT_EXALT.LAST_EXALT_DANGER_CLASSES ");
        sb.append(" where GT_EXALT.ASSIGN_HIDDEN_ID = '"+assignHiddenId+"' and GT_EXALT.del_flg = 0 ");
        sb.append("  order by GT_EXALT.insert_datetime ");
        Cursor cursor = null;
        ArrayList<GtExalt> gtExaltArrayList = new ArrayList<GtExalt>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                GtExalt gtExalt = new GtExalt();
                gtExalt.setExaltPersonName(cursor.getString(0));
                gtExalt.setExaltPersonOverName(cursor.getString(1));
                gtExalt.setExaltDangerClassesName(cursor.getString(2));
                gtExalt.setLastExaltDangerClassesName(cursor.getString(3));
                gtExalt.setExaltTime(cursor.getString(4));
                gtExalt.setExaltReason(cursor.getString(5));
                gtExalt.setStatus(cursor.getString(6));
                gtExalt.setExaltTimeOver(cursor.getString(7));
                gtExalt.setExaltOpinion(cursor.getString(8));
                gtExaltArrayList.add(gtExalt);
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
        return gtExaltArrayList;
    }

}
