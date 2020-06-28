package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.CollieryClasses;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作表次表的DAO类
 */
public class CollieryClassesDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<CollieryClasses, Integer> dao;

    public CollieryClassesDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(CollieryClasses.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(CollieryClasses data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(CollieryClasses data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(CollieryClasses data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过ID查询一条数据
    public CollieryClasses queryById(int id) {
        CollieryClasses collieryClasses = null;
        try {
            collieryClasses = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collieryClasses;
    }

    /**
     * 查找全部班次信息
     * @return
     */
    public List<CollieryClasses> queryAllCollieryClasses() {
        List<CollieryClasses> cs = new ArrayList<CollieryClasses>();
        try {
            cs = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cs;
    }

    //同步判断该条数据是否存在本地库
    public List<CollieryClasses> queryById(CollieryClasses data) {
        List<CollieryClasses> collieryClasses = new ArrayList<CollieryClasses>();
        try {
            collieryClasses = dao.queryBuilder().where().eq("DUTY_ORDER_ID",data.getDutyOrderId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collieryClasses;
    }

    //同步数据
    public void insertOrUpdate(CollieryClasses data){
        //查看当前数据数据存在
        List<CollieryClasses> collieryClasses = queryById(data);
        if(collieryClasses.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(CollieryClasses data){
        try {
            StringBuffer sb = new StringBuffer("update BZH_COLLIERY_CLASSES set ");
            sb.append(" COMPANY_COLLIERY_ID = '"+data.getCompanyCollieryId()+"' ");
            sb.append(" ,DUTY_ORDER_NAME = '"+data.getDutyOrderName()+"'");
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
            sb.append(" where DUTY_ORDER_ID = '"+data.getDutyOrderId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}