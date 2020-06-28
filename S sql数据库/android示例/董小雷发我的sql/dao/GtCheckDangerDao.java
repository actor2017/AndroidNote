package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtCheckDanger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作检查隐患表的DAO类
 */
public class GtCheckDangerDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtCheckDanger, Integer> dao;


    public GtCheckDangerDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtCheckDanger.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 逻辑删除隐患
    public void deleteGtCheckDanger(GtCheckDanger data) {
        try {
            StringBuffer sb = new StringBuffer("update GT_CHECK_DANGER set del_flg = 1");
            if(data.getIsUpload()!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" where CHECK_HIDDEN_ID = '"+data.getCheckHiddenId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<GtCheckDanger> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                GtCheckDanger gtCheckDanger = list.get(i);
                StringBuffer sb = new StringBuffer(" update GT_CHECK_DANGER set IS_UPLOAD = 0 ");
                sb.append(" where CHECK_HIDDEN_ID = '"+gtCheckDanger.getCheckHiddenId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtCheckDanger data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //隐患添加查数据,用于MyReceiver中推送过来的数据不会重复插入.zhangwenping
    public List<GtCheckDanger> findById(String checkHiddenId) {
        List<GtCheckDanger>  gcd = new ArrayList<GtCheckDanger>();
        try {
            gcd = dao.queryBuilder().where().eq("CHECK_HIDDEN_ID",checkHiddenId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gcd;
    }


    // 删除数据
    public void delete(GtCheckDanger data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //本地修改隐患表数据
    public void LocalInsertOrUpdate(GtCheckDanger data){
        //查看当前数据数据存在
        List<GtCheckDanger> gtCheckDangers = queryById(data);
        if(gtCheckDangers.size()>0){
            update(data);
        }else{
            insert(data);
        }
    }

    //更新隐患表状态
    public void updateDangerStatus(String dangerId,String status){
        try {
            StringBuffer sb = new StringBuffer("update GT_CHECK_DANGER set GT_CHECK_DANGER.DANGER_STATUS = '"+status+"' where GT_CHECK_DANGER.CHECK_HIDDEN_ID = '"+dangerId+"' ");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改隐患表数据
    public void update(GtCheckDanger data) {
        try {
            StringBuffer sb = new StringBuffer("UPDATE GT_CHECK_DANGER set update_datatime = datetime('now') ");
            if(data.getRelevanceId()!=null){
                sb.append(" ,RELEVANCE_ID = '"+data.getRelevanceId()+"' ");
            }
            if(data.getPlanId()!=null){
                sb.append(" ,PLAN_ID = '"+data.getPlanId()+"'");
            }
            if(data.getHiddenDangerMajor()!=null){
                sb.append(" , HIDDEN_DANGER_MAJOR = '"+data.getHiddenDangerMajor()+"'");
            }
            if(data.getHiddenDangerClasses()!=null){
                sb.append(" , HIDDEN_DANGER_CLASSES = '"+data.getHiddenDangerClasses()+"'");
            }
            if(data.getCheckDate()!=null){
                sb.append(" , CHECK_DATE = '"+data.getCheckDate()+"'");
            }
            if(data.getCheckClasses()!=null){
                sb.append(" , CHECK_CLASSES = '"+data.getCheckClasses()+"'");
            }
            if(data.getDangerClasses1()!=null){
                sb.append(" , DANGER_CLASSES1 = '"+data.getDangerClasses1()+"'");
            }
            if(data.getDangerClasses2()!=null){
                sb.append(" , DANGER_CLASSES2 = '"+data.getDangerClasses2()+"'");
            }
            if(data.getSystemClasses()!=null){
                sb.append(" , SYSTEM_CLASSES = '"+data.getSystemClasses()+"'");
            }
            if(data.getCheckSite()!=null){
                sb.append(" , CHECK_SITE = '"+data.getCheckSite()+"'");
            }
            if(data.getDangerDescription()!=null){
                sb.append(" , DANGER_DESCRIPTION = '"+data.getDangerDescription()+"'");
            }
            if(data.getCompanyCollieryId()!=null){
                sb.append(" , COMPANY_COLLIERY_ID = '"+data.getCompanyCollieryId()+"'");
            }
            if(data.getDangerStatus()!=null){
                sb.append(" , DANGER_STATUS = '"+data.getDangerStatus()+"'");
            }
            if(data.getDealWithFlg()!=0){
                sb.append(" , DEAL_WITH_FLG = '"+data.getDealWithFlg()+"' ");
            }
            if(data.getReformDept()!=null){
                sb.append(" , REFORM_DEPT = '"+data.getReformDept()+"'");
            }
            if(data.getReformDutyPerson()!=null){
                sb.append(" , REFORM_DUTY_PERSON = '"+data.getReformDutyPerson()+"'");
            }
            if(data.getReformDeadline()!=null){
                sb.append(" , REFORM_DEADLINE = '"+data.getReformDeadline()+"'");
            }
            if(data.getReformOpinion()!=null){
                sb.append(" , REFORM_OPINION = '"+data.getReformOpinion()+"'");
            }
            if(data.getReformClasses()!=null){
                sb.append(" , REFORM_CLASSES = '"+data.getReformClasses()+"'");
            }
            if(data.getSuperviseFlg()!=0){
                sb.append(" , SUPERVISE_FLG = 1 ");
            }else{
                sb.append(" , SUPERVISE_FLG = 0 ");
            }
            if(data.getSuperviseDept()!=null){
                sb.append(" , SUPERVISE_DEPT = '"+data.getSuperviseDept()+"'");
            }
            if(data.getSupervisePerson()!=null){
                sb.append(" , SUPERVISE_PERSON = '"+data.getSupervisePerson()+"'");
            }
            if(data.getAcceptDept()!=null){
                sb.append(" , ACCEPT_DEPT = '"+data.getAcceptDept()+"'");
            }
            if(data.getAssignHiddenId()!=null){
                sb.append(" , ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            }
            if(data.getDangerFrom()!=null){
                sb.append(" , DANGER_FROM = '"+data.getDangerFrom()+"'");
            }
            if(data.getDangerFrom2()!=null){
                sb.append(" , DANGER_FROM2 = '"+data.getDangerFrom2()+"'");
            }
            if(data.getCheckUsers()!=null){
                sb.append(" , checkUsers = '"+data.getCheckUsers()+"'");
            }
            if(data.getCheckUserName()!=null){
                sb.append(" , checkUserName = '"+data.getCheckUserName()+"'");
            }
            if(data.getDangerFrom2()!=null){
                sb.append(" , DANGER_FROM2 = '"+data.getDangerFrom2()+"'");
            }
            if (data.getMoney() != null) {
                sb.append(" , MONEY = '"+data.getMoney()+"'");
            }
            if (data.getExaltSts() != null) {
                sb.append(" , EXALT_STS = '"+data.getExaltSts()+"'");
            }
            if (data.getPsotponeStatus() != null) {
                sb.append(" , POSTPONE_STATUS = '"+data.getPsotponeStatus()+"'");
            }
            if (data.getDangerArea() != null) {
                sb.append(" , DANGER_AREA = '"+data.getDangerArea()+"'");
            }
            if (data.getDangerAddressId() != null) {
                sb.append(" , DANGER_ADDRESS_ID = '"+data.getDangerAddressId()+"'");
            }
            if (data.getSituation() != null) {
                sb.append(" , SITUATION = '"+data.getSituation()+"'");
            }
            if (data.getCancelOption() != null) {
                sb.append(" , CANCEL_OPINION = '"+data.getCancelOption()+"'");
            }
            if (data.getCancelPreson() != null) {
                sb.append(" , cancelPreson = '"+data.getCancelPreson()+"'");
            }
            if (data.getCancelDate() != null) {
                sb.append(" , cancelDate = '"+data.getCancelDate()+"'");
            }
            if (data.getAcceptDeptName() != null) {
                sb.append(" , ACCEPT_DEPT_NAME = '"+data.getAcceptDeptName()+"'");
            }
            if (data.getRemark() != null) {
                sb.append(" , REMARK = '"+data.getRemark()+"'");
            }
            if(data.getIsUpload()!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" where CHECK_HIDDEN_ID = '"+data.getCheckHiddenId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<GtCheckDanger> selectUploadData(){
        List<GtCheckDanger> fList = new ArrayList<GtCheckDanger>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //同步判断该条数据是否存在本地库
    public List<GtCheckDanger> queryById(GtCheckDanger data) {
        List<GtCheckDanger> gtCheckDangers = new ArrayList<GtCheckDanger>();
        try {
            gtCheckDangers = dao.queryBuilder().where().eq("CHECK_HIDDEN_ID",data.getCheckHiddenId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtCheckDangers;
    }

    //同步网络端数据
    public void insertOrUpdate(GtCheckDanger data){
        //查看当前数据数据存在
        List<GtCheckDanger> gtCheckDangers = queryById(data);
        if(gtCheckDangers.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步网络端数据根据uuid更新数据
    public void updateById(GtCheckDanger data){
        try {
            StringBuffer sb = new StringBuffer("update GT_CHECK_DANGER set ");
            sb.append(" CHECK_CLASSES = '"+data.getCheckClasses()+"'");
            sb.append(" ,CHECK_DATE = '"+data.getCheckDate()+"'");
            sb.append(" ,CHECK_SITE = '"+data.getCheckSite()+"'");
            sb.append(" ,checkUserName = '"+data.getCheckUserName()+"'");
            sb.append(" ,checkUsers = '"+data.getCheckUsers()+"'");
            sb.append(" ,COMPANY_COLLIERY_ID = '"+data.getCompanyCollieryId()+"'");
            sb.append(" ,DANGER_CLASSES1 = '"+data.getDangerClasses1()+"'");
            sb.append(" ,DANGER_CLASSES2 = '"+data.getDangerClasses2()+"'");
            sb.append(" ,DANGER_DESCRIPTION = '"+data.getDangerDescription()+"'");
            sb.append(" ,DANGER_FROM = '"+data.getDangerFrom()+"'");
            sb.append(" ,DANGER_FROM2 = '"+data.getDangerFrom2()+"'");
            sb.append(" ,DANGER_STATUS = '"+data.getDangerStatus()+"'");
            sb.append(" ,DEAL_WITH_FLG = '"+data.getDealWithFlg()+"'");
            sb.append(" ,HIDDEN_DANGER_CLASSES = '"+data.getHiddenDangerClasses()+"'");
            sb.append(" ,HIDDEN_DANGER_MAJOR = '"+data.getHiddenDangerMajor()+"'");
            sb.append(" ,PLAN_ID = '"+data.getPlanId()+"'");
            sb.append(" ,REFORM_CLASSES = '"+data.getReformClasses()+"'");
            sb.append(" ,REFORM_DEADLINE = '"+data.getReformDeadline()+"'");
            sb.append(" ,REFORM_DEPT = '"+data.getReformDept()+"'");
            sb.append(" ,REFORM_DUTY_PERSON = '"+data.getReformDutyPerson()+"'");
            sb.append(" ,REFORM_OPINION = '"+data.getReformOpinion()+"'");
            sb.append(" ,RELEVANCE_ID = '"+data.getRelevanceId()+"'");
            sb.append(" ,SUPERVISE_DEPT = '"+data.getSuperviseDept()+"'");
            sb.append(" ,SUPERVISE_FLG = '"+data.getSuperviseFlg()+"'");
            sb.append(" ,SUPERVISE_PERSON = '"+data.getSupervisePerson()+"'");
            sb.append(" ,SYSTEM_CLASSES = '"+data.getSystemClasses()+"'");
            sb.append(" ,MONEY = '"+data.getMoney()+"'");
            sb.append(" ,EXALT_STS = '"+data.getExaltSts()+"'");
            sb.append(" ,POSTPONE_STATUS = '"+data.getPsotponeStatus()+"'");
            sb.append(" ,DANGER_AREA = '"+data.getDangerArea()+"'");
            sb.append(" ,ACCEPT_DEPT = '"+data.getAcceptDept()+"'");
            sb.append(" ,ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            sb.append(" ,SITUATION = '"+data.getSituation()+"'");
            sb.append(" ,CANCEL_OPINION = '"+data.getCancelOption()+"'");
            sb.append(" ,CANCEL_PERSON = '"+data.getCancelPreson()+"'");
            sb.append(" ,CANCEL_DATE = '"+data.getCancelDate()+"'");
            sb.append(" , DANGER_ADDRESS_ID = '"+data.getDangerAddressId()+"'");
            sb.append(" ,ACCEPT_DEPT_NAME = '"+data.getAcceptDeptName()+"'");
            sb.append(" ,REMARK = '"+data.getRemark()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where CHECK_HIDDEN_ID = '"+data.getCheckHiddenId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}