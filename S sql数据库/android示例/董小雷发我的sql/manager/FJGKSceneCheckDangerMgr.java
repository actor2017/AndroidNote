package com.nkay.swyt.database.manager;

/**
 * Created by Dell on 2017/4/26.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.FjgkSceneCheckDanger;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.util.ArrayList;

/**
 * 风险现场风险列表
 */
public class FJGKSceneCheckDangerMgr {
    private Context context;

    public FJGKSceneCheckDangerMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 根据检查ID获取风险详情信息
     * @param SceneCheckId
     * @return
     */
    public ArrayList<FjgkSceneCheckDanger> findDangerListBysceneCheckId(String SceneCheckId, int pageIndex, int pageSize){
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select f.deptName,f.userName,f.DANGER_CONTENT,f.controlMeasure,f.identifyResult,f.remark,");
        sb.append("s1.code_name as hiddenMajorName,s2.code_name as hiddenLevelName,");
        sb.append("s3.code_name,s4.code_name,s5.code_name as classIficationName,");
        sb.append("s6.code_name as checkResult,f.dangerFormName as dangerFormName ,f.dangerAddressId,f.dangerAddressIdName,f.CHECK_DANGER_ID,f.SCENE_CHECK_ID ,c.CHECK_DATE,f.Id ,f.DANGER_ADDRESS_NAME,f.DANGER_AREA_NAME,f.DANGER_AREA,f.riskClassesName,g.RELEVANCE_ID,c.CHECK_CLASSES,f.CHECK_RESULT from ");
        sb.append(" FJGK_SCENECHECK_DANGER f left join SYSTEM_CODE_MASTER s1 on s1.code_type = '010' and s1.code_1 = f.DANGER_MAJOR ");
        sb.append(" and s1.code_2 = ( SELECT c.COLLIERY_TYPE  FROM COMPANY_COLLIERY c ) ");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type = '006' and s2.code_1 = f.DANGER_RANK ");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type = '013' and s3.code_1 = f.DANGER_CLASSES1 ");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type = '014' and s4.code_1 = f.DANGER_CLASSES2 and s4.code_2 = f.DANGER_CLASSES1 ");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type = '015' and s5.code_1 = f.SYSTEM_CLASSIFY ");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type = '022' and s6.code_1 = f.CHECK_RESULT ");
        sb.append(" left join FJGK_SCECE_CHECK c on c.SCENE_CHECK_ID = f.SCENE_CHECK_ID ");
        sb.append(" left join GT_CHECK_DANGER g on g.RELEVANCE_ID = f.CHECK_DANGER_ID and g.del_flg = 0 ");
        sb.append(" where f.SCENE_CHECK_ID = '"+SceneCheckId+"' limit '"+pageSize+"' offset '"+pageIndex*pageSize+"' ");

        Cursor cursor = null;
        ArrayList<FjgkSceneCheckDanger> checkDangerList = new ArrayList<FjgkSceneCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                FjgkSceneCheckDanger fjgkSceneCheckDanger = new FjgkSceneCheckDanger();
                fjgkSceneCheckDanger.setDeptName(cursor.getString(0));
                fjgkSceneCheckDanger.setUserName(cursor.getString(1));
                fjgkSceneCheckDanger.setDangerContent(cursor.getString(2));
                fjgkSceneCheckDanger.setControlMeasure(cursor.getString(3));
                fjgkSceneCheckDanger.setIdentifyResult(cursor.getString(4));
                fjgkSceneCheckDanger.setRemark(cursor.getString(5));
                fjgkSceneCheckDanger.setHiddenMajorName(cursor.getString(6));
                fjgkSceneCheckDanger.setDangerRankName(cursor.getString(7));
                fjgkSceneCheckDanger.setHiddenTypeName1(cursor.getString(8));
                fjgkSceneCheckDanger.setHiddenTypeName2(cursor.getString(9));
                fjgkSceneCheckDanger.setSystemClassifyName(cursor.getString(10));
                fjgkSceneCheckDanger.setCheckResultName(cursor.getString(11));
                fjgkSceneCheckDanger.setDangerFormName(cursor.getString(12));
                fjgkSceneCheckDanger.setDangerAddressId(cursor.getString(13));
                fjgkSceneCheckDanger.setDangerAddressIdName(cursor.getString(14));
                fjgkSceneCheckDanger.setCheckDangerId(cursor.getString(15));
                fjgkSceneCheckDanger.setSceneCheckId(cursor.getString(16));
                fjgkSceneCheckDanger.setCheckData(cursor.getString(17));
                fjgkSceneCheckDanger.setId(cursor.getInt(18));
                fjgkSceneCheckDanger.setDangerAddressName(cursor.getString(19));
                fjgkSceneCheckDanger.setDangerAreaName(cursor.getString(20));
                fjgkSceneCheckDanger.setDangerArea(cursor.getString(21));
                fjgkSceneCheckDanger.setRiskClassesName(cursor.getString(22));
                fjgkSceneCheckDanger.setRelevanceId(cursor.getString(23));
                fjgkSceneCheckDanger.setCheckClasses(cursor.getString(24));
                fjgkSceneCheckDanger.setCheckResult(cursor.getString(25));
                checkDangerList.add(fjgkSceneCheckDanger);
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
        return checkDangerList;
    }

    /**
     * 根据检查ID和当前登录人查询风险检查项
     * @param addressId
     * @return
     */
    public ArrayList<FjgkSceneCheckDanger> findDangerListByAddressId(String addressId, int pageIndex, int pageSize){
        String userId = SharedPreferencesUtil.getStringValue(context,"userId");
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select f.deptName,f.userName,f.DANGER_CONTENT,f.controlMeasure,f.identifyResult,f.remark,");
        sb.append("s1.code_name as hiddenMajorName,s2.code_name as hiddenLevelName,");
        sb.append("s3.code_name,s4.code_name,s5.code_name as classIficationName,");
        sb.append("s6.code_name as checkResult,f.dangerFormName as dangerFormName ,f.dangerAddressId,f.dangerAddressIdName,f.CHECK_DANGER_ID,f.SCENE_CHECK_ID ,c.CHECK_DATE,f.Id ,f.DANGER_ADDRESS_NAME,f.DANGER_AREA_NAME,f.DANGER_AREA,f.riskClassesName,g.RELEVANCE_ID,c.CHECK_CLASSES,f.CHECK_RESULT from ");
        sb.append(" FJGK_SCENECHECK_DANGER f left join SYSTEM_CODE_MASTER s1 on s1.code_type = '010' and s1.code_1 = f.DANGER_MAJOR ");
        sb.append(" and s1.code_2 = ( SELECT c.COLLIERY_TYPE  FROM COMPANY_COLLIERY c ) ");
        sb.append(" left join SYSTEM_CODE_MASTER s2 on s2.code_type = '006' and s2.code_1 = f.DANGER_RANK ");
        sb.append(" left join SYSTEM_CODE_MASTER s3 on s3.code_type = '013' and s3.code_1 = f.DANGER_CLASSES1 ");
        sb.append(" left join SYSTEM_CODE_MASTER s4 on s4.code_type = '014' and s4.code_1 = f.DANGER_CLASSES2 and s4.code_2 = f.DANGER_CLASSES1 ");
        sb.append(" left join SYSTEM_CODE_MASTER s5 on s5.code_type = '015' and s5.code_1 = f.SYSTEM_CLASSIFY ");
        sb.append(" left join SYSTEM_CODE_MASTER s6 on s6.code_type = '022' and s6.code_1 = f.CHECK_RESULT ");
        sb.append(" left join FJGK_SCECE_CHECK c on c.SCENE_CHECK_ID = f.SCENE_CHECK_ID ");
        sb.append(" left join GT_CHECK_DANGER g on g.RELEVANCE_ID = f.CHECK_DANGER_ID and g.del_flg = 0 ");
        sb.append(" where f.dangerAddressId = '"+addressId+"' and f.CHECK_RESULT = '00' and c.CHECK_STATUS = '01' and (c.DETY_MEMBER like '%"+userId+"%' or c.DUTY_PERSON = '"+userId+"' ) limit '"+pageSize+"' offset '"+pageIndex*pageSize+"' ");

        Cursor cursor = null;
        ArrayList<FjgkSceneCheckDanger> checkDangerList = new ArrayList<FjgkSceneCheckDanger>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor!=null&&cursor.moveToNext()) {
                FjgkSceneCheckDanger fjgkSceneCheckDanger = new FjgkSceneCheckDanger();
                fjgkSceneCheckDanger.setDeptName(cursor.getString(0));
                fjgkSceneCheckDanger.setUserName(cursor.getString(1));
                fjgkSceneCheckDanger.setDangerContent(cursor.getString(2));
                fjgkSceneCheckDanger.setControlMeasure(cursor.getString(3));
                fjgkSceneCheckDanger.setIdentifyResult(cursor.getString(4));
                fjgkSceneCheckDanger.setRemark(cursor.getString(5));
                fjgkSceneCheckDanger.setHiddenMajorName(cursor.getString(6));
                fjgkSceneCheckDanger.setDangerRankName(cursor.getString(7));
                fjgkSceneCheckDanger.setHiddenTypeName1(cursor.getString(8));
                fjgkSceneCheckDanger.setHiddenTypeName2(cursor.getString(9));
                fjgkSceneCheckDanger.setSystemClassifyName(cursor.getString(10));
                fjgkSceneCheckDanger.setCheckResultName(cursor.getString(11));
                fjgkSceneCheckDanger.setDangerFormName(cursor.getString(12));
                fjgkSceneCheckDanger.setDangerAddressId(cursor.getString(13));
                fjgkSceneCheckDanger.setDangerAddressIdName(cursor.getString(14));
                fjgkSceneCheckDanger.setCheckDangerId(cursor.getString(15));
                fjgkSceneCheckDanger.setSceneCheckId(cursor.getString(16));
                fjgkSceneCheckDanger.setCheckData(cursor.getString(17));
                fjgkSceneCheckDanger.setId(cursor.getInt(18));
                fjgkSceneCheckDanger.setDangerAddressName(cursor.getString(19));
                fjgkSceneCheckDanger.setDangerAreaName(cursor.getString(20));
                fjgkSceneCheckDanger.setDangerArea(cursor.getString(21));
                fjgkSceneCheckDanger.setRiskClassesName(cursor.getString(22));
                fjgkSceneCheckDanger.setRelevanceId(cursor.getString(23));
                fjgkSceneCheckDanger.setCheckClasses(cursor.getString(24));
                fjgkSceneCheckDanger.setCheckResult(cursor.getString(25));
                checkDangerList.add(fjgkSceneCheckDanger);
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
        return checkDangerList;
    }
}
