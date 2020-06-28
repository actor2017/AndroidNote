package com.nkay.swyt.database.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtDangerSupervise;

import java.util.ArrayList;

/**
 * 督办表管理
 */
public class GtDangerSuperviseMgr {
    private Context context;

    public GtDangerSuperviseMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 查询隐患ID查询隐患督办list
     * @param
     * @return
     */
    public ArrayList<GtDangerSupervise> selectSuperviseListByAssignHiddenId(String assignHiddenId){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select  f.DANGER_SUPERVISE_ID,f.CHECK_DANGER_ID,f.ASSIGN_HIDDEN_ID,f.SUPERVISE_PERSON,u1.USER_NAME as supervisePersonName,f.SUPERVISE_DATE,f.SUPERVISE_MEASURE from GT_DANGER_SUPERVISE f ");
        sb.append(" left join COMPANY_COLLIERY_USER u1 on u1.USER_ID = f.SUPERVISE_PERSON");
        sb.append(" where f.ASSIGN_HIDDEN_ID = '"+assignHiddenId+"'");
        sb.append(" order by f.insert_datetime ");

        Cursor cursor = null;
        ArrayList<GtDangerSupervise> geDangerSuperviseList = new ArrayList<GtDangerSupervise>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                GtDangerSupervise gtDangerSupervise = new GtDangerSupervise();
                gtDangerSupervise.setDangerSuperviseId(cursor.getString(0));
                gtDangerSupervise.setCheckDangerId(cursor.getString(1));
                gtDangerSupervise.setAssignHiddenId(cursor.getString(2));
                gtDangerSupervise.setSupervisePerson(cursor.getString(3));
                gtDangerSupervise.setSupervisePersonName(cursor.getString(4));
                gtDangerSupervise.setSuperviseDate(cursor.getString(5));
                gtDangerSupervise.setSuperviseMeasure(cursor.getString(6));
                geDangerSuperviseList.add(gtDangerSupervise);
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
        return geDangerSuperviseList;
    }

}
