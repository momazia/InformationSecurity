package com.otp.controller.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Sign up model
 * 
 * @author Max
 *
 */
public class UserForm {

	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	@Email
	private String email;
	private String password;
	private String reenterPassword;

	public UserForm() {
	}

	public UserForm(String emailAddress) {
		this.email = emailAddress;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReenterPassword() {
		return reenterPassword;
	}

	public void setReenterPassword(String reenterPassword) {
		this.reenterPassword = reenterPassword;
	}

}
