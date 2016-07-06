package com.security.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.security.util.BlockCipherUtils;

public class TestBlockCipherUtils {

	@Test
	public void test_convertStringToInt() {
		int[] intList = BlockCipherUtils.getInstance().convertStringToInt("love");
		assertEquals(12, intList[0]);
		assertEquals(15, intList[1]);
		assertEquals(22, intList[2]);
		assertEquals(5, intList[3]);
	}

	@Test
	public void test_convertTo8BitBinaryBlocks() {
		int[] intList = new int[] { 1, 5, 8, 26 };
		String[] binaries = BlockCipherUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		assertEquals("00000001", binaries[0]);
		assertEquals("00000101", binaries[1]);
		assertEquals("00001000", binaries[2]);
		assertEquals("00011010", binaries[3]);
	}

	@Test
	public void test_reverse8BitBlocks() {
		int[] intList = new int[] { 1, 5, 8, 26 };
		String[] binaries = BlockCipherUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] reversedBinaries = BlockCipherUtils.getInstance().reverseBits(binaries);
		assertEquals("10000000", reversedBinaries[0]);
		assertEquals("10100000", reversedBinaries[1]);
		assertEquals("00010000", reversedBinaries[2]);
		assertEquals("01011000", reversedBinaries[3]);
	}

	@Test
	public void test_swapBlocks_smaller_than_8() {
		int[] intList = new int[] { 1, 5, 8, 26 };
		String[] binaries = BlockCipherUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] swapped = BlockCipherUtils.getInstance().swapBlocks(binaries);
		assertEquals("00011010", swapped[0]);
		assertEquals("00001000", swapped[1]);
		assertEquals("00000101", swapped[2]);
		assertEquals("00000001", swapped[3]);
	}

	@Test
	public void test_swapBlocks_bigger_than_8_even() {
		int[] intList = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		String[] binaries = BlockCipherUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] swapped = BlockCipherUtils.getInstance().swapBlocks(binaries);

		assertEquals("00000001", swapped[7]);
		assertEquals("00000010", swapped[6]);
		assertEquals("00000011", swapped[5]);
		assertEquals("00000100", swapped[4]);
		assertEquals("00000101", swapped[3]);
		assertEquals("00000110", swapped[2]);
		assertEquals("00000111", swapped[1]);
		assertEquals("00001000", swapped[0]);

		assertEquals("00001001", swapped[15]);
		assertEquals("00001010", swapped[14]);
		assertEquals("00001011", swapped[13]);
		assertEquals("00001100", swapped[12]);
		assertEquals("00001101", swapped[11]);
		assertEquals("00001110", swapped[10]);
		assertEquals("00001111", swapped[9]);
		assertEquals("00010000", swapped[8]);

		assertEquals("00010001", swapped[17]);
		assertEquals("00010010", swapped[16]);
	}

	@Test
	public void test_swapBlocks_bigger_than_8_odd() {
		int[] intList = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
		String[] binaries = BlockCipherUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] swapped = BlockCipherUtils.getInstance().swapBlocks(binaries);

		assertEquals("00000001", swapped[7]);
		assertEquals("00000010", swapped[6]);
		assertEquals("00000011", swapped[5]);
		assertEquals("00000100", swapped[4]);
		assertEquals("00000101", swapped[3]);
		assertEquals("00000110", swapped[2]);
		assertEquals("00000111", swapped[1]);
		assertEquals("00001000", swapped[0]);

		assertEquals("00001001", swapped[15]);
		assertEquals("00001010", swapped[14]);
		assertEquals("00001011", swapped[13]);
		assertEquals("00001100", swapped[12]);
		assertEquals("00001101", swapped[11]);
		assertEquals("00001110", swapped[10]);
		assertEquals("00001111", swapped[9]);
		assertEquals("00010000", swapped[8]);

		assertEquals("00010001", swapped[18]);
		assertEquals("00010010", swapped[17]);
		assertEquals("00010011", swapped[16]);
	}

	@Test
	public void test_encrypt_1() {
		String[] binaries = BlockCipherUtils.getInstance().encrypt("abcd", 1);
		assertEquals("10000001", binaries[3]);
		assertEquals("01000001", binaries[2]);
		assertEquals("11000001", binaries[1]);
		assertEquals("00100001", binaries[0]);
	}

	@Test
	public void test_flipLastBit() {
		String[] blocks = new String[] { "00001100", "00001111" };
		String[] result = BlockCipherUtils.getInstance().flipLastBit(blocks);
		assertEquals("00001101", result[0]);
		assertEquals("00001110", result[1]);
	}

	@Test
	public void test_encrypt_2() {
		String[] binaries = BlockCipherUtils.getInstance().encrypt("abcd", 2);
		assertEquals("10000101", binaries[3]);
		assertEquals("10000010", binaries[2]);
		assertEquals("10000011", binaries[1]);
		assertEquals("10000000", binaries[0]);
	}

	@Test
	public void test_decrypt_1() {
		String[] encryptedBinaries = BlockCipherUtils.getInstance().encrypt("love", 1);
		String originalMessage = BlockCipherUtils.getInstance().decrypt(encryptedBinaries, 1);
		assertEquals("love", originalMessage);
	}

	@Test
	public void test_decrypt_2() {
		String[] encryptedBinaries = BlockCipherUtils.getInstance().encrypt("love", 2);
		String originalMessage = BlockCipherUtils.getInstance().decrypt(encryptedBinaries, 2);
		assertEquals("love", originalMessage);
	}

	@Test
	public void test_decrypt_8() {
		String[] encryptedBinaries = BlockCipherUtils.getInstance().encrypt("abcdefghigklmnop", 8);
		String originalMessage = BlockCipherUtils.getInstance().decrypt(encryptedBinaries, 8);
		assertEquals("abcdefghigklmnop", originalMessage);
	}

}
