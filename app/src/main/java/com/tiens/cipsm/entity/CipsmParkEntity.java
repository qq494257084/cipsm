package com.tiens.cipsm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 园区表
 *
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:23
 */
public class CipsmParkEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    private String parkId;
    /**
     * 园区名称
     */
    private String parkName;
    /**
     * 经度
     */
    private String parkLongitude;
    /**
     * 纬度
     */
    private String parkLatitude;
    /**
     * 园区面积
     */
    private Float parkArea;
    /**
     * 联系人
     */
    private String parkContact;
    /**
     * 联系人电话
     */
    private String parkContactPhone;
    /**
     * 园区形状
     */
    private String parkShape;
    /**
     * 园区状态
     */
    private Integer parkStatus;
    /**
     * 创建时间
     */
    private Date parkCreateTime;
    /**
     * 修改时间
     */
    private Date parkUpdateTime;
    /**
     * 所属企业
     */
    private String parkEnterprise;

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkLongitude() {
        return parkLongitude;
    }

    public void setParkLongitude(String parkLongitude) {
        this.parkLongitude = parkLongitude;
    }

    public String getParkLatitude() {
        return parkLatitude;
    }

    public void setParkLatitude(String parkLatitude) {
        this.parkLatitude = parkLatitude;
    }

    public Float getParkArea() {
        return parkArea;
    }

    public void setParkArea(Float parkArea) {
        this.parkArea = parkArea;
    }

    public String getParkContact() {
        return parkContact;
    }

    public void setParkContact(String parkContact) {
        this.parkContact = parkContact;
    }

    public String getParkContactPhone() {
        return parkContactPhone;
    }

    public void setParkContactPhone(String parkContactPhone) {
        this.parkContactPhone = parkContactPhone;
    }

    public String getParkShape() {
        return parkShape;
    }

    public void setParkShape(String parkShape) {
        this.parkShape = parkShape;
    }

    public Integer getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(Integer parkStatus) {
        this.parkStatus = parkStatus;
    }

    public Date getParkCreateTime() {
        return parkCreateTime;
    }

    public void setParkCreateTime(Date parkCreateTime) {
        this.parkCreateTime = parkCreateTime;
    }

    public Date getParkUpdateTime() {
        return parkUpdateTime;
    }

    public void setParkUpdateTime(Date parkUpdateTime) {
        this.parkUpdateTime = parkUpdateTime;
    }

    public String getParkEnterprise() {
        return parkEnterprise;
    }

    public void setParkEnterprise(String parkEnterprise) {
        this.parkEnterprise = parkEnterprise;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
