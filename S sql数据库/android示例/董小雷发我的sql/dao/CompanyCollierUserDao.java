package com.nkay.swyt.database.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.nkay.swyt.database.helper.DatabaseHelper;
import com.nkay.swyt.model.CompanyCollierUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作煤矿用户表的DAO类
 */
public class CompanyCollierUserDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<CompanyCollierUser, Integer> dao;

    public CompanyCollierUserDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(CompanyCollierUser.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(CompanyCollierUser data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除数据
    public void delete(CompanyCollierUser data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改用户信息
    public void updateUser(CompanyCollierUser data) {
        try {
            StringBuffer sb = new StringBuffer("update COMPANY_COLLIERY_USER set update_datatime = datetime('now')");
            if (data.getStatus() != 0) {
                sb.append(" ,STATUS = 1 ");
            } else {
                sb.append(" ,STATUS = 0 ");
            }
            if (data.getSubjection() != null) {
                sb.append(" ,SUBJECTION = '" + data.getSubjection() + "' ");
            }
            if (data.getDeparymentId() != null) {
                sb.append(" ,DEPARYMENT_ID = '" + data.getDeparymentId() + "'");
            }
            if (data.getLoginName() != null) {
                sb.append(" , LOGIN_NAME = '" + data.getLoginName() + "'");
            }
            if (data.getLoginPassword() != null) {
                sb.append(" , LOGIN_PASSWORD = '" + data.getLoginPassword() + "'");
            }
            if (data.getHpicUrl() != null) {
                sb.append(" , HPIC_URL = '" + data.getHpicUrl() + "'");
            }
            if (data.getUserName() != null) {
                sb.append(" , USER_NAME = '" + data.getUserName() + "'");
            }
            if (data.getUserIdcard() != null) {
                sb.append(" , USER_IDCARD = '" + data.getUserIdcard() + "'");
            }
            if (data.getUserEmall() != null) {
                sb.append(" , USER_EMALL = '" + data.getUserEmall() + "'");
            }
            if (data.getOfficePhone() != null) {
                sb.append(" , OFFICE_PHONE = '" + data.getOfficePhone() + "'");
            }
            if (data.getMobilePhone() != null) {
                sb.append(" , MOBILE_PHONE = '" + data.getMobilePhone() + "'");
            }

            if (data.getDelFlg() != 0) {
                sb.append(" ,del_flg = 1 ");
            } else {
                sb.append(" ,del_flg = 0 ");
            }
            if (data.getIsUpload() != 0) {
                sb.append(" ,IS_UPLOAD = 1 ");
            } else {
                sb.append(" ,IS_UPLOAD = 0 ");
            }
            if (data.getRemark() != null) {
                sb.append(" , REMARK == '" + data.getRemark() + "'");
            }
            if (data.getUpdateUserId() != null) {
                sb.append(" , update_user_id = '" + data.getUpdateUserId() + "'");
            }
            sb.append(" where USER_ID = '" + data.getUserId() + "'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 通过ID查询一条数据
    public CompanyCollierUser queryById(int id) {
        CompanyCollierUser companyCollierUser = null;
        try {
            companyCollierUser = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyCollierUser;
    }

    //根据用户名和密码查询用户的userId,用于MainActivity中本地用户切换账号之后还能收到推送数据.zhangwenping
    public List<CompanyCollierUser> queryByLoginName(String loginName, String password) {
        List<CompanyCollierUser> companyCollierUser = null;
        try {
            companyCollierUser = dao.queryBuilder().where().eq("LOGIN_NAME", loginName).and().eq("LOGIN_PASSWORD", password).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyCollierUser;
    }


    // 通过用户ID查询一条数据
    public List<CompanyCollierUser> queryByUserId(String userId) {
        List<CompanyCollierUser> companyCollierUsers = new ArrayList<CompanyCollierUser>();
        try {
            companyCollierUsers = dao.queryBuilder().where().eq("USER_ID", userId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companyCollierUsers;
    }

    //本地登录方法
    public List<CompanyCollierUser> localLogin(String loginName, String loginPassWord) {
        List<CompanyCollierUser> companyCollierUsers = new ArrayList
                <CompanyCollierUser>();
        try {
            companyCollierUsers = dao.queryBuilder().where().eq("LOGIN_NAME", loginName).and()
                    .eq("LOGIN_PASSWORD", loginPassWord).and()
                    .eq("del_flg", 0).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyCollierUsers;
    }

    //未上传成功的集合
    public List<CompanyCollierUser> selectUploadData() {
        List<CompanyCollierUser> fList = new ArrayList<CompanyCollierUser>();
        try {
            fList = dao.queryBuilder().where().eq("IS_UPLOAD", 1).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    //循环更新
    public void updateIsUpload(List<CompanyCollierUser> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                CompanyCollierUser companyCollierUser = list.get(i);
                StringBuffer sb = new StringBuffer(" update COMPANY_COLLIERY_USER set IS_UPLOAD = 0 ");
                sb.append(" where USER_ID = '" + companyCollierUser.getUserId() + "'");
                dao.updateRaw(sb.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据部门ID查询用户
    public List<CompanyCollierUser> queryByDeptId(String id) {
        List<CompanyCollierUser> fList = new ArrayList<CompanyCollierUser>();
        try {
            fList = dao.queryBuilder().orderBy("SEQ", true).where().eq("DEPARYMENT_ID", id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }

    // 查询全部集合
    public List<CompanyCollierUser> queryAll() {
        List<CompanyCollierUser> fList = new ArrayList<CompanyCollierUser>();
        try {
            fList = dao.queryForAll();
//            for (int i = 0; i <fList.size() ; i++) {
//                new CompanycollierDeptDao(context).queryById(fList.get(i).getDeparymentId())
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fList;
    }
//    // 通过条件查询文章集合（通过用户ID查找）
//    public List<ArticleBean> queryByUserId(int user_id) {
//        try {
//            return dao.queryBuilder().where().eq(ArticleBean.COLUMNNAME_USER, user_id).query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    // 修改用户状态
    public void updateStatus(CompanyCollierUser data, int type) {
        try {
            StringBuffer sb = new StringBuffer("update COMPANY_COLLIERY_USER set ");
            if (type == 4 || type == 5) {
                if (data.getStatus() != 0) {
                    sb.append(" STATUS = 1 ");
                } else {
                    sb.append(" STATUS = 0 ");
                }
            }
            if (type == 14) {
                if (data.getLoginPassword() != null) {
                    sb.append(" LOGIN_PASSWORD = '" + data.getLoginPassword() + "' ");
                } else {
                    sb.append(" LOGIN_PASSWORD = '" + data.getLoginPassword() + "' ");
                }
            }
            if (type == 15) {
                if (data.getDelFlg() != 0) {
                    sb.append(" del_flg = 1 ");
                } else {
                    sb.append(" del_flg = 0 ");
                }
            }
            sb.append(" where USER_ID = '" + data.getUserId() + "'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 修改用户权限
    public void updateUserAuthority(CompanyCollierUser data) {
        try {
            StringBuffer sb = new StringBuffer("update COMPANY_COLLIERY_USER set ");
            if (data.getPostPoneAuthority() != null) {
                sb.append(" postPoneAuthority = '" + data.getPostPoneAuthority() + "' ");
            }
            if (data.getExaltAuthority() != null) {
                sb.append(" ,exaltAuthority = '" + data.getExaltAuthority() + "' ");
            }
            if (data.getAssignAuthority() != null) {
                sb.append(" ,assignAuthority = '" + data.getAssignAuthority() + "' ");
            }
            if (data.getCancelAuthority() != null) {
                sb.append(" ,cancelAuthority = '" + data.getCancelAuthority() + "' ");
            }
            if (data.getReformAuthority() != null) {
                sb.append(" ,reformAuthority = '" + data.getReformAuthority() + "' ");
            }
            if (data.getSuperviseAuthority() != null) {
                sb.append(" ,superviseAuthority = '" + data.getSuperviseAuthority() + "' ");
            }
            if (data.getAcceptAuthority() != null) {
                sb.append(" ,acceptAuthority = '" + data.getAcceptAuthority() + "' ");
            }
            sb.append(" where USER_ID = '" + data.getUserId() + "'");
            dao.updateRaw(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}