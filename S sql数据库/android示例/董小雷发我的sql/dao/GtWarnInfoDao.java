package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.GtWarnInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * FileName: GtWarnInfoDao
 * Author: zhangwenping
 * Date: 2017/7/20
 * Email: httputils@qq.com
 */
public class GtWarnInfoDao {

    private Context context;
    private Dao<GtWarnInfo, Integer> dao;

    public GtWarnInfoDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(GtWarnInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增
     */
    public void insert(GtWarnInfo data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删
     */
    public void delete(String warnId) {
        try {
            StringBuffer sb = new StringBuffer("DELETE FROM GT_WARNINFO WHERE WARN_ID = '" + warnId + "'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改
     */
    public void updateWarnInfoStatus(String status, String warnId) {
        try {
            StringBuffer sb = new StringBuffer("update GT_WARNINFO set WARN_STATUS = '" + status + "' where WARN_ID = '" + warnId + "'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查
     */
    public List<GtWarnInfo> queryNotifyNews() {

        List<GtWarnInfo> gtWarnInfosList = null;
        try {
            //第二个参数是是否升序排列
            gtWarnInfosList = dao.queryBuilder().orderBy("NOTIFY_DATE", false).query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gtWarnInfosList;
    }


}
