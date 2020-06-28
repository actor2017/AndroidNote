package com.nkay.swyt.database.manager;

/**
 * Created by Dell on 2017/4/26.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.HiddenPlanDetail;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * 风险现场风险列表
 */
public class HiddenPlanDetailMgr {
    private Context context;

    public HiddenPlanDetailMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获取隐患检查详情信息
     * @param hiddenPlanId
     * @return
     */
    public ArrayList<HiddenPlanDetail> findHiddenDetailById(String hiddenPlanId, int pageIndex, int pageSize){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID,YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_PLAN_DETAIL,s1.code_name as hiddenMajorName,");
        sb.append("s2.code_name as hiddenLevelName,s3.code_name,s6.code_name ,s4.code_name as checkResult,");
        sb.append("s5.code_name as classIficationNamm,YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_DESCRIBE ,YHPC_HIDDEN_PLAN_DETAIL.hiddenBasis,YHPC_HIDDEN_PLAN_DETAIL.hiddenStandard,YHPC_HIDDEN_PLAN_DETAIL.hiddenOpinion,");
        sb.append(" YHPC_HIDDEN_PLAN_DETAIL.step,YHPC_HIDDEN_PLAN_DETAIL.note ,YHPC_HIDDEN_PLAN_DETAIL.Id ,p.CHECK_TIME ,YHPC_HIDDEN_PLAN_DETAIL.DANGER_ADDRESS_NAME,YHPC_HIDDEN_PLAN_DETAIL.DANGER_AREA_NAME,YHPC_HIDDEN_PLAN_DETAIL.DANGER_ADDRESS,YHPC_HIDDEN_PLAN_DETAIL.DANGER_AREA ,YHPC_HIDDEN_PLAN_DETAIL.dangerAddressIdName,g.RELEVANCE_ID, YHPC_HIDDEN_PLAN_DETAIL.CHECK_RESULT from YHPC_HIDDEN_PLAN_DETAIL ");
        sb.append(" left join YHPC_HIDDEN_PLAN yhp on yhp.HIDDEN_CHECK_PLANID = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID ");
        sb.append(" left join COMPANY_COLLIERY cc on yhp.COMPANY_COLLIERY_ID = cc.COMPANY_COLLIERY_ID ");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type = '010' and s1.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_MAJOR and s1.code_2 = cc.COLLIERY_TYPE ");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type = '006' and s2.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_LEVEL");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type = '013' and s3.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_TYPE1 ");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type = '022' and s4.code_1 = YHPC_HIDDEN_PLAN_DETAIL.CHECK_RESULT ");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type = '015' and s5.code_1 = YHPC_HIDDEN_PLAN_DETAIL.CLASS_IFICATION ");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type = '014' and s6.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_TYPE2 and s6.code_2 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_TYPE1 ");
        sb.append(" LEFT JOIN YHPC_HIDDEN_PLAN p ON p.HIDDEN_CHECK_PLANID = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID ");
        sb.append(" left join GT_CHECK_DANGER g on g.RELEVANCE_ID = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_PLAN_DETAIL and g.del_flg = 0");
        sb.append(" where YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID = '"+hiddenPlanId+"' limit '"+pageSize+"' offset '"+pageIndex*pageSize+"'");

        Cursor cursor = null;
        ArrayList<HiddenPlanDetail> planDetailList = new ArrayList<HiddenPlanDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                HiddenPlanDetail hiddenPlanDetail = new HiddenPlanDetail();
                hiddenPlanDetail.setHiddenCheckPlanid(cursor.getString(0));
                hiddenPlanDetail.setHiddenPlanDetail(cursor.getString(1));
                hiddenPlanDetail.setHiddenMajorName(cursor.getString(2));
                hiddenPlanDetail.setHiddenLevelName(cursor.getString(3));
                hiddenPlanDetail.setHiddenTypeName1(cursor.getString(4));
                hiddenPlanDetail.setHiddenTypeName2(cursor.getString(5));
                hiddenPlanDetail.setCheckRsultName(cursor.getString(6));
                hiddenPlanDetail.setClassIficationName(cursor.getString(7));
                hiddenPlanDetail.setHiddenDescribe(cursor.getString(8));
                hiddenPlanDetail.setHiddenBasis(cursor.getString(9));
                hiddenPlanDetail.setHiddenStandard(cursor.getString(10));
                hiddenPlanDetail.setHiddenOpinion(cursor.getString(11));
                hiddenPlanDetail.setStep(cursor.getString(12));
                hiddenPlanDetail.setNote(cursor.getString(13));
                hiddenPlanDetail.setId(cursor.getInt(14));
                hiddenPlanDetail.setCheckTime(cursor.getString(15));
                hiddenPlanDetail.setDangerAddressName(cursor.getString(16));
                hiddenPlanDetail.setDangerAreaName(cursor.getString(17));
                hiddenPlanDetail.setDangerAddress(cursor.getString(18));
                hiddenPlanDetail.setDangerArea(cursor.getString(19));
                hiddenPlanDetail.setDangerAddressIdName(cursor.getString(20));
                hiddenPlanDetail.setRelevanceId(cursor.getString(21));
                hiddenPlanDetail.setCheckResult(cursor.getString(22));
                planDetailList.add(hiddenPlanDetail);
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
        return planDetailList;
    }


    /**
     * 根据地址Id获取隐患排查项信息
     * @param addressId
     * @return
     */
    public ArrayList<HiddenPlanDetail> findHiddenDetailByAddressId(String addressId, int pageIndex, int pageSize){
        String userId = SharedPreferencesUtil.getStringValue(context,"userId");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID,YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_PLAN_DETAIL,s1.code_name as hiddenMajorName,");
        sb.append("s2.code_name as hiddenLevelName,s3.code_name,s6.code_name ,s4.code_name as checkResult,");
        sb.append("s5.code_name as classIficationNamm,YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_DESCRIBE ,YHPC_HIDDEN_PLAN_DETAIL.hiddenBasis,YHPC_HIDDEN_PLAN_DETAIL.hiddenStandard,YHPC_HIDDEN_PLAN_DETAIL.hiddenOpinion,");
        sb.append(" YHPC_HIDDEN_PLAN_DETAIL.step,YHPC_HIDDEN_PLAN_DETAIL.note ,YHPC_HIDDEN_PLAN_DETAIL.Id ,p.CHECK_TIME ,YHPC_HIDDEN_PLAN_DETAIL.DANGER_ADDRESS_NAME,YHPC_HIDDEN_PLAN_DETAIL.DANGER_AREA_NAME,YHPC_HIDDEN_PLAN_DETAIL.DANGER_ADDRESS,YHPC_HIDDEN_PLAN_DETAIL.DANGER_AREA ,YHPC_HIDDEN_PLAN_DETAIL.dangerAddressIdName,g.RELEVANCE_ID, YHPC_HIDDEN_PLAN_DETAIL.CHECK_RESULT from YHPC_HIDDEN_PLAN_DETAIL ");
        sb.append(" left join YHPC_HIDDEN_PLAN yhp on yhp.HIDDEN_CHECK_PLANID = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID ");
        sb.append(" left join COMPANY_COLLIERY cc on yhp.COMPANY_COLLIERY_ID = cc.COMPANY_COLLIERY_ID ");
        sb.append(" left join SYSTEM_CODE_MASTER s1 on s1.code_type = '010' and s1.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_MAJOR and s1.code_2 = cc.COLLIERY_TYPE ");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type = '006' and s2.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_LEVEL");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type = '013' and s3.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_TYPE1 ");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type = '022' and s4.code_1 = YHPC_HIDDEN_PLAN_DETAIL.CHECK_RESULT ");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type = '015' and s5.code_1 = YHPC_HIDDEN_PLAN_DETAIL.CLASS_IFICATION ");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type = '014' and s6.code_1 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_TYPE2 and s6.code_2 = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_TYPE1 ");
        sb.append(" LEFT JOIN YHPC_HIDDEN_PLAN p ON p.HIDDEN_CHECK_PLANID = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_CHECK_PLANID ");
        sb.append(" left join GT_CHECK_DANGER g on g.RELEVANCE_ID = YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_PLAN_DETAIL and g.del_flg = 0");
        sb.append(" where YHPC_HIDDEN_PLAN_DETAIL.DANGER_ADDRESS = '"+addressId+"' and YHPC_HIDDEN_PLAN_DETAIL.CHECK_RESULT = '00' and (p.DEPARTMENT_PERSONS like '%"+userId+"%' or p.DEPARTMENT_MAN = '"+userId+"') and p.TASK_STATUS = '01' limit '"+pageSize+"' offset '"+pageIndex*pageSize+"'");

        Cursor cursor = null;
        ArrayList<HiddenPlanDetail> planDetailList = new ArrayList<HiddenPlanDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                HiddenPlanDetail hiddenPlanDetail = new HiddenPlanDetail();
                hiddenPlanDetail.setHiddenCheckPlanid(cursor.getString(0));
                hiddenPlanDetail.setHiddenPlanDetail(cursor.getString(1));
                hiddenPlanDetail.setHiddenMajorName(cursor.getString(2));
                hiddenPlanDetail.setHiddenLevelName(cursor.getString(3));
                hiddenPlanDetail.setHiddenTypeName1(cursor.getString(4));
                hiddenPlanDetail.setHiddenTypeName2(cursor.getString(5));
                hiddenPlanDetail.setCheckRsultName(cursor.getString(6));
                hiddenPlanDetail.setClassIficationName(cursor.getString(7));
                hiddenPlanDetail.setHiddenDescribe(cursor.getString(8));
                hiddenPlanDetail.setHiddenBasis(cursor.getString(9));
                hiddenPlanDetail.setHiddenStandard(cursor.getString(10));
                hiddenPlanDetail.setHiddenOpinion(cursor.getString(11));
                hiddenPlanDetail.setStep(cursor.getString(12));
                hiddenPlanDetail.setNote(cursor.getString(13));
                hiddenPlanDetail.setId(cursor.getInt(14));
                hiddenPlanDetail.setCheckTime(cursor.getString(15));
                hiddenPlanDetail.setDangerAddressName(cursor.getString(16));
                hiddenPlanDetail.setDangerAreaName(cursor.getString(17));
                hiddenPlanDetail.setDangerAddress(cursor.getString(18));
                hiddenPlanDetail.setDangerArea(cursor.getString(19));
                hiddenPlanDetail.setDangerAddressIdName(cursor.getString(20));
                hiddenPlanDetail.setRelevanceId(cursor.getString(21));
                hiddenPlanDetail.setCheckResult(cursor.getString(22));
                planDetailList.add(hiddenPlanDetail);
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
        return planDetailList;
    }
}
