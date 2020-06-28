package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.FjgkSceneCheckDanger;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作风险列表的DAO类
 */
public class FjgkSceneCheckDangerDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<FjgkSceneCheckDanger, Integer> dao;

    public FjgkSceneCheckDangerDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(FjgkSceneCheckDanger.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<FjgkSceneCheckDanger> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                FjgkSceneCheckDanger fjgkSceneCheckDanger = list.get(i);
                StringBuffer sb = new StringBuffer(" update FJGK_SCENECHECK_DANGER set IS_UPLOAD = 0 ");
                sb.append(" where CHECK_DANGER_ID = '"+fjgkSceneCheckDanger.getCheckDangerId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(FjgkSceneCheckDanger data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(FjgkSceneCheckDanger data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改隐患检查结果
    public void updateDangerResult(FjgkSceneCheckDanger data) {
        String updateId = SharedPreferencesUtil.getStringValue(context, "userId");
        try {
            StringBuffer sb = new StringBuffer("update FJGK_SCENECHECK_DANGER set CHECK_RESULT = '"+data.getCheckResult()+"'");
            sb.append(" ,update_datatime = datetime('now')");
            sb.append(" ,IS_UPLOAD = "+data.getIsUpload()+"");
            sb.append(" ,update_user_id = '"+updateId+"'");
            sb.append(" where CHECK_DANGER_ID = '"+data.getCheckDangerId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过ID查询一条数据
    public FjgkSceneCheckDanger queryById(int id) {
    	FjgkSceneCheckDanger fjgkSceneCheckDanger = null;
        try {
        	fjgkSceneCheckDanger = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fjgkSceneCheckDanger;
    }

    //未上传成功的集合
    public List<FjgkSceneCheckDanger> selectUploadData(){
        List<FjgkSceneCheckDanger> fList = new ArrayList<FjgkSceneCheckDanger>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 查询全部集合
    public List<FjgkSceneCheckDanger> queryAll() {

    	List<FjgkSceneCheckDanger> fList = new ArrayList<FjgkSceneCheckDanger>();
        try {
        	fList = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }


    //同步判断该条数据是否存在本地库
    public List<FjgkSceneCheckDanger> queryById(FjgkSceneCheckDanger data) {
        List<FjgkSceneCheckDanger> fjgkSceneCheckDangers = new ArrayList<FjgkSceneCheckDanger>();
        try {
            fjgkSceneCheckDangers = dao.queryBuilder().where().eq("CHECK_DANGER_ID",data.getCheckDangerId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fjgkSceneCheckDangers;
    }

    //同步数据
    public void insertOrUpdate(FjgkSceneCheckDanger data){
        //查看当前数据数据存在
        List<FjgkSceneCheckDanger> fjgkSceneChecks = queryById(data);
        if(fjgkSceneChecks.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(FjgkSceneCheckDanger data){
        try {
            StringBuffer sb = new StringBuffer("update FJGK_SCENECHECK_DANGER set ");
            sb.append(" CHECK_RESULT = '"+data.getCheckResult()+"' ");
            sb.append(" ,controlMeasure = '"+data.getControlMeasure()+"'");
            sb.append(" ,dangerAddressId = '"+data.getDangerAddressId()+"'");
            sb.append(" ,dangerAddressIdName = '"+data.getDangerAddressIdName()+"'");
            sb.append(" ,DANGER_CLASSES1 = '"+data.getDangerClasses1()+"'");
            sb.append(" ,DANGER_CLASSES2 = '"+data.getDangerClasses2()+"'");
            sb.append(" ,DANGER_CONTENT = '"+data.getDangerContent()+"'");
            sb.append(" ,dangerFormName = '"+data.getDangerFormName()+"'");
            sb.append(" ,DANGER_LIST_ID = '"+data.getDangerListId()+"'");
            sb.append(" ,DANGER_MAJOR = '"+data.getDangerMajor()+"'");
            sb.append(" ,DANGER_RANK = '"+data.getDangerRank()+"'");
            sb.append(" ,deptName = '"+data.getDeptName()+"'");
            sb.append(" ,identifyResult = '"+data.getIdentifyResult()+"'");
            sb.append(" ,remark = '"+data.getRemark()+"'");
            sb.append(" ,SCENE_CHECK_ID = '"+data.getSceneCheckId()+"'");
            sb.append(" ,SYSTEM_CLASSIFY = '"+data.getSystemClassify()+"'");
            sb.append(" ,userName = '"+data.getUserName()+"'");
            sb.append(" ,DANGER_AREA = '"+data.getDangerArea()+"'");
            sb.append(" ,DANGER_ADDRESS_NAME = '"+data.getDangerAddressName()+"'");
            sb.append(" ,DANGER_AREA_NAME = '"+data.getDangerAreaName()+"'");

            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where CHECK_DANGER_ID = '"+data.getCheckDangerId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}