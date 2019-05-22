package com.tiens.cipsm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 监测点表
 * 
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:23
 */
public class CipsmSensorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * UUID
	 */
	private String sensorId;
	/**
	 * 监测点名称
	 */
	private String sensorNames;
	/**
	 * 经度
	 */
	private String sensorLongitude;
	/**
	 * 纬度
	 */
	private String sensorLatitude;
	/**
	 * 监测点类型
	 */
	private Integer sensorType;
	/**
	 * 设备名称
	 */
	private String sensorName;
	/**
	 * 设备型号
	 */
	private String sensorCode;
	/**
	 * 阈值
	 */
	private String sensorThreshold;
	/**
	 * 所属园区
	 */
	private String sensorParkId;
	/**
	 * 创建用户
	 */
	private String sensorCreateUser;
	/**
	 * 修改用户
	 */
	private String sensorUpdateUser;
	/**
	 * 创建时间
	 */
	private Date sensorCreateTime;
	/**
	 * 修改时间
	 */
	private Date sensorUpdateTime;
	/**
	 * 监测点状态
	 */
	private Integer sensorStatus;

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorNames() {
		return sensorNames;
	}

	public void setSensorNames(String sensorNames) {
		this.sensorNames = sensorNames;
	}

	public String getSensorLongitude() {
		return sensorLongitude;
	}

	public void setSensorLongitude(String sensorLongitude) {
		this.sensorLongitude = sensorLongitude;
	}

	public String getSensorLatitude() {
		return sensorLatitude;
	}

	public void setSensorLatitude(String sensorLatitude) {
		this.sensorLatitude = sensorLatitude;
	}

	public Integer getSensorType() {
		return sensorType;
	}

	public void setSensorType(Integer sensorType) {
		this.sensorType = sensorType;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorCode() {
		return sensorCode;
	}

	public void setSensorCode(String sensorCode) {
		this.sensorCode = sensorCode;
	}

	public String getSensorThreshold() {
		return sensorThreshold;
	}

	public void setSensorThreshold(String sensorThreshold) {
		this.sensorThreshold = sensorThreshold;
	}

	public String getSensorParkId() {
		return sensorParkId;
	}

	public void setSensorParkId(String sensorParkId) {
		this.sensorParkId = sensorParkId;
	}

	public String getSensorCreateUser() {
		return sensorCreateUser;
	}

	public void setSensorCreateUser(String sensorCreateUser) {
		this.sensorCreateUser = sensorCreateUser;
	}

	public String getSensorUpdateUser() {
		return sensorUpdateUser;
	}

	public void setSensorUpdateUser(String sensorUpdateUser) {
		this.sensorUpdateUser = sensorUpdateUser;
	}

	public Date getSensorCreateTime() {
		return sensorCreateTime;
	}

	public void setSensorCreateTime(Date sensorCreateTime) {
		this.sensorCreateTime = sensorCreateTime;
	}

	public Date getSensorUpdateTime() {
		return sensorUpdateTime;
	}

	public void setSensorUpdateTime(Date sensorUpdateTime) {
		this.sensorUpdateTime = sensorUpdateTime;
	}

	public Integer getSensorStatus() {
		return sensorStatus;
	}

	public void setSensorStatus(Integer sensorStatus) {
		this.sensorStatus = sensorStatus;
	}
}
