package com.nkay.swyt.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * 自定义SQLiteOpenHelper
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	public static int version = 1;

	// 数据库名称
	public static final String DB_NAME = Environment.getExternalStorageDirectory() + "/swyt.db";

	//table name
	//企业煤矿表
	public static final String T_COMPANY_COLLIERY = "CompanyColliery";
	//用户表
	public static final String T_COMPANY_COLLIERY_USER = "COMPANY_COLLIERY_USER";
	//部门表
	public static final String T_COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT = "COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT";
	//系统编码表
	public static final String T_SYSTEM_CODE_MASTER = "SYSTEM_CODE_MASTER";
	//班次表
	public static final String T_BZH_COLLIERY_CLASSES = "BZH_COLLIERY_CLASSES";
	//现场检查管理
	public static final String T_FJGK_SCECE_CHECK = "FJGK_SCECE_CHECK";
	//现场检查风险列表
	public static final String T_FJGK_SCENECHECK_DANGER = "FJGK_SCENECHECK_DANGER";
	//安全风险清单
	public static final String T_FJGK_DANGER_LIST = "FJGK_DANGER_LIST";

	//create  table
	private static final String CREARE_T_COMPANY_COLLIERY_USER = "CREATE TABLE "+T_COMPANY_COLLIERY_USER
			+"(USER_ID 					text,"
			+"SUBJECTION 				text,"
			+"DEPARYMENT_ID		    	text,"
			+"LOGIN_NAME				text,"
			+"LOGIN_PASSWORD			text,"
			+"HPIC_URL					text,"
			+"USER_NAME					text,"
			+"USER_IDCARD				text,"
			+"USER_EMALL				text,"
			+"OFFICE_PHONE				text,"
			+"MOBILE_PHONE				text,"
			+"STATUS		  	        integer,"
			+"REMARK			        text,"
			+"DEL_FLG					integer,"
			+"INSERT_USER_ID	    	text,"
			+"INSERT_DATETIME			text,"
			+"UPDATE_USER_ID			text,"
			+"UPDATE_DATETIME			text"
			+")";

	private static final String CREARE_T_COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT = "CREATE TABLE "+T_COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT
			+"(DEPARTMENT_MANAGEMENT_ID 	text,"
			+"COMPANY_COLLIERY_ID 			text,"
			+"DEPARTMENT_NAME		    	text,"
			+"LEADER						text,"
			+"PHONE							text,"
			+"TELEPHONENUMBER				text,"
			+"REMARK				   	    text,"
			+"DEL_FLG					   	integer,"
			+"INSERT_USER_ID	    		text,"
			+"INSERT_DATETIME				text,"
			+"UPDATE_USER_ID				text,"
			+"UPDATE_DATETIME				text"
			+")";

	private static final String CREARE_T_SYSTEM_CODE_MASTER = "CREATE TABLE "+T_SYSTEM_CODE_MASTER
			+"(system_code_master_id   	    integer,"
			+"code_type 					text,"
			+"code_1		    			text,"
			+"code_2						text,"
			+"code_full_name			    text,"
			+"code_name						text,"
			+"code_value_1				   	text,"
			+"code_value_2				   	text,"
			+"DEL_FLG					   	integer,"
			+"INSERT_USER_ID	    		text,"
			+"INSERT_DATETIME				text,"
			+"UPDATE_USER_ID				text,"
			+"UPDATE_DATETIME				text"
			+")";

	private static final String CREARE_T_BZH_COLLIERY_CLASSES = "CREATE TABLE "+T_BZH_COLLIERY_CLASSES
			+"(DUTY_ORDER_ID   	    		text,"
			+"COMPANY_COLLIERY_ID 			text,"
			+"DUTY_ORDER_NAME		    	text,"
			+"REMARK				   	    text,"
			+"DEL_FLG					   	integer,"
			+"INSERT_USER_ID	    		text,"
			+"INSERT_DATETIME				text,"
			+"UPDATE_USER_ID				text,"
			+"UPDATE_DATETIME				text"
			+")";



	public SQLiteHelper(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREARE_T_COMPANY_COLLIERY_USER);
		db.execSQL(CREARE_T_COMPANYCOLLIERY_DEPARTMENT_MANAGEMENT);
		db.execSQL(CREARE_T_SYSTEM_CODE_MASTER);
		db.execSQL(CREARE_T_BZH_COLLIERY_CLASSES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
