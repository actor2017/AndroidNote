package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhPlan;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 标准化计划表的DAO类
 */
public class BzhPlanDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<BzhPlan, Integer> dao;

    public BzhPlanDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(BzhPlan.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(BzhPlan data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(BzhPlan data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<BzhPlan> selectUploadData(){
        List<BzhPlan> fList = new ArrayList<BzhPlan>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 修改标准化检查状态
    public void updatePlanStatus(BzhPlan data) {
        String updateId = SharedPreferencesUtil.getStringValue(context, "userId");
        try {
            StringBuffer sb = new StringBuffer("update BZH_PLAN set IS_EXECUTE = '"+data.getIsExecute()+"'");
            sb.append(" ,update_datatime = datetime('now')");
            sb.append(" ,IS_UPLOAD = "+data.getIsUpload()+"");
            sb.append(" ,update_user_id = '"+updateId+"'");
            sb.append(" where QUALITYPLAN_ID = '"+data.getQualityplanId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<BzhPlan> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                BzhPlan bzhPlan = list.get(i);
                StringBuffer sb = new StringBuffer(" update BZH_PLAN set IS_UPLOAD = 0 ");
                sb.append(" where QUALITYPLAN_ID = '"+bzhPlan.getQualityplanId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(BzhPlan data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<BzhPlan> queryById(BzhPlan data) {
        List<BzhPlan> bzhPlans = new ArrayList<BzhPlan>();
        try {
            bzhPlans = dao.queryBuilder().where().eq("QUALITYPLAN_ID",data.getQualityplanId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bzhPlans;
    }

    //同步数据
    public void insertOrUpdate(BzhPlan data){
        //查看当前数据数据存在
        List<BzhPlan> bzhPlans = queryById(data);
        if(bzhPlans.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(BzhPlan data){
        try {
            StringBuffer sb = new StringBuffer("update BZH_PLAN set ");
            sb.append(" CHECK_APPROVER = '"+data.getCheckApprover()+"' ");
            sb.append(" ,CHECK_APPROVER_ADVICE = '"+data.getCheckApproverAdvice()+"'");
            sb.append(" ,CHECK_APPROVER_DATA = '"+data.getCheckApproverData()+"'");
            sb.append(" ,CHECK_AUDIT = '"+data.getCheckAudit()+"'");
            sb.append(" ,CHECK_AUDIT_ADVICE = '"+data.getCheckAuditAdvice()+"'");
            sb.append(" ,CHECK_AUDIT_DATE = '"+data.getCheckAuditDate()+"'");
            sb.append(" ,CHECK_AUDIT_STATUS = '"+data.getCheckAuditStatus()+"'");
            sb.append(" ,CHECK_COMMENTS = '"+data.getCheckComments()+"'");
            sb.append(" ,CHECK_MONTH = '"+data.getCheckMonth()+"'");
            sb.append(" ,CHECK_TYPE = '"+data.getCheckType()+"'");
            sb.append(" ,CHECK_YEAR = '"+data.getCheckYear()+"'");
            sb.append(" ,CHECKED_SECTION = '"+data.getCheckedSection()+"'");
            sb.append(" ,COLLIERY_TYPE = '"+data.getCollieryType()+"'");
            sb.append(" ,IS_EXECUTE = '"+data.getIsExecute()+"'");
            sb.append(" ,PLAN_NAME = '"+data.getPlanName()+"'");

            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where QUALITYPLAN_ID = '"+data.getQualityplanId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}