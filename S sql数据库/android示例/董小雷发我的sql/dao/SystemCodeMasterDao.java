package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.SystemCodeMaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作码表的DAO类
 */
public class SystemCodeMasterDao {
	
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<SystemCodeMaster, Integer> dao;

    public SystemCodeMasterDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(SystemCodeMaster.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(SystemCodeMaster data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(SystemCodeMaster data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(SystemCodeMaster data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 查询全部集合
    public List<SystemCodeMaster> queryAll() {
    	List<SystemCodeMaster> sc = new ArrayList<SystemCodeMaster>();
        try {
        	sc = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sc;
    }

    /**
     * 根据codeType查询码表数据
     * @param codeType
     * @return
     */
    public List<SystemCodeMaster> queryById(String codeType) {
        List<SystemCodeMaster> sc = new ArrayList<SystemCodeMaster>();
        try {
            sc = dao.queryBuilder().where().eq("code_type",codeType).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sc;
    }

    /**
     * 根据codeType和code2联合查询码表数据
     * @param codeType
     * @return
     */
    public List<SystemCodeMaster> queryById1(String codeType,String code2) {
        List<SystemCodeMaster> sc = new ArrayList<SystemCodeMaster>();
        try {
            sc = dao.queryBuilder().where().eq("code_type",codeType).and().eq("code_2",code2).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sc;
    }

    /**
     * 根据codeType和code1联合查询码表数据
     * @param codeType code1
     * @return
     */
    public List<SystemCodeMaster> queryByCode1(String codeType,String code1) {
        List<SystemCodeMaster> sc = new ArrayList<SystemCodeMaster>();
        try {
            sc = dao.queryBuilder().where().eq("code_type",codeType).and().eq("code_1",code1).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sc;
    }
}