package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.FjgkSceneCheck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作风险现场管理表的DAO类
 */
public class FjgkSceneCheckDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<FjgkSceneCheck, Integer> dao;

    public FjgkSceneCheckDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(FjgkSceneCheck.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(FjgkSceneCheck data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<FjgkSceneCheck> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                FjgkSceneCheck fjgkSceneCheck = list.get(i);
                StringBuffer sb = new StringBuffer(" update FJGK_SCECE_CHECK set IS_UPLOAD = 0 ");
                sb.append(" where SCENE_CHECK_ID = '"+fjgkSceneCheck.getSceneCheckId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<FjgkSceneCheck> selectUploadData(){
        List<FjgkSceneCheck> fList = new ArrayList<FjgkSceneCheck>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 删除数据
    public void delete(FjgkSceneCheck data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改风险检查状态
    public void updateCheckStatus(FjgkSceneCheck data) {
        try {
            StringBuffer sb = new StringBuffer("update FJGK_SCECE_CHECK set CHECK_STATUS = '"+data.getCheckStatus()+"'");
            sb.append(" ,update_datatime = datetime('now')");
            sb.append(" ,IS_UPLOAD = "+data.getIsUpload()+"");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where SCENE_CHECK_ID = '"+data.getSceneCheckId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过ID查询一条数据
    public FjgkSceneCheck queryById(int id) {
    	FjgkSceneCheck fjgkSceneCheck = null;
        try {
        	fjgkSceneCheck = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fjgkSceneCheck;
    }
    
 // 查询全部集合
    public List<FjgkSceneCheck> queryAll() {
    	List<FjgkSceneCheck> fList = new ArrayList<FjgkSceneCheck>();
        try {
        	fList = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //同步判断该条数据是否存在本地库
    public List<FjgkSceneCheck> queryById(FjgkSceneCheck data) {
        List<FjgkSceneCheck> fjgkSceneChecks = new ArrayList<FjgkSceneCheck>();
        try {
            fjgkSceneChecks = dao.queryBuilder().where().eq("SCENE_CHECK_ID",data.getSceneCheckId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fjgkSceneChecks;
    }

    //同步数据
    public void insertOrUpdate(FjgkSceneCheck data){
        //查看当前数据数据存在
        List<FjgkSceneCheck> fjgkSceneChecks = queryById(data);
        if(fjgkSceneChecks.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(FjgkSceneCheck data){
        try {
            StringBuffer sb = new StringBuffer("update FJGK_SCECE_CHECK set ");
            sb.append(" AUDIT_DATE = '"+data.getAuditDate()+"' ");
            sb.append(" ,AUDIT_OPINION = '"+data.getAuditOpinion()+"'");
            sb.append(" ,AUDIT_PERSON = '"+data.getAuditPerson()+"'");
            sb.append(" ,AUDIT_STATUS = '"+data.getAuditStatus()+"'");
            sb.append(" ,CHECK_CONTENT = '"+data.getCheckContent()+"'");
            sb.append(" ,CHECK_DATE = '"+data.getCheckDate()+"'");
            sb.append(" ,CHECK_NAME = '"+data.getCheckName()+"'");
            sb.append(" ,CHECK_PATH = '"+data.getCheckPath()+"'");
            sb.append(" ,CHECK_STATUS = '"+data.getCheckStatus()+"'");
            sb.append(" ,COLLIERY_ID = '"+data.getCollieryId()+"'");
            sb.append(" ,DETY_MEMBER = '"+data.getDetyMember()+"'");
            sb.append(" ,DUTY_DEPT = '"+data.getDutyDept()+"'");
            sb.append(" ,DUTY_PERSON = '"+data.getDutyPerson()+"'");
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
            sb.append(" where SCENE_CHECK_ID = '"+data.getSceneCheckId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}