package com.security.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.security.util.PolyAlphabeticUtils;

/**
 * The main application which takes user's input and encrypt it using a given
 * key. Later it uses the same key to decrypt the same message.
 * 
 * @author Max
 *
 */
public class MainApplication {

	private static final int[] KEY_PATTERN = new int[] { 5, 19 };

	public static void main(String[] args) throws IOException {

		// Encryption
		System.out.println("Enter your message Alice: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String plainText = br.readLine();

		System.out.println("Message:\t\t" + plainText);

		String encryptedMessage = PolyAlphabeticUtils.getInstance().encrypt(plainText, KEY_PATTERN);
		System.out.println("Encrypted Message:\t" + encryptedMessage);

		// Decryption
		String decryptedMessage = PolyAlphabeticUtils.getInstance().decrypt(encryptedMessage, KEY_PATTERN);
		System.out.println("Decrypted Message:\t" + decryptedMessage);
	}
}
