package com.nkay.swyt.database.manager;

/**
 * Created by Dell on 2017/4/27.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtPostpone;

import java.util.ArrayList;

/**
 * 隐患延期管理
 */
public class GtPostPoneMgr {
    private Context context;

    public GtPostPoneMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取隐患延期信息
     * @return
     */
    public ArrayList<GtPostpone> getGtPostPoneByHiddenId(String assignHiddenId){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select user1.USER_NAME postponePersionName,g.APPLICATION_DATE,g.POSTPONE_DATE,g.POSTPONE_OPINION,");
        sb.append(" user2.USER_NAME auditPersionName,g.AUDIT_DATE,g.AUDIT_STATUS,g.AUDIT_OPINION FROM GT_POSTPONE g ");
        sb.append(" LEFT JOIN COMPANY_COLLIERY_USER user1 ON user1.USER_ID = g.POSTPONE_PERSION ");
        sb.append(" LEFT JOIN COMPANY_COLLIERY_USER user2 ON user2.USER_ID = g.AUDIT_PERSION ");
        sb.append(" where g.ASSIGN_HIDDEN_ID = '"+assignHiddenId+"' AND g.del_flg = 0  ");
        sb.append(" ORDER BY g.insert_datetime ");
        Cursor cursor = null;
        ArrayList<GtPostpone> gtPostponeArrayList = new ArrayList<GtPostpone>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                GtPostpone gtPostpone = new GtPostpone();
                gtPostpone.setPostponePersionName(cursor.getString(0));
                gtPostpone.setApplicationDate(cursor.getString(1));
                gtPostpone.setPostponeDate(cursor.getString(2));
                gtPostpone.setPostponeOpinion(cursor.getString(3));
                gtPostpone.setAuditPersionName(cursor.getString(4));
                gtPostpone.setAuditDate(cursor.getString(5));
                gtPostpone.setAuditStatus(cursor.getString(6));
                gtPostpone.setAuditOpinion(cursor.getString(7));
                gtPostponeArrayList.add(gtPostpone);
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
        return gtPostponeArrayList;
    }

}
