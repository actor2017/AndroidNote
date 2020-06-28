package com.nkay.swyt.database.manager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtCheckDanger;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * 检查隐患表管理
 */
public class GtCheckDangerMgr {
    private Context context;

    public GtCheckDangerMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 根据隐患状态查询隐患信息
     *
     * @param status
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerByStatus(String status, int pageIndex, int pageSize) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,GT_CHECK_DANGER.IMMEDIATELY_FLG,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME ,GT_CHECK_DANGER.REFORM_DUTY_PERSON,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER ");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0 and GT_CHECK_DANGER.DANGER_STATUS = '" + status + "'  limit '" + pageSize + "' offset '" + pageIndex * pageSize + "' ORDER BY GT_CHECK_DANGER.update_datatime desc");

        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setImmediatelyFlg(cursor.getInt(5));
                gtCheckDanger.setDangerClassesName(cursor.getString(6));
                gtCheckDanger.setSystemClassesName(cursor.getString(7));
                gtCheckDanger.setReFormDeptName(cursor.getString(8));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(9));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(10));
                gtCheckDanger.setSupervisePerson(cursor.getString(11));
                gtCheckDanger.setSupervisePersonName(cursor.getString(12));
                gtCheckDanger.setReformClassesName(cursor.getString(13));
                gtCheckDanger.setCheckSite(cursor.getString(14));
                gtCheckDanger.setDangerDescription(cursor.getString(15));
                gtCheckDanger.setReformDeadline(cursor.getString(16));
                gtCheckDanger.setReformOpinion(cursor.getString(17));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(18));
                gtCheckDanger.setDangerFromName(cursor.getString(19));
                gtCheckDanger.setDangerStatusName(cursor.getString(20));
                gtCheckDanger.setCheckHiddenId(cursor.getString(21));
                gtCheckDanger.setPlanName(cursor.getString(22));
                gtCheckDanger.setReformDutyPerson(cursor.getString(23));
                gtCheckDanger.setRemark(cursor.getString(24));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }


    /**
     * 根据隐患状态查询隐患信息(未分配)
     *
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerByNoAllot(int pageIndex, int pageSize, String editText) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.REFORM_DEPT,GT_CHECK_DANGER.SUPERVISE_DEPT,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,GT_CHECK_DANGER.IMMEDIATELY_FLG,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME ,GT_CHECK_DANGER.REFORM_DUTY_PERSON ,g1.ADDRESS_NAME,g2.ADDRESS_NAME ,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER ");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.CHECK_SITE ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0 and (((GT_CHECK_DANGER.REFORM_DEPT != '' and GT_CHECK_DANGER.REFORM_DEPT != '" + null + "') and (GT_CHECK_DANGER.REFORM_DUTY_PERSON == '" + null + "' or GT_CHECK_DANGER.REFORM_DUTY_PERSON == '')) ");
        sb.append(" or ((GT_CHECK_DANGER.SUPERVISE_DEPT != '' and GT_CHECK_DANGER.SUPERVISE_DEPT != '" + null + "') and (GT_CHECK_DANGER.SUPERVISE_PERSON == '" + null + "' or GT_CHECK_DANGER.SUPERVISE_PERSON == ''))) ");
        sb.append(" and GT_CHECK_DANGER.DANGER_STATUS='01' and ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime desc limit '" + pageSize + "' offset '" + pageIndex * pageSize + "'  ");
        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setReformDept(cursor.getString(1));
                gtCheckDanger.setSuperviseDept(cursor.getString(2));
                gtCheckDanger.setCheckDate(cursor.getString(3));
                gtCheckDanger.setCheckUserName(cursor.getString(4));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(5));
                gtCheckDanger.setCheckClassesName(cursor.getString(6));
                gtCheckDanger.setImmediatelyFlg(cursor.getInt(7));
                gtCheckDanger.setDangerClassesName(cursor.getString(8));
                gtCheckDanger.setSystemClassesName(cursor.getString(9));
                gtCheckDanger.setReFormDeptName(cursor.getString(10));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(11));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(12));
                gtCheckDanger.setSupervisePerson(cursor.getString(13));
                gtCheckDanger.setSupervisePersonName(cursor.getString(14));
                gtCheckDanger.setReformClassesName(cursor.getString(15));
                gtCheckDanger.setCheckSite(cursor.getString(16));
                gtCheckDanger.setDangerDescription(cursor.getString(17));
                gtCheckDanger.setReformDeadline(cursor.getString(18));
                gtCheckDanger.setReformOpinion(cursor.getString(19));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(20));
                gtCheckDanger.setDangerFromName(cursor.getString(21));
                gtCheckDanger.setDangerStatusName("未分配");
                gtCheckDanger.setCheckHiddenId(cursor.getString(23));
                gtCheckDanger.setPlanName(cursor.getString(24));
                gtCheckDanger.setReformDutyPerson(cursor.getString(25));
                gtCheckDanger.setDangerAddressName(cursor.getString(26));
                gtCheckDanger.setDangerAreaName(cursor.getString(27));
                gtCheckDanger.setRemark(cursor.getString(28));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }


    /**
     * 根据隐患状态和人员Id查询隐患信息(整改)
     *
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerByPersonId1(String addressId, String status, int pageIndex, int pageSize, String loginUserId, String editText) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME ,GT_CHECK_DANGER.REFORM_DUTY_PERSON,GT_CHECK_DANGER.EXALT_STS,GT_CHECK_DANGER.POSTPONE_STATUS,g1.ADDRESS_NAME,g2.ADDRESS_NAME,GT_CHECK_DANGER.ASSIGN_HIDDEN_ID,GT_CHECK_DANGER.DANGER_STATUS,GT_CHECK_DANGER.SITUATION,s8.code_name as dangerFromName2,GT_CHECK_DANGER.DANGER_FROM,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.DEAL_WITH_FLG,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER ");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join SYSTEM_CODE_MASTER s8 on s8.code_type='029' and s8.code_1 = GT_CHECK_DANGER.DANGER_FROM2 ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.DANGER_ADDRESS_ID ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0");
        if (!addressId.equals("")) {
            sb.append(" and GT_CHECK_DANGER.DANGER_ADDRESS_ID = '" + addressId + "' ");
        }
        sb.append(" and GT_CHECK_DANGER.REFORM_DUTY_PERSON = '" + loginUserId + "' and GT_CHECK_DANGER.DANGER_STATUS = '" + status + "'  and ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime desc limit '" + pageSize + "' offset '" + pageIndex * pageSize + "' ");

        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setDangerClassesName(cursor.getString(5));
                gtCheckDanger.setSystemClassesName(cursor.getString(6));
                gtCheckDanger.setReFormDeptName(cursor.getString(7));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(8));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(9));
                gtCheckDanger.setSupervisePerson(cursor.getString(10));
                gtCheckDanger.setSupervisePersonName(cursor.getString(11));
                gtCheckDanger.setReformClassesName(cursor.getString(12));
                gtCheckDanger.setCheckSite(cursor.getString(13));
                gtCheckDanger.setDangerDescription(cursor.getString(14));
                gtCheckDanger.setReformDeadline(cursor.getString(15));
                gtCheckDanger.setReformOpinion(cursor.getString(16));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(17));
                gtCheckDanger.setDangerFromName(cursor.getString(18));
                gtCheckDanger.setDangerStatusName(cursor.getString(19));
                gtCheckDanger.setCheckHiddenId(cursor.getString(20));
                gtCheckDanger.setPlanName(cursor.getString(21));
                gtCheckDanger.setReformDutyPerson(cursor.getString(22));
                gtCheckDanger.setExaltSts(cursor.getString(23));
                gtCheckDanger.setPsotponeStatus(cursor.getString(24));
                gtCheckDanger.setDangerAddressIdName(cursor.getString(25));
                gtCheckDanger.setDangerAreaName(cursor.getString(26));
                gtCheckDanger.setAssignHiddenId(cursor.getString(27));
                gtCheckDanger.setDangerStatus(cursor.getString(28));
                gtCheckDanger.setSituation(cursor.getString(29));
                gtCheckDanger.setDangerFromName2(cursor.getString(30));
                gtCheckDanger.setDangerFrom(cursor.getString(31));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(32));
                gtCheckDanger.setDealWithFlg(cursor.getInt(33));
                gtCheckDanger.setRemark(cursor.getString(34));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }

    /**
     * 查询待整改数量
     * @param userId
     * @return
     */
    public int getGtReformNum(String userId) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        //整改
        StringBuffer sb = new StringBuffer("SELECT CHECK_HIDDEN_ID FROM GT_CHECK_DANGER ");
        sb.append("WHERE GT_CHECK_DANGER.DANGER_STATUS = '02'");
        sb.append("AND GT_CHECK_DANGER.REFORM_DUTY_PERSON = '" + userId + "'");
        sb.append("AND GT_CHECK_DANGER.DEL_FLG = 0");

        Cursor cursor = null;
        ArrayList<String> gtReformList = new ArrayList<>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                gtReformList.add(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtReformList.size();
    }

    /**
     * 查询待督办数量
     * @param userId
     * @return
     */
    public int getGtSuperviseNum(String userId) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT CHECK_HIDDEN_ID FROM GT_CHECK_DANGER ");
        sb.append("WHERE (GT_CHECK_DANGER.DANGER_STATUS = '02' OR GT_CHECK_DANGER.DANGER_STATUS = '03')");
        sb.append("AND GT_CHECK_DANGER.SUPERVISE_PERSON = '" + userId + "'");
        sb.append("AND GT_CHECK_DANGER.DEL_FLG = 0");

        Cursor cursor = null;
        ArrayList<String> gtSuperviseList = new ArrayList<>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                gtSuperviseList.add(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtSuperviseList.size();
    }

    /**
     * 查询待验收的数量
     * @param userId
     * @return
     */
    public int getGtAcceptNum(String userId) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT CHECK_HIDDEN_ID FROM GT_CHECK_DANGER ");
        sb.append("LEFT JOIN COMPANY_COLLIERY_USER ccu ON ccu.USER_ID = '" + userId + "'");
        sb.append("WHERE GT_CHECK_DANGER.DANGER_STATUS = '03'");
        sb.append("AND (like(ccu.DEPARYMENT_ID,GT_CHECK_DANGER.ACCEPT_DEPT)");
        sb.append("OR ccu.DEPARYMENT_ID = GT_CHECK_DANGER .SUPERVISE_DEPT)");
        sb.append("AND GT_CHECK_DANGER.DEL_FLG = 0");

        Cursor cursor = null;
        ArrayList<String> gtAcceptList = new ArrayList<>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                gtAcceptList.add(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtAcceptList.size();
    }


    /**
     * 根据隐患状态和人员Id查询隐患信息(督办)
     *
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerByPersonId2(String status, int pageIndex, int pageSize, String loginUserId, String editText) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME ,GT_CHECK_DANGER.REFORM_DUTY_PERSON ,g1.ADDRESS_NAME,g2.ADDRESS_NAME,GT_CHECK_DANGER.ASSIGN_HIDDEN_ID,GT_CHECK_DANGER.DANGER_STATUS,GT_CHECK_DANGER.SITUATION,s8.code_name as dangerFromName2,GT_CHECK_DANGER.DANGER_FROM,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.DEAL_WITH_FLG,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER ");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join SYSTEM_CODE_MASTER s8 on s8.code_type='029' and s8.code_1 = GT_CHECK_DANGER.DANGER_FROM2 ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.DANGER_ADDRESS_ID ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0 and GT_CHECK_DANGER.SUPERVISE_FLG = 1 and  GT_CHECK_DANGER.SUPERVISE_PERSON = '" + loginUserId + "' and (GT_CHECK_DANGER.DANGER_STATUS = '02' or GT_CHECK_DANGER.DANGER_STATUS = '03') and  ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime desc limit '" + pageSize + "' offset '" + pageIndex * pageSize + "' ");

        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setDangerClassesName(cursor.getString(5));
                gtCheckDanger.setSystemClassesName(cursor.getString(6));
                gtCheckDanger.setReFormDeptName(cursor.getString(7));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(8));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(9));
                gtCheckDanger.setSupervisePerson(cursor.getString(10));
                gtCheckDanger.setSupervisePersonName(cursor.getString(11));
                gtCheckDanger.setReformClassesName(cursor.getString(12));
                gtCheckDanger.setCheckSite(cursor.getString(13));
                gtCheckDanger.setDangerDescription(cursor.getString(14));
                gtCheckDanger.setReformDeadline(cursor.getString(15));
                gtCheckDanger.setReformOpinion(cursor.getString(16));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(17));
                gtCheckDanger.setDangerFromName(cursor.getString(18));
                gtCheckDanger.setDangerStatusName(cursor.getString(19));
                gtCheckDanger.setCheckHiddenId(cursor.getString(20));
                gtCheckDanger.setPlanName(cursor.getString(21));
                gtCheckDanger.setReformDutyPerson(cursor.getString(22));
                gtCheckDanger.setDangerAddressIdName(cursor.getString(23));
                gtCheckDanger.setDangerAreaName(cursor.getString(24));
                gtCheckDanger.setAssignHiddenId(cursor.getString(25));
                gtCheckDanger.setDangerStatus(cursor.getString(26));
                gtCheckDanger.setSituation(cursor.getString(27));
                gtCheckDanger.setDangerFromName2(cursor.getString(28));
                gtCheckDanger.setDangerFrom(cursor.getString(29));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(30));
                gtCheckDanger.setDealWithFlg(cursor.getInt(31));
                gtCheckDanger.setRemark(cursor.getString(32));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }

    /**
     * 根据隐患状态和人员Id查询隐患信息(验收)
     *
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerByPersonId3(String addressId, String status, int pageIndex, int pageSize, String loginUserId, String editText) {
        String deptId = SharedPreferencesUtil.getStringValue(context, "deparymentId");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME ,GT_CHECK_DANGER.REFORM_DUTY_PERSON,g1.ADDRESS_NAME,g2.ADDRESS_NAME,GT_CHECK_DANGER.ASSIGN_HIDDEN_ID,GT_CHECK_DANGER.DANGER_STATUS,GT_CHECK_DANGER.SITUATION,s8.code_name as dangerFromName2,GT_CHECK_DANGER.DANGER_FROM,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.DEAL_WITH_FLG,GT_CHECK_DANGER.REMARK  from GT_CHECK_DANGER ");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join SYSTEM_CODE_MASTER s8 on s8.code_type='029' and s8.code_1 = GT_CHECK_DANGER.DANGER_FROM2 ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.DANGER_ADDRESS_ID ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0");
        if (!addressId.equals("")) {
            sb.append(" and GT_CHECK_DANGER.DANGER_ADDRESS_ID = '" + addressId + "' ");
        }
        sb.append("  and GT_CHECK_DANGER.ACCEPT_DEPT like '%" + deptId + "%' and GT_CHECK_DANGER.DANGER_STATUS = '" + status + "' and ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime desc limit '" + pageSize + "' offset '" + pageIndex * pageSize + "' ");

        Cursor cursor = null;

        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setDangerClassesName(cursor.getString(5));
                gtCheckDanger.setSystemClassesName(cursor.getString(6));
                gtCheckDanger.setReFormDeptName(cursor.getString(7));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(8));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(9));
                gtCheckDanger.setSupervisePerson(cursor.getString(10));
                gtCheckDanger.setSupervisePersonName(cursor.getString(11));
                gtCheckDanger.setReformClassesName(cursor.getString(12));
                gtCheckDanger.setCheckSite(cursor.getString(13));
                gtCheckDanger.setDangerDescription(cursor.getString(14));
                gtCheckDanger.setReformDeadline(cursor.getString(15));
                gtCheckDanger.setReformOpinion(cursor.getString(16));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(17));
                gtCheckDanger.setDangerFromName(cursor.getString(18));
                gtCheckDanger.setDangerStatusName(cursor.getString(19));
                gtCheckDanger.setCheckHiddenId(cursor.getString(20));
                gtCheckDanger.setPlanName(cursor.getString(21));
                gtCheckDanger.setReformDutyPerson(cursor.getString(22));
                gtCheckDanger.setDangerAddressIdName(cursor.getString(23));
                gtCheckDanger.setDangerAreaName(cursor.getString(24));
                gtCheckDanger.setAssignHiddenId(cursor.getString(25));
                gtCheckDanger.setDangerStatus(cursor.getString(26));
                gtCheckDanger.setSituation(cursor.getString(27));
                gtCheckDanger.setDangerFromName2(cursor.getString(28));
                gtCheckDanger.setDangerFrom(cursor.getString(29));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(30));
                gtCheckDanger.setDealWithFlg(cursor.getInt(31));
                gtCheckDanger.setRemark(cursor.getString(32));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }

    /**
     * 根据隐患状态和人员Id查询隐患信息(下达)
     *
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerByPersonId4(String status, int pageIndex, int pageSize, String loginUserId, String editText) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.REFORM_DEPT,GT_CHECK_DANGER.SUPERVISE_DEPT,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME ,GT_CHECK_DANGER.REFORM_DUTY_PERSON ,g1.ADDRESS_NAME,g2.ADDRESS_NAME,GT_CHECK_DANGER.SITUATION,s8.code_name as dangerFromName2,GT_CHECK_DANGER.DANGER_FROM,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.EXALT_STS,GT_CHECK_DANGER.POSTPONE_STATUS,GT_CHECK_DANGER.ACCEPT_DEPT_NAME,GT_CHECK_DANGER.MONEY,GT_CHECK_DANGER.DEAL_WITH_FLG,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER ");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join SYSTEM_CODE_MASTER s8 on s8.code_type='029' and s8.code_1 = GT_CHECK_DANGER.DANGER_FROM2 ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.CHECK_SITE ");
        sb.append(" left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0  and GT_CHECK_DANGER.DANGER_STATUS = '" + status + "'  and ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime limit '" + pageSize + "' offset '" + pageIndex * pageSize + "' ");


        Cursor cursor = null;

        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setReformDept(cursor.getString(1));
                gtCheckDanger.setSuperviseDept(cursor.getString(2));
                gtCheckDanger.setCheckDate(cursor.getString(3));
                gtCheckDanger.setCheckUserName(cursor.getString(4));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(5));
                gtCheckDanger.setCheckClassesName(cursor.getString(6));
                gtCheckDanger.setDangerClassesName(cursor.getString(7));
                gtCheckDanger.setSystemClassesName(cursor.getString(8));
                gtCheckDanger.setReFormDeptName(cursor.getString(9));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(10));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(11));
                gtCheckDanger.setSupervisePerson(cursor.getString(12));
                gtCheckDanger.setSupervisePersonName(cursor.getString(13));
                gtCheckDanger.setReformClassesName(cursor.getString(14));
                gtCheckDanger.setCheckSite(cursor.getString(15));
                gtCheckDanger.setDangerDescription(cursor.getString(16));
                gtCheckDanger.setReformDeadline(cursor.getString(17));
                gtCheckDanger.setReformOpinion(cursor.getString(18));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(19));
                gtCheckDanger.setDangerFromName(cursor.getString(20));
                gtCheckDanger.setDangerStatusName(cursor.getString(21));
                gtCheckDanger.setCheckHiddenId(cursor.getString(22));
                gtCheckDanger.setPlanName(cursor.getString(23));
                gtCheckDanger.setReformDutyPerson(cursor.getString(24));
                gtCheckDanger.setDangerAddressName(cursor.getString(25));
                gtCheckDanger.setDangerAreaName(cursor.getString(26));
                gtCheckDanger.setSituation(cursor.getString(27));
                gtCheckDanger.setDangerFromName2(cursor.getString(28));
                gtCheckDanger.setDangerFrom(cursor.getString(29));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(30));
                gtCheckDanger.setExaltSts(cursor.getString(31));
                gtCheckDanger.setPsotponeStatus(cursor.getString(32));
                gtCheckDanger.setAcceptDeptName(cursor.getString(33));
                gtCheckDanger.setMoney(cursor.getString(34));
                gtCheckDanger.setDealWithFlg(cursor.getInt(35));
                gtCheckDanger.setRemark(cursor.getString(36));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }


    /**
     * 隐患上报列表信息
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<GtCheckDanger> getGtCheckDangerReport(String planId, int pageIndex, int pageSize, String loginUserId, String editText) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME, ");
        sb.append(" GT_CHECK_DANGER.REFORM_DUTY_PERSON,GT_CHECK_DANGER.EXALT_STS,GT_CHECK_DANGER.POSTPONE_STATUS,g1.ADDRESS_NAME,g2.ADDRESS_NAME,");
        sb.append("GT_CHECK_DANGER.ASSIGN_HIDDEN_ID,GT_CHECK_DANGER.DANGER_STATUS,GT_CHECK_DANGER.SITUATION,s8.code_name as dangerFromName2,");
        sb.append("GT_CHECK_DANGER.DANGER_FROM,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.DEAL_WITH_FLG,u5.USER_NAME ,GT_CHECK_DANGER.CANCEL_OPINION,GT_CHECK_DANGER.CANCEL_DATE,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join COMPANY_COLLIERY_USER u5 on u5.USER_ID = GT_CHECK_DANGER.CANCEL_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join SYSTEM_CODE_MASTER s8 on s8.code_type='029' and s8.code_1 = GT_CHECK_DANGER.DANGER_FROM2 ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.DANGER_ADDRESS_ID ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE GT_CHECK_DANGER.del_flg = 0 and (GT_CHECK_DANGER.PLAN_ID == '" + planId + "' and (GT_CHECK_DANGER.RELEVANCE_ID == '" + null + "' or GT_CHECK_DANGER.RELEVANCE_ID == '' or GT_CHECK_DANGER.RELEVANCE_ID == null ))  and ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime desc limit '" + pageSize + "' offset '" + pageIndex * pageSize + "'");

        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setDangerClassesName(cursor.getString(5));
                gtCheckDanger.setSystemClassesName(cursor.getString(6));
                gtCheckDanger.setReFormDeptName(cursor.getString(7));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(8));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(9));
                gtCheckDanger.setSupervisePerson(cursor.getString(10));
                gtCheckDanger.setSupervisePersonName(cursor.getString(11));
                gtCheckDanger.setReformClassesName(cursor.getString(12));
                gtCheckDanger.setCheckSite(cursor.getString(13));
                gtCheckDanger.setDangerDescription(cursor.getString(14));
                gtCheckDanger.setReformDeadline(cursor.getString(15));
                gtCheckDanger.setReformOpinion(cursor.getString(16));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(17));
                gtCheckDanger.setDangerFromName(cursor.getString(18));
                gtCheckDanger.setDangerStatusName(cursor.getString(19));
                gtCheckDanger.setCheckHiddenId(cursor.getString(20));
                gtCheckDanger.setPlanName(cursor.getString(21));
                gtCheckDanger.setReformDutyPerson(cursor.getString(22));
                gtCheckDanger.setExaltSts(cursor.getString(23));
                gtCheckDanger.setPsotponeStatus(cursor.getString(24));
                gtCheckDanger.setDangerAddressIdName(cursor.getString(25));
                gtCheckDanger.setDangerAreaName(cursor.getString(26));
                gtCheckDanger.setAssignHiddenId(cursor.getString(27));
                gtCheckDanger.setDangerStatus(cursor.getString(28));
                gtCheckDanger.setSituation(cursor.getString(29));
                gtCheckDanger.setDangerFromName2(cursor.getString(30));
                gtCheckDanger.setDangerFrom(cursor.getString(31));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(32));
                gtCheckDanger.setDealWithFlg(cursor.getInt(33));
                gtCheckDanger.setCancelPresonName(cursor.getString(34));
                gtCheckDanger.setCancelOption(cursor.getString(35));
                gtCheckDanger.setCancelDate(cursor.getString(36));
                gtCheckDanger.setRemark(cursor.getString(37));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }


    /**
     * 根据计划ID获取隐患列表
     *
     * @param id
     * @return
     */
    public GtCheckDanger getGtCheckDangerById(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG,GT_CHECK_DANGER.Id ,GT_CHECK_DANGER.CHECK_HIDDEN_ID , ");
        sb.append("GT_CHECK_DANGER.REFORM_DUTY_PERSON,GT_CHECK_DANGER.SUPERVISE_PERSON,g1.ADDRESS_NAME,g2.ADDRESS_NAME,GT_CHECK_DANGER.SITUATION,");
        sb.append("GT_CHECK_DANGER.ASSIGN_HIDDEN_ID,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.DEAL_WITH_FLG,u5.USER_NAME,GT_CHECK_DANGER.CANCEL_OPINION,GT_CHECK_DANGER.CANCEL_DATE,GT_CHECK_DANGER.DANGER_STATUS,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join COMPANY_COLLIERY_USER u5 on u5.USER_ID = GT_CHECK_DANGER.CANCEL_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.DANGER_ADDRESS_ID ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" where GT_CHECK_DANGER.del_flg = 0 and GT_CHECK_DANGER.RELEVANCE_ID = '" + id + "'");
        Cursor cursor = null;
        GtCheckDanger gtCheckDanger = new GtCheckDanger();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setDangerClassesName(cursor.getString(5));
                gtCheckDanger.setSystemClassesName(cursor.getString(6));
                gtCheckDanger.setReFormDeptName(cursor.getString(7));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(8));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(9));
                gtCheckDanger.setSupervisePersonName(cursor.getString(10));
                gtCheckDanger.setReformClassesName(cursor.getString(11));
                gtCheckDanger.setCheckSite(cursor.getString(12));
                gtCheckDanger.setDangerDescription(cursor.getString(13));
                gtCheckDanger.setReformDeadline(cursor.getString(14));
                gtCheckDanger.setReformOpinion(cursor.getString(15));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(16));
                gtCheckDanger.setId(cursor.getInt(17));
                gtCheckDanger.setCheckHiddenId(cursor.getString(18));
                gtCheckDanger.setReformDutyPerson(cursor.getString(19));
                gtCheckDanger.setSupervisePerson(cursor.getString(20));
                gtCheckDanger.setDangerAddressIdName(cursor.getString(21));
                gtCheckDanger.setDangerAreaName(cursor.getString(22));
                gtCheckDanger.setSituation(cursor.getString(23));
                gtCheckDanger.setAssignHiddenId(cursor.getString(24));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(25));
                gtCheckDanger.setDealWithFlg(cursor.getInt(26));
                gtCheckDanger.setCancelPresonName(cursor.getString(27));
                gtCheckDanger.setCancelOption(cursor.getString(28));
                gtCheckDanger.setCancelDate(cursor.getString(29));
                gtCheckDanger.setDangerStatus(cursor.getString(30));
                gtCheckDanger.setRemark(cursor.getString(31));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDanger;
    }

    /**
     * 所有隐患列表信息
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ArrayList<GtCheckDanger> getAllGtCheckDanger(String userId, int pageIndex, int pageSize, String editText) {
        String deptId = SharedPreferencesUtil.getStringValue(context, "deparymentId");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select s1.code_name as hiddenDangerClassesName,GT_CHECK_DANGER.CHECK_DATE,GT_CHECK_DANGER.checkUserName,");
        sb.append("s5.code_name as hiddenDangerMajorName,cc.DUTY_ORDER_NAME as checkClassesName,s2.code_name ||'-'||s3.code_name as dangerClassesName,");
        sb.append("s4.code_name as systemClassesName,dept.DEPARTMENT_NAME as reformDeptName,u2.USER_NAME reformDutyPersonName,");
        sb.append("dept2.DEPARTMENT_NAME superviseDeptName,GT_CHECK_DANGER.SUPERVISE_PERSON,u3.USER_NAME supervisePersonName,");
        sb.append("u4.code_name as reformClassesName,GT_CHECK_DANGER.CHECK_SITE,GT_CHECK_DANGER.DANGER_DESCRIPTION,GT_CHECK_DANGER.REFORM_DEADLINE,");
        sb.append("GT_CHECK_DANGER.REFORM_OPINION,GT_CHECK_DANGER.SUPERVISE_FLG ,s6.code_name,s7.code_name ,GT_CHECK_DANGER.CHECK_HIDDEN_ID ,plan.PLANNAME , ");
        sb.append("GT_CHECK_DANGER.REFORM_DUTY_PERSON,GT_CHECK_DANGER.EXALT_STS,GT_CHECK_DANGER.POSTPONE_STATUS,g1.ADDRESS_NAME,g2.ADDRESS_NAME,GT_CHECK_DANGER.ASSIGN_HIDDEN_ID,");
        sb.append("GT_CHECK_DANGER.DANGER_STATUS,GT_CHECK_DANGER.SITUATION,s8.code_name as dangerFromName2,GT_CHECK_DANGER.DANGER_FROM,GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES,GT_CHECK_DANGER.DEAL_WITH_FLG,u5.USER_NAME,GT_CHECK_DANGER.CANCEL_OPINION,GT_CHECK_DANGER.CANCEL_DATE,GT_CHECK_DANGER.REMARK from GT_CHECK_DANGER");
        sb.append(" LEFT JOIN ( SELECT QUALITYPLAN_ID planId, PLAN_NAME PLANNAME, CHECKED_SECTION collieryId,IS_EXECUTE  CHECK_STATUS FROM BZH_PLAN ");
        sb.append("  UNION SELECT HIDDEN_CHECK_PLANID planId, PLAN_NAME PLANNAME, COMPANY_COLLIERY_ID collieryId,TASK_STATUS CHECK_STATUS FROM YHPC_HIDDEN_PLAN ");
        sb.append("  UNION SELECT SCENE_CHECK_ID planId, CHECK_NAME PLANNAME, COLLIERY_ID collieryId,CHECK_STATUS  CHECK_STATUS FROM FJGK_SCECE_CHECK) ");
        sb.append("  AS plan ON plan.planId = GT_CHECK_DANGER.PLAN_ID");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type='006' and s1.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_CLASSES");
        sb.append(" left join COMPANY_COLLIERY_USER u on u.USER_ID = GT_CHECK_DANGER.INSERT_USER_ID");
        sb.append(" left join BZH_COLLIERY_CLASSES cc on cc.DUTY_ORDER_ID = GT_CHECK_DANGER.CHECK_CLASSES");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type='013' and s2.code_1 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type='014' and s3.code_1 = GT_CHECK_DANGER.DANGER_CLASSES2 and s3.code_2 = GT_CHECK_DANGER.DANGER_CLASSES1");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type='015' and s4.code_1 = GT_CHECK_DANGER.SYSTEM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept on dept.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.REFORM_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u2 on u2.USER_ID = GT_CHECK_DANGER.REFORM_DUTY_PERSON");
        sb.append(" left join COMPANY_COLLIERY_USER u5 on u5.USER_ID = GT_CHECK_DANGER.CANCEL_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER u4 on u4.code_type='026' AND u4.code_1 = GT_CHECK_DANGER.REFORM_CLASSES");
        sb.append(" left join COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT dept2 on dept2.DEPARTMENT_MANAGEMENT_ID = GT_CHECK_DANGER.SUPERVISE_DEPT");
        sb.append(" left join COMPANY_COLLIERY_USER u3 on u3.USER_ID = GT_CHECK_DANGER.SUPERVISE_PERSON");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type='010' and s5.code_1 = GT_CHECK_DANGER.HIDDEN_DANGER_MAJOR  and s5.code_2  = (select COLLIERY_TYPE from COMPANY_COLLIERY)");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type='021' and s6.code_1 = GT_CHECK_DANGER.DANGER_FROM");
        sb.append(" left join SYSTEM_CODE_MASTER s7 on s7.code_type='027' and s7.code_1 = GT_CHECK_DANGER.DANGER_STATUS ");
        sb.append(" left join SYSTEM_CODE_MASTER s8 on s8.code_type='029' and s8.code_1 = GT_CHECK_DANGER.DANGER_FROM2 ");
        sb.append(" left join GT_ADDRESS g1 on g1.ADDRESS_ID = GT_CHECK_DANGER.DANGER_ADDRESS_ID ");
        sb.append("  left join GT_ADDRESS g2 on g2.ADDRESS_ID = GT_CHECK_DANGER.DANGER_AREA ");
        sb.append(" WHERE (GT_CHECK_DANGER.SUPERVISE_PERSON = '" + userId + "' or GT_CHECK_DANGER.REFORM_DUTY_PERSON = '" + userId + "' or GT_CHECK_DANGER.checkUsers like '%" + userId + "%' or GT_CHECK_DANGER.ACCEPT_DEPT like '%" + deptId + "%') and  GT_CHECK_DANGER.del_flg = 0  and ( PLANNAME like '%" + editText + "%' or s1.code_name like '%" + editText + "%' or s6.code_name like '%" + editText + "%' or GT_CHECK_DANGER.DANGER_DESCRIPTION like '%" + editText + "%' or g1.ADDRESS_NAME like '%" + editText + "%' or g2.ADDRESS_NAME like '%" + editText + "%' ) ORDER BY GT_CHECK_DANGER.update_datatime desc limit '" + pageSize + "' offset '" + pageIndex * pageSize + "'");

        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setHiddenDangerClassesName(cursor.getString(0));
                gtCheckDanger.setCheckDate(cursor.getString(1));
                gtCheckDanger.setCheckUserName(cursor.getString(2));
                gtCheckDanger.setHiddenDangerMajorName(cursor.getString(3));
                gtCheckDanger.setCheckClassesName(cursor.getString(4));
                gtCheckDanger.setDangerClassesName(cursor.getString(5));
                gtCheckDanger.setSystemClassesName(cursor.getString(6));
                gtCheckDanger.setReFormDeptName(cursor.getString(7));
                gtCheckDanger.setReformDutyPersonName(cursor.getString(8));
                gtCheckDanger.setSuperviseDeptName(cursor.getString(9));
                gtCheckDanger.setSupervisePerson(cursor.getString(10));
                gtCheckDanger.setSupervisePersonName(cursor.getString(11));
                gtCheckDanger.setReformClassesName(cursor.getString(12));
                gtCheckDanger.setCheckSite(cursor.getString(13));
                gtCheckDanger.setDangerDescription(cursor.getString(14));
                gtCheckDanger.setReformDeadline(cursor.getString(15));
                gtCheckDanger.setReformOpinion(cursor.getString(16));
                gtCheckDanger.setSuperviseFlg(cursor.getInt(17));
                gtCheckDanger.setDangerFromName(cursor.getString(18));
                gtCheckDanger.setDangerStatusName(cursor.getString(19));
                gtCheckDanger.setCheckHiddenId(cursor.getString(20));
                gtCheckDanger.setPlanName(cursor.getString(21));
                gtCheckDanger.setReformDutyPerson(cursor.getString(22));
                gtCheckDanger.setExaltSts(cursor.getString(23));
                gtCheckDanger.setPsotponeStatus(cursor.getString(24));
                gtCheckDanger.setDangerAddressIdName(cursor.getString(25));
                gtCheckDanger.setDangerAreaName(cursor.getString(26));
                gtCheckDanger.setAssignHiddenId(cursor.getString(27));
                gtCheckDanger.setDangerStatus(cursor.getString(28));
                gtCheckDanger.setSituation(cursor.getString(29));
                gtCheckDanger.setDangerFromName2(cursor.getString(30));
                gtCheckDanger.setDangerFrom(cursor.getString(31));
                gtCheckDanger.setHiddenDangerClasses(cursor.getString(32));
                gtCheckDanger.setDealWithFlg(cursor.getInt(33));
                gtCheckDanger.setCancelPresonName(cursor.getString(34));
                gtCheckDanger.setCancelOption(cursor.getString(35));
                gtCheckDanger.setCancelDate(cursor.getString(36));
                gtCheckDanger.setRemark(cursor.getString(37));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }


    /**
     * 计算隐患(当月)隐患总数
     */
    public ArrayList<GtCheckDanger> selectGtCheckDangerCount() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        String deptId = SharedPreferencesUtil.getStringValue(context, "deparymentId");
