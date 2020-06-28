package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtDangerReform1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作隐患整改表的DAO类
 */
public class GtDangerReformDao1 {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtDangerReform1, Integer> dao;

    public GtDangerReformDao1(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtDangerReform1.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtDangerReform1 data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtDangerReform1 data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<GtDangerReform1> selectUploadData(){
        List<GtDangerReform1> fList = new ArrayList<GtDangerReform1>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //本地修改整改表数据
    public void LocalInsertOrUpdate(GtDangerReform1 data){
        //查看当前数据数据存在
        List<GtDangerReform1> gtDangerReform1s = queryById(data);
        if(gtDangerReform1s.size()>0){
            update(data);
        }else{
            insert(data);
        }
    }

    // 修改数据
    public void update(GtDangerReform1 data) {
        try {
            StringBuffer sb = new StringBuffer("UPDATE GT_DANGER_REFORM set update_datatime = datetime('now') ");
            if(data.getCheckDangerId()!=null){
                sb.append(" ,CHECK_DANGER_ID = '"+data.getCheckDangerId()+"' ");
            }
            if(data.getReformPerson()!=null){
                sb.append(" ,REFORM_PERSON = '"+data.getReformPerson()+"'");
            }
            if(data.getReformResult()!=null){
                sb.append(" , REFORM_RESULT = '"+data.getReformResult()+"'");
            }
            if(data.getReformDate()!=null){
                sb.append(" , REFORM_DATE = '"+data.getReformDate()+"'");
            }
            if(data.getReformMeasure()!=null){
                sb.append(" , REFORM_MEASURE = '"+data.getReformMeasure()+"'");
            }
            if(data.getReformClasses()!=null){
                sb.append(" , REFORM_CLASSES = '"+data.getReformClasses()+"'");
            }
            if(data.getAssignHiddenId()!=null){
                sb.append(" , ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            }
            if(data.getIsUpload()!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" where DANGER_REFORM_ID = '"+data.getDangerReformId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //隐患添加查数据,用于MyReceiver中推送过来的数据不会重复插入.zhangwenping
    public List<GtDangerReform1> findById(String dangerReformId) {
        List<GtDangerReform1>  gdr = new ArrayList<GtDangerReform1>();
        try {
            gdr = dao.queryBuilder().where().eq("DANGER_REFORM_ID",dangerReformId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gdr;
    }
    //循环更新
    public void updateIsUpload(List<GtDangerReform1> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                GtDangerReform1 gtDangerReform = list.get(i);
                StringBuffer sb = new StringBuffer(" update GT_DANGER_REFORM set IS_UPLOAD = 0 ");
                sb.append(" where DANGER_REFORM_ID = '"+gtDangerReform.getDangerReformId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //同步判断该条数据是否存在本地库
    public List<GtDangerReform1> queryById(GtDangerReform1 data) {
        List<GtDangerReform1> gtDangerReforms = new ArrayList<GtDangerReform1>();
        try {
            gtDangerReforms = dao.queryBuilder().where().eq("DANGER_REFORM_ID",data.getDangerReformId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtDangerReforms;
    }

    //同步数据
    public void insertOrUpdate(GtDangerReform1 data){
        //查看当前数据数据存在
        List<GtDangerReform1> gtDangerReforms = queryById(data);
        if(gtDangerReforms.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtDangerReform1 data){
        try {
            StringBuffer sb = new StringBuffer("update GT_DANGER_REFORM set ");
            sb.append(" ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            sb.append(" ,CHECK_DANGER_ID = '"+data.getCheckDangerId()+"'");
            sb.append(" ,REFORM_CLASSES = '"+data.getReformClasses()+"'");
            sb.append(" ,REFORM_DATE = '"+data.getReformDate()+"'");
            sb.append(" ,REFORM_MEASURE = '"+data.getReformMeasure()+"'");
            sb.append(" ,REFORM_PERSON = '"+data.getReformPerson()+"'");
            sb.append(" ,REFORM_RESULT = '"+data.getReformResult()+"'");

            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where DANGER_REFORM_ID = '"+data.getDangerReformId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}