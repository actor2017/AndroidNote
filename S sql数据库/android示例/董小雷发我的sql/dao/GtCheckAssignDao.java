package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtCheckAssign;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作隐患下达表的DAO类
 */
public class GtCheckAssignDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtCheckAssign, Integer> dao;

    public GtCheckAssignDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtCheckAssign.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtCheckAssign data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtCheckAssign data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<GtCheckAssign> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                GtCheckAssign gtCheckAssign = list.get(i);
                StringBuffer sb = new StringBuffer(" update GT_CHECK_ASSIGN set IS_UPLOAD = 0 ");
                sb.append(" where ASSIGN_HIDDEN_ID = '"+gtCheckAssign.getAssignHiddenId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<GtCheckAssign> selectUploadData(){
        List<GtCheckAssign> fList = new ArrayList<GtCheckAssign>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //本地修改整改表数据
    public void LocalInsertOrUpdate(GtCheckAssign data){
        //查看当前数据数据存在
        List<GtCheckAssign> gtCheckAssigns = queryById(data);
        if(gtCheckAssigns.size()>0){
            update(data);
        }else{
            insert(data);
        }
    }

    // 修改数据
    public void update(GtCheckAssign data) {
        try {
            StringBuffer sb = new StringBuffer("UPDATE GT_CHECK_ASSIGN set update_datatime = datetime('now') ");
            if(data.getAssignHiddenId()!=null){
                sb.append(" ,ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"' ");
            }
            if(data.getCheckHiddenId()!=null){
                sb.append(" ,CHECK_HIDDEN_ID = '"+data.getCheckHiddenId()+"'");
            }
            if(data.getDealWithFlg()!=0){
                sb.append(" , DEAL_WITH_FLG = '"+data.getDealWithFlg()+"'");
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

            if(data.getReformClasses()!=null){
                sb.append(" , REFORM_CLASSES = '"+data.getReformClasses()+"'");
            }
            if(data.getReformOpinion()!=null){
                sb.append(" , REFORM_OPINION = '"+data.getReformOpinion()+"'");
            }
            if (data.getSuperviseFlg() != 0) {
                sb.append(" , SUPERVISE_FLG = 1 ");
            }else{
                sb.append(" , SUPERVISE_FLG = 0 ");
            }
            if (data.getSuperviseDept() != null) {
                sb.append(" , SUPERVISE_DEPT = '"+data.getSuperviseDept()+"'");
            }
            if (data.getSupervisePerson() != null) {
                sb.append(" , SUPERVISE_PERSON = '"+data.getSupervisePerson()+"'");
            }
            if (data.getAcceptDept() != null) {
                sb.append(" , ACCEPT_DEPT = '"+data.getAcceptDept()+"'");
            }
            if (data.getAcceptDeptName() != null) {
                sb.append(" , ACCEPT_DEPT_NAME = '"+data.getAcceptDeptName()+"'");
            }
            if (data.getMoney() != null) {
                sb.append(" , MONEY = '"+data.getMoney()+"'");
            }
            if(data.getIsUpload()!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" where ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //同步判断该条数据是否存在本地库
    public List<GtCheckAssign> queryById(GtCheckAssign data) {
        List<GtCheckAssign> gtDangerAssign = new ArrayList<GtCheckAssign>();
        try {
            gtDangerAssign = dao.queryBuilder().where().eq("ASSIGN_HIDDEN_ID",data.getAssignHiddenId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtDangerAssign;
    }

    //同步数据
    public void insertOrUpdate(GtCheckAssign data){
        //查看当前数据数据存在
        List<GtCheckAssign> gtCheckAssign = queryById(data);
        if(gtCheckAssign.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtCheckAssign data){
        try {
            StringBuffer sb = new StringBuffer("update GT_CHECK_ASSIGN set ");
            sb.append(" CHECK_HIDDEN_ID = '"+data.getCheckHiddenId()+"'");
            sb.append(" ,DEAL_WITH_FLG = '"+data.getDealWithFlg()+"'");
            sb.append(" ,REFORM_DEPT = '"+data.getReformDept()+"'");
            sb.append(" ,REFORM_DUTY_PERSON = '"+data.getReformDutyPerson()+"'");
            sb.append(" ,REFORM_DEADLINE = '"+data.getReformDeadline()+"'");
            sb.append(" ,REFORM_CLASSES = '"+data.getReformClasses()+"'");
            sb.append(" ,REFORM_OPINION = '"+data.getReformOpinion()+"'");
            sb.append(" ,SUPERVISE_FLG = '"+data.getSuperviseFlg()+"'");
            sb.append(" ,SUPERVISE_DEPT = '"+data.getSuperviseDept()+"'");
            sb.append(" ,SUPERVISE_PERSON = '"+data.getSupervisePerson()+"'");
            sb.append(" ,ACCEPT_DEPT = '"+data.getAcceptDept()+"'");
            sb.append(" ,ACCEPT_DEPT_NAME = '"+data.getAcceptDeptName()+"'");
            sb.append(" ,MONEY = '"+data.getMoney()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}