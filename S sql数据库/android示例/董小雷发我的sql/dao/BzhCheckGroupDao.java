package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhCheckGroup;
import com.nkay.swyt.model.BzhPlanMajor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 标准化检查人员表的DAO类
 */
public class BzhCheckGroupDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<BzhCheckGroup, Integer> dao;

    public BzhCheckGroupDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(BzhCheckGroup.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(BzhCheckGroup data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<BzhCheckGroup> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                BzhCheckGroup bzhCheckGroup = list.get(i);
                StringBuffer sb = new StringBuffer(" update BZH_CHECKGROUP set IS_UPLOAD = 0 ");
                sb.append(" where SELF_CHECK_GROUP_ID = '"+bzhCheckGroup.getSelfCheckGroupId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<BzhCheckGroup> selectUploadData(){
        List<BzhCheckGroup> fList = new ArrayList<BzhCheckGroup>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 添加检查人员
    public void insertBzhCheckGroupList(List<BzhCheckGroup> groupListList, BzhPlanMajor bzhPlanMajor, int isUpload) {
        try {
            //先根据已选专业删除人员
            StringBuffer sb = new StringBuffer("DELETE FROM BZH_CHECKGROUP where BZH_CHECKGROUP.YIXUANZEPROJECTS_ID = '"+bzhPlanMajor.getYixuanzeprojectsId()+"';");
            dao.updateRaw(sb.toString());
            for (int i = 0; i < groupListList.size(); i++) {
                BzhCheckGroup bzhGroup = groupListList.get(i);
                bzhGroup.setIsUpload(isUpload);
                insert(bzhGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(BzhCheckGroup data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(BzhCheckGroup data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<BzhCheckGroup> queryById(BzhCheckGroup data) {
        List<BzhCheckGroup> bzhCheckGroups = new ArrayList<BzhCheckGroup>();
        try {
            bzhCheckGroups = dao.queryBuilder().where().eq("SELF_CHECK_GROUP_ID",data.getSelfCheckGroupId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bzhCheckGroups;
    }

    //同步数据
    public void insertOrUpdate(BzhCheckGroup data){
        //查看当前数据数据存在
        List<BzhCheckGroup> bzhCheckGroups = queryById(data);
        if(bzhCheckGroups.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(BzhCheckGroup data){
        try {
            StringBuffer sb = new StringBuffer("update BZH_CHECKGROUP set ");
            sb.append(" SELF_CHECK_EXAMINER = '"+data.getSelfCheckExaminer()+"' ");
            sb.append(" ,YIXUANZEPROJECTS_ID = '"+data.getYixuanzeprojectsId()+"'");
            if(data.getSelfCheckDeputyleader()!=0){
                sb.append(" ,SELF_CHECK_DEPUTYLEADER = 1 ");
            }else{
                sb.append(" ,SELF_CHECK_DEPUTYLEADER = 0 ");
            }
            if(data.getSelfCheckInspectionleader()!=0){
                sb.append(" ,SELF_CHECK_INSPECTIONLEADER = 1 ");
            }else{
                sb.append(" ,SELF_CHECK_INSPECTIONLEADER = 0 ");
            }
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where SELF_CHECK_GROUP_ID = '"+data.getSelfCheckGroupId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}