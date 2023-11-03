package com.example.btl.web;

public class User {
	private String account;
	private String password;
	private String email;
	
public User(String account,String password , String email) {
	this.account=account;
	this.password=password;
	this.email=email;
}

public User() {
}
public String getAccount() {
	return account;
}
public String getEmail() {
	return email;
}
public String getPassword() {
	return password;
}
public void setAccount(String account) {
	this.account = account;
}
public void setEmail(String email) {
	this.email = email;
}
public void setPassword(String password) {
	this.password = password;
}
}