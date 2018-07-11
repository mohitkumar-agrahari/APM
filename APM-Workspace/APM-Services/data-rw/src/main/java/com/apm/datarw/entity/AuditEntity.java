package com.apm.datarw.entity;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class AuditEntity {
	
	@Basic
	@CreatedDate
	@JsonIgnore
	@Column(name = "CREATED_AT", nullable = false, insertable = true, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "AuditEntity [createdAt=" + createdAt + "]";
	}
	
	

}
