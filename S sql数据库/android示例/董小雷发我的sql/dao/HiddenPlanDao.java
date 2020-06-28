package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.HiddenPlan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作隐患计划的DAO类
 */
public class HiddenPlanDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<HiddenPlan, Integer> dao;

    public HiddenPlanDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(HiddenPlan.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(HiddenPlan data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //循环更新
    public void updateIsUpload(List<HiddenPlan> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                HiddenPlan hiddenPlan = list.get(i);
                StringBuffer sb = new StringBuffer(" update YHPC_HIDDEN_PLAN set IS_UPLOAD = 0 ");
                sb.append(" where HIDDEN_CHECK_PLANID = '"+hiddenPlan.getHiddenCheckPlanid()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(HiddenPlan data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<HiddenPlan> selectUploadData(){
        List<HiddenPlan> fList = new ArrayList<HiddenPlan>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //修改计划任务状态
    public void updateTaskStatus(HiddenPlan data) {
        try {
            StringBuffer sb = new StringBuffer("update YHPC_HIDDEN_PLAN set TASK_STATUS = '"+data.getTaskStatus()+"',");
            sb.append(" update_datatime = datetime('now')");
            sb.append(" ,IS_UPLOAD = "+data.getIsUpload()+"");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where HIDDEN_CHECK_PLANID = '"+data.getHiddenCheckPlanid()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<HiddenPlan> queryById(HiddenPlan data) {
        List<HiddenPlan> hiddenPlanList = new ArrayList<HiddenPlan>();
        try {
            hiddenPlanList = dao.queryBuilder().where().eq("HIDDEN_CHECK_PLANID",data.getHiddenCheckPlanid()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hiddenPlanList;
    }

    //同步数据
    public void insertOrUpdate(HiddenPlan data){
        //查看当前数据数据存在
        List<HiddenPlan> hiddenPlanList = queryById(data);
        if(hiddenPlanList.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(HiddenPlan data){
        try {
            StringBuffer sb = new StringBuffer("update YHPC_HIDDEN_PLAN set ");
            sb.append(" AUDIT_DATE = '"+data.getAuditDate()+"' ");
            sb.append(" ,AUDIT_PERSON = '"+data.getAuditPerson()+"'");
            sb.append(" ,CHECK_CONTENT = '"+data.getCheckContent()+"'");
            sb.append(" ,CHECK_RANGE = '"+data.getCheckRange()+"'");
            sb.append(" ,CHECK_ROUTE = '"+data.getCheckRoute()+"'");
            sb.append(" ,CHECK_TIME = '"+data.getCheckTime()+"'");
            sb.append(" ,CHECK_TYPE = '"+data.getCheckType()+"'");
            sb.append(" ,COMPANY_COLLIERY_ID = '"+data.getCompanyCollieryId()+"'");
            sb.append(" ,DEPARTMENT = '"+data.getDepartment()+"'");
            sb.append(" ,DEPARTMENT_MAN = '"+data.getDepartmentMan()+"'");
            sb.append(" ,DEPARTMENT_PERSONS = '"+data.getDepartmentPersons()+"'");
            sb.append(" ,EXAMINE_OPINION = '"+data.getExamineOpinion()+"'");
            sb.append(" ,EXAMINE_STATUS = '"+data.getExamineStatus()+"'");
            sb.append(" ,NOTE = '"+data.getNote()+"'");
            sb.append(" ,PLAN_NAME = '"+data.getPlanName()+"'");
            sb.append(" ,TASK_STATUS = '"+data.getTaskStatus()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where HIDDEN_CHECK_PLANID = '"+data.getHiddenCheckPlanid()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}