package com.princeframework.jlightspeed.task;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.princeframework.jlightspeed.common.utils.myUtil.ReflectUtil;
import com.princeframework.jlightspeed.modules.bms.entity.BmsImageServer;

/**
 * 卡口数据采集定时任务
 * @author Administrator
 *
 */
public class BmsCrossSyncTask {
	
	@Test
	public void main() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("IMAGE_SERVER_ID","1");
		jsonObject.put("CASCADE_ID","2");
		jsonObject.put("CONTROL_PORT","3");
		jsonObject.put("CONTROL_UNIT_ID","4");
		jsonObject.put("CONTROLUNIT_INDEXCODE","5");
		jsonObject.put("DATA_PORT","6");
		jsonObject.put("INDEX_CODE","7");
		jsonObject.put("IP_ADDR","8");
		jsonObject.put("IMAGE_SERVER_NAME","9");
		jsonObject.put("NET_PORT","10");
		jsonObject.put("NET_ZONE_ID","11");
		jsonObject.put("PASSWORD","12");
		jsonObject.put("STOREDISK","13");
		jsonObject.put("STORETYPE","14");
		jsonObject.put("USERNAME","15");
		jsonObject.put("VIEW_PORT","16");
		
		
	    BmsImageServer bis = JSONObject.toJavaObject(jsonObject, BmsImageServer.class);
		
//		BmsImageServer bis = ReflectUtil.getKeyAndValue(jsonObject,BmsImageServer.class);
		System.out.println(bis);
		//打印结果:
		//com.princeframework.jlightspeed.modules.bms.entity.BmsImageServer@26be92ad[imageServerId=1,indexCode=7,
		//imageServerName=9,controlunitIndexcode=5,controlUnitId=4,ipAddr=8,dataPort=6,controlPort=3,netPort=10,
		//netZoneId=11,storetype=14,username=15,password=12,storedisk=13,cascadeId=2,viewPort=16,remarks=<null>,
		//createBy=<null>,createDate=<null>,updateBy=<null>,updateDate=<null>,delFlag=0,id=<null>,currentUser=<null>,
		//page=<null>,sqlMap=<null>,isNewRecord=false]
	}
	
}
