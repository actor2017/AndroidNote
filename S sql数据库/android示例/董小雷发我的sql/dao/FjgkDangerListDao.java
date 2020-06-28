package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.FjgkDangerList;

import java.sql.SQLException;

/**
 * 操作风险清单表的DAO类
 */
public class FjgkDangerListDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<FjgkDangerList, Integer> dao;

    public FjgkDangerListDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(FjgkDangerList.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(FjgkDangerList data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(FjgkDangerList data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(FjgkDangerList data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过ID查询一条数据
    public FjgkDangerList queryById(int id) {
    	FjgkDangerList fjgkDangerList = null;
        try {
        	fjgkDangerList = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fjgkDangerList;
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