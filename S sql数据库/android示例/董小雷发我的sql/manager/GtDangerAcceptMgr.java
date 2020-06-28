package com.nkay.swyt.database.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtDangerAccept;

import java.util.ArrayList;

/**
 * 验收表管理
 */
public class GtDangerAcceptMgr {
    private Context context;

    public GtDangerAcceptMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 查询隐患ID查询隐患验收list
     * @param
     * @return
     */
    public ArrayList<GtDangerAccept> selectAcceptListByAssignHiddenId(String assignHiddenId){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select  f.DANGER_ACCEPT_ID,f.CHECK_DANGER_ID,f.ASSIGN_HIDDEN_ID,f.ACCEPT_DEPT,f.ACCEPT_RESULT,f.ACCEPT_DATE,f.ACCEPT_OPINION,f.ACCEPT_PERSON,u1.USER_NAME as acceptPersonName,f.ACCEPT_TYPE,dept.DEPARTMENT_NAME from GT_DANGER_ACCEPT f ");
        sb.append(" left join COMPANY_COLLIERY_USER u1 on u1.USER_ID = f.ACCEPT_PERSON");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = f.ACCEPT_DEPT");
        sb.append(" where f.ASSIGN_HIDDEN_ID = '"+assignHiddenId+"'");
        sb.append(" order by f.insert_datetime ");

        Cursor cursor = null;
        ArrayList<GtDangerAccept> geDangerAcceptList = new ArrayList<GtDangerAccept>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                GtDangerAccept gtDangerAccept = new GtDangerAccept();
                gtDangerAccept.setDangerAcceptId(cursor.getString(0));
                gtDangerAccept.setCheckDangerId(cursor.getString(1));
                gtDangerAccept.setAssignHiddenId(cursor.getString(2));
                gtDangerAccept.setAcceptDept(cursor.getString(3));
                gtDangerAccept.setAcceptResult(cursor.getString(4));
                gtDangerAccept.setAcceptDate(cursor.getString(5));
                gtDangerAccept.setAcceptOpinion(cursor.getString(6));
                gtDangerAccept.setAcceptPerson(cursor.getString(7));
                gtDangerAccept.setAcceptPersonName(cursor.getString(8));
                gtDangerAccept.setAcceptType(cursor.getInt(9));
                gtDangerAccept.setAcceptDeptName(cursor.getString(10));
                geDangerAcceptList.add(gtDangerAccept);
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
        return geDangerAcceptList;
    }

}
