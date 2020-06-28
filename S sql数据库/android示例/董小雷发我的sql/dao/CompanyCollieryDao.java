package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.CompanyColliery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作煤矿表的DAO类
 */
public class CompanyCollieryDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<CompanyColliery, Integer> dao;

    public CompanyCollieryDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(CompanyColliery.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(CompanyColliery data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(CompanyColliery data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新全部数据
    public void update(CompanyColliery data){
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过煤矿ID查询煤矿信息
    public List<CompanyColliery> queryByColliery(String collieryd) {
        List<CompanyColliery> companyCollieries = new ArrayList<CompanyColliery>();
        try {
            companyCollieries = dao.queryBuilder().where().eq("COMPANY_COLLIERY_ID", collieryd).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyCollieries;
    }
}