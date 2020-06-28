package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtAccessory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件表的DAO类
 */
public class GtAccessoryDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<GtAccessory, Integer> dao;

    public GtAccessoryDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtAccessory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 添加数据
    public void insert(GtAccessory data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(GtAccessory gtAccessory){
        try {
                StringBuffer sb = new StringBuffer(" update GT_ACCESSORY set IS_UPLOAD = 0 ");
                sb.append(" where ACCESSORY_ID = '"+gtAccessory.getAccessoryId()+"'");
                dao.updateRaw(sb.toString());
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(String photoName) {
        try {

            //dao.deleteBuilder().where().eq("ACCESSORY_NAME",photoName);

            StringBuffer sb = new StringBuffer("DELETE FROM GT_ACCESSORY where GT_ACCESSORY.ACCESSORY_NAME = '"+photoName+"';");
            dao.updateRaw(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //根据隐患ID/整改ID查找附件
    public List<GtAccessory> selectAccessoryById(String Id){
        List<GtAccessory> gtAccessoryList = new ArrayList<GtAccessory>();
        try {
            gtAccessoryList = dao.queryBuilder().where().eq("RELEVANCE_ID",Id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gtAccessoryList;
    }

    //未上传成功的集合
    public List<GtAccessory> selectUploadData(){
        List<GtAccessory> fList = new ArrayList<GtAccessory>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 修改数据
    public void update(GtAccessory data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}