package com.liaoin.microservice.bean.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liaoin.microservice.bean.entity.backstage.PcMenu;
import com.liaoin.microservice.bean.entity.backstage.Permisssion;
import com.liaoin.microservice.bean.entity.backstage.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户返回实体类")
public class UserDTO implements Serializable {

    @ApiModelProperty(value = "系统标识")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String imagesUrl;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "政治面貌")
    private String politicalStatus;

    @ApiModelProperty(value = "区域ID")
    private Integer regionId;

    @ApiModelProperty(value = "区域名称")
    private String regionName;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "性别0=男、1=女")
    private String sex;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "状态0=正常、1=禁用或删除")
    private Integer accountStatus;

    @ApiModelProperty(value = "用于验证数据")
    private String token;

    @ApiModelProperty(value = "所属网格系统标识")
    private Integer gridId;

    @ApiModelProperty(value = "所属网格名称")
    private String gridName;

    @ApiModelProperty(value = "网格状态0=普通成员、1=网格成员、2=网格负责人")
    private String gridStatus;

    @ApiModelProperty(value = "用户环信ID")
    private String userImId;

    @ApiModelProperty(value = "用户角色以及权限列表")
    private Role roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否管理员0=否、1=是、2=普通")
    private Integer isadmin;

    @ApiModelProperty(value = "出生年月")
    @JsonFormat(pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date birthday;

    @ApiModelProperty(value = "人员类型")
    private Integer isTwoCadre;

    @ApiModelProperty(value = "身份证")
    private String identityNumber;

    @ApiModelProperty(value = "人员类型名称")
    private String userTypeName;

    @ApiModelProperty(value = "权限列表")
    private List<Permisssion> permisssions = new ArrayList<>();

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "职务")
    private String duties;

    @ApiModelProperty(value = "关联户信息")
    private Integer familyMemberId;
    @ApiModelProperty(value = "是否工作人员")
    private Integer isStaff;

    @ApiModelProperty(value = "pc端权限控制 暂不纳入 APP")
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"status", "url", "createTime", "createUserId", "deleteTime"
            , "deleteUserId", "updateTime", "updateUserId", "roles"})
    private List<PcMenu> pcPermisssions = new ArrayList<>();

    public Integer getAge() {
        if (birthday != null) {
            age = getAgeByBirth(birthday);
        }
        return age;
    }

    public String getGridName() {
        if (StringUtils.isBlank(gridName)) {
            gridName = "暂无网格";
        }
        return gridName;
    }

    //由出生日期获得年龄
    private int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//异常后返回数据
            return age;
        }
    }

}
