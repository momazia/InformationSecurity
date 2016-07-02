package com.security.util;

/**
 * Main utility class for CRC
 * 
 * @author Max
 *
 */
public class CRCUtils {

	private static final int ASCII_MIN = 96;

	/**
	 * static Singleton instance
	 */
	private static CRCUtils instance;

	/**
	 * Private constructor for singleton
	 */
	private CRCUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static CRCUtils getInstance() {
		if (instance == null) {
			instance = new CRCUtils();
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
	 * Flips the the 8 bit blocks
	 * 
	 * @param blocks
	 * @return
	 */
	public String[] flip8BitBlocks(String[] blocks) {
		String[] result = new String[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			result[i] = blocks[i].replace('0', 'x').replace('1', '0').replace('x', '1');
		}
		return result;
	}

	/**
	 * Swap the blocks two by two, if there is any left at the end (in case of odd number of blocks), it will copy over the last block over to the result.
	 * 
	 * @param blocks
	 * @return
	 */
	public String[] swapBlocks(String[] blocks) {
		String[] result = new String[blocks.length];
		for (int i = 0; i + 1 < blocks.length; i = i + 2) {
			// If there is a next block
			result[i + 1] = blocks[i];
			result[i] = blocks[i + 1];
		}
		// If there is odd number of blocks, copy the last one as it is.
		if (blocks.length % 2 != 0) {
			int lastIndex = blocks.length - 1;
			result[lastIndex] = blocks[lastIndex];
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
			String[] flipped8BitBlocks = flip8BitBlocks(_8BitBlocks);
			_8BitBlocks = swapBlocks(flipped8BitBlocks);
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
			inputBlocks = flip8BitBlocks(swappedBlocks);
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
