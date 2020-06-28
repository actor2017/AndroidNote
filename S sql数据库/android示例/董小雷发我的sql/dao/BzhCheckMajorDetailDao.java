package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.BzhCheckMajorDetail;
import com.nkay.swyt.utils.SharedPreferencesUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 专业详细表的DAO类
 */
public class BzhCheckMajorDetailDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<BzhCheckMajorDetail, Integer> dao;

    public BzhCheckMajorDetailDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(BzhCheckMajorDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(BzhCheckMajorDetail data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改检查项实际得分
    public void updateScore(List<BzhCheckMajorDetail> data,int isUpload) {
        String updateId = SharedPreferencesUtil.getStringValue(context, "userId");
        try {
            for (int i = 0; i < data.size(); i++) {
                BzhCheckMajorDetail bzhCheckMajorDetail = data.get(i);
                StringBuffer sb = new StringBuffer("update BZH_CHECK_MAJOR_DETAIL set SELF_CHECK_SCORE = '"+bzhCheckMajorDetail.getSelfCheckScore()+"'");
                sb.append(",update_datatime = datetime('now')");
                sb.append(",LACK_FLG = '"+bzhCheckMajorDetail.getLackFlg()+"'");
                sb.append(" ,IS_UPLOAD = "+isUpload+"");
                sb.append(" ,update_user_id = '"+updateId+"'");
                sb.append(" where SELF_CHECK_SPECIALTY_ID = '"+bzhCheckMajorDetail.getSelfCheckSpecialtyId()+"'");
                dao.updateRaw(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //循环更新
    public void updateIsUpload(List<BzhCheckMajorDetail> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                BzhCheckMajorDetail bzhCheckMajorDetail = list.get(i);
                StringBuffer sb = new StringBuffer(" update BZH_CHECK_MAJOR_DETAIL set IS_UPLOAD = 0 ");
                sb.append(" where SELF_CHECK_SPECIALTY_ID = '"+bzhCheckMajorDetail.getSelfCheckSpecialtyId()+"'");
                dao.updateRaw(sb.toString());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //未上传成功的集合
    public List<BzhCheckMajorDetail> selectUploadData(){
        List<BzhCheckMajorDetail> fList = new ArrayList<BzhCheckMajorDetail>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD",1).query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 删除数据
    public void delete(BzhCheckMajorDetail data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public void update(BzhCheckMajorDetail data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //同步判断该条数据是否存在本地库
    public List<BzhCheckMajorDetail> queryById(BzhCheckMajorDetail data) {
        List<BzhCheckMajorDetail> bzhCheckMajorDetails = new ArrayList<BzhCheckMajorDetail>();
        try {
            bzhCheckMajorDetails = dao.queryBuilder().where().eq("SELF_CHECK_SPECIALTY_ID",data.getSelfCheckSpecialtyId()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bzhCheckMajorDetails;
    }

    //同步数据
    public void insertOrUpdate(BzhCheckMajorDetail data){
        //查看当前数据数据存在
        List<BzhCheckMajorDetail> bzhCheckMajorDetails = queryById(data);
        if(bzhCheckMajorDetails.size()>0){
            updateById(data);
        }else{
            insert(data);
        }
    }

    //同步数据根据uuid更新数据
    public void updateById(BzhCheckMajorDetail data){
        try {
            StringBuffer sb = new StringBuffer("update BZH_CHECK_MAJOR_DETAIL set ");
            sb.append(" YIXUANZEPROJECTS_ID = '"+data.getYixuanzeprojectsId()+"' ");
            sb.append(" ,dutyDept = '"+data.getDutyDept()+"'");
            sb.append(" ,nodeName = '"+data.getNodeName()+"'");
            sb.append(" ,SELF_CHECK_BASICREQUIREMENTS = '"+data.getSelfCheckBasicrequirements()+"'");
            sb.append(" ,SELF_CHECK_METHODOFCOMMENT = '"+data.getSelfCheckMethodofcomment()+"'");
            sb.append(" ,SELF_CHECK_PROJECTCONTENTS = '"+data.getSelfCheckProjectcontents()+"'");
            sb.append(" ,SELF_CHECK_PROJECTCONTENTS2 = '"+data.getSelfCheckProjectcontents2()+"'");
            sb.append(" ,SELF_CHECK_PROJECTNAME = '"+data.getSelfCheckProjectname()+"'");
            sb.append(" ,SELF_CHECK_SCORE = '"+data.getSelfCheckScore()+"'");
            sb.append(" ,SELF_CHECK_SORT = '"+data.getSelfCheckSort()+"'");
            sb.append(" ,SELF_CHECK_STANDARTSCORE = '"+data.getSelfCheckStandartscore()+"'");
            sb.append(" ,SELF_CHECKCONTENTS_ID = '"+data.getSelfCheckcontentsId()+"'");
            sb.append(" ,LACK_FLG = '"+data.getLackFlg()+"'");
            sb.append(" ,SELF_CHECK_SCORE_GROUP = '"+data.getSelfCheckScoreGroup()+"'");
            sb.append(" ,SELF_CHECK_METHOD_GROUP = '"+data.getSelfCheckMethodGroup()+"'");
            sb.append(" ,SELF_CONTENT_SORT = '"+data.getSelfContentSort()+"'");
            sb.append(" ,CHECK_WORKFACE = '"+data.getCheckWorkface()+"'");
            sb.append(" ,CHECK_WORKFACE_NAME = '"+data.getCheckWorkfaceName()+"'");
            sb.append(" ,CHECK_NAME = '"+data.getCheckWorkfaceName()+"'");
            if(data.getDelFlg()!=0){
                sb.append(" ,del_flg = 1 ");
            }else{
                sb.append(" ,del_flg = 0 ");
            }
            sb.append(" ,insert_datetime = '"+data.getInsertDatetime()+"'");
            sb.append(" ,insert_user_id = '"+data.getInsertUserId()+"'");
            sb.append(" ,update_datatime = '"+data.getUpdateDatetime()+"'");
            sb.append(" ,update_user_id = '"+data.getUpdateUserId()+"'");
            sb.append(" where SELF_CHECK_SPECIALTY_ID = '"+data.getSelfCheckSpecialtyId()+"'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据workfaceID更新数据
    public void updateDataByWorkfeceId(String workFaceId,int isUpload) {
        try {
            String updateId = SharedPreferencesUtil.getStringValue(context, "userId");
            //先根据已选专业删除人员
            StringBuffer sb = new StringBuffer("UPDATE BZH_CHECK_MAJOR_DETAIL  SET DEL_FLG = 1 ");
            if(isUpload!=0){
                sb.append(" ,IS_UPLOAD = 1 ");
            }else{
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            sb.append(" ,update_datatime = datetime('now')");
            sb.append(" ,update_user_id = '"+updateId+"'");
            sb.append(" where BZH_CHECK_MAJOR_DETAIL.CHECK_WORKFACE = '"+workFaceId+"' ");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}