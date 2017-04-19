package com.mkyong.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "info")
public class Info implements java.io.Serializable {

	
private long id;
private String companyName;
private String empName;
private Date  joinDate;

private Integer numberOfDocuments;


@Id
@GeneratedValue
@Column(name = "id")
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

@Column(name = "CompanyName")
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

@Column(name = "joindate")
public Date getJoinDate() {
	return joinDate;
}
public void setJoinDate(Date joinDate) {
	this.joinDate = joinDate;
}

@Column(name = "empName")
public String getEmpName() {
	return empName;
}
public void setEmpName(String empName) {
	this.empName = empName;
}

@Column(name = "numberofdoc")
public Integer getNumberOfDocuments() {
	return numberOfDocuments;
}
public void setNumberOfDocuments(Integer numberOfDocuments) {
	this.numberOfDocuments = numberOfDocuments;
}


}
