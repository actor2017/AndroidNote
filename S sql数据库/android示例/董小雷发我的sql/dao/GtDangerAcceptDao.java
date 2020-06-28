package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtDangerAccept;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作隐患验收表的DAO类
 */
public class GtDangerAcceptDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtDangerAccept, Integer> dao;

    public GtDangerAcceptDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtDangerAccept.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtDangerAccept data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtDangerAccept data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<GtDangerAccept> selectUploadData(){
        List<GtDangerAccept> fList = new ArrayList<GtDangerAccept>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //本地修改整改表数据
    public void LocalInsertOrUpdate(GtDangerAccept data){
        //查看当前数据数据存在
        List<GtDangerAccept> gtDangerAccepts = queryById(data);
        if(gtDangerAccepts.size()>0){
            update(data);
        }else{
            insert(data);
        }
    }

    // 修改数据
    public void update(GtDangerAccept data) {
        try {
            StringBuffer sb = new StringBuffer("UPDATE GT_DANGER_ACCEPT set update_datatime = datetime('now') ");
            if(data.getCheckDangerId()!=null){
                sb.append(" ,CHECK_DANGER_ID = '"+data.getCheckDangerId()+"' ");
            }
            if(data.getAssignHiddenId()!=null){
                sb.append(" ,ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            }
            if(data.getAcceptDept()!=null){
                sb.append(" , ACCEPT_DEPT = '"+data.getAcceptDept()+"'");
            }
            if(data.getAcceptResult()!=null){
                sb.append(" , ACCEPT_RESULT = '"+data.getAcceptResult()+"'");
            }
            if(data.getAcceptDate()!=null){
                sb.append(" , ACCEPT_DATE = '"+data.getAcceptDate()+"'");
            }
            if(data.getAcceptOpinion()!=null){
                sb.append(" , ACCEPT_OPINION = '"+data.getAcceptOpinion()+"'");
            }
            if(data.getAcceptPerson()!=null){
                sb.append(" , ACCEPT_PERSON = '"+data.getAcceptPerson()+"'");
            }
            if(data.getAcceptType()!=0){
                sb.append(" ,ACCEPT_TYPE = 1 ");
            }else{
                sb.append(" ,ACCEPT_TYPE = 0 ");
            }
            if(data.getAssignHiddenId()!=null){
                sb.append(" , ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            }
            if(data.getIsUpload()!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" where DANGER_ACCEPT_ID = '"+data.getDangerAcceptId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //隐患添加查数据,用于MyReceiver中推送过来的数据不会重复插入.zhangwenping
    public List<GtDangerAccept> findById(String dangerAcceptId) {
        List<GtDangerAccept>  gda = new ArrayList<GtDangerAccept>();
        try {
            gda = dao.queryBuilder().where().eq("DANGER_ACCEPT_ID",dangerAcceptId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gda;
    }
    //循环更新
    public void updateIsUpload(List<GtDangerAccept> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                GtDangerAccept gtDangerAccept = list.get(i);
                StringBuffer sb = new StringBuffer(" update GT_DANGER_ACCEPT set IS_UPLOAD = 0 ");
                sb.append(" where DANGER_ACCEPT_ID = '"+gtDangerAccept.getDangerAcceptId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //同步判断该条数据是否存在本地库
    public List<GtDangerAccept> queryById(GtDangerAccept data) {
        List<GtDangerAccept> gtDangerAccepts = new ArrayList<GtDangerAccept>();
        try {
            gtDangerAccepts = dao.queryBuilder().where().eq("DANGER_ACCEPT_ID",data.getDangerAcceptId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtDangerAccepts;
    }

    //同步数据
    public void insertOrUpdate(GtDangerAccept data){
        //查看当前数据数据存在
        List<GtDangerAccept> gtDangerAccepts = queryById(data);
        if(gtDangerAccepts.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtDangerAccept data){
        try {
            StringBuffer sb = new StringBuffer("update GT_DANGER_ACCEPT set ");
            sb.append(" ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            sb.append(" ,CHECK_DANGER_ID = '"+data.getCheckDangerId()+"'");
            sb.append(" ,ACCEPT_DEPT = '"+data.getAcceptDept()+"'");
            sb.append(" ,ACCEPT_RESULT = '"+data.getAcceptResult()+"'");
            sb.append(" ,ACCEPT_DATE = '"+data.getAcceptDate()+"'");
            sb.append(" ,ACCEPT_OPINION = '"+data.getAcceptOpinion()+"'");
            sb.append(" ,ACCEPT_PERSON = '"+data.getAcceptPerson()+"'");
            sb.append(" ,ACCEPT_TYPE = '"+data.getAcceptType()+"'");

            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where DANGER_ACCEPT_ID = '"+data.getDangerAcceptId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}