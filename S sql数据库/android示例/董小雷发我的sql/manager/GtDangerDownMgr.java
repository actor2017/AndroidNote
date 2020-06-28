package com.nkay.swyt.database.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtCheckAssign;

/**
 * FileName: GtDangerDownMgr
 * 隐患下达表管理
 */
public class GtDangerDownMgr {
    private Context context;

    public GtDangerDownMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 查询下达ID查询隐患下达信息
     * @param
     * @return
     */
    public GtCheckAssign selectGtCheckAssignById(String assignHiddenId){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select assign.ASSIGN_HIDDEN_ID,assign.CHECK_HIDDEN_ID,assign.DEAL_WITH_FLG,assign.REFORM_DEPT, ");
        sb.append(" dept.DEPARTMENT_NAME,assign.REFORM_DUTY_PERSON,u1.USER_NAME,assign.REFORM_DEADLINE,assign.REFORM_CLASSES,assign.REFORM_OPINION,assign.SUPERVISE_FLG, ");
        sb.append(" assign.SUPERVISE_DEPT,dept1.DEPARTMENT_NAME,assign.SUPERVISE_PERSON,u2.USER_NAME, assign.ACCEPT_DEPT, assign.ACCEPT_DEPT_NAME,assign.MONEY from GT_CHECK_ASSIGN assign ");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = assign.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u1 on u1.USER_ID = assign.REFORM_DUTY_PERSON");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept1 on dept1.DEPARTMENT_MANAGEMENT_ID = assign.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = assign.SUPERVISE_PERSON");
        sb.append(" where assign.ASSIGN_HIDDEN_ID = '"+assignHiddenId+"'");
        GtCheckAssign gtCheckAssign = new GtCheckAssign();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                gtCheckAssign.setAssignHiddenId(cursor.getString(0));
                gtCheckAssign.setCheckHiddenId(cursor.getString(1));
                gtCheckAssign.setDealWithFlg(cursor.getInt(2));
                gtCheckAssign.setReformDept(cursor.getString(3));
                gtCheckAssign.setReformDeptName(cursor.getString(4));
                gtCheckAssign.setReformDutyPerson(cursor.getString(5));
                gtCheckAssign.setReformDutyPersonName(cursor.getString(6));
                gtCheckAssign.setReformDeadline(cursor.getString(7));
                gtCheckAssign.setReformClasses(cursor.getString(8));
                gtCheckAssign.setReformOpinion(cursor.getString(9));
                gtCheckAssign.setSuperviseFlg(cursor.getInt(10));
                gtCheckAssign.setSuperviseDept(cursor.getString(11));
                gtCheckAssign.setSuperviseDeptName(cursor.getString(12));
                gtCheckAssign.setSupervisePerson(cursor.getString(13));
                gtCheckAssign.setSupervisePersonName(cursor.getString(14));
                gtCheckAssign.setAcceptDept(cursor.getString(15));
                gtCheckAssign.setAcceptDeptName(cursor.getString(16));
                gtCheckAssign.setMoney(cursor.getString(17));

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
        return gtCheckAssign;
    }

}
