package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.CompanycollierDepartmentManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作部门表的DAO类
 */
public class CompanycollierDeptDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<CompanycollierDepartmentManagement, Integer> dao;

    public CompanycollierDeptDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(CompanycollierDepartmentManagement.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(CompanycollierDepartmentManagement data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(CompanycollierDepartmentManagement data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(CompanycollierDepartmentManagement data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询全部部门
    public List<CompanycollierDepartmentManagement> queryAll() {
        List<CompanycollierDepartmentManagement> dept = new ArrayList<CompanycollierDepartmentManagement>();
        try {
            dept = dao.queryBuilder().orderBy("SEQ",true).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dept;
    }

    // 通过用户ID查询部门信息
    public List<CompanycollierDepartmentManagement> queryBydeptId(String deptId) {
        List<CompanycollierDepartmentManagement> depts = new ArrayList<CompanycollierDepartmentManagement>();
        try {
            depts = dao.queryBuilder().where().eq("DEPARTMENT_MANAGEMENT_ID",deptId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return depts;
    }



    // 通过ID查询一条数据
    public CompanycollierDepartmentManagement queryById(int id) {
    	CompanycollierDepartmentManagement dept = null;
        try {



        	dept = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dept;
    }
//
//    // 通过条件查询文章集合（通过用户ID查找）
//    public List<ArticleBean> queryByUserId(int user_id) {
//        try {
//            return dao.queryBuilder().where().eq(ArticleBean.COLUMNNAME_USER, user_id).query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}