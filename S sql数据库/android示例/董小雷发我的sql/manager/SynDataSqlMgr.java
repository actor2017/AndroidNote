package com.nkay.swyt.database.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhCheckMajorDetail;
import com.nkay.swyt.model.BzhMajorCheckContent;
import com.nkay.swyt.model.BzhPlan;
import com.nkay.swyt.model.BzhPlanMajor;
import com.nkay.swyt.model.CompanyCollierUser;
import com.nkay.swyt.model.CompanycollierDepartmentManagement;
import com.nkay.swyt.model.FjgkSceneCheck;
import com.nkay.swyt.model.FjgkSceneCheckDanger;
import com.nkay.swyt.model.GtCheckAssign;
import com.nkay.swyt.model.GtCheckDanger;
import com.nkay.swyt.model.GtDangerAccept;
import com.nkay.swyt.model.GtDangerReform1;
import com.nkay.swyt.model.GtDangerSupervise;
import com.nkay.swyt.model.GtExalt;
import com.nkay.swyt.model.GtPostpone;
import com.nkay.swyt.model.HiddenPlan;
import com.nkay.swyt.model.HiddenPlanDetail;
import com.nkay.swyt.model.SystemCodeMaster;

import java.util.List;

/**
 * FileName: SynDataSqlMgr,同步数据工具类
 * Author: zhangwenping
 * Date: 2017/8/29
 * Email: httputils@qq.com
 */
public class SynDataSqlMgr {

    private Context context;

    public SynDataSqlMgr(Context context) {
        super();
        this.context = context;
    }


