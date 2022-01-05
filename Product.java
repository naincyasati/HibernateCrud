package com.crud;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.Id;
@Entity
public class Product {

@Id 

private Integer id;

private String pName;

private String cName;
public Product() {}
public Integer getId() {
	return id;
}
public String getpName() {
	return pName;
}
public String getcName() {
	return cName;
}
public void setId(Integer id) {
	this.id = id;
}
public void setpName(String pName) {
	this.pName = pName;
}
public void setcName(String cName) {
	this.cName = cName;
}

}