//        StringBuffer sb = new StringBuffer("select GT_CHECK_DANGER.CHECK_HIDDEN_ID from GT_CHECK_DANGER");
//        sb.append(" where GT_CHECK_DANGER.insert_datetime between datetime('now','start of month','+1 second')");
//        sb.append(" and datetime('now','start of month','+1 month','-1 second') ");
        StringBuffer sb = new StringBuffer("select GT_CHECK_DANGER.CHECK_HIDDEN_ID from GT_CHECK_DANGER");
        sb.append(" where (GT_CHECK_DANGER.SUPERVISE_PERSON = '" + userId + "' or GT_CHECK_DANGER.REFORM_DUTY_PERSON = '" + userId + "' or GT_CHECK_DANGER.checkUsers like '%" + userId + "%' or GT_CHECK_DANGER.ACCEPT_DEPT like '%" + deptId + "%') and  GT_CHECK_DANGER.del_flg = 0 ");
        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setCheckHiddenId(cursor.getString(0));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }

    /**
     * 计算隐患(当月)隐患整改总数
     */
    public ArrayList<GtCheckDanger> selectGtCheckDangerChangeCount() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        String deptId = SharedPreferencesUtil.getStringValue(context, "deparymentId");
//        StringBuffer sb = new StringBuffer("select GT_CHECK_DANGER.CHECK_HIDDEN_ID from GT_CHECK_DANGER  where (GT_CHECK_DANGER.DANGER_STATUS = '03' or GT_CHECK_DANGER.DANGER_STATUS = '04' or GT_CHECK_DANGER.DANGER_STATUS = '05') and ");
//        sb.append(" GT_CHECK_DANGER.insert_datetime between datetime('now','start of month','+1 second') and ");
//        sb.append(" datetime('now','start of month','+1 month','-1 second')  ");
        StringBuffer sb = new StringBuffer("select GT_CHECK_DANGER.CHECK_HIDDEN_ID from GT_CHECK_DANGER  where (GT_CHECK_DANGER.DANGER_STATUS = '03' or GT_CHECK_DANGER.DANGER_STATUS = '04' or GT_CHECK_DANGER.DANGER_STATUS = '05') and  ");
        sb.append(" (GT_CHECK_DANGER.SUPERVISE_PERSON = '" + userId + "' or GT_CHECK_DANGER.REFORM_DUTY_PERSON = '" + userId + "' or GT_CHECK_DANGER.checkUsers like '%" + userId + "%' or GT_CHECK_DANGER.ACCEPT_DEPT like '%" + deptId + "%') and  GT_CHECK_DANGER.del_flg = 0 ");
        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setCheckHiddenId(cursor.getString(0));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }

    /**
     * 计算隐患(当月)隐患验收总数
     */
    public ArrayList<GtCheckDanger> selectGtCheckDangerAcceptCount() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        String userId = SharedPreferencesUtil.getStringValue(context, "userId");
        String deptId = SharedPreferencesUtil.getStringValue(context, "deparymentId");
