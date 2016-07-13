package com.otp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordUtils {

	private static StandardPasswordEncoder encoder = new StandardPasswordEncoder("{XuY&@(ruz;a5hgr");
	private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}$";
	private static final int MINIMUM_PASS_LENGTH = 8;
	private static final Map<Integer, String> CHARACTER_GROUPS;

	static {
		CHARACTER_GROUPS = new HashMap<>();
		CHARACTER_GROUPS.put(0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		CHARACTER_GROUPS.put(1, "abcdefghijklmnopqrstuvwxyz");
		CHARACTER_GROUPS.put(2, "0123456789");
		CHARACTER_GROUPS.put(3, "@#$%^&+=_");
	}

	/**
	 * static Singleton instance
	 */
	private static PasswordUtils instance;

	/**
	 * Private constructor for singleton
	 */
	private PasswordUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static PasswordUtils getInstance() {
		if (instance == null) {
			instance = new PasswordUtils();
		}
		return instance;
	}

	/**
	 * Encodes the given password
	 * 
	 * @param password
	 * @return
	 */
	public String encode(String password) {
		return encoder.encode(password);
	}

	/**
	 * Checks to see if the passwords match or not.
	 * 
	 * @param password
	 * @param encodedPassword
	 * @return
	 */
	public boolean matches(String password, String encodedPassword) {
		return encoder.matches(password, encodedPassword);
	}

	/**
	 * Validates the given password against the REGEX to see if it matches or
	 * not.
	 * 
	 * @param password
	 * @return
	 */
	public boolean validatePasswordFormat(String password) {
		return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
	}

	/**
	 * Generates a random password which matches the criteria.
	 * 
	 * @return
	 */
	public String random() {
		StringBuffer password = new StringBuffer();
		for (int index = 0; index < MINIMUM_PASS_LENGTH - CHARACTER_GROUPS.size(); index++) {
			password.append(createRandomCharacter());
		}
		// Making sure the last CHARACTER_GROUPS.size() characters will contrain
		// a character from each
		// group at least.
		for (int index = 0; index < CHARACTER_GROUPS.size(); index++) {
			password.append(createRandomCharacter(index, new Random()));
		}

		return password.toString();
	}

	/**
	 * Creates a random character for the given group number.
	 * 
	 * @param groupIndex
	 * @param rand
	 * @return
	 */
	private char createRandomCharacter(int groupIndex, Random rand) {
		String str = CHARACTER_GROUPS.get(groupIndex);
		int strIndex = rand.nextInt(str.length());
		return str.charAt(strIndex);
	}

	/**
	 * Creates a random character from all the character groups.
	 * 
	 * @return
	 */
	private char createRandomCharacter() {
		Random rand = new Random();
		int groupIndex = rand.nextInt(CHARACTER_GROUPS.size());
		return createRandomCharacter(groupIndex, rand);
	}

}
