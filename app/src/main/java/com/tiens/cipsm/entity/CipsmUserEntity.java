package com.tiens.cipsm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:21
 */
public class CipsmUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 住址
     */
    private String userAddress;
    /**
     * 所属企业
     */
    private String userEnterprise;
    /**
     * 创建时间
     */
    private Date userCreateTime;
    /**
     * 修改时间
     */
    private Date userUpdateTime;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 标志码
     */
    private String mark;
    /**
     * 用户状态
     */
    private Integer userStatus;
    /**
     * 用户类别
     */
    private Integer userType;
    private Integer parkFlag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEnterprise() {
        return userEnterprise;
    }

    public void setUserEnterprise(String userEnterprise) {
        this.userEnterprise = userEnterprise;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Date getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(Date userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getParkFlag() {
        return parkFlag;
    }

    public void setParkFlag(Integer parkFlag) {
        this.parkFlag = parkFlag;
    }
}