    /**
     * BZH_PLAN
     */
    public void insertBzhPlan(List<BzhPlan> bzhPlan) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (bzhPlan != null && bzhPlan.size() > 0) {

                for (BzhPlan bzhPlan1: bzhPlan) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("QUALITYPLAN_ID",bzhPlan1.getQualityplanId());
                    contentValues.put("PLAN_NAME",bzhPlan1.getPlanName());
                    contentValues.put("CHECK_TYPE",bzhPlan1.getCheckType());
                    contentValues.put("CHECKED_SECTION",bzhPlan1.getCheckedSection());
                    contentValues.put("COLLIERY_TYPE",bzhPlan1.getCollieryType());
                    contentValues.put("CHECK_YEAR",bzhPlan1.getCheckYear());
                    contentValues.put("CHECK_MONTH",bzhPlan1.getCheckMonth());
                    contentValues.put("CHECK_COMMENTS",bzhPlan1.getCheckComments());
                    contentValues.put("CHECK_AUDIT",bzhPlan1.getCheckAudit());
                    contentValues.put("CHECK_AUDIT_DATE", bzhPlan1.getCheckAuditDate());
                    contentValues.put("CHECK_AUDIT_STATUS", bzhPlan1.getCheckAuditStatus());
                    contentValues.put("CHECK_AUDIT_ADVICE", bzhPlan1.getCheckAuditAdvice());
                    contentValues.put("CHECK_APPROVER", bzhPlan1.getCheckApprover());
                    contentValues.put("CHECK_APPROVER_DATA",bzhPlan1.getCheckApproverData());
                    contentValues.put("CHECK_APPROVER_ADVICE",bzhPlan1.getCheckApproverAdvice());
                    contentValues.put("IS_EXECUTE",bzhPlan1.getIsExecute());
                    contentValues.put("IS_UPLOAD",bzhPlan1.getIsUpload());
                    contentValues.put("CHECKNUM",bzhPlan1.getCheckNum());
                    contentValues.put("del_flg", bzhPlan1.getDelFlg());
                    contentValues.put("insert_user_id", bzhPlan1.getInsertDatetime());
                    contentValues.put("insert_datetime", bzhPlan1.getInsertUserId());
                    contentValues.put("update_datatime", bzhPlan1.getUpdateDatetime());
                    contentValues.put("update_user_id", bzhPlan1.getUpdateUserId());

                    BzhPlan bzhPlanById = findBzhPlanById(bzhPlan1.getQualityplanId());

                    if (bzhPlanById != null) {
                        db.update("BZH_PLAN",contentValues, "QUALITYPLAN_ID = ? ", new String[] { bzhPlan1.getQualityplanId()});
                    } else {
                        db.insert("BZH_PLAN", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private BzhPlan findBzhPlanById(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select BZH_PLAN.QUALITYPLAN_ID ");
        sb.append(" from BZH_PLAN where BZH_PLAN.QUALITYPLAN_ID = '" + id + "'");

        Cursor cursor = null;
        BzhPlan detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new BzhPlan();
                detail.setQualityplanId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }
/***************************************************************************************************************************/


    /**
     * BZH_PLANMAJOR
     */
    public void insertBzhPlanMajor(List<BzhPlanMajor> bzhPlanMajor) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (bzhPlanMajor != null && bzhPlanMajor.size() > 0) {

                for (BzhPlanMajor bzhPlanMajor1: bzhPlanMajor) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("YIXUANZEPROJECTS_ID",bzhPlanMajor1.getYixuanzeprojectsId());
                    contentValues.put("MAJOR_ID",bzhPlanMajor1.getMajorId());
                    contentValues.put("QUALITYPLAN_ID",bzhPlanMajor1.getQualityplanId());
                    contentValues.put("YIXUANZEPROJECTS_NAME",bzhPlanMajor1.getYixuanzeprojectsName());
                    contentValues.put("FINALL_NUM",bzhPlanMajor1.getFinallNum());
                    contentValues.put("IS_UPLOAD",bzhPlanMajor1.getIsUpload());
                    contentValues.put("CHECK_LEAD_NAME",bzhPlanMajor1.getCheckleadName());
                    contentValues.put("CHECK_LEAD_ID",bzhPlanMajor1.getCheckleadId());
                    contentValues.put("CHECK_VICE_NAME",bzhPlanMajor1.getCheckviceName());
                    contentValues.put("CHECK_VICE_ID",bzhPlanMajor1.getCheckviceId());
                    contentValues.put("CHECK_MEMBER_NAME", bzhPlanMajor1.getCheckmemberName());
                    contentValues.put("CHECK_MEMBER_ID", bzhPlanMajor1.getCheckmemberId());
                    contentValues.put("del_flg", bzhPlanMajor1.getDelFlg());
                    contentValues.put("insert_user_id", bzhPlanMajor1.getInsertDatetime());
                    contentValues.put("insert_datetime", bzhPlanMajor1.getInsertUserId());
                    contentValues.put("update_datatime", bzhPlanMajor1.getUpdateDatetime());
                    contentValues.put("update_user_id", bzhPlanMajor1.getUpdateUserId());


                    BzhPlanMajor bzhPlanMajorById = findBzhPlanMajorById(bzhPlanMajor1.getYixuanzeprojectsId());
                    if (bzhPlanMajorById != null) {
                        db.update("BZH_PLANMAJOR",contentValues, "YIXUANZEPROJECTS_ID = ? ", new String[] { bzhPlanMajor1.getYixuanzeprojectsId()});
                    } else {
                        db.insert("BZH_PLANMAJOR", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private BzhPlanMajor findBzhPlanMajorById(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select BZH_PLANMAJOR.YIXUANZEPROJECTS_ID ");
        sb.append(" from BZH_PLANMAJOR where BZH_PLANMAJOR.YIXUANZEPROJECTS_ID = '" + id + "'");

        Cursor cursor = null;
        BzhPlanMajor detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new BzhPlanMajor();
                detail.setQualityplanId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }
/*****************************************************************/


    /**
     * 插入BZH详情表数据
     */
    public void insertBzhCheckMajorDetail(List<BzhCheckMajorDetail> bzhDetails) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        try {
            if (bzhDetails != null && bzhDetails.size() > 0) {
                for (BzhCheckMajorDetail bzhCheckMajorDetail : bzhDetails) {
                    ContentValues values = new ContentValues();
                    values.put("dutyDept", bzhCheckMajorDetail.getDutyDept());
                    values.put("IS_UPLOAD",bzhCheckMajorDetail.getIsUpload());
                    values.put("nodeName", bzhCheckMajorDetail.getNodeName());
                    values.put("SELF_CHECK_BASICREQUIREMENTS", bzhCheckMajorDetail.getSelfCheckBasicrequirements());
                    values.put("SELF_CHECK_METHODOFCOMMENT", bzhCheckMajorDetail.getSelfCheckMethodofcomment());
                    values.put("SELF_CHECK_PROJECTCONTENTS", bzhCheckMajorDetail.getSelfCheckProjectcontents());
                    values.put("SELF_CHECK_PROJECTCONTENTS2", bzhCheckMajorDetail.getSelfCheckProjectcontents2());
                    values.put("SELF_CHECK_PROJECTNAME", bzhCheckMajorDetail.getSelfCheckProjectname());
                    values.put("SELF_CHECK_SCORE", bzhCheckMajorDetail.getSelfCheckScore());
                    values.put("SELF_CHECK_SORT", bzhCheckMajorDetail.getSelfCheckSort());
                    values.put("SELF_CHECK_SPECIALTY_ID", bzhCheckMajorDetail.getSelfCheckSpecialtyId());
                    values.put("SELF_CHECK_STANDARTSCORE", bzhCheckMajorDetail.getSelfCheckStandartscore());
                    values.put("SELF_CHECKCONTENTS_ID", bzhCheckMajorDetail.getSelfCheckcontentsId());
                    values.put("YIXUANZEPROJECTS_ID", bzhCheckMajorDetail.getYixuanzeprojectsId());
                    values.put("LACK_FLG",bzhCheckMajorDetail.getLackFlg());
                    values.put("SELF_CHECK_SCORE_GROUP",bzhCheckMajorDetail.getSelfCheckScoreGroup());
                    values.put("SELF_CHECK_METHOD_GROUP",bzhCheckMajorDetail.getSelfCheckMethodGroup());
                    values.put("SELF_CONTENT_SORT",bzhCheckMajorDetail.getSelfContentSort());
                    values.put("CHECK_WORKFACE",bzhCheckMajorDetail.getCheckWorkface());
                    values.put("CHECK_WORKFACE_NAME",bzhCheckMajorDetail.getCheckWorkfaceName());
                    values.put("CHECK_NAME",bzhCheckMajorDetail.getCheckName());
                    values.put("del_flg", bzhCheckMajorDetail.getDelFlg());
                    values.put("insert_datetime", bzhCheckMajorDetail.getInsertDatetime());
                    values.put("insert_user_id", bzhCheckMajorDetail.getInsertUserId());
                    values.put("update_datatime", bzhCheckMajorDetail.getUpdateDatetime());
                    values.put("update_user_id", bzhCheckMajorDetail.getUpdateUserId());
                    BzhCheckMajorDetail detail = selectMajorDetailById(bzhCheckMajorDetail.getSelfCheckSpecialtyId());
                    if (detail != null) {
                        db.update("BZH_CHECK_MAJOR_DETAIL",values, "SELF_CHECK_SPECIALTY_ID = ? ", new String[] { detail.getSelfCheckSpecialtyId()});
                    } else {
                        db.insert("BZH_CHECK_MAJOR_DETAIL", null, values);
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
//            if (db != null) {
//                db.close();
//            }
        }
    }

    /**
     * 根据检查专业详细编码查询信息
     */
    private BzhCheckMajorDetail selectMajorDetailById(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer("select BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SPECIALTY_ID ");
        sb.append(" from BZH_CHECK_MAJOR_DETAIL where BZH_CHECK_MAJOR_DETAIL.SELF_CHECK_SPECIALTY_ID = '" + id + "'");

        Cursor cursor = null;
        BzhCheckMajorDetail detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new BzhCheckMajorDetail();
                detail.setSelfCheckSpecialtyId(cursor.getString(0));
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
        return detail;
    }

/********************************************************************************************************************************/

    /**
     * 插入质量标准化专业检查内容表
     */
    public void insertBzhMajorCheckContent(List<BzhMajorCheckContent> bzhMajorCheckContent) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (bzhMajorCheckContent != null && bzhMajorCheckContent.size() > 0) {

                for (BzhMajorCheckContent bzhMajorCheckContent1 : bzhMajorCheckContent) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("SELF_CHECKCONTENTS_ID", bzhMajorCheckContent1.getSelfCheckcontentsId());
                    contentValues.put("COLLIERY_TYPE_ID", bzhMajorCheckContent1.getCollieryTypeId());
                    contentValues.put("YIXUANZEPROJECTS_ID", bzhMajorCheckContent1.getYixuanzeprojectsId());
                    contentValues.put("SELF_CHECK_PROJECTNAME", bzhMajorCheckContent1.getSelfCheckProjectname());
                    contentValues.put("SELF_CHECK_PROJECTCONTENTS", bzhMajorCheckContent1.getSelfCheckProjectcontents());
                    contentValues.put("SELF_CHECK_BASICREQUIREMENTS", bzhMajorCheckContent1.getSelfCheckBasicrequirements());
                    contentValues.put("SELF_CHECK_STANDARTSCORE", bzhMajorCheckContent1.getSelfCheckStandartscore());
                    contentValues.put("SELF_CHECK_METHODOFCOMMENT", bzhMajorCheckContent1.getSelfCheckMethodofcomment());
                    contentValues.put("del_flg", bzhMajorCheckContent1.getDelFlg());
                    contentValues.put("insert_user_id", bzhMajorCheckContent1.getInsertDatetime());
                    contentValues.put("insert_datetime", bzhMajorCheckContent1.getInsertUserId());
                    contentValues.put("update_datatime", bzhMajorCheckContent1.getUpdateDatetime());
                    contentValues.put("update_user_id", bzhMajorCheckContent1.getUpdateUserId());
                    contentValues.put("SELF_CHECK_SORT", bzhMajorCheckContent1.getSelfCheckSort());
                    contentValues.put("SELF_CHECK_PROJECTCONTENTS2", bzhMajorCheckContent1.getSelfCheckProjectcontents2());
                    contentValues.put("SELF_CHECK_SCORE_GROUP", bzhMajorCheckContent1.getSelfCheckScoreGroup());
                    contentValues.put("SELF_CHECK_METHOD_GROUP", bzhMajorCheckContent1.getSelfCheckMethodGroup());
                    contentValues.put("SELF_CONTENT_SORT", bzhMajorCheckContent1.getSelfContentSort());
                    BzhMajorCheckContent bzhMajorCheckContentId = findBzhMajorCheckContentId(bzhMajorCheckContent1.getSelfCheckcontentsId());
                    if (bzhMajorCheckContentId != null) {
                        db.update("BZH_MAJOR_CHECK_CONTENT", contentValues, "SELF_CHECKCONTENTS_ID = ? ", new String[]{bzhMajorCheckContent1.getSelfCheckcontentsId()});
                    } else {
                        db.insert("BZH_MAJOR_CHECK_CONTENT", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    private BzhMajorCheckContent findBzhMajorCheckContentId(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select BZH_MAJOR_CHECK_CONTENT.SELF_CHECKCONTENTS_ID ");
        sb.append(" from BZH_MAJOR_CHECK_CONTENT where BZH_MAJOR_CHECK_CONTENT.SELF_CHECKCONTENTS_ID = '" + id + "'");

        Cursor cursor = null;
        BzhMajorCheckContent detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new BzhMajorCheckContent();
                detail.setSelfCheckcontentsId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

    /********************************************************************************************************************************/
    /**
     * COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT
     */
    public void insertCompanycollierDepartmentManagement(List<CompanycollierDepartmentManagement> companycollierDepartmentManagement) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (companycollierDepartmentManagement != null && companycollierDepartmentManagement.size() > 0) {

                for (CompanycollierDepartmentManagement companycollierDepartmentManagement1: companycollierDepartmentManagement) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DEPARTMENT_MANAGEMENT_ID",companycollierDepartmentManagement1.getDepartmentManagementId());
                    contentValues.put("COMPANY_COLLIERY_ID",companycollierDepartmentManagement1.getCompanyCollieryId());
                    contentValues.put("DEPARTMENT_NAME",companycollierDepartmentManagement1.getDepartmentName());
                    contentValues.put("LEADER",companycollierDepartmentManagement1.getLeader());
                    contentValues.put("PHONE",companycollierDepartmentManagement1.getPhone());
                    contentValues.put("TELEPHONENUMBER",companycollierDepartmentManagement1.getTelephonenumber());
                    contentValues.put("REMARK",companycollierDepartmentManagement1.getRemark());
                    contentValues.put("IS_UPLOAD",companycollierDepartmentManagement1.getIsUpload());
                    contentValues.put("SEQ",companycollierDepartmentManagement1.getSeq());
                    contentValues.put("del_flg", companycollierDepartmentManagement1.getDelFlg());
                    contentValues.put("insert_user_id", companycollierDepartmentManagement1.getInsertDatetime());
                    contentValues.put("insert_datetime", companycollierDepartmentManagement1.getInsertUserId());
                    contentValues.put("update_datatime", companycollierDepartmentManagement1.getUpdateDatetime());
                    contentValues.put("update_user_id", companycollierDepartmentManagement1.getUpdateUserId());

                    CompanycollierDepartmentManagement companycollierDepartmentManagement2 = findCompanycollierDepartmentManagement(companycollierDepartmentManagement1.getDepartmentManagementId());

                    if (companycollierDepartmentManagement2 != null) {
                        db.update("COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT",contentValues, "DEPARTMENT_MANAGEMENT_ID = ? ", new String[] { companycollierDepartmentManagement1.getDepartmentManagementId()});
                    } else {
                        db.insert("COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private CompanycollierDepartmentManagement findCompanycollierDepartmentManagement(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT.DEPARTMENT_MANAGEMENT_ID ");
        sb.append(" from COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT where COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT.DEPARTMENT_MANAGEMENT_ID = '" + id + "'");

        Cursor cursor = null;
        CompanycollierDepartmentManagement detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new CompanycollierDepartmentManagement();
                detail.setDepartmentManagementId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }




/*****************************************************************/


    /**
     * COMPANY_COLLIERY_USER
     */
    public void insertCompanyCollierUser(List<CompanyCollierUser> companyCollierUser) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (companyCollierUser != null && companyCollierUser.size() > 0) {

                for (CompanyCollierUser companyCollierUser1: companyCollierUser) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("USER_ID",companyCollierUser1.getUserId());
                    contentValues.put("SUBJECTION",companyCollierUser1.getSubjection());
                    contentValues.put("LOGIN_NAME",companyCollierUser1.getLoginName());
                    contentValues.put("LOGIN_PASSWORD",companyCollierUser1.getLoginPassword());
                    contentValues.put("HPIC_URL",companyCollierUser1.getHpicUrl());
                    contentValues.put("USER_NAME",companyCollierUser1.getUserName());
                    contentValues.put("USER_IDCARD",companyCollierUser1.getUserIdcard());
                    contentValues.put("USER_EMALL",companyCollierUser1.getUserEmall());
                    contentValues.put("OFFICE_PHONE",companyCollierUser1.getOfficePhone());
                    contentValues.put("MOBILE_PHONE", companyCollierUser1.getMobilePhone());
                    contentValues.put("STATUS", companyCollierUser1.getStatus());
                    contentValues.put("REMARK", companyCollierUser1.getRemark());
                    contentValues.put("DEPARYMENT_ID", companyCollierUser1.getDeparymentId());
                    contentValues.put("SEQ", companyCollierUser1.getSeq());
                    contentValues.put("postPoneAuthority", companyCollierUser1.getPostPoneAuthority());
                    contentValues.put("exaltAuthority", companyCollierUser1.getExaltAuthority());
                    contentValues.put("assignAuthority", companyCollierUser1.getAssignAuthority());
                    contentValues.put("cancelAuthority", companyCollierUser1.getCancelAuthority());
                    contentValues.put("reformAuthority", companyCollierUser1.getReformAuthority());
                    contentValues.put("superviseAuthority", companyCollierUser1.getSuperviseAuthority());
                    contentValues.put("acceptAuthority", companyCollierUser1.getAcceptAuthority());
                    contentValues.put("del_flg", companyCollierUser1.getDelFlg());
                    contentValues.put("insert_user_id", companyCollierUser1.getInsertDatetime());
                    contentValues.put("insert_datetime", companyCollierUser1.getInsertUserId());
                    contentValues.put("update_datatime", companyCollierUser1.getUpdateDatetime());
                    contentValues.put("update_user_id", companyCollierUser1.getUpdateUserId());

                    CompanyCollierUser companyCollierUser2 = findCompanyCollierUser(companyCollierUser1.getUserId());

                    if (companyCollierUser2 != null) {
                        db.update("COMPANY_COLLIERY_USER",contentValues, "USER_ID = ? ", new String[] { companyCollierUser1.getUserId()});
                    } else {
                        db.insert("COMPANY_COLLIERY_USER", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private CompanyCollierUser findCompanyCollierUser(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select COMPANY_COLLIERY_USER.USER_ID ");
        sb.append(" from COMPANY_COLLIERY_USER where COMPANY_COLLIERY_USER.USER_ID = '" + id + "'");

        Cursor cursor = null;
        CompanyCollierUser detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new CompanyCollierUser();
                detail.setUserId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/***************************************************************************************************************************/


    /**
     * FJGK_SCECE_CHECK
     */
    public void insertFjgkSceneCheck(List<FjgkSceneCheck> fjgkSceneCheck) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (fjgkSceneCheck != null && fjgkSceneCheck.size() > 0) {

                for (FjgkSceneCheck fjgkSceneCheck1: fjgkSceneCheck) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("SCENE_CHECK_ID",fjgkSceneCheck1.getSceneCheckId());
                    contentValues.put("COLLIERY_ID",fjgkSceneCheck1.getCollieryId());
                    contentValues.put("CHECK_NAME",fjgkSceneCheck1.getCheckName());
                    contentValues.put("CHECK_DATE",fjgkSceneCheck1.getCheckDate());
                    contentValues.put("CHECK_PATH",fjgkSceneCheck1.getCheckPath());
                    contentValues.put("CHECK_CONTENT",fjgkSceneCheck1.getCheckContent());
                    contentValues.put("DUTY_DEPT",fjgkSceneCheck1.getDutyDept());
                    contentValues.put("DUTY_PERSON",fjgkSceneCheck1.getDutyPerson());
                    contentValues.put("CHECK_STATUS", fjgkSceneCheck1.getCheckStatus());
                    contentValues.put("AUDIT_STATUS", fjgkSceneCheck1.getAuditStatus());
                    contentValues.put("AUDIT_OPINION", fjgkSceneCheck1.getAuditOpinion());
                    contentValues.put("AUDIT_DATE", fjgkSceneCheck1.getAuditDate());
                    contentValues.put("AUDIT_PERSON", fjgkSceneCheck1.getAuditPerson());
                    contentValues.put("DETY_MEMBER", fjgkSceneCheck1.getDetyMember());
                    contentValues.put("REMARK", fjgkSceneCheck1.getRemark());
                    contentValues.put("CHECK_CLASSES", fjgkSceneCheck1.getCheckClasses());
                    contentValues.put("IS_UPLOAD", fjgkSceneCheck1.getIsUpload());
                    contentValues.put("del_flg", fjgkSceneCheck1.getDelFlg());
                    contentValues.put("insert_user_id", fjgkSceneCheck1.getInsertDatetime());
                    contentValues.put("insert_datetime", fjgkSceneCheck1.getInsertUserId());
                    contentValues.put("update_datatime", fjgkSceneCheck1.getUpdateDatetime());
                    contentValues.put("update_user_id", fjgkSceneCheck1.getUpdateUserId());

                    FjgkSceneCheck fjgkSceneCheck2 = findFjgkSceneCheck(fjgkSceneCheck1.getSceneCheckId());

                    if (fjgkSceneCheck2 != null) {
                        db.update("FJGK_SCECE_CHECK",contentValues, "SCENE_CHECK_ID = ? ", new String[] {fjgkSceneCheck1.getSceneCheckId()});
                    } else {
                        db.insert("FJGK_SCECE_CHECK", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }

    private FjgkSceneCheck findFjgkSceneCheck(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select FJGK_SCECE_CHECK.SCENE_CHECK_ID ");
        sb.append(" from FJGK_SCECE_CHECK where FJGK_SCECE_CHECK.SCENE_CHECK_ID = '" + id + "'");

        Cursor cursor = null;
        FjgkSceneCheck detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new FjgkSceneCheck();
                detail.setSceneCheckId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }
/***************************************************************************************************************************/



    /**
     * FJGK_SCENECHECK_DANGER
     */
    public void insertFjgkSceneCheckDanger(List<FjgkSceneCheckDanger> fjgkSceneCheckDanger) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (fjgkSceneCheckDanger != null && fjgkSceneCheckDanger.size() > 0) {

                for (FjgkSceneCheckDanger fjgkSceneCheckDanger1: fjgkSceneCheckDanger) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("CHECK_DANGER_ID",fjgkSceneCheckDanger1.getCheckDangerId());
                    contentValues.put("SCENE_CHECK_ID",fjgkSceneCheckDanger1.getSceneCheckId());
                    contentValues.put("DANGER_LIST_ID",fjgkSceneCheckDanger1.getDangerListId());
                    contentValues.put("DANGER_MAJOR",fjgkSceneCheckDanger1.getDangerMajor());
                    contentValues.put("DANGER_RANK",fjgkSceneCheckDanger1.getDangerRank());
                    contentValues.put("SYSTEM_CLASSIFY",fjgkSceneCheckDanger1.getSystemClassify());
                    contentValues.put("DANGER_CLASSES1",fjgkSceneCheckDanger1.getDangerClasses1());
                    contentValues.put("DANGER_CLASSES2",fjgkSceneCheckDanger1.getDangerClasses2());
                    contentValues.put("DANGER_CONTENT", fjgkSceneCheckDanger1.getDangerContent());
                    contentValues.put("CHECK_RESULT", fjgkSceneCheckDanger1.getCheckResult());
                    contentValues.put("dangerAddressId", fjgkSceneCheckDanger1.getDangerAddressId());
                    contentValues.put("dangerAddressIdName", fjgkSceneCheckDanger1.getDangerAddressIdName());
                    contentValues.put("DANGER_AREA", fjgkSceneCheckDanger1.getDangerArea());
                    contentValues.put("DANGER_ADDRESS_NAME", fjgkSceneCheckDanger1.getDangerAddressName());
                    contentValues.put("DANGER_AREA_NAME", fjgkSceneCheckDanger1.getDangerAreaName());
                    contentValues.put("dangerFormName", fjgkSceneCheckDanger1.getDangerFormName());
                    contentValues.put("controlMeasure", fjgkSceneCheckDanger1.getControlMeasure());
                    contentValues.put("deptName", fjgkSceneCheckDanger1.getDeptName());
                    contentValues.put("userName", fjgkSceneCheckDanger1.getUserName());
                    contentValues.put("identifyResult", fjgkSceneCheckDanger1.getIdentifyResult());
                    contentValues.put("remark", fjgkSceneCheckDanger1.getRemark());
                    contentValues.put("riskClassesName", fjgkSceneCheckDanger1.getRiskClassesName());
                    contentValues.put("IS_UPLOAD", fjgkSceneCheckDanger1.getIsUpload());
                    contentValues.put("del_flg", fjgkSceneCheckDanger1.getDelFlg());
                    contentValues.put("insert_user_id", fjgkSceneCheckDanger1.getInsertDatetime());
                    contentValues.put("insert_datetime", fjgkSceneCheckDanger1.getInsertUserId());
                    contentValues.put("update_datatime", fjgkSceneCheckDanger1.getUpdateDatetime());
                    contentValues.put("update_user_id", fjgkSceneCheckDanger1.getUpdateUserId());

                    FjgkSceneCheckDanger fjgkSceneCheckDanger2 = findFjgkSceneCheckDanger(fjgkSceneCheckDanger1.getCheckDangerId());


                    if (fjgkSceneCheckDanger2 != null) {
                        db.update("FJGK_SCENECHECK_DANGER",contentValues, "CHECK_DANGER_ID = ? ", new String[] {fjgkSceneCheckDanger1.getCheckDangerId()});
                    } else {
                        db.insert("FJGK_SCENECHECK_DANGER", null, contentValues);
                    }

                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private FjgkSceneCheckDanger findFjgkSceneCheckDanger(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select FJGK_SCENECHECK_DANGER.CHECK_DANGER_ID ");
        sb.append(" from FJGK_SCENECHECK_DANGER where FJGK_SCENECHECK_DANGER.CHECK_DANGER_ID = '" + id + "'");

        Cursor cursor = null;
        FjgkSceneCheckDanger detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new FjgkSceneCheckDanger();
                detail.setCheckDangerId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/********************************************************************************/

    /**
     * GT_CHECK_ASSIGN
     */
    public void insertGtCheckAssign(List<GtCheckAssign> gtCheckAssign) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtCheckAssign != null && gtCheckAssign.size() > 0) {

                for (GtCheckAssign gtCheckAssign1: gtCheckAssign) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ASSIGN_HIDDEN_ID",gtCheckAssign1.getAssignHiddenId());
                    contentValues.put("CHECK_HIDDEN_ID",gtCheckAssign1.getCheckHiddenId());
                    contentValues.put("DEAL_WITH_FLG",gtCheckAssign1.getDealWithFlg());
                    contentValues.put("REFORM_DEPT",gtCheckAssign1.getReformDept());
                    contentValues.put("REFORM_DUTY_PERSON",gtCheckAssign1.getReformDutyPerson());
                    contentValues.put("REFORM_DEADLINE",gtCheckAssign1.getReformDeadline());
                    contentValues.put("REFORM_CLASSES",gtCheckAssign1.getReformClasses());
                    contentValues.put("REFORM_OPINION",gtCheckAssign1.getReformOpinion());
                    contentValues.put("SUPERVISE_FLG",gtCheckAssign1.getSuperviseFlg());
                    contentValues.put("SUPERVISE_DEPT", gtCheckAssign1.getSuperviseDept());
                    contentValues.put("SUPERVISE_PERSON", gtCheckAssign1.getSupervisePerson());
                    contentValues.put("ACCEPT_DEPT", gtCheckAssign1.getAcceptDept());
                    contentValues.put("ACCEPT_DEPT_NAME", gtCheckAssign1.getAcceptDeptName());
                    contentValues.put("MONEY", gtCheckAssign1.getMoney());
                    contentValues.put("IS_UPLOAD", gtCheckAssign1.getIsUpload());
                    contentValues.put("del_flg", gtCheckAssign1.getDelFlg());
                    contentValues.put("insert_user_id", gtCheckAssign1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtCheckAssign1.getInsertUserId());
                    contentValues.put("update_datatime", gtCheckAssign1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtCheckAssign1.getUpdateUserId());

                    GtCheckAssign gtCheckAssign2 = findGtCheckAssign(gtCheckAssign1.getAssignHiddenId());

                    if (gtCheckAssign2 != null) {
                        db.update("GT_CHECK_ASSIGN",contentValues, "ASSIGN_HIDDEN_ID = ? ", new String[] {gtCheckAssign1.getAssignHiddenId()});
                    } else {
                        db.insert("GT_CHECK_ASSIGN", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtCheckAssign findGtCheckAssign(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_CHECK_ASSIGN.ASSIGN_HIDDEN_ID ");
        sb.append(" from GT_CHECK_ASSIGN where GT_CHECK_ASSIGN.ASSIGN_HIDDEN_ID = '" + id + "'");

        Cursor cursor = null;
        GtCheckAssign detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtCheckAssign();
                detail.setAssignHiddenId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/**************************************************************************/

    /**
     * GT_CHECK_DANGER
     */
    public void insertGtCheckDanger(List<GtCheckDanger> gtCheckDanger) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtCheckDanger != null && gtCheckDanger.size() > 0) {

                for (GtCheckDanger gtCheckDanger1: gtCheckDanger) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("CHECK_HIDDEN_ID",gtCheckDanger1.getCheckHiddenId());
                    contentValues.put("RELEVANCE_ID",gtCheckDanger1.getRelevanceId());
                    contentValues.put("PLAN_ID",gtCheckDanger1.getPlanId());
                    contentValues.put("HIDDEN_DANGER_MAJOR",gtCheckDanger1.getHiddenDangerMajor());
                    contentValues.put("HIDDEN_DANGER_CLASSES",gtCheckDanger1.getHiddenDangerClasses());
                    contentValues.put("CHECK_DATE",gtCheckDanger1.getCheckDate());
                    contentValues.put("CHECK_CLASSES",gtCheckDanger1.getCheckClasses());
                    contentValues.put("DANGER_CLASSES1",gtCheckDanger1.getDangerClasses1());
                    contentValues.put("DANGER_CLASSES2", gtCheckDanger1.getDangerClasses2());
                    contentValues.put("SYSTEM_CLASSES", gtCheckDanger1.getSystemClasses());
                    contentValues.put("CHECK_SITE", gtCheckDanger1.getCheckSite());
                    contentValues.put("DANGER_DESCRIPTION", gtCheckDanger1.getDangerDescription());
                    contentValues.put("COMPANY_COLLIERY_ID", gtCheckDanger1.getCompanyCollieryId());
                    contentValues.put("DANGER_STATUS", gtCheckDanger1.getDangerStatus());
                    contentValues.put("DEAL_WITH_FLG",gtCheckDanger1.getDealWithFlg());
                    contentValues.put("REFORM_DEPT",gtCheckDanger1.getReformDept());
                    contentValues.put("REFORM_DUTY_PERSON",gtCheckDanger1.getReformDutyPerson());
                    contentValues.put("REFORM_DEADLINE",gtCheckDanger1.getReformDeadline());
                    contentValues.put("REFORM_OPINION",gtCheckDanger1.getReformOpinion());
                    contentValues.put("SUPERVISE_FLG",gtCheckDanger1.getSuperviseFlg());
                    contentValues.put("SUPERVISE_DEPT",gtCheckDanger1.getSuperviseDept());
                    contentValues.put("SUPERVISE_PERSON",gtCheckDanger1.getSupervisePerson());
                    contentValues.put("ACCEPT_DEPT",gtCheckDanger1.getAcceptDept());
                    contentValues.put("ACCEPT_DEPT_NAME", gtCheckDanger1.getAcceptDeptName());
                    contentValues.put("ASSIGN_HIDDEN_ID", gtCheckDanger1.getAssignHiddenId());
                    contentValues.put("DANGER_ADDRESS_ID", gtCheckDanger1.getDangerAddressId());
                    contentValues.put("REFORM_CLASSES", gtCheckDanger1.getReformClasses());
                    contentValues.put("DANGER_FROM", gtCheckDanger1.getDangerFrom());
                    contentValues.put("DANGER_FROM2", gtCheckDanger1.getDangerFrom2());
                    contentValues.put("checkUsers",gtCheckDanger1.getCheckUsers());
                    contentValues.put("checkUserName",gtCheckDanger1.getCheckUserName());
                    contentValues.put("IS_UPLOAD",gtCheckDanger1.getIsUpload());
                    contentValues.put("DANGER_AREA",gtCheckDanger1.getDangerArea());
                    contentValues.put("MONEY", gtCheckDanger1.getMoney());
                    contentValues.put("EXALT_STS", gtCheckDanger1.getExaltSts());
                    contentValues.put("POSTPONE_STATUS", gtCheckDanger1.getPsotponeStatus());
                    contentValues.put("SITUATION", gtCheckDanger1.getSituation());
                    contentValues.put("CANCEL_OPINION", gtCheckDanger1.getCancelOption());
                    contentValues.put("CANCEL_PERSON", gtCheckDanger1.getCancelPreson());
                    contentValues.put("CANCEL_DATE", gtCheckDanger1.getCancelDate());
                    contentValues.put("REMARK", gtCheckDanger1.getRemark());
                    contentValues.put("del_flg", gtCheckDanger1.getDelFlg());
                    contentValues.put("insert_user_id", gtCheckDanger1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtCheckDanger1.getInsertUserId());
                    contentValues.put("update_datatime", gtCheckDanger1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtCheckDanger1.getUpdateUserId());

                    GtCheckDanger gtCheckDanger2 = findGtCheckDanger(gtCheckDanger1.getCheckHiddenId());


                    if (gtCheckDanger2 != null) {
                        db.update("GT_CHECK_DANGER",contentValues, "CHECK_HIDDEN_ID = ? ", new String[] {gtCheckDanger1.getCheckHiddenId()});
                    } else {
                        db.insert("GT_CHECK_DANGER", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtCheckDanger findGtCheckDanger(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_CHECK_DANGER.CHECK_HIDDEN_ID ");
        sb.append(" from GT_CHECK_DANGER where GT_CHECK_DANGER.CHECK_HIDDEN_ID = '" + id + "'");

        Cursor cursor = null;
        GtCheckDanger detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtCheckDanger();
                detail.setCheckHiddenId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/***************************************************************************/

    /**
     * GT_DANGER_ACCEPT
     */
    public void insertGtDangerAccept(List<GtDangerAccept> gtDangerAccept) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtDangerAccept != null && gtDangerAccept.size() > 0) {

                for (GtDangerAccept gtDangerAccept1: gtDangerAccept) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DANGER_ACCEPT_ID",gtDangerAccept1.getDangerAcceptId());
                    contentValues.put("CHECK_DANGER_ID",gtDangerAccept1.getCheckDangerId());
                    contentValues.put("ASSIGN_HIDDEN_ID",gtDangerAccept1.getAssignHiddenId());
                    contentValues.put("ACCEPT_DEPT",gtDangerAccept1.getAcceptDept());
                    contentValues.put("ACCEPT_RESULT",gtDangerAccept1.getAcceptResult());
                    contentValues.put("ACCEPT_DATE",gtDangerAccept1.getAcceptDate());
                    contentValues.put("ACCEPT_OPINION",gtDangerAccept1.getAcceptOpinion());
                    contentValues.put("ACCEPT_PERSON",gtDangerAccept1.getAcceptPerson());
                    contentValues.put("ACCEPT_TYPE", gtDangerAccept1.getAcceptType());
                    contentValues.put("IS_UPLOAD", gtDangerAccept1.getIsUpload());
                    contentValues.put("del_flg", gtDangerAccept1.getDelFlg());
                    contentValues.put("insert_user_id", gtDangerAccept1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtDangerAccept1.getInsertUserId());
                    contentValues.put("update_datatime", gtDangerAccept1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtDangerAccept1.getUpdateUserId());

                    GtDangerAccept gtDangerAccept2 = findGtDangerAccept(gtDangerAccept1.getDangerAcceptId());

                    if (gtDangerAccept2 != null) {
                        db.update("GT_DANGER_ACCEPT",contentValues, "DANGER_ACCEPT_ID = ? ", new String[] {gtDangerAccept1.getDangerAcceptId()});
                    } else {
                        db.insert("GT_DANGER_ACCEPT", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtDangerAccept findGtDangerAccept(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_DANGER_ACCEPT.DANGER_ACCEPT_ID ");
        sb.append(" from GT_DANGER_ACCEPT where GT_DANGER_ACCEPT.DANGER_ACCEPT_ID = '" + id + "'");

        Cursor cursor = null;
        GtDangerAccept detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtDangerAccept();
                detail.setDangerAcceptId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/*****************************************************************************/



    /**
     * GT_DANGER_REFORM
     */
    public void insertGtDangerReform1(List<GtDangerReform1> gtDangerAccept) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtDangerAccept != null && gtDangerAccept.size() > 0) {

                for (GtDangerReform1 gtDangerReform1: gtDangerAccept) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DANGER_REFORM_ID",gtDangerReform1.getDangerReformId());
                    contentValues.put("ASSIGN_HIDDEN_ID",gtDangerReform1.getAssignHiddenId());
                    contentValues.put("CHECK_DANGER_ID",gtDangerReform1.getCheckDangerId());
                    contentValues.put("REFORM_PERSON",gtDangerReform1.getReformPerson());
                    contentValues.put("REFORM_RESULT",gtDangerReform1.getReformResult());
                    contentValues.put("REFORM_DATE",gtDangerReform1.getReformDate());
                    contentValues.put("REFORM_MEASURE",gtDangerReform1.getReformMeasure());
                    contentValues.put("REFORM_CLASSES",gtDangerReform1.getReformClasses());
                    contentValues.put("IS_UPLOAD", gtDangerReform1.getIsUpload());
                    contentValues.put("del_flg", gtDangerReform1.getDelFlg());
                    contentValues.put("insert_user_id", gtDangerReform1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtDangerReform1.getInsertUserId());
                    contentValues.put("update_datatime", gtDangerReform1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtDangerReform1.getUpdateUserId());

                    GtDangerReform1 gtDangerReform2 = findGtDangerReform1(gtDangerReform1.getDangerReformId());
                    if (gtDangerReform2 != null) {
                        db.update("GT_DANGER_REFORM",contentValues, "DANGER_REFORM_ID = ? ", new String[] {gtDangerReform1.getDangerReformId()});
                    } else {
                        db.insert("GT_DANGER_REFORM", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtDangerReform1 findGtDangerReform1(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_DANGER_REFORM.DANGER_REFORM_ID ");
        sb.append(" from GT_DANGER_REFORM where GT_DANGER_REFORM.DANGER_REFORM_ID = '" + id + "'");

        Cursor cursor = null;
        GtDangerReform1 detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtDangerReform1();
                detail.setDangerReformId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/*****************************************************************/

    /**
     * GT_DANGER_SUPERVISE
     */
    public void insertGtDangerSupervise(List<GtDangerSupervise> gtDangerSupervise) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtDangerSupervise != null && gtDangerSupervise.size() > 0) {

                for (GtDangerSupervise gtDangerSupervise1: gtDangerSupervise) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DANGER_SUPERVISE_ID",gtDangerSupervise1.getDangerSuperviseId());
                    contentValues.put("CHECK_DANGER_ID",gtDangerSupervise1.getCheckDangerId());
                    contentValues.put("ASSIGN_HIDDEN_ID",gtDangerSupervise1.getAssignHiddenId());
                    contentValues.put("SUPERVISE_DATE",gtDangerSupervise1.getSuperviseDate());
                    contentValues.put("SUPERVISE_MEASURE",gtDangerSupervise1.getSuperviseMeasure());
                    contentValues.put("SUPERVISE_PERSON",gtDangerSupervise1.getSupervisePerson());
                    contentValues.put("IS_UPLOAD",gtDangerSupervise1.getIsUpload());
                    contentValues.put("del_flg", gtDangerSupervise1.getDelFlg());
                    contentValues.put("insert_user_id", gtDangerSupervise1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtDangerSupervise1.getInsertUserId());
                    contentValues.put("update_datatime", gtDangerSupervise1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtDangerSupervise1.getUpdateUserId());

                    GtDangerSupervise dangerSupervise2 = findDangerSupervise(gtDangerSupervise1.getDangerSuperviseId());

                    if (dangerSupervise2 != null) {
                        db.update("GT_DANGER_SUPERVISE",contentValues, "DANGER_SUPERVISE_ID = ? ", new String[] {gtDangerSupervise1.getDangerSuperviseId()});
                    } else {
                        db.insert("GT_DANGER_SUPERVISE", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtDangerSupervise findDangerSupervise(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_DANGER_SUPERVISE.DANGER_SUPERVISE_ID ");
        sb.append(" from GT_DANGER_SUPERVISE where GT_DANGER_SUPERVISE.DANGER_SUPERVISE_ID = '" + id + "'");

        Cursor cursor = null;
        GtDangerSupervise detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtDangerSupervise();
                detail.setDangerSuperviseId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/**********************************************************/


    /**
     * GT_EXALT
     */
    public void insertGtExalt(List<GtExalt> gtExalt) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtExalt != null && gtExalt.size() > 0) {

                for (GtExalt gtExalt1: gtExalt) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("GT_EXALT_ID",gtExalt1.getGtExaltId());
                    contentValues.put("CHECK_HIDDEN_ID",gtExalt1.getCheckHiddenId());
                    contentValues.put("EXALT_REASON",gtExalt1.getExaltReason());
                    contentValues.put("EXALT_OPINION",gtExalt1.getExaltOpinion());
                    contentValues.put("EXALT_DANGER_CLASSES",gtExalt1.getExaltDangerClasses());
                    contentValues.put("EXALT_PERSON",gtExalt1.getExaltPerson());
                    contentValues.put("EXALT_TIME",gtExalt1.getExaltTime());
                    contentValues.put("LAST_EXALT_DANGER_CLASSES",gtExalt1.getLastExaltDangerClasses());
                    contentValues.put("EXALT_TIME_OVER",gtExalt1.getExaltTimeOver());
                    contentValues.put("EXALT_PERSON_OVER", gtExalt1.getExaltPersonOver());
                    contentValues.put("STATUS", gtExalt1.getStatus());
                    contentValues.put("ASSIGN_HIDDEN_ID", gtExalt1.getAssignHiddenId());
                    contentValues.put("del_flg", gtExalt1.getDelFlg());
                    contentValues.put("insert_user_id", gtExalt1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtExalt1.getInsertUserId());
                    contentValues.put("update_datatime", gtExalt1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtExalt1.getUpdateUserId());

                    GtExalt gtExalt2 = findGtExalt(gtExalt1.getGtExaltId());

                    if (gtExalt2 != null) {
                        db.update("GT_EXALT",contentValues, "GT_EXALT_ID = ? ", new String[] {gtExalt1.getGtExaltId()});
                    } else {
                        db.insert("GT_EXALT", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtExalt findGtExalt(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_EXALT.GT_EXALT_ID ");
        sb.append(" from GT_EXALT where GT_EXALT.GT_EXALT_ID = '" + id + "'");

        Cursor cursor = null;
        GtExalt detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtExalt();
                detail.setGtExaltId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }


/***************************************************************************/


    /**
     * GT_POSTPONE
     */
    public void insertGtPostpone(List<GtPostpone> gtPostpone) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtPostpone != null && gtPostpone.size() > 0) {

                for (GtPostpone gtPostpone1: gtPostpone) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("POSTPONE_ID",gtPostpone1.getPostponeId());
                    contentValues.put("HIDDEN_ID",gtPostpone1.getHiddenId());
                    contentValues.put("POSTPONE_PERSION",gtPostpone1.getAuditPersion());
                    contentValues.put("APPLICATION_DATE",gtPostpone1.getApplicationDate());
                    contentValues.put("POSTPONE_DATE",gtPostpone1.getPostponeDate());
                    contentValues.put("POSTPONE_OPINION",gtPostpone1.getPostponeOpinion());
                    contentValues.put("AUDIT_PERSION",gtPostpone1.getAuditPersion());
                    contentValues.put("AUDIT_DATE",gtPostpone1.getAuditDate());
                    contentValues.put("AUDIT_OPINION",gtPostpone1.getAuditOpinion());
                    contentValues.put("AUDIT_STATUS", gtPostpone1.getAuditStatus());
                    contentValues.put("ASSIGN_HIDDEN_ID", gtPostpone1.getAssignHiddenId());
                    contentValues.put("del_flg", gtPostpone1.getDelFlg());
                    contentValues.put("insert_user_id", gtPostpone1.getInsertDatetime());
                    contentValues.put("insert_datetime", gtPostpone1.getInsertUserId());
                    contentValues.put("update_datatime", gtPostpone1.getUpdateDatetime());
                    contentValues.put("update_user_id", gtPostpone1.getUpdateUserId());

                    GtPostpone gtPostpone2 = findGtPostpone(gtPostpone1.getPostponeId());

                    if (gtPostpone2 != null) {
                        db.update("GT_POSTPONE",contentValues, "POSTPONE_ID = ? ", new String[] {gtPostpone1.getPostponeId()});
                    } else {
                        db.insert("GT_POSTPONE", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private GtPostpone findGtPostpone(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select GT_POSTPONE.POSTPONE_ID ");
        sb.append(" from GT_POSTPONE where GT_POSTPONE.POSTPONE_ID = '" + id + "'");

        Cursor cursor = null;
        GtPostpone detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new GtPostpone();
                detail.setPostponeId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }

/****************************************************************/

    /**
     * SYSTEM_CODE_MASTER
     */
    public void insertSystemCodeMaster(List<SystemCodeMaster> gtPostpone) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (gtPostpone != null && gtPostpone.size() > 0) {

                for (SystemCodeMaster systemCodeMaster1: gtPostpone) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("system_code_master_id",systemCodeMaster1.getSystemCodeMasterId());
                    contentValues.put("code_type",systemCodeMaster1.getCodeType());
                    contentValues.put("code_1",systemCodeMaster1.getCode1());
                    contentValues.put("code_2",systemCodeMaster1.getCode2());
                    contentValues.put("code_full_name",systemCodeMaster1.getCodeFullName());
                    contentValues.put("code_name",systemCodeMaster1.getCodeName());
                    contentValues.put("code_value_1",systemCodeMaster1.getCodeValue1());
                    contentValues.put("code_value_2",systemCodeMaster1.getCodeValue2());
                    contentValues.put("del_flg", systemCodeMaster1.getDelFlg());
                    contentValues.put("insert_user_id", systemCodeMaster1.getInsertDatetime());
                    contentValues.put("insert_datetime", systemCodeMaster1.getInsertUserId());
                    contentValues.put("update_datatime", systemCodeMaster1.getUpdateDatetime());
                    contentValues.put("update_user_id", systemCodeMaster1.getUpdateUserId());


                    SystemCodeMaster systemCodeMaster2 = findSystemCodeMaster(systemCodeMaster1.getSystemCodeMasterId());

                    if (systemCodeMaster2 != null) {
                        db.update("SYSTEM_CODE_MASTER",contentValues, "system_code_master_id = ? ", new String[] {systemCodeMaster2.getSystemCodeMasterId()});
                    } else {
                        db.insert("SYSTEM_CODE_MASTER", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private SystemCodeMaster findSystemCodeMaster(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select SYSTEM_CODE_MASTER.system_code_master_id ");
        sb.append(" from SYSTEM_CODE_MASTER where SYSTEM_CODE_MASTER.system_code_master_id = '" + id + "'");

        Cursor cursor = null;
        SystemCodeMaster detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new SystemCodeMaster();
                detail.setSystemCodeMasterId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }
/******************************************************************************/


    /**
     * YHPC_HIDDEN_PLAN
     */
    public void insertHiddenPlan(List<HiddenPlan> hiddenPlen) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (hiddenPlen != null && hiddenPlen.size() > 0) {

                for (HiddenPlan hiddenPlan: hiddenPlen) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("HIDDEN_CHECK_PLANID",hiddenPlan.getHiddenCheckPlanid());
                    contentValues.put("COMPANY_COLLIERY_ID",hiddenPlan.getCompanyCollieryId());
                    contentValues.put("PLAN_NAME",hiddenPlan.getPlanName());
                    contentValues.put("CHECK_TYPE",hiddenPlan.getCheckType());
                    contentValues.put("CHECK_TIME",hiddenPlan.getCheckTime());
                    contentValues.put("CHECK_ROUTE",hiddenPlan.getCheckRoute());
                    contentValues.put("CHECK_RANGE",hiddenPlan.getCheckRange());
                    contentValues.put("CHECK_CONTENT",hiddenPlan.getCheckContent());
                    contentValues.put("DEPARTMENT",hiddenPlan.getDepartment());
                    contentValues.put("DEPARTMENT_MAN",hiddenPlan.getDepartmentMan());
                    contentValues.put("DEPARTMENT_PERSONS",hiddenPlan.getDepartmentPersons());
                    contentValues.put("EXAMINE_STATUS",hiddenPlan.getExamineStatus());
                    contentValues.put("EXAMINE_OPINION",hiddenPlan.getExamineOpinion());
                    contentValues.put("AUDIT_DATE",hiddenPlan.getAuditDate());
                    contentValues.put("AUDIT_PERSON",hiddenPlan.getAuditPerson());
                    contentValues.put("TASK_STATUS",hiddenPlan.getTaskStatus());
                    contentValues.put("NOTE",hiddenPlan.getNote());
                    contentValues.put("IS_UPLOAD",hiddenPlan.getIsUpload());
                    contentValues.put("del_flg", hiddenPlan.getDelFlg());
                    contentValues.put("insert_user_id", hiddenPlan.getInsertDatetime());
                    contentValues.put("insert_datetime", hiddenPlan.getInsertUserId());
                    contentValues.put("update_datatime", hiddenPlan.getUpdateDatetime());
                    contentValues.put("update_user_id", hiddenPlan.getUpdateUserId());

                    HiddenPlan hiddenPlan1 = findHiddenPlan(hiddenPlan.getHiddenCheckPlanid());


                    if (hiddenPlan1 != null) {
                        db.update("YHPC_HIDDEN_PLAN",contentValues, "HIDDEN_CHECK_PLANID = ? ", new String[] {hiddenPlan.getHiddenCheckPlanid()});
                    } else {
                        db.insert("YHPC_HIDDEN_PLAN", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private HiddenPlan findHiddenPlan(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select YHPC_HIDDEN_PLAN.HIDDEN_CHECK_PLANID ");
        sb.append(" from YHPC_HIDDEN_PLAN where YHPC_HIDDEN_PLAN.HIDDEN_CHECK_PLANID = '" + id + "'");

        Cursor cursor = null;
        HiddenPlan detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new HiddenPlan();
                detail.setHiddenCheckPlanid(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }


/************************************************************************************/


    /**
     * YHPC_HIDDEN_PLAN_DETAIL
     */
    public void insertHiddenPlanDetail(List<HiddenPlanDetail> hiddenPlanDetail) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();

        try {

            if (hiddenPlanDetail != null && hiddenPlanDetail.size() > 0) {

                for (HiddenPlanDetail hiddenPlanDetail1: hiddenPlanDetail) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("HIDDEN_PLAN_DETAIL",hiddenPlanDetail1.getHiddenPlanDetail());
                    contentValues.put("HIDDEN_CHECK_PLANID",hiddenPlanDetail1.getHiddenCheckPlanid());
                    contentValues.put("HIDDEN_ID",hiddenPlanDetail1.getHiddenId());
                    contentValues.put("HIDDEN_MAJOR",hiddenPlanDetail1.getHiddenMajor());
                    contentValues.put("HIDDEN_LEVEL",hiddenPlanDetail1.getHiddenLevel());
                    contentValues.put("HIDDEN_TYPE1",hiddenPlanDetail1.getHiddenType1());
                    contentValues.put("HIDDEN_TYPE2",hiddenPlanDetail1.getHiddenType2());
                    contentValues.put("CLASS_IFICATION",hiddenPlanDetail1.getClassIfication());
                    contentValues.put("HIDDEN_DESCRIBE",hiddenPlanDetail1.getHiddenDescribe());
                    contentValues.put("CHECK_RESULT",hiddenPlanDetail1.getCheckResult());
                    contentValues.put("hiddenBasis",hiddenPlanDetail1.getHiddenBasis());
                    contentValues.put("hiddenStandard",hiddenPlanDetail1.getHiddenStandard());
                    contentValues.put("hiddenOpinion",hiddenPlanDetail1.getHiddenOpinion());
                    contentValues.put("DANGER_ADDRESS",hiddenPlanDetail1.getDangerAddress());
                    contentValues.put("dangerAddressIdName",hiddenPlanDetail1.getDangerAddressIdName());
                    contentValues.put("DANGER_ADDRESS_NAME",hiddenPlanDetail1.getDangerAddressName());
                    contentValues.put("DANGER_AREA_NAME",hiddenPlanDetail1.getDangerAreaName());
                    contentValues.put("DANGER_AREA",hiddenPlanDetail1.getDangerArea());
                    contentValues.put("step",hiddenPlanDetail1.getStep());
                    contentValues.put("note",hiddenPlanDetail1.getNote());
                    contentValues.put("del_flg", hiddenPlanDetail1.getDelFlg());
                    contentValues.put("insert_user_id", hiddenPlanDetail1.getInsertDatetime());
                    contentValues.put("insert_datetime", hiddenPlanDetail1.getInsertUserId());
                    contentValues.put("update_datatime", hiddenPlanDetail1.getUpdateDatetime());
                    contentValues.put("update_user_id", hiddenPlanDetail1.getUpdateUserId());

                    HiddenPlanDetail hiddenPlanDetail2 = findHiddenPlanDetail(hiddenPlanDetail1.getHiddenPlanDetail());


                    if (hiddenPlanDetail2 != null) {
                        db.update("YHPC_HIDDEN_PLAN_DETAIL",contentValues, "HIDDEN_PLAN_DETAIL = ? ", new String[] {hiddenPlanDetail1.getHiddenPlanDetail()});
                    } else {
                        db.insert("YHPC_HIDDEN_PLAN_DETAIL", null, contentValues);
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    private HiddenPlanDetail findHiddenPlanDetail(String id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        StringBuffer sb = new StringBuffer(" select YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_PLAN_DETAIL ");
        sb.append(" from YHPC_HIDDEN_PLAN_DETAIL where YHPC_HIDDEN_PLAN_DETAIL.HIDDEN_PLAN_DETAIL = '" + id + "'");

        Cursor cursor = null;
        HiddenPlanDetail detail = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            while (cursor != null && cursor.moveToNext()) {
                detail = new HiddenPlanDetail();
                detail.setHiddenPlanDetail(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detail;
    }





}
