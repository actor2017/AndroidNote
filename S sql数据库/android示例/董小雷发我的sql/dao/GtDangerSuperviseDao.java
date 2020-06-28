package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtDangerSupervise;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作隐患督办表的DAO类
 */
public class GtDangerSuperviseDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtDangerSupervise, Integer> dao;

    public GtDangerSuperviseDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtDangerSupervise.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtDangerSupervise data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtDangerSupervise data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<GtDangerSupervise> selectUploadData(){
        List<GtDangerSupervise> fList = new ArrayList<GtDangerSupervise>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 修改数据
    public void update(GtDangerSupervise data) {
        try {
            StringBuffer sb = new StringBuffer("UPDATE GT_DANGER_SUPERVISE set update_datatime = datetime('now') ");
            if(data.getCheckDangerId()!=null){
                sb.append(" ,DANGER_SUPERVISE_ID = '"+data.getDangerSuperviseId()+"' ");
            }
            if(data.getCheckDangerId()!=null){
                sb.append(" ,CHECK_DANGER_ID = '"+data.getCheckDangerId()+"'");
            }
            if(data.getAssignHiddenId()!=null){
                sb.append(" , ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            }
            if(data.getSuperviseDate()!=null){
                sb.append(" , SUPERVISE_DATE = '"+data.getSuperviseDate()+"'");
            }
            if(data.getSuperviseMeasure()!=null){
                sb.append(" , SUPERVISE_MEASURE = '"+data.getSuperviseMeasure()+"'");
            }
            if(data.getSupervisePerson()!=null){
                sb.append(" , SUPERVISE_PERSON = '"+data.getSupervisePerson()+"'");
            }

            if(data.getInsertUserId()!=null){
                sb.append(" , INSERT_USER_ID = '"+data.getInsertUserId()+"'");
            }
            if(data.getUpdateUserId()!=null){
                sb.append(" , UPDATE_USER_ID = '"+data.getUpdateUserId()+"'");
            }
            if(data.getUpdateDatetime()!=null){
                sb.append(" , UPDATE_DATETIME = '"+data.getUpdateDatetime()+"'");
            }
            if(data.getInsertDatetime()!=null){
                sb.append(" , INSERT_DATETIME = '"+data.getInsertDatetime()+"'");
            }
            if(data.getAssignHiddenId()!=null){
                sb.append(" , ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            }
            if(data.getIsUpload()!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" where DANGER_SUPERVISE_ID = '"+data.getDangerSuperviseId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //隐患添加查数据,用于MyReceiver中推送过来的数据不会重复插入.zhangwenping
    public List<GtDangerSupervise> findById(String dangerSuperviseId) {
        List<GtDangerSupervise>  gds = new ArrayList<GtDangerSupervise>();
        try {
            gds = dao.queryBuilder().where().eq("DANGER_SUPERVISE_ID",dangerSuperviseId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gds;
    }
    //循环更新
    public void updateIsUpload(List<GtDangerSupervise> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                GtDangerSupervise gtDangerSupervises = list.get(i);
                StringBuffer sb = new StringBuffer(" update GT_DANGER_SUPERVISE set IS_UPLOAD = 0 ");
                sb.append(" where DANGER_SUPERVISE_ID = '"+gtDangerSupervises.getDangerSuperviseId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //同步判断该条数据是否存在本地库
    public List<GtDangerSupervise> queryById(GtDangerSupervise data) {
        List<GtDangerSupervise> gtDangerSupervises = new ArrayList<GtDangerSupervise>();
        try {
            gtDangerSupervises = dao.queryBuilder().where().eq("DANGER_SUPERVISE_ID",data.getDangerSuperviseId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtDangerSupervises;
    }

    //同步数据
    public void insertOrUpdate(GtDangerSupervise data){
        //查看当前数据数据存在
        List<GtDangerSupervise> gtDangerSupervises = queryById(data);
        if(gtDangerSupervises.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //本地修改整改表数据
    public void LocalInsertOrUpdate(GtDangerSupervise data){
        //查看当前数据数据存在
        List<GtDangerSupervise> gtDangerSupervises = queryById(data);
        if(gtDangerSupervises.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtDangerSupervise data){
        try {
            StringBuffer sb = new StringBuffer("update GT_DANGER_SUPERVISE set ");
            sb.append(" ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            sb.append(" ,CHECK_DANGER_ID = '"+data.getCheckDangerId()+"'");
            sb.append(" ,SUPERVISE_DATE = '"+data.getSuperviseDate()+"'");
            sb.append(" ,SUPERVISE_MEASURE = '"+data.getSuperviseMeasure()+"'");
            sb.append(" ,SUPERVISE_PERSON = '"+data.getSupervisePerson()+"'");

            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where DANGER_SUPERVISE_ID = '"+data.getDangerSuperviseId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}