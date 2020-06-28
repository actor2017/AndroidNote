/**
 * 卧龙924544835@qq.com
 */
package com.princeframework.jlightspeed.modules.bms.entity;

import org.hibernate.validator.constraints.Length;
import com.princeframework.jlightspeed.common.persistence.DataEntity;

/**
 * 图片存储服务器Entity
 * @author liwenjie
 * @version 2017-12-25
 */
public class BmsImageServer extends DataEntity<BmsImageServer> {
	
	private static final long serialVersionUID = 1L;
	private String imageServerId;		// 服务器 ID
	private String indexCode;		// 服务器编号
	private String imageServerName;		// 服务器名称
	private String controlunitIndexcode;		// 所属组织编号
	private String controlUnitId;		// 组织机构 id(已无效)
	private String ipAddr;		// ip 地址
	private String dataPort;		// 数据端口
	private String controlPort;		// 控制端口
	private String netPort;		// 网管端口
	private String netZoneId;		// 网域 ID
	private String storetype;		// 存储类型 20015：图片 服 务 器 20008 ：CVR 存储， 20028：云存储
	private String username;		// 用户名,CVR 和云存储需要
	private String password;		// 密码,CVR 和云存储需要
	private String storedisk;		// 录像池 Id
	private String cascadeId;		// 级联 ID
	private String viewPort;		// 数据访问端口
	
	public BmsImageServer() {
		super();
	}

	public BmsImageServer(String id){
		super(id);
	}

	public String getImageServerId() {
		return imageServerId;
	}

	public void setImageServerId(String imageServerId) {
		this.imageServerId = imageServerId;
	}
	
	@Length(min=1, max=64, message="服务器编号长度必须介于 1 和 64 之间")
	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	
	@Length(min=1, max=64, message="服务器名称长度必须介于 1 和 64 之间")
	public String getImageServerName() {
		return imageServerName;
	}

	public void setImageServerName(String imageServerName) {
		this.imageServerName = imageServerName;
	}
	
	@Length(min=1, max=32, message="所属组织编号长度必须介于 1 和 32 之间")
	public String getControlunitIndexcode() {
		return controlunitIndexcode;
	}

	public void setControlunitIndexcode(String controlunitIndexcode) {
		this.controlunitIndexcode = controlunitIndexcode;
	}
	
	@Length(min=0, max=64, message="组织机构 id(已无效)长度必须介于 0 和 64 之间")
	public String getControlUnitId() {
		return controlUnitId;
	}

	public void setControlUnitId(String controlUnitId) {
		this.controlUnitId = controlUnitId;
	}
	
	@Length(min=1, max=64, message="ip 地址长度必须介于 1 和 64 之间")
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
	@Length(min=1, max=64, message="数据端口长度必须介于 1 和 64 之间")
	public String getDataPort() {
		return dataPort;
	}

	public void setDataPort(String dataPort) {
		this.dataPort = dataPort;
	}
	
	@Length(min=0, max=64, message="控制端口长度必须介于 0 和 64 之间")
	public String getControlPort() {
		return controlPort;
	}

	public void setControlPort(String controlPort) {
		this.controlPort = controlPort;
	}
	
	@Length(min=0, max=64, message="网管端口长度必须介于 0 和 64 之间")
	public String getNetPort() {
		return netPort;
	}

	public void setNetPort(String netPort) {
		this.netPort = netPort;
	}
	
	@Length(min=1, max=64, message="网域 ID长度必须介于 1 和 64 之间")
	public String getNetZoneId() {
		return netZoneId;
	}

	public void setNetZoneId(String netZoneId) {
		this.netZoneId = netZoneId;
	}
	
	@Length(min=0, max=64, message="存储类型 20015：图片 服 务 器 20008 ：CVR 存储， 20028：云存储长度必须介于 0 和 64 之间")
	public String getStoretype() {
		return storetype;
	}

	public void setStoretype(String storetype) {
		this.storetype = storetype;
	}
	
	@Length(min=0, max=255, message="用户名,CVR 和云存储需要长度必须介于 0 和 255 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=255, message="密码,CVR 和云存储需要长度必须介于 0 和 255 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=64, message="录像池 Id长度必须介于 0 和 64 之间")
	public String getStoredisk() {
		return storedisk;
	}

	public void setStoredisk(String storedisk) {
		this.storedisk = storedisk;
	}
	
	@Length(min=0, max=64, message="级联 ID长度必须介于 0 和 64 之间")
	public String getCascadeId() {
		return cascadeId;
	}

	public void setCascadeId(String cascadeId) {
		this.cascadeId = cascadeId;
	}
	
	@Length(min=0, max=64, message="数据访问端口长度必须介于 0 和 64 之间")
	public String getViewPort() {
		return viewPort;
	}

	public void setViewPort(String viewPort) {
		this.viewPort = viewPort;
	}

}