package com.security.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;

import com.security.util.RSA;

/**
 * Main application to be executed for running RSA encryption
 * 
 * @author Max
 *
 */
public class MainApplication {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Attention: since ASCII code is used in this program, please make sure P*Q is more than 255 (Number of characters in extended ASCII table)");
		System.out.println("Do you wish to auto generate P and Q? [Y/n]");
		String autoPQAnswer = br.readLine();
		RSA rsa = null;

		if (autoGeneratePQ(autoPQAnswer)) {

			System.out.println("Enter a minimun number (inclusive) for P and Q:");
			String minNumber = br.readLine();

			System.out.println("Enter a maximum number (exclusive) for P and Q: (This is to avoid very big numbers)");
			String maxNumber = br.readLine();

			rsa = new RSA(Integer.valueOf(minNumber), Integer.valueOf(maxNumber));
		} else {

			System.out.println("Enter P:");
			String p = br.readLine();

			System.out.println("Enter Q:");
			String q = br.readLine();

			rsa = new RSA(BigInteger.valueOf(Long.valueOf(p)), BigInteger.valueOf(Long.valueOf(q)));
		}
		System.out.println("P [" + rsa.getP() + "], Q[" + rsa.getQ() + "], N[" + rsa.getN() + "], Z[" + rsa.getZ() + "], E[" + rsa.getE() + "], D[" + rsa.getD() + "]");

		System.out.println("Enter your message:");
		String message = br.readLine();

		List<BigInteger> encryptedInts = rsa.encrypt(message);
		String decryptedMessage = rsa.decrypt(encryptedInts);
		printOutput(message, encryptedInts, decryptedMessage);
	}

	/**
	 * Pretty prints the result of the application into console.
	 * 
	 * @param message
	 * @param encryptedInts
	 * @param decryptedMessage
	 */
	private static void printOutput(String message, List<BigInteger> encryptedInts, String decryptedMessage) {
		StringBuffer fmt = new StringBuffer();
		for (int i = 0; i < message.length(); i++) {
			fmt.append("%");
			fmt.append(i + 1);
			fmt.append("$10s ");
		}
		fmt.append("%n");

		System.out.println("Original message:");
		System.out.printf(fmt.toString(), (Object[]) ConvertToStringChars(message.toCharArray()));

		System.out.println("Encrypted message:");
		System.out.printf(fmt.toString(), (Object[]) convertToInt(encryptedInts));

		System.out.println("Decrypted message:");
		System.out.printf(fmt.toString(), (Object[]) ConvertToStringChars(message.toCharArray()));
	}

	private static String[] ConvertToStringChars(char[] charArray) {
		String[] result = new String[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			result[i] = String.valueOf(charArray[i]);
		}
		return result;
	}

	/**
	 * converts the given big integer numbers to an array of ints
	 * 
	 * @param encryptedInts
	 * @return
	 */
	private static String[] convertToInt(List<BigInteger> encryptedInts) {
		String[] result = new String[encryptedInts.size()];
		for (int i = 0; i < encryptedInts.size(); i++) {
			result[i] = encryptedInts.get(i).toString();
		}
		return result;
	}

	/**
	 * Checks user's input to see if the user is asking for auto generated P and Q or not.
	 * 
	 * @param autoPQAnswer
	 * @return
	 */
	private static boolean autoGeneratePQ(String autoPQAnswer) {
		return !"n".equals(autoPQAnswer);
	}

}
