package com.nkay.swyt.database.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhCheckGroup;

import java.util.ArrayList;

/**
 * 质量标准化检查管理
 */
public class BzhCheckGroupMgr {
    private Context context;

    public BzhCheckGroupMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取质量标准化检查人员信息
     * @return
     */
    public ArrayList<BzhCheckGroup> getBzhCheckGroup(String yiXuanZhuanYeId){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT BZH_CHECKGROUP.YIXUANZEPROJECTS_ID, BZH_CHECKGROUP.SELF_CHECK_EXAMINER,BZH_CHECKGROUP.SELF_CHECK_INSPECTIONLEADER,BZH_CHECKGROUP.SELF_CHECK_DEPUTYLEADER,u.USER_NAME FROM BZH_CHECKGROUP ");
        sb.append(" LEFT JOIN COMPANY_COLLIERY_USER u ON u.USER_ID = BZH_CHECKGROUP.SELF_CHECK_EXAMINER ");
        sb.append(" WHERE YIXUANZEPROJECTS_ID = '"+yiXuanZhuanYeId+"' ");
        Cursor cursor = null;
        ArrayList<BzhCheckGroup> bzhCheckGroups = new ArrayList<BzhCheckGroup>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                BzhCheckGroup bzhCheckGroup = new BzhCheckGroup();
                bzhCheckGroup.setYixuanzeprojectsId(cursor.getString(0));
                bzhCheckGroup.setSelfCheckExaminer(cursor.getString(1));
                bzhCheckGroup.setSelfCheckInspectionleader(cursor.getInt(2));
                bzhCheckGroup.setSelfCheckDeputyleader(cursor.getInt(3));
                bzhCheckGroup.setUserName(cursor.getString(4));
                bzhCheckGroups.add(bzhCheckGroup);
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
        return bzhCheckGroups;
    }
}
