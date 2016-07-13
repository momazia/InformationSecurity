package com.otp.controller.form;

/**
 * Login form
 * 
 * @author Max
 *
 */
public class LoginForm {

    private String email;
    private String password;

    public LoginForm() {
    }

    public LoginForm(String emailAddress) {
	this.email = emailAddress;
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

}
