package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtExalt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 隐患提交表的DAO类
 */
public class GtExaltDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtExalt, Integer> dao;

    public GtExaltDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtExalt.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtExalt data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtExalt data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //同步判断该条数据是否存在本地库
    public List<GtExalt> queryById(GtExalt data) {
        List<GtExalt> gtExalts = new ArrayList<GtExalt>();
        try {
            gtExalts = dao.queryBuilder().where().eq("GT_EXALT_ID",data.getGtExaltId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtExalts;
    }

    //同步数据
    public void insertOrUpdate(GtExalt data){
        //查看当前数据数据存在
        List<GtExalt> gtExalts = queryById(data);
        if(gtExalts.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtExalt data){
        try {
            StringBuffer sb = new StringBuffer("update GT_EXALT set ");
            sb.append(" CHECK_HIDDEN_ID = '"+data.getCheckHiddenId()+"' ");
            sb.append(" ,EXALT_DANGER_CLASSES = '"+data.getExaltDangerClasses()+"'");
            sb.append(" ,EXALT_OPINION = '"+data.getExaltOpinion()+"'");
            sb.append(" ,EXALT_PERSON = '"+data.getExaltPerson()+"'");
            sb.append(" ,EXALT_PERSON_OVER = '"+data.getExaltPersonOver()+"'");
            sb.append(" ,EXALT_REASON = '"+data.getExaltReason()+"'");
            sb.append(" ,EXALT_TIME = '"+data.getExaltTime()+"'");
            sb.append(" ,EXALT_TIME_OVER = '"+data.getExaltTimeOver()+"'");
            sb.append(" ,LAST_EXALT_DANGER_CLASSES = '"+data.getLastExaltDangerClasses()+"'");
            sb.append(" ,STATUS = '"+data.getStatus()+"'");
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
            sb.append(" where GT_EXALT_ID = '"+data.getGtExaltId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}