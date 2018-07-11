package com.ge.sst.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_GROUP")
public class UserGroupEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_group_id_seq")
	@SequenceGenerator(
			name="user_group_id_seq",
			sequenceName="user_group_id_seq",
			allocationSize=1
			)
	@Column(name="user_group_id")
	private long userGroupId;
	
	@Column(name="user_group_name")
	private String userGroupName;

	public long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}	
}
