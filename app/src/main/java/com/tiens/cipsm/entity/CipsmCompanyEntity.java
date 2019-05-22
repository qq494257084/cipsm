package com.tiens.cipsm.entity;

import java.io.Serializable;

/**
 * 企业表
 *
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:20
 */
public class CipsmCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * UUID
	 */
	private String companyUuid;
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 企业编号
	 */
	private String companyCode;
	/**
	 * 用于管理员注册HASH
	 */
	private String companyHash;
	/**
	 * 法定代表人
	 */
	private String companyContact;
	/**
	 * 法定代表人联系方式
	 */
	private String companyContactPhone;
	/**
	 * 企业地址
	 */
	private String companyAddress;

	public String getCompanyUuid() {
		return companyUuid;
	}

	public void setCompanyUuid(String companyUuid) {
		this.companyUuid = companyUuid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyHash() {
		return companyHash;
	}

	public void setCompanyHash(String companyHash) {
		this.companyHash = companyHash;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	public String getCompanyContactPhone() {
		return companyContactPhone;
	}

	public void setCompanyContactPhone(String companyContactPhone) {
		this.companyContactPhone = companyContactPhone;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
