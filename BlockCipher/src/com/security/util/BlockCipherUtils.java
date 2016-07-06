package com.security.util;

/**
 * Main utility class for Block cipher
 * 
 * @author Max
 *
 */
public class BlockCipherUtils {

	private static final int BLOCK_SIZE = 8;

	private static final int ASCII_MIN = 96;

	/**
	 * static Singleton instance
	 */
	private static BlockCipherUtils instance;

	/**
	 * Private constructor for singleton
	 */
	private BlockCipherUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static BlockCipherUtils getInstance() {
		if (instance == null) {
			instance = new BlockCipherUtils();
		}
		return instance;
	}

	/**
	 * Converts the given string message to a number starting from 1 to 26, where a = 1, b = 3, ..., z = 26. The capital letters are first converted to lower case.
	 * 
	 * @param message
	 * @return
	 */
	public int[] convertStringToInt(String message) {
		int[] result = new int[message.length()];
		for (int i = 0; i < result.length; i++) {
			result[i] = Character.toLowerCase(message.charAt(i)) - ASCII_MIN;
		}
		return result;
	}

	/**
	 * Converts the given indexes into binary code in String format.
	 * 
	 * @param indexes
	 * @return
	 */
	public String[] convertTo8BitBinaryBlocks(int[] indexes) {
		String[] result = new String[indexes.length];
		for (int i = 0; i < indexes.length; i++) {
			result[i] = String.format("%8s", Integer.toBinaryString(indexes[i])).replace(' ', '0');
		}
		return result;
	}

	/**
	 * Reverse the the 8 bit blocks given.
	 * 
	 * @param blocks
	 * @return
	 */
	public String[] reverseBits(String[] blocks) {
		String[] result = new String[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			result[i] = new StringBuffer(blocks[i]).reverse().toString();
		}
		return result;
	}

	public String[] flipLastBit(String[] blocks) {
		String[] result = new String[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			result[i] = flipLastBit(blocks[i]);
		}
		return result;
	}

	/**
	 * Flips the last digit in the string.
	 * 
	 * @param input
	 * @return
	 */
	private String flipLastBit(String input) {
		char lastChar = input.charAt(input.length() - 1);
		String result = input.substring(0, input.length() - 1);
		if (lastChar == '1') {
			return result + '0';
		}
		return result + '1';
	}

	/**
	 * Swap the blocks of size 8, if there is a reminder to 8 division, it will swap them as a one chunk.
	 * 
	 * @param blocks
	 * @return
	 */
	public String[] swapBlocks(String[] blocks) {
		String[] result = new String[blocks.length];
		for (int i = 0; i + BLOCK_SIZE - 1 < blocks.length; i = i + BLOCK_SIZE) {
			for (int j = 0; j < BLOCK_SIZE; j++) {
				result[i + j] = blocks[i + BLOCK_SIZE - j - 1];
			}
		}
		int mod = blocks.length % BLOCK_SIZE;
		int div = blocks.length / BLOCK_SIZE;
		if (mod != 0) {
			for (int i = 0; i < mod; i++) {
				result[div * BLOCK_SIZE + i] = blocks[div * BLOCK_SIZE + mod - i - 1];

			}
		}
		return result;
	}

	/**
	 * Encrypts the given message for number of iterations passed and returns the blocks of 8 bits representing the encrypted message.
	 * 
	 * @param message
	 * @param numberOfIteration
	 * @return
	 */
	public String[] encrypt(String message, int numberOfIteration) {
		int[] intBlocks = convertStringToInt(message);
		String[] _8BitBlocks = convertTo8BitBinaryBlocks(intBlocks);
		for (int i = 0; i < numberOfIteration; i++) {
			String[] reveresed8BitBlocks = reverseBits(_8BitBlocks);
			String[] flippedLastBit = flipLastBit(reveresed8BitBlocks);
			_8BitBlocks = swapBlocks(flippedLastBit);
		}
		return _8BitBlocks;
	}

	/**
	 * Decrypt the encrypted binary blocks.
	 * 
	 * @param encryptedBinaries
	 * @param numberOfIteration
	 * @return
	 */
	public String decrypt(String[] encryptedBinaries, int numberOfIteration) {
		String[] inputBlocks = encryptedBinaries;
		for (int i = 0; i < numberOfIteration; i++) {
			String[] swappedBlocks = swapBlocks(inputBlocks);
			String[] flippedLastBit = flipLastBit(swappedBlocks);
			inputBlocks = reverseBits(flippedLastBit);
		}
		return convert8BitBinaryBlocksToString(inputBlocks);
	}

	/**
	 * Converts the binary blocks passed into a string.
	 * 
	 * @param blocks
	 * @return
	 */
	private String convert8BitBinaryBlocksToString(String[] blocks) {
		StringBuffer result = new StringBuffer();
		for (String block : blocks) {
			result.append(Character.toChars(Integer.parseInt(block, 2) + ASCII_MIN));
		}
		return result.toString();
	}
}
