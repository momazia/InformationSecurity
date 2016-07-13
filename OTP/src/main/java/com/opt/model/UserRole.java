package com.opt.model;

import org.springframework.security.core.GrantedAuthority;

import com.opt.model.constant.Role;

/**
 * System user role
 * 
 * @author Max
 *
 */
public class UserRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2195045660477833723L;
	private Role role;

	public UserRole(Role role) {
		setRole(role);
	}

	@Deprecated
	public String getAuthority() {
		return this.role == null ? null : this.role.name();
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return this.role;
	}
}
