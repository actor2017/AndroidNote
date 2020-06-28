package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.HiddenPlanDetail;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作隐患详情表的DAO类
 */
public class HiddenPlanDetailDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<HiddenPlanDetail, Integer> dao;

    public HiddenPlanDetailDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(HiddenPlanDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(HiddenPlanDetail data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(HiddenPlanDetail data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<HiddenPlanDetail> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                HiddenPlanDetail hiddenPlanDetail = list.get(i);
                StringBuffer sb = new StringBuffer(" update YHPC_HIDDEN_PLAN_DETAIL set IS_UPLOAD = 0 ");
                sb.append(" where HIDDEN_PLAN_DETAIL = '"+hiddenPlanDetail.getHiddenPlanDetail()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<HiddenPlanDetail> selectUploadData(){
        List<HiddenPlanDetail> fList = new ArrayList<HiddenPlanDetail>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 修改隐患排查结果
    public void update(HiddenPlanDetail data) {
        String updateId = SharedPreferencesUtil.getStringValue(context, "userId");
        try {
            StringBuffer sb = new StringBuffer("update YHPC_HIDDEN_PLAN_DETAIL set CHECK_RESULT = '"+data.getCheckResult()+"',");
            sb.append(" update_datatime = datetime('now')");
            sb.append(" ,IS_UPLOAD = "+data.getIsUpload()+"");
            sb.append(" ,update_user_id = '"+updateId+"'");
            sb.append("  where HIDDEN_PLAN_DETAIL = '"+data.getHiddenPlanDetail()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<HiddenPlanDetail> queryById(HiddenPlanDetail data) {
        List<HiddenPlanDetail> hiddenPlanDetails = new ArrayList<HiddenPlanDetail>();
        try {
            hiddenPlanDetails = dao.queryBuilder().where().eq("HIDDEN_PLAN_DETAIL",data.getHiddenPlanDetail()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hiddenPlanDetails;
    }

    //同步数据
    public void insertOrUpdate(HiddenPlanDetail data){
        //查看当前数据数据存在
        List<HiddenPlanDetail> hiddenPlanDetails = queryById(data);
        if(hiddenPlanDetails.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(HiddenPlanDetail data){
        try {
            StringBuffer sb = new StringBuffer("update YHPC_HIDDEN_PLAN_DETAIL set ");
            sb.append(" CHECK_RESULT = '"+data.getCheckResult()+"' ");
            sb.append(" ,CLASS_IFICATION = '"+data.getClassIfication()+"'");
            sb.append(" ,hiddenBasis = '"+data.getHiddenBasis()+"'");
            sb.append(" ,HIDDEN_CHECK_PLANID = '"+data.getHiddenCheckPlanid()+"'");
            sb.append(" ,HIDDEN_DESCRIBE = '"+data.getHiddenDescribe()+"'");
            sb.append(" ,HIDDEN_ID = '"+data.getHiddenId()+"'");
            sb.append(" ,HIDDEN_LEVEL = '"+data.getHiddenLevel()+"'");
            sb.append(" ,HIDDEN_MAJOR = '"+data.getHiddenMajor()+"'");
            sb.append(" ,hiddenOpinion = '"+data.getHiddenOpinion()+"'");
            sb.append(" ,hiddenStandard = '"+data.getHiddenStandard()+"'");
            sb.append(" ,HIDDEN_TYPE1 = '"+data.getHiddenType1()+"'");
            sb.append(" ,HIDDEN_TYPE2 = '"+data.getHiddenType2()+"'");
            sb.append(" ,note = '"+data.getNote()+"'");
            sb.append(" ,step = '"+data.getStep()+"'");
            sb.append(" ,DANGER_ADDRESS = '"+data.getDangerAddress()+"'");
            sb.append(" ,dangerAddressIdName = '"+data.getDangerAddressIdName()+"'");
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
            sb.append(" where HIDDEN_PLAN_DETAIL = '"+data.getHiddenPlanDetail()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}