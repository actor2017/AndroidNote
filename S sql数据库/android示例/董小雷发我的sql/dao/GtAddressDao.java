package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtAddress;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 地点管理的DAO类
 */
public class GtAddressDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtAddress, Integer> dao;

    public GtAddressDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtAddress.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(GtAddress data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(GtAddress data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询区域信息
    public List<GtAddress> selectArea(){
        List<GtAddress> gtAddresses = new ArrayList<GtAddress>();
        try {
            gtAddresses = dao.queryBuilder().where().eq("ADDRESS_TYPE","0").and().eq("del_flg",0).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtAddresses;
    }

    //查询区域下的地点信息
    public List<GtAddress> selectGtAddressByArea(String areaId ){
        List<GtAddress> gtAddresses = new ArrayList<GtAddress>();
        try {
            gtAddresses = dao.queryBuilder().where().eq("AREA",areaId).and().eq("del_flg",0).and().eq("ADDRESS_TYPE","1").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtAddresses;
    }


    //同步判断该条数据是否存在本地库
    public List<GtAddress> queryById(GtAddress data) {
        List<GtAddress> gtAddresses = new ArrayList<GtAddress>();
        try {
            gtAddresses = dao.queryBuilder().where().eq("ADDRESS_ID",data.getAddressId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtAddresses;
    }

    //同步数据
    public void insertOrUpdate(GtAddress data){
        //查看当前数据数据存在
        List<GtAddress> gtAddresses = queryById(data);
        if(gtAddresses.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(GtAddress data){
        try {
            StringBuffer sb = new StringBuffer("update GT_ADDRESS set ");
            sb.append(" COLLIERY_ID = '"+data.getCollieryId()+"' ");
            sb.append(" ,ADDRESS_NAME = '"+data.getAddressName()+"'");
            sb.append(" ,ADDRESS_DES = '"+data.getAddressDes()+"'");
            sb.append(" ,ADDRESS_STATUS = '"+data.getAddressStatus()+"'");
            sb.append(" ,LOSE_REASON = '"+data.getLoseReason()+"'");
            sb.append(" ,LOSE_DATE = '"+data.getLoseDate()+"'");
            sb.append(" ,ADDRESS_TYPE = '"+data.getAddressType()+"'");
            sb.append(" ,AREA = '"+data.getArea()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where ADDRESS_ID = '"+data.getAddressId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}