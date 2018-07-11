package com.ge.sst.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_DETAILS")
public class UserDetailsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_details_id_seq")
	@SequenceGenerator(
			name="user_details_id_seq",
			sequenceName="user_details_id_seq",
			allocationSize=1
			)
	@Column(name="user_id")
	private long userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_first_name")
	private String userFirstName;
	
	@Column(name="user_last_name")
	private String userLastName;
	
	@Column(name="user_email_id")
	private String userEmailId;
	
	@Column(name="user_password")
	private String userPassword;
	
	@OneToOne()
	@JoinColumn(name="user_group_id", referencedColumnName="user_group_id")
	private UserGroupEntity userGroupId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public UserGroupEntity getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(UserGroupEntity userGroupId) {
		this.userGroupId = userGroupId;
	}
}
