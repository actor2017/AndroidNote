package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhPlanMajor;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 标准化计划专业表的DAO类
 */
public class BzhPlanMajorDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<BzhPlanMajor, Integer> dao;

    public BzhPlanMajorDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(BzhPlanMajor.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(BzhPlanMajor data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(BzhPlanMajor data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<BzhPlanMajor> selectUploadData(){
        List<BzhPlanMajor> fList = new ArrayList<BzhPlanMajor>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //循环更新
    public void updateIsUpload(List<BzhPlanMajor> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                BzhPlanMajor bzhPlanMajor = list.get(i);
                StringBuffer sb = new StringBuffer(" update BZH_PLANMAJOR set IS_UPLOAD = 0 ");
                sb.append(" where YIXUANZEPROJECTS_ID = '"+bzhPlanMajor.getYixuanzeprojectsId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改计划专业总得分
    public void updateMajorFinllNum(BzhPlanMajor data) {
        String updateId = SharedPreferencesUtil.getStringValue(context, "userId");
        try {
            StringBuffer sb = new StringBuffer("update BZH_PLANMAJOR set FINALL_NUM = '"+data.getFinallNum()+"'");
            sb.append(" ,update_datatime = datetime('now')");
            sb.append(" ,IS_UPLOAD = "+data.getIsUpload()+"");
            sb.append(" ,update_user_id = '"+updateId+"'");
            sb.append(" where YIXUANZEPROJECTS_ID = '"+data.getYixuanzeprojectsId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(BzhPlanMajor data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<BzhPlanMajor> queryById(BzhPlanMajor data) {
        List<BzhPlanMajor> bzhPlanMajors = new ArrayList<BzhPlanMajor>();
        try {
            bzhPlanMajors = dao.queryBuilder().where().eq("YIXUANZEPROJECTS_ID",data.getYixuanzeprojectsId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bzhPlanMajors;
    }

    //同步数据
    public void insertOrUpdate(BzhPlanMajor data){
        //查看当前数据数据存在
        List<BzhPlanMajor> bzhPlanMajors = queryById(data);
        if(bzhPlanMajors.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(BzhPlanMajor data){
        try {
            StringBuffer sb = new StringBuffer("update BZH_PLANMAJOR set ");
            sb.append(" FINALL_NUM = '"+data.getFinallNum()+"' ");
            sb.append(" ,MAJOR_ID = '"+data.getMajorId()+"'");
            sb.append(" ,QUALITYPLAN_ID = '"+data.getQualityplanId()+"'");
            sb.append(" ,YIXUANZEPROJECTS_NAME = '"+data.getYixuanzeprojectsName()+"'");
            sb.append(" ,CHECK_LEAD_NAME = '"+data.getCheckleadName()+"'");
            sb.append(" ,CHECK_LEAD_ID = '"+data.getCheckleadId()+"'");
            sb.append(" ,CHECK_VICE_NAME = '"+data.getCheckviceName()+"'");
            sb.append(" ,CHECK_VICE_ID = '"+data.getCheckviceId()+"'");
            sb.append(" ,CHECK_MEMBER_NAME = '"+data.getCheckmemberName()+"'");
            sb.append(" ,CHECK_MEMBER_ID = '"+data.getCheckmemberId()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where YIXUANZEPROJECTS_ID = '"+data.getYixuanzeprojectsId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}