package com.otp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.otp.datastore.Kind;
import com.otp.datastore.entity.User;
import com.otp.logger.CustomLogger;
import com.otp.service.DatastoreService;

/**
 * DAO for user related operations
 * 
 * @author Max
 *
 */
@Repository
public class UserDAOImpl extends DAOImpl {

	@Autowired
	private DatastoreService datastoreService;

	private static final CustomLogger LOG = CustomLogger.getLogger(UserDAOImpl.class.getName());

	/**
	 * Loads the user based on the username
	 * 
	 * @param userName
	 * @return
	 */
	public User datastoreUserByUsername(final String userName) {
		try {
			return (User) datastoreService.get(Kind.USER, userName);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
			LOG.error("Cannot find the user with username [" + userName + "], returning null.", e);
		} catch (EntityNotFoundException e) {
			LOG.info("Cannot find the user with username [" + userName + "], returning null: " + e.getMessage());
		}
		return null;

	}

	/**
	 * Loads the user based on the username
	 * 
	 * @param userName
	 * @return
	 */
	public User loadUserByUsername(final String userName) {
		return ObjectifyService.ofy().load().key(Key.create(User.class, userName)).now();
	}

	/**
	 * Saves the information given into user kind.
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param fbId
	 * @param useFbPhoto
	 */
	public void saveUser(String userName, String firstName, String lastName, String password, List<String> roles, Date createdOn) {
		ObjectifyService.ofy().save().entity(new User(userName, password, firstName, lastName, roles, createdOn)).now();
	}

	/**
	 * Saves the given user into the database.
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		ObjectifyService.ofy().save().entity(user).now();
	}

	/**
	 * Returns true if the user name is already registered in the system.
	 * 
	 * @param userName
	 * @return
	 */
	public boolean isUserRegistered(String userName) {
		return loadUserByUsername(userName) != null;
	}

}