//        StringBuffer sb = new StringBuffer("select GT_CHECK_DANGER.CHECK_HIDDEN_ID from GT_CHECK_DANGER  where ( GT_CHECK_DANGER.DANGER_STATUS = '04' or GT_CHECK_DANGER.DANGER_STATUS = '05') and ");
//        sb.append(" GT_CHECK_DANGER.insert_datetime between datetime('now','start of month','+1 second') and ");
//        sb.append(" datetime('now','start of month','+1 month','-1 second')  ");
        StringBuffer sb = new StringBuffer("select GT_CHECK_DANGER.CHECK_HIDDEN_ID from GT_CHECK_DANGER  where ( GT_CHECK_DANGER.DANGER_STATUS = '04' or GT_CHECK_DANGER.DANGER_STATUS = '05') and ");
        sb.append(" (GT_CHECK_DANGER.SUPERVISE_PERSON = '" + userId + "' or GT_CHECK_DANGER.REFORM_DUTY_PERSON = '" + userId + "' or GT_CHECK_DANGER.checkUsers like '%" + userId + "%' or GT_CHECK_DANGER.ACCEPT_DEPT like '%" + deptId + "%') and  GT_CHECK_DANGER.del_flg = 0 ");
        Cursor cursor = null;
        ArrayList<GtCheckDanger> gtCheckDangerList = new ArrayList<GtCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                GtCheckDanger gtCheckDanger = new GtCheckDanger();
                gtCheckDanger.setCheckHiddenId(cursor.getString(0));
                gtCheckDangerList.add(gtCheckDanger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return gtCheckDangerList;
    }

}
