package com.security.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.security.model.AccessControl;

/**
 * Access Control List utility class
 * 
 * @author Max
 *
 */
public class ACL {

	private List<AccessControl> accessControls;
	private List<String> users;

	/**
	 * The main constructor to use for initializing the values.
	 * 
	 * @param numOfUsers
	 * @param folderName
	 */
	public ACL(int numOfUsers, String folderName) {
		// Construct n number of users
		List<String> users = createUsers(numOfUsers);
		this.users = users;

		// Reading all the existing files in the folderName
		List<String> fileNames = getFiles(folderName);

		// Construct access controls
		setAccessControls(constructAccessControls(users, fileNames));
	}

	/**
	 * Creates access control values randomly using the user names and file
	 * names given.
	 * 
	 * @param users
	 * @param fileNames
	 * @return
	 */
	private List<AccessControl> constructAccessControls(List<String> users, List<String> fileNames) {
		List<AccessControl> result = new ArrayList<>();
		for (String fileName : fileNames) {
			AccessControl accessControl = new AccessControl();
			accessControl.setFileName(fileName);
			accessControl.setUserAccesses(createUserAccessList(users));
			result.add(accessControl);
		}
		return result;
	}

	/**
	 * Creates user access list for each file using the random list of users
	 * passed.
	 * 
	 * @param users
	 * @return
	 */
	private Map<String, Integer> createUserAccessList(List<String> users) {
		// Generating a random number for number of users having access to the
		// file.
		int numOfUsers = new Random().nextInt(users.size());
		Map<String, Integer> result = new HashMap<>(numOfUsers);
		for (int i = 0; i < numOfUsers; i++) {
			// Generating a random index number for the users. It could be that
			// we overwrite an existing user, which is fine.
			int userIndex = new Random().nextInt(users.size());
			String userName = users.get(userIndex);
			Integer accessRight = createRandomRight();
			result.put(userName, accessRight);
		}
		return result;
	}

	/**
	 * Considering the following rights: <br>
	 * READ: 1 WRITE: 2 EXECUTE: 4 <br>
	 * The method creates a number between 1 to 7.
	 * 
	 * @return
	 */
	private int createRandomRight() {
		return 1 + new Random().nextInt(7);
	}

	/**
	 * Default constructor to be used in unit test.
	 */
	public ACL() {

	}

	/**
	 * Gets the list of files in the folder name passed.
	 * 
	 * @param folderName
	 * @return
	 */
	public List<String> getFiles(String folderName) {
		List<String> result = new ArrayList<>();
		File folderFile = new File(folderName);
		for (File file : folderFile.listFiles()) {
			result.add(file.getName());
		}
		return result;
	}

	/**
	 * Creates n number of users based on the parameter passed using the
	 * following format: user1, user2 ...
	 * 
	 * @param numOfUsers
	 * @return
	 */
	public List<String> createUsers(int numOfUsers) {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < numOfUsers; i++) {
			result.add("user" + (i + 1));
		}
		return result;
	}

	public List<AccessControl> getAccessControls() {
		return accessControls;
	}

	public void setAccessControls(List<AccessControl> accessControls) {
		this.accessControls = accessControls;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

}
