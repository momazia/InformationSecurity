package com.otp.datastore.entity;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.otp.datastore.Kind;

@com.otp.datastore.Entity(kind = Kind.USER)
@Entity(name = "USER")
@Cache
public class User {

	@com.otp.datastore.Id
	@Id
	@Index
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private List<String> roles;
	private Date createdOn;

	public User() {
	}

	public User(String username, String password, String firstName, String lastName, List<String> roles, Date createdOn) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.createdOn = createdOn;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}