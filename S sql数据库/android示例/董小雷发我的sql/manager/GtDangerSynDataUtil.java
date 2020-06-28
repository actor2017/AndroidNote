package com.nkay.swyt.database.manager;


import android.content.Context;

import com.nkay.swyt.database.dao.BzhCheckGroupDao;
import com.nkay.swyt.database.dao.BzhPlanDao;
import com.nkay.swyt.database.dao.CollieryClassesDao;
import com.nkay.swyt.database.dao.CompanyCollieryDao;
import com.nkay.swyt.database.dao.FjgkSceneCheckDao;
import com.nkay.swyt.database.dao.GtAddressDao;
import com.nkay.swyt.database.dao.GtCheckDangerDao;
import com.nkay.swyt.database.dao.HiddenPlanDao;
import com.nkay.swyt.model.BzhCheckGroup;
import com.nkay.swyt.model.BzhCheckMajorDetail;
import com.nkay.swyt.model.BzhMajorCheckContent;
import com.nkay.swyt.model.BzhPlan;
import com.nkay.swyt.model.BzhPlanMajor;
import com.nkay.swyt.model.CollieryClasses;
import com.nkay.swyt.model.CompanyCollierUser;
import com.nkay.swyt.model.CompanyColliery;
import com.nkay.swyt.model.CompanycollierDepartmentManagement;
import com.nkay.swyt.model.FjgkSceneCheck;
import com.nkay.swyt.model.FjgkSceneCheckDanger;
import com.nkay.swyt.model.GtAddress;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 最新流程隐患相关数据下拉刷新工具类
 */
public class GtDangerSynDataUtil {
    // 本类的单例实例
    private static GtDangerSynDataUtil instance;

    // 获取本类单例对象的方法
    public static synchronized GtDangerSynDataUtil getInstance() {
        if (instance == null) {
            synchronized (GtDangerSynDataUtil.class) {
                if (instance == null) {
                    instance = new GtDangerSynDataUtil();
                }
            }
        }
        return instance;
    }

    //同步隐患相关信息
    public void synGtDangerData(JSONObject jb, Context context) throws JSONException {
        //同步基础数据
        synBasicData(jb, context);
        //同步风险相关数据
        synFjkgData(jb, context);
        //同步隐患排查相关数据
        synYHPCData(jb, context);
        //隐患相关数据
        synHiddenData(jb, context);
        //质量标准化相关数据
        synBZHData(jb, context);
    }

