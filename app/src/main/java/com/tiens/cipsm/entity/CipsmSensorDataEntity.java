package com.tiens.cipsm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 监测点数据表
 * 
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:22
 */
public class CipsmSensorDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer dataId;
	/**
	 * 监测点ID
	 */
	private String dataSensorId;
	/**
	 * 创建时间
	 */
	private Date dataCreateTime;
	/**
	 * 数据1
	 */
	private Float data1;
	/**
	 * 数据2
	 */
	private String data2;
	/**
	 * 数据3
	 */
	private String data3;
	/**
	 * 数据4
	 */
	private String data4;
	/**
	 * 数据5
	 */
	private String data5;

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getDataSensorId() {
		return dataSensorId;
	}

	public void setDataSensorId(String dataSensorId) {
		this.dataSensorId = dataSensorId;
	}

	public Date getDataCreateTime() {
		return dataCreateTime;
	}

	public void setDataCreateTime(Date dataCreateTime) {
		this.dataCreateTime = dataCreateTime;
	}

	public Float getData1() {
		return data1;
	}

	public void setData1(Float data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public String getData4() {
		return data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}
}
