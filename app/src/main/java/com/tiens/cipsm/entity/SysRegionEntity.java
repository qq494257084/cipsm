package com.tiens.cipsm.entity;

import java.io.Serializable;

/**
 * 行政区划信息表
 * 
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:21
 */
public class SysRegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private Integer id;
	/**
	 * 行政区划编码
	 */
	private String code;
	/**
	 * 行政区划名称
	 */
	private String name;
	/**
	 * 上级节点id
	 */
	private Integer parent;
	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 关键词
	 */
	private String keyword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
