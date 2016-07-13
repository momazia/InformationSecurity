package com.otp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.opt.model.User;
import com.opt.model.UserRole;
import com.opt.model.constant.Role;
import com.otp.dao.UserDAOImpl;
import com.otp.util.PasswordUtils;

/**
 * Takes care of all the user related operations
 * 
 * @author Max
 *
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAOImpl userDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Deprecated
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		com.otp.datastore.entity.User registeredUser = userDao.datastoreUserByUsername(username);
		User user = null;
		if (registeredUser != null) {
			user = new User();
			user.setFirstName(registeredUser.getFirstName());
			user.setLastName(registeredUser.getLastName());
			user.setUsername(registeredUser.getUsername());
			user.setPassword(registeredUser.getPassword());
			user.setAuthorities(getAuthorities(registeredUser.getRoles()));
		}
		return user;
	}

	/**
	 * Gets the user information using the username passed.
	 * 
	 * @param username
	 * @return
	 */
	public User get(String username) {
		com.otp.datastore.entity.User registeredUser = userDao.loadUserByUsername(username);
		User user = null;
		if (registeredUser != null) {
			user = new User();
			user.setFirstName(registeredUser.getFirstName());
			user.setLastName(registeredUser.getLastName());
			user.setUsername(registeredUser.getUsername());
			user.setPassword(registeredUser.getPassword());
			user.setAuthorities(getAuthorities(registeredUser.getRoles()));
		}
		return user;
	}

	/**
	 * Converts the given list of roles into UserRoles
	 * 
	 * @param roles
	 * @return
	 */
	private List<UserRole> getAuthorities(List<String> roles) {
		List<UserRole> result = new ArrayList<>();
		for (String role : roles) {
			result.add(new UserRole(Role.valueOf(role)));
		}
		return result;
	}

	/**
	 * Creates a user with a default role.
	 * 
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param fbId
	 */
	public void createUser(String email, String firstName, String lastName, String password) {
		saveUser(email, firstName, lastName, password);
	}

	private void saveUser(String email, String firstName, String lastName, String password) {
		List<String> roles = Arrays.asList(Role.STANDARD_USER.name());
		Date createdOn = new Date();
		String encodedPassword = PasswordUtils.getInstance().encode(password);
		userDao.saveUser(email, firstName, lastName, encodedPassword, roles, createdOn);
	}

	/**
	 * Updates the user information
	 * 
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param password
	 */
	public void updateUser(String email, String firstName, String lastName, String password) {
		com.otp.datastore.entity.User existingUser = userDao.loadUserByUsername(email);
		String newPassword = StringUtils.isBlank(password) ? existingUser.getPassword() : PasswordUtils.getInstance().encode(password);
		userDao.saveUser(email, firstName, lastName, newPassword, existingUser.getRoles(), existingUser.getCreatedOn());
	}

	/**
	 * Returns true if the user name is already registered in the system.
	 * 
	 * @param email
	 * @return
	 */
	public boolean isUserRegistered(String email) {
		return userDao.isUserRegistered(email);
	}

}
