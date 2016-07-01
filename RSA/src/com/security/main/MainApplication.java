package com.security.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

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

		Integer[] encryptedText = rsa.encrypt(message);
	}

	private static boolean autoGeneratePQ(String autoPQAnswer) {
		return !"n".equals(autoPQAnswer);
	}

}
