package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtPostpone;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 隐患延期表的DAO类
 */
public class GtPostponeDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtPostpone, Integer> dao;

    public GtPostponeDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtPostpone.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtPostpone data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtPostpone data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //同步判断该条数据是否存在本地库
    public List<GtPostpone> queryById(GtPostpone data) {
        List<GtPostpone> gtPostpones = new ArrayList<GtPostpone>();
        try {
            gtPostpones = dao.queryBuilder().where().eq("POSTPONE_ID",data.getPostponeId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtPostpones;
    }

    //同步数据
    public void insertOrUpdate(GtPostpone data){
        //查看当前数据数据存在
        List<GtPostpone> bzhPlans = queryById(data);
        if(bzhPlans.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtPostpone data){
        try {
            StringBuffer sb = new StringBuffer("update GT_POSTPONE set ");
            sb.append(" APPLICATION_DATE = '"+data.getApplicationDate()+"' ");
            sb.append(" ,AUDIT_DATE = '"+data.getAuditDate()+"'");
            sb.append(" ,AUDIT_OPINION = '"+data.getAuditOpinion()+"'");
            sb.append(" ,AUDIT_PERSION = '"+data.getAuditPersion()+"'");
            sb.append(" ,AUDIT_STATUS = '"+data.getAuditStatus()+"'");
            sb.append(" ,HIDDEN_ID = '"+data.getHiddenId()+"'");
            sb.append(" ,POSTPONE_DATE = '"+data.getPostponeDate()+"'");
            sb.append(" ,POSTPONE_OPINION = '"+data.getPostponeOpinion()+"'");
            sb.append(" ,POSTPONE_PERSION = '"+data.getPostponePersion()+"'");
            sb.append(" ,ASSIGN_HIDDEN_ID = '"+data.getAssignHiddenId()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where POSTPONE_ID = '"+data.getPostponeId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}