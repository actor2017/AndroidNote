package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhMajorCheckContent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: BzhMajorCheckContentDao
 * Author: zhangwenping
 * Date: 2017/8/29
 * Email: httputils@qq.com
 */
public class BzhMajorCheckContentDao {

    private Context context;
    private Dao<BzhMajorCheckContent, Integer> dao;

    public BzhMajorCheckContentDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(BzhMajorCheckContent.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(BzhMajorCheckContent data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(BzhMajorCheckContent data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(BzhMajorCheckContent data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<BzhMajorCheckContent> queryById(BzhMajorCheckContent data) {
        List<BzhMajorCheckContent> bzhMajorCheckContents = new ArrayList<BzhMajorCheckContent>();
        try {
            bzhMajorCheckContents = dao.queryBuilder().where().eq("SELF_CHECKCONTENTS_ID",data.getSelfCheckcontentsId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bzhMajorCheckContents;
    }

    //同步数据
    public void insertOrUpdate(BzhMajorCheckContent data){
        //查看当前数据数据存在
        List<BzhMajorCheckContent> bzhMajorCheckContents = queryById(data);
        if(bzhMajorCheckContents.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(BzhMajorCheckContent data){
        try {
            StringBuffer sb = new StringBuffer("update BZH_MAJOR_CHECK_CONTENT set ");
            sb.append(" COLLIERY_TYPE_ID = '"+data.getCollieryTypeId()+"' ");
            sb.append(" ,SELF_CHECK_BASICREQUIREMENTS = '"+data.getSelfCheckBasicrequirements()+"'");
            sb.append(" ,SELF_CHECK_METHOD_GROUP = '"+data.getSelfCheckMethodGroup()+"'");
            sb.append(" ,SELF_CHECK_METHODOFCOMMENT = '"+data.getSelfCheckMethodofcomment()+"'");
            sb.append(" ,SELF_CHECK_PROJECTCONTENTS = '"+data.getSelfCheckProjectcontents()+"'");
            sb.append(" ,SELF_CHECK_PROJECTCONTENTS2 = '"+data.getSelfCheckProjectcontents2()+"'");
            sb.append(" ,SELF_CHECK_PROJECTNAME = '"+data.getSelfCheckProjectname()+"'");
            sb.append(" ,SELF_CHECK_SCORE_GROUP = '"+data.getSelfCheckScoreGroup()+"'");
            sb.append(" ,SELF_CHECK_SORT = '"+data.getSelfCheckSort()+"'");
            sb.append(" ,SELF_CONTENT_SORT = '"+data.getSelfContentSort()+"'");
            sb.append(" ,YIXUANZEPROJECTS_ID = '"+data.getYixuanzeprojectsId()+"'");

            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where SELF_CHECKCONTENTS_ID = '"+data.getSelfCheckcontentsId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
