package com.tiens.cipsm.entity;

import java.io.Serializable;

/**
 * 字典表
 * 
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:21
 */
public class SysDictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer dictId;
	/**
	 * 类型
	 */
	private String dictType;
	/**
	 * 类型名称
	 */
	private String dictName;
	/**
	 * 代码
	 */
	private Integer dictCode;
	/**
	 * 状态
	 */
	private Integer dictStatus;

	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public Integer getDictCode() {
		return dictCode;
	}

	public void setDictCode(Integer dictCode) {
		this.dictCode = dictCode;
	}

	public Integer getDictStatus() {
		return dictStatus;
	}

	public void setDictStatus(Integer dictStatus) {
		this.dictStatus = dictStatus;
	}
}
