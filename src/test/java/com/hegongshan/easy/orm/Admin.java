package com.hegongshan.easy.orm;

import com.hegongshan.easy.orm.annotation.Column;
import com.hegongshan.easy.orm.annotation.Id;
import com.hegongshan.easy.orm.annotation.Table;

@Table
public class Admin implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String password;
	@Column
	private java.util.Date gmtModify;
	@Column
	private String signature;
	@Column
	private String imageUrl;
	@Column
	private String profile;
	@Id
	private Integer adminId;
	@Column
	private String nickname;
	@Column(allowUpdate=false)
	private java.util.Date gmtCreate;
	@Column
	private String username;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.util.Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public java.util.Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Admin [password=" + password + ", gmtModify=" + gmtModify + ", signature=" + signature + ", imageUrl="
				+ imageUrl + ", profile=" + profile + ", adminId=" + adminId + ", nickname=" + nickname + ", gmtCreate="
				+ gmtCreate + ", username=" + username + "]";
	}

}