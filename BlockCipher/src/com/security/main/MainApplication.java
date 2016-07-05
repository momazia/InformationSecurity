package com.security.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.security.util.BlockCipherUtils;

/**
 * The main application which takes an input from user and runs Block cipher on it.
 * 
 * @author Max
 *
 */
public class MainApplication {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter your messge (Just lower case characters):");
		String originalMessage = br.readLine();

		System.out.println("Enter the number of iterations:");
		int numberOfIteration = Integer.valueOf(br.readLine());

		// Encrypting
		String[] encryptedBlocks = BlockCipherUtils.getInstance().encrypt(originalMessage, numberOfIteration);

		StringBuffer encryptedBlocksBuffer = new StringBuffer();
		for (String encryptedBlock : encryptedBlocks) {
			encryptedBlocksBuffer.append(encryptedBlock);
			encryptedBlocksBuffer.append("\t");
		}
		System.out.println("Encrypted message:");
		System.out.println(originalMessage);

		System.out.println("Encrypted message (8 bits):");
		System.out.println(encryptedBlocksBuffer.toString());

		// Decrypting
		String decryptedMessage = BlockCipherUtils.getInstance().decrypt(encryptedBlocks, numberOfIteration);

		System.out.println("Decrypted message:");
		System.out.println(decryptedMessage);
	}
}
