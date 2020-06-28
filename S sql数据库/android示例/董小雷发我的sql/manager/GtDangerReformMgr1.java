package com.nkay.swyt.database.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtDangerReform1;

import java.util.ArrayList;

/**
 * 整改表管理
 */
public class GtDangerReformMgr1 {
    private Context context;

    public GtDangerReformMgr1(Context context) {
        super();
        this.context = context;
    }

    /**
     * 查询隐患ID查询隐患整改list
     * @param
     * @return
     */
    public ArrayList<GtDangerReform1> selectReformListByAssignHiddenId(String assignHiddenId){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select  f.DANGER_REFORM_ID,f.CHECK_DANGER_ID,f.ASSIGN_HIDDEN_ID,f.REFORM_PERSON,f.REFORM_RESULT,f.REFORM_DATE,f.REFORM_MEASURE from GT_DANGER_REFORM f ");
        sb.append(" where f.ASSIGN_HIDDEN_ID = '"+assignHiddenId+"'");
        sb.append(" order by f.update_datatime ");
        Cursor cursor = null;
        ArrayList<GtDangerReform1> geDangerReformList = new ArrayList<GtDangerReform1>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                GtDangerReform1 gtDangerReform = new GtDangerReform1();
                gtDangerReform.setDangerReformId(cursor.getString(0));
                gtDangerReform.setCheckDangerId(cursor.getString(1));
                gtDangerReform.setAssignHiddenId(cursor.getString(2));
                gtDangerReform.setReformPerson(cursor.getString(3));
                gtDangerReform.setReformResult(cursor.getString(4));
                gtDangerReform.setReformDate(cursor.getString(5));
                gtDangerReform.setReformMeasure(cursor.getString(6));
                geDangerReformList.add(gtDangerReform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (cursor!=null) {
                cursor.close();
            }
        }
        return geDangerReformList;
    }

}
