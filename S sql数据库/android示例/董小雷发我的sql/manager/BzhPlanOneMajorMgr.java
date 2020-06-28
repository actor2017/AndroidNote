package com.nkay.swyt.database.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhCheckMajorDetail;
import com.nkay.swyt.model.BzhPlanMajor;

import java.util.ArrayList;

/**
 * Created by Dell on 2017/5/12.
 */

public class BzhPlanOneMajorMgr {
    private Context context;

    public BzhPlanOneMajorMgr(Context context) {
        super();
        this.context = context;
    }

    /**
     * 根据计划ID获取检查专业一级列表数据
     *
     * @param planId
     * @return
     */
    public ArrayList<BzhPlanMajor> selectPlanMajorByPanlId(String planId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select BZH_PLANMAJOR.YIXUANZEPROJECTS_ID,BZH_PLANMAJOR.CHECK_LEAD_NAME,BZH_PLANMAJOR.CHECK_LEAD_ID,BZH_PLANMAJOR.CHECK_VICE_NAME,BZH_PLANMAJOR.CHECK_VICE_ID,BZH_PLANMAJOR.CHECK_MEMBER_NAME,BZH_PLANMAJOR.CHECK_MEMBER_ID,BZH_PLANMAJOR.YIXUANZEPROJECTS_NAME,BZH_PLANMAJOR.QUALITYPLAN_ID,BZH_PLANMAJOR.FINALL_NUM,BZH_PLANMAJOR.MAJOR_ID ");
        sb.append(" from BZH_PLANMAJOR where QUALITYPLAN_ID = '" + planId + "' order by BZH_PLANMAJOR.MAJOR_ID ");

        Cursor cursor = null;
        ArrayList<BzhPlanMajor> bzhPlanMajors = new ArrayList<BzhPlanMajor>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhPlanMajor bzhPlanMajor = new BzhPlanMajor();
                bzhPlanMajor.setYixuanzeprojectsId(cursor.getString(0));
                bzhPlanMajor.setCheckleadName(cursor.getString(1));
                bzhPlanMajor.setCheckleadId(cursor.getString(2));
                bzhPlanMajor.setCheckviceName(cursor.getString(3));
                bzhPlanMajor.setCheckviceId(cursor.getString(4));
                bzhPlanMajor.setCheckmemberName(cursor.getString(5));
                bzhPlanMajor.setCheckmemberId(cursor.getString(6));
                bzhPlanMajor.setYixuanzeprojectsName(cursor.getString(7));
                bzhPlanMajor.setQualityplanId(cursor.getString(8));
                bzhPlanMajor.setFinallNum(cursor.getString(9));
                bzhPlanMajor.setMajorId(cursor.getString(10));
                bzhPlanMajors.add(bzhPlanMajor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return bzhPlanMajors;
    }


    /**
     * 根据已选专业ID获取二级检查专业列表数据
     *
     * @param yixuanzeprojectsId
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectNodeNameById(String yixuanzeprojectsId,String projectName) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID,BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID,BZH_CHECK_MAJOR_DETAIL.nodeName,BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE,BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE_NAME ");
        sb.append(" FROM BZH_CHECK_MAJOR_DETAIL ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0  AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        if(projectName.equals("通风")||projectName.equals("地质灾害防治与测量")){
            sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID ");
        }else {
            sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE ");
        }

        Cursor cursor = null;
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setSelfCheckcontentsId(cursor.getString(0));
                bzhCheckMajorDetail.setYixuanzeprojectsId(cursor.getString(1));
                bzhCheckMajorDetail.setNodeName(cursor.getString(2));
                bzhCheckMajorDetail.setCheckWorkface(cursor.getString(3));
                bzhCheckMajorDetail.setCheckWorkfaceName(cursor.getString(4));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }


    /**
     * 根据已选专业和节点ID查询父级菜单
     *
     * @param yixuanzeprojectsId,nodeId
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectGroupDetailById(String yixuanzeprojectsId, String nodeId,String nodeName) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT ");
        if(nodeName.indexOf("2局部通风")==-1&&nodeName.indexOf("3通风设施")==-1){
            sb.append(" DISTINCT ");
        }
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME,BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID,BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID,BZH_CHECK_MAJOR_DETAIL.nodeName  ");
        if(nodeName.indexOf("2局部通风")!=-1||nodeName.indexOf("3通风设施")!=-1){
            sb.append(" ,BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE,BZH_CHECK_MAJOR_DETAIL.CHECK_NAME ");
        }
        sb.append(" from BZH_CHECK_MAJOR_DETAIL left join  SortTable s on s.sort = REPLACE(substr(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME ,1,2),'、','') ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0  ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID = '" + nodeId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        if(nodeName.indexOf("2局部通风")!=-1||nodeName.indexOf("3通风设施")!=-1){
            sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE ");
        }
        sb.append("  order by s.Id ");
        Cursor cursor = null;
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setSelfCheckProjectname(cursor.getString(0));
                bzhCheckMajorDetail.setYixuanzeprojectsId(cursor.getString(1));
                bzhCheckMajorDetail.setSelfCheckcontentsId(cursor.getString(2));
                bzhCheckMajorDetail.setNodeName(cursor.getString(3));
                if(nodeName.indexOf("2局部通风")!=-1||nodeName.indexOf("3通风设施")!=-1) {
                    bzhCheckMajorDetail.setCheckWorkface(cursor.getString(4));
                    bzhCheckMajorDetail.setCheckName(cursor.getString(5));
                }
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }


    /**
     * 普通专业标准总分和实际得分总分
     *
     * @param yixuanzeprojectsId,nodeId
     * @return
     */
    public BzhCheckMajorDetail selectPTZYSumTotalScore(String yixuanzeprojectsId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        Float groupTotalScore = null;
        StringBuffer sb = new StringBuffer("SELECT SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE),SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE) from BZH_CHECK_MAJOR_DETAIL ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0 AND BZH_CHECK_MAJOR_DETAIL.LACK_FLG = 0 ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                bzhCheckMajorDetail.setStandartTotalscore(cursor.getFloat(0));
                bzhCheckMajorDetail.setCheckTotalScore(cursor.getFloat(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return bzhCheckMajorDetail;
    }

    /**
     * 地质灾害防治与测量/露天矿(采煤、运输、排土)总分计算
      * @param yixuanzeprojectsId
    * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectDZZHSumStandartScore(String yixuanzeprojectsId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        StringBuffer sb = new StringBuffer("SELECT SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE),SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE),BZH_CHECK_MAJOR_DETAIL.nodeName from BZH_CHECK_MAJOR_DETAIL ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0 AND BZH_CHECK_MAJOR_DETAIL.LACK_FLG = 0 ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID ");
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setStandartTotalscore(cursor.getFloat(0));
                bzhCheckMajorDetail.setCheckTotalScore(cursor.getFloat(1));
                bzhCheckMajorDetail.setNodeName(cursor.getString(2));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }

    /**
     * 井工矿采煤、掘进标准总分(按工作面)
     *
     * @param yixuanzeprojectsId
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectGZMSumStandartScore(String yixuanzeprojectsId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        StringBuffer sb = new StringBuffer("SELECT SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE),SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE) from BZH_CHECK_MAJOR_DETAIL ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0 AND BZH_CHECK_MAJOR_DETAIL.LACK_FLG = 0 ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE ");
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setStandartTotalscore(cursor.getFloat(0));
                bzhCheckMajorDetail.setCheckTotalScore(cursor.getFloat(1));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }


    /**
     * 井工矿通风专业计算总分
     *
     * @param yixuanzeprojectsId
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectTFSumStandartScore(String yixuanzeprojectsId) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        StringBuffer sb = new StringBuffer("SELECT SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE),SUM(BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE),BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME from BZH_CHECK_MAJOR_DETAIL ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0 AND BZH_CHECK_MAJOR_DETAIL.LACK_FLG = 0 ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE,BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID ");
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setStandartTotalscore(cursor.getFloat(0));
                bzhCheckMajorDetail.setCheckTotalScore(cursor.getFloat(1));
                bzhCheckMajorDetail.setSelfCheckProjectname(cursor.getString(2));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }



    /**
     * 根据已选专业和节点ID查询子集菜单(除运输用)
     *
     * @param yixuanzeprojectsId,nodeId,projectName
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectChildDetailById(String yixuanzeprojectsId, String nodeId, String projectName) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT DISTINCT BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS,BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID,BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID from BZH_CHECK_MAJOR_DETAIL ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0 ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID = '" + nodeId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME = '" + projectName + "' ");

        Cursor cursor = null;
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setSelfCheckProjectcontents(cursor.getString(0));
                bzhCheckMajorDetail.setYixuanzeprojectsId(cursor.getString(1));
                bzhCheckMajorDetail.setSelfCheckcontentsId(cursor.getString(2));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }

    /**
     * 运输按分数分组查询
     *
     * @param yixuanzeprojectsId,nodeId,projectName
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectYSFZDetailById(String yixuanzeprojectsId, String nodeId, String projectName) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SPECIALTY_ID,BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID,BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS2, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_BASICREQUIREMENTS,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_METHODOFCOMMENT, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE,BZH_CHECK_MAJOR_DETAIL.dutyDept,BZH_CHECK_MAJOR_DETAIL.LACK_FLG,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE_GROUP from BZH_CHECK_MAJOR_DETAIL  ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0  ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID = '" + nodeId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME = '" + projectName + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE != 0 ");
        sb.append(" GROUP BY BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE_GROUP order by BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE desc ");

        Cursor cursor = null;
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setSelfCheckSpecialtyId(cursor.getString(0));
                bzhCheckMajorDetail.setYixuanzeprojectsId(cursor.getString(1));
                bzhCheckMajorDetail.setSelfCheckcontentsId(cursor.getString(2));
                bzhCheckMajorDetail.setSelfCheckProjectname(cursor.getString(3));
                bzhCheckMajorDetail.setSelfCheckProjectcontents(cursor.getString(4));
                bzhCheckMajorDetail.setSelfCheckProjectcontents2(cursor.getString(5));
                bzhCheckMajorDetail.setSelfCheckBasicrequirements(cursor.getString(6));
                bzhCheckMajorDetail.setSelfCheckStandartscore(cursor.getDouble(7));
                bzhCheckMajorDetail.setSelfCheckMethodofcomment(cursor.getString(8));
                bzhCheckMajorDetail.setSelfCheckScore(cursor.getDouble(9));
                bzhCheckMajorDetail.setDutyDept(cursor.getString(10));
                bzhCheckMajorDetail.setLackFlg(cursor.getString(11));
                bzhCheckMajorDetail.setSelfCheckScoreGroup(cursor.getString(12));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }

    /**
     * 根据已选专业和节点ID查询检查专业信息
     *
     * @param yixuanzeprojectsId,nodeId,projectContents
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectCheckDetailByContents(String yixuanzeprojectsId, String nodeId, String projectContents,String checkWorkFace) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SPECIALTY_ID,BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID,BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS2, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_BASICREQUIREMENTS,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_METHODOFCOMMENT, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE,BZH_CHECK_MAJOR_DETAIL.dutyDept,BZH_CHECK_MAJOR_DETAIL.LACK_FLG from BZH_CHECK_MAJOR_DETAIL  ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0  ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID = '" + nodeId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS = '" + projectContents + "' ");
        if(checkWorkFace.equals("01")||checkWorkFace.equals("02")||checkWorkFace.equals("03")){
            sb.append(" AND BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE = '" + checkWorkFace + "' ");
        }
        sb.append(" ORDER BY BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SORT ,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_BASICREQUIREMENTS ");

        Cursor cursor = null;
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setSelfCheckSpecialtyId(cursor.getString(0));
                bzhCheckMajorDetail.setYixuanzeprojectsId(cursor.getString(1));
                bzhCheckMajorDetail.setSelfCheckcontentsId(cursor.getString(2));
                bzhCheckMajorDetail.setSelfCheckProjectname(cursor.getString(3));
                bzhCheckMajorDetail.setSelfCheckProjectcontents(cursor.getString(4));
                bzhCheckMajorDetail.setSelfCheckProjectcontents2(cursor.getString(5));
                bzhCheckMajorDetail.setSelfCheckBasicrequirements(cursor.getString(6));
                bzhCheckMajorDetail.setSelfCheckStandartscore(cursor.getDouble(7));
                bzhCheckMajorDetail.setSelfCheckMethodofcomment(cursor.getString(8));
                bzhCheckMajorDetail.setSelfCheckScore(cursor.getDouble(9));
                bzhCheckMajorDetail.setDutyDept(cursor.getString(10));
                bzhCheckMajorDetail.setLackFlg(cursor.getString(11));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }


    /**
     * 根据运输分组信息查询标准分以及其他信息
     *
     * @param yixuanzeprojectsId,nodeId,SELF_CHECK_SCORE_GROUP
     * @return
     */
    public ArrayList<BzhCheckMajorDetail> selectCheckDetailByScoreGroup(String yixuanzeprojectsId, String nodeId, String scroreGroup) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("SELECT BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SPECIALTY_ID,BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID,BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS2, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_BASICREQUIREMENTS,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_STANDARTSCORE,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_METHODOFCOMMENT, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE,BZH_CHECK_MAJOR_DETAIL.dutyDept,BZH_CHECK_MAJOR_DETAIL.LACK_FLG from BZH_CHECK_MAJOR_DETAIL  ");
        sb.append(" where BZH_CHECK_MAJOR_DETAIL.DEL_FLG = 0  ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECKCONTENTS_ID = '" + nodeId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.YIXUANZEPROJECTS_ID = '" + yixuanzeprojectsId + "' ");
        sb.append(" AND BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SCORE_GROUP = '" + scroreGroup + "' ");
        sb.append(" ORDER BY BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTNAME,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_PROJECTCONTENTS, ");
        sb.append(" BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SORT ,BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_BASICREQUIREMENTS ");

        Cursor cursor = null;
        ArrayList<BzhCheckMajorDetail> checkMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                bzhCheckMajorDetail.setSelfCheckSpecialtyId(cursor.getString(0));
                bzhCheckMajorDetail.setYixuanzeprojectsId(cursor.getString(1));
                bzhCheckMajorDetail.setSelfCheckcontentsId(cursor.getString(2));
                bzhCheckMajorDetail.setSelfCheckProjectname(cursor.getString(3));
                bzhCheckMajorDetail.setSelfCheckProjectcontents(cursor.getString(4));
                bzhCheckMajorDetail.setSelfCheckProjectcontents2(cursor.getString(5));
                bzhCheckMajorDetail.setSelfCheckBasicrequirements(cursor.getString(6));
                bzhCheckMajorDetail.setSelfCheckStandartscore(cursor.getDouble(7));
                bzhCheckMajorDetail.setSelfCheckMethodofcomment(cursor.getString(8));
                bzhCheckMajorDetail.setSelfCheckScore(cursor.getDouble(9));
                bzhCheckMajorDetail.setDutyDept(cursor.getString(10));
                bzhCheckMajorDetail.setLackFlg(cursor.getString(11));
                checkMajorDetails.add(bzhCheckMajorDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
//            if (db!=null) {
//                db.close();
//            }
//            if (db!=null) {
//                db.close();
//            }
        }
        return checkMajorDetails;
    }

}