    public void synBZHData(JSONObject jb, Context context) throws JSONException {

        if (jb.getString("plan") != null
                && !jb.getString("plan").equals("")
                && !jb.getString("plan").equals("null")) {
            BzhPlan bzhPlan = new BzhPlan();
            JSONObject jb1 = jb.getJSONObject("plan");
            bzhPlan.setQualityplanId(jb1.getString("qualityplanId"));
            bzhPlan.setPlanName(jb1.getString("planName"));
            bzhPlan.setCheckType(jb1.getString("checkType"));
            bzhPlan.setCheckedSection(jb1.getString("checkedSection"));
            bzhPlan.setCollieryType(jb1.getString("collieryType"));
            bzhPlan.setCheckYear(jb1.getString("checkYear"));
            bzhPlan.setCheckMonth(jb1.getString("checkMonth"));
            bzhPlan.setCheckComments(jb1.getString("checkComments"));
            bzhPlan.setCheckAudit(jb1.getString("checkAudit"));
            bzhPlan.setCheckAuditDate(jb1.getString("checkAuditDate"));
            bzhPlan.setCheckAuditStatus(jb1.getString("checkAuditStatus"));
            bzhPlan.setCheckAuditAdvice(jb1.getString("checkAuditAdvice"));
            bzhPlan.setCheckApprover(jb1.getString("checkApprover"));
            bzhPlan.setCheckApproverData(jb1.getString("checkApproverData"));
            bzhPlan.setCheckApproverAdvice(jb1.getString("checkApproverAdvice"));
            bzhPlan.setIsExecute(jb1.getString("isExecute"));
            bzhPlan.setDelFlg(0);
            bzhPlan.setInsertUserId(jb1.getString("insertUserId"));
            bzhPlan.setInsertDatetime(jb1.getString("insertDatetime"));
            bzhPlan.setUpdateUserId(jb1.getString("updateUserId"));
            bzhPlan.setUpdateDatetime(jb1.getString("updateDatetime"));
            new BzhPlanDao(context).insertOrUpdate(bzhPlan);
        }

        if (jb.getString("bzhPlan") != null
                && !jb.getString("bzhPlan").equals("")
                && !jb.getString("bzhPlan").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("bzhPlan");
            ArrayList<BzhPlan> bzhPlen = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                BzhPlan bzhPlan = new BzhPlan();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                bzhPlan.setQualityplanId(jb1.getString("qualityplanId"));
                bzhPlan.setPlanName(jb1.getString("planName"));
                bzhPlan.setCheckType(jb1.getString("checkType"));
                bzhPlan.setCheckedSection(jb1.getString("checkedSection"));
                bzhPlan.setCollieryType(jb1.getString("collieryType"));
                bzhPlan.setCheckYear(jb1.getString("checkYear"));
                bzhPlan.setCheckMonth(jb1.getString("checkMonth"));
                bzhPlan.setCheckComments(jb1.getString("checkComments"));
                bzhPlan.setCheckAudit(jb1.getString("checkAudit"));
                bzhPlan.setCheckAuditDate(jb1.getString("checkAuditDate"));
                bzhPlan.setCheckAuditStatus(jb1.getString("checkAuditStatus"));
                bzhPlan.setCheckAuditAdvice(jb1.getString("checkAuditAdvice"));
                bzhPlan.setCheckApprover(jb1.getString("checkApprover"));
                bzhPlan.setCheckApproverData(jb1.getString("checkApproverData"));
                bzhPlan.setCheckApproverAdvice(jb1.getString("checkApproverAdvice"));
                bzhPlan.setIsExecute(jb1.getString("isExecute"));
                bzhPlan.setDelFlg(0);
                bzhPlan.setInsertUserId(jb1.getString("insertUserId"));
                bzhPlan.setInsertDatetime(jb1.getString("insertDatetime"));
                bzhPlan.setUpdateUserId(jb1.getString("updateUserId"));
                bzhPlan.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new BzhPlanDao(context).insertOrUpdate(bzhPlan);
                bzhPlen.add(bzhPlan);

            }
            new SynDataSqlMgr(context).insertBzhPlan(bzhPlen);


        }
        if (jb.getString("bzhPlanMajor") != null
                && !jb.getString("bzhPlanMajor").equals("")
                && !jb.getString("bzhPlanMajor").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("bzhPlanMajor");
            ArrayList<BzhPlanMajor> bzhPlanMajors = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                BzhPlanMajor bzhPlanMajor = new BzhPlanMajor();
                bzhPlanMajor.setYixuanzeprojectsId(jb1.getString("yixuanzeprojectsId"));
                bzhPlanMajor.setMajorId(jb1.getString("majorId"));
                bzhPlanMajor.setQualityplanId(jb1.getString("qualityplanId"));
                bzhPlanMajor.setYixuanzeprojectsName(jb1.getString("yixuanzeprojectsName"));
                bzhPlanMajor.setFinallNum(jb1.getString("finallNum"));
                bzhPlanMajor.setCheckleadName(jb1.getString("checkleadName"));
                bzhPlanMajor.setCheckleadId(jb1.getString("checkleadId"));
                bzhPlanMajor.setCheckviceName(jb1.getString("checkviceName"));
                bzhPlanMajor.setCheckviceId(jb1.getString("checkviceId"));
                bzhPlanMajor.setCheckmemberName(jb1.getString("checkmemberName"));
                bzhPlanMajor.setCheckmemberId(jb1.getString("checkmemberId"));
                bzhPlanMajor.setDelFlg(0);
                bzhPlanMajor.setInsertUserId(jb1.getString("insertUserId"));
                bzhPlanMajor.setInsertDatetime(jb1.getString("insertDatetime"));
                bzhPlanMajor.setUpdateUserId(jb1.getString("updateUserId"));
                bzhPlanMajor.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new BzhPlanMajorDao(context).insertOrUpdate(bzhPlanMajor);
                bzhPlanMajors.add(bzhPlanMajor);

            }
            new SynDataSqlMgr(context).insertBzhPlanMajor(bzhPlanMajors);

        }
        if (jb.getString("bzhCheckMajorDetail") != null
                && !jb.getString("bzhCheckMajorDetail").equals("")
                && !jb.getString("bzhCheckMajorDetail").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("bzhCheckMajorDetail");
            List<BzhCheckMajorDetail> bzhDetails = new ArrayList<BzhCheckMajorDetail>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                BzhCheckMajorDetail bzhCheckMajorDetail = new BzhCheckMajorDetail();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                bzhCheckMajorDetail.setSelfCheckSpecialtyId(jb1.getString("selfCheckSpecialtyId"));
                bzhCheckMajorDetail.setYixuanzeprojectsId(jb1.getString("yixuanzeprojectsId"));
                bzhCheckMajorDetail.setSelfCheckcontentsId(jb1.getString("selfCheckcontentsId"));
                bzhCheckMajorDetail.setSelfCheckProjectname(jb1.getString("selfCheckProjectname"));
                bzhCheckMajorDetail.setSelfCheckProjectcontents(jb1.getString("selfCheckProjectcontents"));
                bzhCheckMajorDetail.setSelfCheckProjectcontents2(jb1.getString("selfCheckProjectcontents2"));
                bzhCheckMajorDetail.setSelfCheckBasicrequirements(jb1.getString("selfCheckBasicrequirements"));
                bzhCheckMajorDetail.setSelfCheckStandartscore(jb1.getDouble("selfCheckStandartscore"));
                bzhCheckMajorDetail.setSelfCheckMethodofcomment(jb1.getString("selfCheckMethodofcomment"));
                bzhCheckMajorDetail.setSelfCheckScore(jb1.getDouble("selfCheckScore") == -1 ? 0 : jb1.getDouble("selfCheckScore"));
                bzhCheckMajorDetail.setSelfCheckSort(jb1.getString("selfCheckSort"));
                bzhCheckMajorDetail.setNodeName(jb1.getString("nodeName"));
                bzhCheckMajorDetail.setSelfCheckScoreGroup(jb1.getString("selfCheckScoreGroup"));
                bzhCheckMajorDetail.setSelfCheckMethodGroup(jb1.getString("selfCheckMethodGroup"));
                bzhCheckMajorDetail.setCheckWorkface(jb1.getString("checkWorkface"));
                bzhCheckMajorDetail.setCheckWorkfaceName(jb1.getString("checkWorkfaceName"));
                bzhCheckMajorDetail.setCheckName(jb1.getString("checkName"));
                bzhCheckMajorDetail.setLackFlg(jb1.getString("lackFlg"));
                //黄陵专用
                bzhCheckMajorDetail.setDutyDept(jb1.getString("dutyDept"));
                bzhCheckMajorDetail.setDelFlg(0);
                bzhCheckMajorDetail.setInsertUserId(jb1.getString("insertUserId"));
                bzhCheckMajorDetail.setInsertDatetime(jb1.getString("insertDatetime"));
                bzhCheckMajorDetail.setUpdateUserId(jb1.getString("updateUserId"));
                bzhCheckMajorDetail.setUpdateDatetime(jb1.getString("updateDatetime"));
                bzhDetails.add(bzhCheckMajorDetail);
//                new BzhCheckMajorDetailDao(context).insertOrUpdate(bzhCheckMajorDetail);
            }
             new SynDataSqlMgr(context).insertBzhCheckMajorDetail(bzhDetails);
        }

        if (jb.getString("bzhCheckGroup") != null
                && !jb.getString("bzhCheckGroup").equals("")
                && !jb.getString("bzhCheckGroup").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("bzhCheckGroup");
            for (int j = 0; j < jsonArray1.length(); j++) {
                BzhCheckGroup bzhCheckGroup = new BzhCheckGroup();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                bzhCheckGroup.setSelfCheckGroupId(jb1.getString("selfCheckGroupId"));
                bzhCheckGroup.setYixuanzeprojectsId(jb1.getString("yixuanzeprojectsId"));
                bzhCheckGroup.setSelfCheckExaminer(jb1.getString("selfCheckExaminer"));
                bzhCheckGroup.setSelfCheckInspectionleader(jb1.getString("selfCheckInspectionleader").equals("true") ? 1 : 0);
                bzhCheckGroup.setSelfCheckDeputyleader(jb1.getString("selfCheckDeputyleader").equals("true") ? 1 : 0);
                bzhCheckGroup.setDelFlg(0);
                bzhCheckGroup.setInsertUserId(jb1.getString("insertUserId"));
                bzhCheckGroup.setInsertDatetime(jb1.getString("insertDatetime"));
                bzhCheckGroup.setUpdateUserId(jb1.getString("updateUserId"));
                bzhCheckGroup.setUpdateDatetime(jb1.getString("updateDatetime"));
                new BzhCheckGroupDao(context).insertOrUpdate(bzhCheckGroup);
            }
        }
    }

    /**
     * 同步隐患排查相关表数据
     */
    public void synYHPCData(JSONObject jb, Context context) throws JSONException {
        //单个计划
        if (jb.getString("checkHiddenPlan") != null
                && !jb.getString("checkHiddenPlan").equals("")
                && !jb.getString("checkHiddenPlan").equals("null")) {
            HiddenPlan bzhPlan = new HiddenPlan();
            JSONObject jb1 = jb.getJSONObject("checkHiddenPlan");
            bzhPlan.setHiddenCheckPlanid(jb1.getString("hiddenCheckPlanid"));
            bzhPlan.setCompanyCollieryId(jb1.getString("companyCollieryId"));
            bzhPlan.setPlanName(jb1.getString("planName"));
            bzhPlan.setCheckType(jb1.getString("checkType"));
            bzhPlan.setCheckTime(jb1.getString("checkTime"));
            bzhPlan.setCheckRoute(jb1.getString("checkRoute"));
            bzhPlan.setCheckRange(jb1.getString("checkRange"));
            bzhPlan.setCheckContent(jb1.getString("checkContent"));
            bzhPlan.setDepartment(jb1.getString("department"));
            bzhPlan.setDepartmentMan(jb1.getString("departmentMan"));
            bzhPlan.setDepartmentPersons(jb1.getString("departmentPersons"));
            bzhPlan.setExamineStatus(jb1.getString("examineStatus"));
            bzhPlan.setExamineOpinion(jb1.getString("examineOpinion"));
            bzhPlan.setAuditDate(jb1.getString("auditDate"));
            bzhPlan.setAuditPerson(jb1.getString("auditPerson"));
            bzhPlan.setTaskStatus(jb1.getString("taskStatus"));
            bzhPlan.setNote(jb1.getString("note"));
            bzhPlan.setDelFlg(0);
            bzhPlan.setInsertUserId(jb1.getString("insertUserId"));
            bzhPlan.setInsertDatetime(jb1.getString("insertDatetime"));
            bzhPlan.setUpdateUserId(jb1.getString("updateUserId"));
            bzhPlan.setUpdateDatetime(jb1.getString("updateDatetime"));
            new HiddenPlanDao(context).insertOrUpdate(bzhPlan);
        }
        //隐患排查计划表数据
        if (jb.getString("hiddenPlan") != null
                && !jb.getString("hiddenPlan").equals("")
                && !jb.getString("hiddenPlan").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("hiddenPlan");

            ArrayList<HiddenPlan> hiddenPlen = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                HiddenPlan hp = new HiddenPlan();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                hp.setHiddenCheckPlanid(jb1.getString("hiddenCheckPlanid"));
                hp.setCompanyCollieryId(jb1.getString("companyCollieryId"));
                hp.setPlanName(jb1.getString("planName"));
                hp.setCheckType(jb1.getString("checkType"));
                hp.setCheckTime(jb1.getString("checkTime"));
                hp.setCheckRoute(jb1.getString("checkRoute"));
                hp.setCheckRange(jb1.getString("checkRange"));
                hp.setCheckContent(jb1.getString("checkContent"));
                hp.setDepartment(jb1.getString("department"));
                hp.setDepartmentMan(jb1.getString("departmentMan"));
                hp.setDepartmentPersons(jb1.getString("departmentPersons"));
                hp.setExamineStatus(jb1.getString("examineStatus"));
                hp.setExamineOpinion(jb1.getString("examineOpinion"));
                hp.setAuditDate(jb1.getString("auditDate"));
                hp.setAuditPerson(jb1.getString("auditPerson"));
                hp.setTaskStatus(jb1.getString("taskStatus"));
                hp.setNote(jb1.getString("note"));
                hp.setDelFlg(0);
                hp.setInsertUserId(jb1.getString("insertUserId"));
                hp.setInsertDatetime(jb1.getString("insertDatetime"));
                hp.setUpdateUserId(jb1.getString("updateUserId"));
                hp.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new HiddenPlanDao(context).insertOrUpdate(hp);
                hiddenPlen.add(hp);
            }
            new SynDataSqlMgr(context).insertHiddenPlan(hiddenPlen);
        }
        //隐患排查详情表数据
        if (jb.getString("hiddenPlanDetail") != null
                && !jb.getString("hiddenPlanDetail").equals("")
                && !jb.getString("hiddenPlanDetail").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("hiddenPlanDetail");
            ArrayList<HiddenPlanDetail> hiddenPlanDetails = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                HiddenPlanDetail hpd = new HiddenPlanDetail();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                hpd.setHiddenCheckPlanid(jb1.getString("hiddenCheckPlanid"));
                hpd.setHiddenPlanDetail(jb1.getString("hiddenPlanDetail"));
                hpd.setHiddenId(jb1.getString("hiddenId"));
                hpd.setHiddenMajor(jb1.getString("hiddenMajor"));
                hpd.setHiddenLevel(jb1.getString("hiddenLevel"));
                hpd.setHiddenType1(jb1.getString("hiddenType1"));
                hpd.setHiddenType2(jb1.getString("hiddenType2"));
                hpd.setClassIfication(jb1.getString("classIfication"));
                hpd.setHiddenDescribe(jb1.getString("hiddenDescribe"));
                hpd.setCheckResult(jb1.getString("checkResult"));
                hpd.setHiddenBasis(jb1.getString("hiddenBasis"));
                hpd.setHiddenStandard(jb1.getString("hiddenStandard"));
                hpd.setHiddenOpinion(jb1.getString("hiddenOpinion"));
                hpd.setDangerAddress(jb1.getString("dangerAddress"));
                hpd.setDangerAddressName(jb1.getString("dangerAddressName"));
                hpd.setDangerAreaName(jb1.getString("dangerAreaName"));
                hpd.setDangerArea(jb1.getString("dangerArea"));
                hpd.setDangerAddressIdName(jb1.getString("dangerAddressIdName"));
                hpd.setStep(jb1.getString("step"));
                hpd.setNote(jb1.getString("note"));
                hpd.setDelFlg(0);
                hpd.setInsertUserId(jb1.getString("insertUserId"));
                hpd.setInsertDatetime(jb1.getString("insertDatetime"));
                hpd.setUpdateUserId(jb1.getString("updateUserId"));
                hpd.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new HiddenPlanDetailDao(context).insertOrUpdate(hpd);
                hiddenPlanDetails.add(hpd);
            }

            new  SynDataSqlMgr(context).insertHiddenPlanDetail(hiddenPlanDetails);
        }
    }

    /**
     * 隐患信息相关数据
     *
     * @param jb
     */
    public void synHiddenData(JSONObject jb, Context context) throws JSONException {
        //多条隐患信息
        if (jb.getString("gtCheckDanger") != null
                && !jb.getString("gtCheckDanger").equals("")
                && !jb.getString("gtCheckDanger").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtCheckDanger");

            ArrayList<GtCheckDanger> gtCheckDangers = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                GtCheckDanger gcd = new GtCheckDanger();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gcd.setCheckHiddenId(jb1.getString("checkHiddenId"));
                gcd.setRelevanceId(jb1.getString("relevanceId"));
                gcd.setPlanId(jb1.getString("planId"));
                gcd.setHiddenDangerMajor(jb1.getString("hiddenDangerMajor"));
                gcd.setHiddenDangerClasses(jb1.getString("hiddenDangerClasses"));
                gcd.setCheckDate(jb1.getString("checkDate"));
                gcd.setCheckClasses(jb1.getString("checkClasses"));
                gcd.setDangerClasses1(jb1.getString("dangerClasses1"));
                gcd.setDangerClasses2(jb1.getString("dangerClasses2"));
                gcd.setSystemClasses(jb1.getString("systemClasses"));
                gcd.setCheckSite(jb1.getString("checkSite"));
                gcd.setDangerDescription(jb1.getString("dangerDescription"));
                gcd.setCompanyCollieryId(jb1.getString("companyCollieryId"));
                gcd.setDangerStatus(jb1.getString("dangerStatus"));
                gcd.setDealWithFlg(Integer.parseInt(jb1.getString("dealWithFlg")));
                gcd.setReformDept(jb1.getString("reformDept"));
                gcd.setReformDutyPerson(jb1.getString("reformDutyPerson"));
                gcd.setReformDeadline(jb1.getString("reformDeadline"));
                gcd.setReformClasses(jb1.getString("reformClasses"));
                gcd.setReformOpinion(jb1.getString("reformOpinion"));
                gcd.setSuperviseFlg(jb1.getString("superviseFlg").equals("true") ? 1 : 0);
                gcd.setSuperviseDept(jb1.getString("superviseDept"));
                gcd.setSupervisePerson(jb1.getString("supervisePerson"));
                gcd.setAcceptDept(jb1.getString("acceptDept"));
                gcd.setAcceptDeptName(jb1.getString("acceptDeptName"));
                gcd.setDangerFrom(jb1.getString("dangerFrom"));
                gcd.setDangerFrom2(jb1.getString("dangerFrom2"));
                gcd.setReformClasses(jb1.getString("reformClasses"));
                gcd.setCheckUsers(jb1.getString("checkUsers"));
                gcd.setCheckUserName(jb1.getString("checkUserName"));
                gcd.setDangerArea(jb1.getString("dangerArea"));
                gcd.setDangerAddressId(jb1.getString("dangerAddressId"));
                gcd.setMoney(jb1.getString("money"));
                gcd.setExaltSts(jb1.getString("exaltSts"));
                gcd.setPsotponeStatus(jb1.getString("postponeStatus"));
                gcd.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gcd.setSituation(jb1.getString("situation"));
                gcd.setCancelOption(jb1.getString("cancelOption"));
                gcd.setCancelPreson(jb1.getString("cancelPreson"));
                gcd.setCancelDate(jb1.getString("cancelDate"));
                gcd.setRemark(jb1.getString("remark"));
                gcd.setDelFlg(0);
                gcd.setInsertUserId(jb1.getString("insertUserId"));
                gcd.setInsertDatetime(jb1.getString("insertDatetime"));
                gcd.setUpdateUserId(jb1.getString("updateUserId"));
                gcd.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new GtCheckDangerDao(context).insertOrUpdate(gcd);
                gtCheckDangers.add(gcd);
            }

            new SynDataSqlMgr(context).insertGtCheckDanger(gtCheckDangers);
        }

        //单个隐患信息
        if (jb.getString("checkDanger") != null
                && !jb.getString("checkDanger").equals("")
                && !jb.getString("checkDanger").equals("null")) {
            GtCheckDanger gcd = new GtCheckDanger();
            JSONObject jb1 = jb.getJSONObject("checkDanger");
            gcd.setCheckHiddenId(jb1.getString("checkHiddenId"));
            gcd.setRelevanceId(jb1.getString("relevanceId"));
            gcd.setPlanId(jb1.getString("planId"));
            gcd.setHiddenDangerMajor(jb1.getString("hiddenDangerMajor"));
            gcd.setHiddenDangerClasses(jb1.getString("hiddenDangerClasses"));
            gcd.setCheckDate(jb1.getString("checkDate"));
            gcd.setCheckClasses(jb1.getString("checkClasses"));
            gcd.setDangerClasses1(jb1.getString("dangerClasses1"));
            gcd.setDangerClasses2(jb1.getString("dangerClasses2"));
            gcd.setSystemClasses(jb1.getString("systemClasses"));
            gcd.setCheckSite(jb1.getString("checkSite"));
            gcd.setDangerDescription(jb1.getString("dangerDescription"));
            gcd.setCompanyCollieryId(jb1.getString("companyCollieryId"));
            gcd.setDangerStatus(jb1.getString("dangerStatus"));
            gcd.setDealWithFlg(Integer.parseInt(jb1.getString("dealWithFlg")));
            gcd.setReformDept(jb1.getString("reformDept"));
            gcd.setReformDutyPerson(jb1.getString("reformDutyPerson"));
            gcd.setReformDeadline(jb1.getString("reformDeadline"));
            gcd.setReformClasses(jb1.getString("reformClasses"));
            gcd.setReformOpinion(jb1.getString("reformOpinion"));
            gcd.setSuperviseFlg(jb1.getString("superviseFlg").equals("true") ? 1 : 0);
            gcd.setSuperviseDept(jb1.getString("superviseDept"));
            gcd.setSupervisePerson(jb1.getString("supervisePerson"));
            gcd.setAcceptDept(jb1.getString("acceptDept"));
            gcd.setAcceptDeptName(jb1.getString("acceptDeptName"));
            gcd.setDangerFrom(jb1.getString("dangerFrom"));
            gcd.setDangerFrom2(jb1.getString("dangerFrom2"));
            gcd.setReformClasses(jb1.getString("reformClasses"));
            gcd.setCheckUsers(jb1.getString("checkUsers"));
            gcd.setCheckUserName(jb1.getString("checkUserName"));
            gcd.setDangerArea(jb1.getString("dangerArea"));
            gcd.setDangerAddressId(jb1.getString("dangerAddressId"));
            gcd.setMoney(jb1.getString("money"));
            gcd.setExaltSts(jb1.getString("exaltSts"));
            gcd.setPsotponeStatus(jb1.getString("postponeStatus"));
            gcd.setAssignHiddenId(jb1.getString("assignHiddenId"));
            gcd.setSituation(jb1.getString("situation"));
            gcd.setCancelOption(jb1.getString("cancelOption"));
            gcd.setCancelPreson(jb1.getString("cancelPreson"));
            gcd.setCancelDate(jb1.getString("cancelDate"));
            gcd.setRemark(jb1.getString("remark"));
            gcd.setDelFlg(0);
            gcd.setInsertUserId(jb1.getString("insertUserId"));
            gcd.setInsertDatetime(jb1.getString("insertDatetime"));
            gcd.setUpdateUserId(jb1.getString("updateUserId"));
            gcd.setUpdateDatetime(jb1.getString("updateDatetime"));
            new GtCheckDangerDao(context).insertOrUpdate(gcd);
        }

        //下达表信息
        if (jb.getString("gtCheckAssign") != null
                && !jb.getString("gtCheckAssign").equals("")
                && !jb.getString("gtCheckAssign").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtCheckAssign");

            ArrayList<GtCheckAssign> gtCheckAssigns = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                GtCheckAssign gtCheckAssign = new GtCheckAssign();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gtCheckAssign.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gtCheckAssign.setCheckHiddenId(jb1.getString("checkHiddenId"));
                gtCheckAssign.setDealWithFlg(Integer.parseInt(jb1.getString("dealWithFlg")));
                gtCheckAssign.setReformDept(jb1.getString("reformDept"));
                gtCheckAssign.setReformDutyPerson(jb1.getString("reformDutyPerson"));
                gtCheckAssign.setReformDeadline(jb1.getString("reformDeadline"));
                gtCheckAssign.setReformClasses(jb1.getString("reformClasses"));
                gtCheckAssign.setReformOpinion(jb1.getString("reformOpinion"));
                gtCheckAssign.setSuperviseFlg(jb1.getString("superviseFlg").equals("true") ? 1 : 0);
                gtCheckAssign.setSuperviseDept(jb1.getString("superviseDept"));
                gtCheckAssign.setSupervisePerson(jb1.getString("supervisePerson"));
                gtCheckAssign.setAcceptDept(jb1.getString("acceptDept"));
                gtCheckAssign.setAcceptDeptName(jb1.getString("acceptDeptName"));
                gtCheckAssign.setMoney(jb1.getString("money"));
                gtCheckAssign.setDelFlg(0);
                gtCheckAssign.setInsertUserId(jb1.getString("insertUserId"));
                gtCheckAssign.setInsertDatetime(jb1.getString("insertDatetime"));
                gtCheckAssign.setUpdateUserId(jb1.getString("updateUserId"));
                gtCheckAssign.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new GtCheckAssignDao(context).insertOrUpdate(gtCheckAssign);
                gtCheckAssigns.add(gtCheckAssign);
            }

            new SynDataSqlMgr(context).insertGtCheckAssign(gtCheckAssigns);

        }

        //督办表信息
        if (jb.getString("gtDangerSupervise") != null
                && !jb.getString("gtDangerSupervise").equals("")
                && !jb.getString("gtDangerSupervise").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtDangerSupervise");
            ArrayList<GtDangerSupervise> gtDangerSupervises = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                GtDangerSupervise gtDangerSupervise = new GtDangerSupervise();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gtDangerSupervise.setDangerSuperviseId(jb1.getString("dangerSuperviseId"));
                gtDangerSupervise.setCheckDangerId(jb1.getString("checkDangerId"));
                gtDangerSupervise.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gtDangerSupervise.setSuperviseDate(jb1.getString("superviseDate"));
                gtDangerSupervise.setSuperviseMeasure(jb1.getString("superviseMeasure"));
                gtDangerSupervise.setSupervisePerson(jb1.getString("supervisePerson"));
                gtDangerSupervise.setDelFlg(0);
                gtDangerSupervise.setInsertUserId(jb1.getString("insertUserId"));
                gtDangerSupervise.setInsertDatetime(jb1.getString("insertDatetime"));
                gtDangerSupervise.setUpdateUserId(jb1.getString("updateUserId"));
                gtDangerSupervise.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new GtDangerSuperviseDao(context).insertOrUpdate(gtDangerSupervise);
                gtDangerSupervises.add(gtDangerSupervise);
            }

            new SynDataSqlMgr(context).insertGtDangerSupervise(gtDangerSupervises);
        }

        //整改表信息
        if (jb.getString("gtDangerReform") != null
                && !jb.getString("gtDangerReform").equals("")
                && !jb.getString("gtDangerReform").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtDangerReform");

            ArrayList<GtDangerReform1> gtDangerReform1s = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                GtDangerReform1 gdr = new GtDangerReform1();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gdr.setDangerReformId(jb1.getString("dangerReformId"));
                gdr.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gdr.setCheckDangerId(jb1.getString("checkDangerId"));
                gdr.setReformPerson(jb1.getString("reformPerson"));
                gdr.setReformResult(jb1.getString("reformResult"));
                gdr.setReformDate(jb1.getString("reformDate"));
                gdr.setReformMeasure(jb1.getString("reformMeasure"));
                gdr.setReformClasses(jb1.getString("reformClasses"));
                gdr.setDelFlg(0);
                gdr.setInsertUserId(jb1.getString("insertUserId"));
                gdr.setInsertDatetime(jb1.getString("insertDatetime"));
                gdr.setUpdateUserId(jb1.getString("updateUserId"));
                gdr.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new GtDangerReformDao1(context).insertOrUpdate(gdr);
                gtDangerReform1s.add(gdr);
            }
            new SynDataSqlMgr(context).insertGtDangerReform1(gtDangerReform1s);
        }

        //验收表信息
        if (jb.getString("gtDangerAccept") != null
                && !jb.getString("gtDangerAccept").equals("")
                && !jb.getString("gtDangerAccept").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtDangerAccept");

            ArrayList<GtDangerAccept> gtDangerAccepts = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                GtDangerAccept gtDangerAccept = new GtDangerAccept();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gtDangerAccept.setDangerAcceptId(jb1.getString("dangerAcceptId"));
                gtDangerAccept.setCheckDangerId(jb1.getString("checkDangerId"));
                gtDangerAccept.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gtDangerAccept.setAcceptDept(jb1.getString("acceptDept"));
                gtDangerAccept.setAcceptResult(jb1.getString("acceptResult"));
                gtDangerAccept.setAcceptDate(jb1.getString("acceptDate"));
                gtDangerAccept.setAcceptOpinion(jb1.getString("acceptOpinion"));
                gtDangerAccept.setAcceptPerson(jb1.getString("acceptPerson"));
                gtDangerAccept.setAcceptType(jb1.getString("acceptType").equals("true") ? 1 : 0);
                gtDangerAccept.setDelFlg(0);
                gtDangerAccept.setInsertUserId(jb1.getString("insertUserId"));
                gtDangerAccept.setInsertDatetime(jb1.getString("insertDatetime"));
                gtDangerAccept.setUpdateUserId(jb1.getString("updateUserId"));
                gtDangerAccept.setUpdateDatetime(jb1.getString("updateDatetime"));
               // new GtDangerAcceptDao(context).insertOrUpdate(gtDangerAccept);
                gtDangerAccepts.add(gtDangerAccept);
            }
            new SynDataSqlMgr(context).insertGtDangerAccept(gtDangerAccepts);
        }

        //延期
        if (jb.getString("gtPostpone") != null
                && !jb.getString("gtPostpone").equals("")
                && !jb.getString("gtPostpone").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtPostpone");

            ArrayList<GtPostpone> gtPostpones = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                GtPostpone gtPostpone = new GtPostpone();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gtPostpone.setPostponeId(jb1.getString("postponeId"));
                gtPostpone.setHiddenId(jb1.getString("hiddenId"));
                gtPostpone.setPostponePersion(jb1.getString("postponePersion"));
                gtPostpone.setApplicationDate(jb1.getString("applicationDate"));
                gtPostpone.setPostponeDate(jb1.getString("postponeDate"));
                gtPostpone.setPostponeOpinion(jb1.getString("postponeOpinion"));
                gtPostpone.setAuditPersion(jb1.getString("auditPersion"));
                gtPostpone.setAuditDate(jb1.getString("auditDate"));
                gtPostpone.setAuditOpinion(jb1.getString("auditOpinion"));
                gtPostpone.setAuditStatus(jb1.getString("auditStatus"));
                gtPostpone.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gtPostpone.setDelFlg(0);
                gtPostpone.setInsertUserId(jb1.getString("insertUserId"));
                gtPostpone.setInsertDatetime(jb1.getString("insertDatetime"));
                gtPostpone.setUpdateUserId(jb1.getString("updateUserId"));
                gtPostpone.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new GtPostponeDao(context).insertOrUpdate(gtPostpone);
                gtPostpones.add(gtPostpone);
            }
            new SynDataSqlMgr(context).insertGtPostpone(gtPostpones);
        }

        //提级
        if (jb.getString("gtExalt") != null
                && !jb.getString("gtExalt").equals("")
                && !jb.getString("gtExalt").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtExalt");

            ArrayList<GtExalt> gtExalts = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                GtExalt gtExalt = new GtExalt();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gtExalt.setGtExaltId(jb1.getString("gtExaltId"));
                gtExalt.setCheckHiddenId(jb1.getString("checkHiddenId"));
                gtExalt.setExaltReason(jb1.getString("exaltReason"));
                gtExalt.setExaltOpinion(jb1.getString("exaltOpinion"));
                gtExalt.setExaltDangerClasses(jb1.getString("exaltDangerClasses"));
                gtExalt.setExaltPerson(jb1.getString("exaltPerson"));
                gtExalt.setExaltTime(jb1.getString("exaltTime"));
                gtExalt.setLastExaltDangerClasses(jb1.getString("lastExaltDangerClasses"));
                gtExalt.setExaltTimeOver(jb1.getString("exaltTimeOver"));
                gtExalt.setExaltPersonOver(jb1.getString("exaltPersonOver"));
                gtExalt.setStatus(jb1.getString("status"));
                gtExalt.setAssignHiddenId(jb1.getString("assignHiddenId"));
                gtExalt.setDelFlg(0);
                gtExalt.setInsertUserId(jb1.getString("insertUserId"));
                gtExalt.setInsertDatetime(jb1.getString("insertDatetime"));
                gtExalt.setUpdateUserId(jb1.getString("updateUserId"));
                gtExalt.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new GtExaltDao(context).insertOrUpdate(gtExalt);
                gtExalts.add(gtExalt);
            }
            new SynDataSqlMgr(context).insertGtExalt(gtExalts);
        }
    }

    /**
     * 同步风险相关数据
     *
     * @param jb
     */
    public void synFjkgData(JSONObject jb, Context context) throws JSONException {
        //单个计划
        if (jb.getString("sceneCheck") != null
                && !jb.getString("sceneCheck").equals("")
                && !jb.getString("sceneCheck").equals("null")) {
            FjgkSceneCheck fsc = new FjgkSceneCheck();
            JSONObject jb1 = jb.getJSONObject("sceneCheck");
            fsc.setSceneCheckId(jb1.getString("sceneCheckId"));
            fsc.setCollieryId(jb1.getString("collieryId"));
            fsc.setCheckName(jb1.getString("checkName"));
            fsc.setCheckDate(jb1.getString("checkDate"));
            fsc.setCheckPath(jb1.getString("checkPath"));
            fsc.setCheckContent(jb1.getString("checkContent"));
            fsc.setDutyDept(jb1.getString("dutyDept"));
            fsc.setDutyPerson(jb1.getString("dutyPerson"));
            fsc.setCheckStatus(jb1.getString("checkStatus"));
            fsc.setAuditStatus(jb1.getString("auditStatus"));
            fsc.setAuditOpinion(jb1.getString("auditOpinion"));
            fsc.setAuditDate(jb1.getString("auditDate"));
            fsc.setAuditPerson(jb1.getString("auditPerson"));
            fsc.setDetyMember(jb1.getString("detyMember"));
            fsc.setRemark(jb1.getString("remark").equals("") ? "" : jb1.getString("remark"));
            fsc.setCheckClasses(jb1.getString("checkClasses"));
            fsc.setDelFlg(0);
            fsc.setInsertUserId(jb1.getString("insertUserId"));
            fsc.setInsertDatetime(jb1.getString("insertDatetime"));
            fsc.setUpdateUserId(jb1.getString("updateUserId"));
            fsc.setUpdateDatetime(jb1.getString("updateDatetime"));
            new FjgkSceneCheckDao(context).insertOrUpdate(fsc);
        }

        //风险管控计划
        if (jb.getString("fjgkSceneCheck") != null
                && !jb.getString("fjgkSceneCheck").equals("")
                && !jb.getString("fjgkSceneCheck").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("fjgkSceneCheck");

            ArrayList<FjgkSceneCheck> fjgkSceneChecks = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                FjgkSceneCheck fsc = new FjgkSceneCheck();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                fsc.setSceneCheckId(jb1.getString("sceneCheckId"));
                fsc.setCollieryId(jb1.getString("collieryId"));
                fsc.setCheckName(jb1.getString("checkName"));
                fsc.setCheckDate(jb1.getString("checkDate"));
                fsc.setCheckPath(jb1.getString("checkPath"));
                fsc.setCheckContent(jb1.getString("checkContent"));
                fsc.setDutyDept(jb1.getString("dutyDept"));
                fsc.setDutyPerson(jb1.getString("dutyPerson"));
                fsc.setCheckStatus(jb1.getString("checkStatus"));
                fsc.setAuditStatus(jb1.getString("auditStatus"));
                fsc.setAuditOpinion(jb1.getString("auditOpinion"));
                fsc.setAuditDate(jb1.getString("auditDate"));
                fsc.setAuditPerson(jb1.getString("auditPerson"));
                fsc.setDetyMember(jb1.getString("detyMember"));
                fsc.setRemark(jb1.getString("remark").equals("") ? "" : jb1.getString("remark"));
                fsc.setCheckClasses(jb1.getString("checkClasses"));
                fsc.setDelFlg(0);
                fsc.setInsertUserId(jb1.getString("insertUserId"));
                fsc.setInsertDatetime(jb1.getString("insertDatetime"));
                fsc.setUpdateUserId(jb1.getString("updateUserId"));
                fsc.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new FjgkSceneCheckDao(context).insertOrUpdate(fsc);
                fjgkSceneChecks.add(fsc);
            }

            new SynDataSqlMgr(context).insertFjgkSceneCheck(fjgkSceneChecks);
        }

        //风险管控计划检查项
        if (jb.getString("fjgkSceneCheckDanger") != null
                && !jb.getString("fjgkSceneCheckDanger").equals("")
                && !jb.getString("fjgkSceneCheckDanger").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("fjgkSceneCheckDanger");

            ArrayList<FjgkSceneCheckDanger> fjgkSceneCheckDangers = new ArrayList<>();

            for (int j = 0; j < jsonArray1.length(); j++) {
                FjgkSceneCheckDanger fscd = new FjgkSceneCheckDanger();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                fscd.setCheckDangerId(jb1.getString("checkDangerId"));
                fscd.setSceneCheckId(jb1.getString("sceneCheckId"));
                fscd.setDangerListId(jb1.getString("dangerListId"));
                fscd.setDangerMajor(jb1.getString("dangerMajor"));
                fscd.setDangerRank(jb1.getString("dangerRank"));
                fscd.setSystemClassify(jb1.getString("systemClassify"));
                fscd.setDangerClasses1(jb1.getString("dangerClasses1"));
                fscd.setDangerClasses2(jb1.getString("dangerClasses2"));
                fscd.setDangerContent(jb1.getString("dangerContent"));
                fscd.setCheckResult(jb1.getString("checkResult"));
                fscd.setDangerAddressId(jb1.getString("dangerAddressId"));
                fscd.setDangerAddressIdName(jb1.getString("dangerAddressIdName"));
                fscd.setDangerAddressName(jb1.getString("dangerAddressName"));
                fscd.setDangerAreaName(jb1.getString("dangerAreaName"));
                fscd.setDangerArea(jb1.getString("dangerArea"));
                fscd.setDangerFormName(jb1.getString("dangerFormName"));
                fscd.setControlMeasure(jb1.getString("controlMeasure"));
                fscd.setDeptName(jb1.getString("deptName"));
                fscd.setUserName(jb1.getString("userName"));
                fscd.setIdentifyResult(jb1.getString("identifyResult"));
                fscd.setRemark(jb1.getString("remark"));
                fscd.setRiskClassesName(jb1.getString("riskClassesName"));
                fscd.setDelFlg(0);
                fscd.setInsertUserId(jb1.getString("insertUserId"));
                fscd.setInsertDatetime(jb1.getString("insertDatetime"));
                fscd.setUpdateUserId(jb1.getString("updateUserId"));
                fscd.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new FjgkSceneCheckDangerDao(context).insertOrUpdate(fscd);
                fjgkSceneCheckDangers.add(fscd);
            }

            new SynDataSqlMgr(context).insertFjgkSceneCheckDanger(fjgkSceneCheckDangers);
        }
    }


    /**
     * 同步基础表数据
     *
     * @param jb
     */
    public void synBasicData(JSONObject jb, Context context) throws JSONException {
        if (jb.getString("companyCollier") != null
                && !jb.getString("companyCollier").equals("")
                && !jb.getString("companyCollier").equals("null")) {
            CompanyColliery com = new CompanyColliery();
            JSONObject jb1 = jb.getJSONObject("companyCollier");
            com.setCompanyCollieryId(jb1.getString("companyCollieryId"));
            com.setName(jb1.getString("name"));
            com.setCollieryType(jb1.getString("collieryType"));
            com.setApprovalToleranceRange(jb1.getString("approvalToleranceRange"));
            com.setSlightPressure(jb1.getString("slightPressure"));
            new CompanyCollieryDao(context).insert(com);
        }
        if (jb.getString("collieryClasses") != null
                && !jb.getString("collieryClasses").equals("")
                && !jb.getString("collieryClasses").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("collieryClasses");
            for (int j = 0; j < jsonArray1.length(); j++) {
                CollieryClasses cc = new CollieryClasses();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                cc = com.alibaba.fastjson.JSONObject.parseObject(jb1.toString(),
                        CollieryClasses.class);
                new CollieryClassesDao(context).insert(cc);
            }
        }
        if (jb.getString("systemCodeMastersList") != null
                && !jb.getString("systemCodeMastersList").equals("")
                && !jb.getString("systemCodeMastersList").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("systemCodeMastersList");
            ArrayList<SystemCodeMaster> systemCodeMasters = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                SystemCodeMaster sc = new SystemCodeMaster();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                sc = com.alibaba.fastjson.JSONObject.parseObject(jb1.toString(),
                        SystemCodeMaster.class);
                //new SystemCodeMasterDao(context).insert(sc);
                systemCodeMasters.add(sc);
            }
            new SynDataSqlMgr(context).insertSystemCodeMaster(systemCodeMasters);
        }
        if (jb.getString("deptInfosList") != null
                && !jb.getString("deptInfosList").equals("")
                && !jb.getString("deptInfosList").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("deptInfosList");
            ArrayList<CompanycollierDepartmentManagement> companycollierDepartmentManagements = new ArrayList<>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                CompanycollierDepartmentManagement dept = new CompanycollierDepartmentManagement();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                dept.setCompanyCollieryId(jb1.getString("companyCollieryId"));
                dept.setDepartmentManagementId(jb1.getString("departmentManagementId"));
                dept.setDepartmentName(jb1.getString("departmentName"));
                dept.setLeader(jb1.getString("leader"));
                dept.setPhone(jb1.getString("phone"));
                dept.setTelephonenumber(jb1.getString("telephonenumber"));
                dept.setRemark(jb1.getString("remark"));
                dept.setDelFlg(0);
                dept.setInsertUserId(jb1.getString("insertUserId"));
                dept.setInsertDatetime(jb1.getString("insertDatetime"));
                dept.setUpdateUserId(jb1.getString("updateUserId"));
                dept.setUpdateDatetime(jb1.getString("updateDatetime"));
                companycollierDepartmentManagements.add(dept);
               // new CompanycollierDeptDao(context).insert(dept);
            }

            new SynDataSqlMgr(context).insertCompanycollierDepartmentManagement(companycollierDepartmentManagements);
        }


        if (jb.getString("userInfoList") != null
                && !jb.getString("userInfoList").equals("")
                && !jb.getString("userInfoList").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("userInfoList");

            ArrayList<CompanyCollierUser> companyCollierUsers = new ArrayList<CompanyCollierUser>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                CompanyCollierUser user = new CompanyCollierUser();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                user.setUserId(jb1.getString("userId"));
                user.setSubjection(jb1.getString("subjection"));
                user.setLoginName(jb1.getString("loginName"));
                user.setLoginPassword(jb1.getString("loginPassword"));
                user.setHpicUrl(jb1.getString("hpicUrl"));
                user.setUserName(jb1.getString("userName"));
                user.setUserIdcard(jb1.getString("userIdcard"));
                user.setUserEmall(jb1.getString("userEmall"));
                user.setOfficePhone(jb1.getString("officePhone"));
                user.setMobilePhone(jb1.getString("mobilePhone"));
                user.setStatus(jb1.getString("status").equals("true") ? 1 : 0);
                user.setRemark(jb1.getString("remark"));
                user.setDeparymentId(jb1.getString("deparymentId"));
                user.setPostPoneAuthority(jb1.getString("postPoneAuthority"));
                user.setExaltAuthority(jb1.getString("exaltAuthority"));
                user.setAssignAuthority(jb1.getString("assignAuthority"));
                user.setCancelAuthority(jb1.getString("cancelAuthority"));
                user.setReformAuthority(jb1.getString("reformAuthority"));
                user.setSuperviseAuthority(jb1.getString("superviseAuthority"));
                user.setAcceptAuthority(jb1.getString("acceptAuthority"));
                user.setDelFlg(0);
                user.setInsertUserId(jb1.getString("insertUserId"));
                user.setInsertDatetime(jb1.getString("insertDatetime"));
                user.setUpdateUserId(jb1.getString("updateUserId"));
                user.setUpdateDatetime(jb1.getString("updateDatetime"));
                //new CompanyCollierUserDao(context).insert(user);
                companyCollierUsers.add(user);
            }

            new SynDataSqlMgr(context).insertCompanyCollierUser(companyCollierUsers);
        }

        if (jb.getString("gtAddress") != null
                && !jb.getString("gtAddress").equals("")
                && !jb.getString("gtAddress").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("gtAddress");
            for (int j = 0; j < jsonArray1.length(); j++) {
                GtAddress gtAddress = new GtAddress();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                gtAddress.setAddressId(jb1.getString("addressId"));
                gtAddress.setCollieryId(jb1.getString("collieryId"));
                gtAddress.setAddressName(jb1.getString("addressName"));
                gtAddress.setAddressDes(jb1.getString("addressDes"));
                gtAddress.setAddressStatus(jb1.getString("addressStatus"));
                gtAddress.setLoseReason(jb1.getString("loseReason"));
                gtAddress.setLoseDate(jb1.getString("loseDate"));
                gtAddress.setAddressType(jb1.getString("addressType"));
                gtAddress.setArea(jb1.getString("area"));
                gtAddress.setDelFlg(0);
                gtAddress.setInsertUserId(jb1.getString("insertUserId"));
                gtAddress.setInsertDatetime(jb1.getString("insertDatetime"));
                gtAddress.setUpdateUserId(jb1.getString("updateUserId"));
                gtAddress.setUpdateDatetime(jb1.getString("updateDatetime"));
                new GtAddressDao(context).insert(gtAddress);
            }
        }

        //同步BZH专业检查内容基础表
        if (jb.getString("checkContent") != null
                && !jb.getString("checkContent").equals("")
                && !jb.getString("checkContent").equals("null")) {
            JSONArray jsonArray1 = jb.getJSONArray("checkContent");
            List<BzhMajorCheckContent> bzhContentList = new ArrayList<BzhMajorCheckContent>();
            for (int j = 0; j < jsonArray1.length(); j++) {
                BzhMajorCheckContent bzhContent = new BzhMajorCheckContent();
                JSONObject jb1 = jsonArray1.getJSONObject(j);
                bzhContent.setSelfCheckcontentsId(jb1.getString("selfCheckcontentsId"));
                bzhContent.setCollieryTypeId(jb1.getString("collieryTypeId"));
                bzhContent.setYixuanzeprojectsId(jb1.getString("yixuanzeprojectsId"));
                bzhContent.setSelfCheckProjectname(jb1.getString("selfCheckProjectname"));
                bzhContent.setSelfCheckProjectcontents(jb1.getString("selfCheckProjectcontents"));
                bzhContent.setSelfCheckProjectcontents2(jb1.getString("selfCheckProjectcontents2"));
                bzhContent.setSelfCheckBasicrequirements(jb1.getString("selfCheckBasicrequirements"));
                bzhContent.setSelfCheckStandartscore(jb1.getString("selfCheckStandartscore"));
                bzhContent.setSelfCheckMethodofcomment(jb1.getString("selfCheckMethodofcomment"));
                bzhContent.setSelfCheckSort(jb1.getString("selfCheckSort"));
                bzhContent.setSelfCheckScoreGroup(jb1.getString("selfCheckScoreGroup"));
                bzhContent.setSelfCheckMethodGroup(jb1.getString("selfCheckMethodGroup"));
                bzhContent.setSelfContentSort(jb1.getString("selfContentSort"));
                bzhContent.setDelFlg(0);
                bzhContent.setInsertUserId(jb1.getString("insertUserId"));
                bzhContent.setInsertDatetime(jb1.getString("insertDatetime"));
                bzhContent.setUpdateUserId(jb1.getString("updateUserId"));
                bzhContent.setUpdateDatetime(jb1.getString("updateDatetime"));
                bzhContentList.add(bzhContent);
//                new BzhCheckMajorDetailDao(context).insertOrUpdate(bzhCheckMajorDetail);
            }
            new SynDataSqlMgr(context).insertBzhMajorCheckContent(bzhContentList);
        }

    }


}
