package com.huiche.bean;
/**
 * 用户登录保存信息的实体类
 * @author Administrator
 *
 */
public class LoginInfo {
	public String account;//用户账号
	public int cardNo;//会员卡号
	public String city;//城市
	public String companyTel;
	public double freezeIntegral;//冻结积分
	public int id;//主键
	public double integral;
	public String  name;//昵称
	public String phone;//手机号
	public int recharge;
	public String status;//状态 0：冻结 1：正常
	public String token;
	
	
	public LoginInfo() {
		
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public double getFreezeIntegral() {
		return freezeIntegral;
	}

	public void setFreezeIntegral(double freezeIntegral) {
		this.freezeIntegral = freezeIntegral;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getIntegral() {
		return integral;
	}

	public void setIntegral(double integral) {
		this.integral = integral;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRecharge() {
		return recharge;
	}

	public void setRecharge(int recharge) {
		this.recharge = recharge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "LoginInfo [account=" + account + ", cardNo=" + cardNo
				+ ", city=" + city + ", companyTel=" + companyTel
				+ ", freezeIntegral=" + freezeIntegral + ", id=" + id
				+ ", integral=" + integral + ", name=" + name + ", phone="
				+ phone + ", recharge=" + recharge + ", status=" + status
				+ ", token=" + token + "]";
	}
	

}
