package com.security.util;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Main utility class for Polyalphabetic encryption/decryption
 * 
 * @author Max
 *
 */
public class PolyAlphabeticUtils {

	/**
	 * static Singleton instance
	 */
	private static PolyAlphabeticUtils instance;
	private static int MIN_ASCII_CODE = 97; // a
	private static int MAX_ASCII_CODE = 122; // z

	/**
	 * Private constructor for singleton
	 */
	private PolyAlphabeticUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static PolyAlphabeticUtils getInstance() {
		if (instance == null) {
			instance = new PolyAlphabeticUtils();
		}
		return instance;
	}

	/**
	 * Encrypts the given plain text using the pattern passed. It only encrypts
	 * the lower case characters, otherwise it will be ignored and the pattern
	 * index will not increase.
	 * 
	 * @param plainText
	 * @param pattern
	 * @return
	 */
	public String encrypt(String plainText, int... pattern) {
		return convert(plainText, OperationType.ENCRYPT, pattern);
	}

	private String convert(String plainText, OperationType type, int... pattern) {
		String result = "";
		MutableInt patternIndex = new MutableInt(0);
		for (char chr : plainText.toCharArray()) {
			char encryptedCharacter = chr;
			if (Character.isAlphabetic(chr) && Character.isLowerCase(chr)) {
				encryptedCharacter = encryptCharacter(chr, type, patternIndex, pattern);
			}
			result += encryptedCharacter;
		}
		return result;
	}

	/**
	 * Encrypts the given character using the patterns and the index given. If
	 * the pattern Index goes higher than the patterns length, it will start
	 * from the beginning of the pattern and apply the changes to the character.
	 * 
	 * @param chr
	 * @param type
	 * @param patternIndex
	 * @param patterns
	 * @return
	 */
	public char encryptCharacter(char chr, OperationType type, MutableInt patternIndex, int... patterns) {
		if (patternIndex.getValue() >= patterns.length) {
			patternIndex.setValue(0);
		}
		Integer key = patterns[patternIndex.getValue()];
		patternIndex.setValue(patternIndex.getValue() + 1);
		return convert(chr, type, key);
	}

	/**
	 * Converts the given character by adding/subtracting (depending on the
	 * operation type) the number key to its ASCII code, if it passes more than
	 * the boundary, it will continue from from the start of the table,
	 * depending on the operation type.
	 * 
	 * @param chr
	 * @param type
	 * @param key
	 * @return
	 */
	public char convert(char chr, OperationType type, Integer key) {
		switch (type) {
		case DECRYPT: {
			int newCharAscii = (int) chr - key;
			if (newCharAscii < MIN_ASCII_CODE) {
				newCharAscii = MAX_ASCII_CODE - (MIN_ASCII_CODE - newCharAscii) + 1;
			}
			return (char) newCharAscii;
		}
		case ENCRYPT: {
			int newCharAscii = (int) chr + key;
			if (newCharAscii > MAX_ASCII_CODE) {
				newCharAscii = MIN_ASCII_CODE + (newCharAscii - MAX_ASCII_CODE) - 1;
			}
			return (char) newCharAscii;
		}
		}
		return 0;
	}

	/**
	 * Decrypt the message given using the key pattern given.
	 * 
	 * @param encryptedMessage
	 * @param keyPattern
	 * @return
	 */
	public String decrypt(String encryptedMessage, int[] keyPattern) {
		return convert(encryptedMessage, OperationType.DECRYPT, keyPattern);
	}

}
