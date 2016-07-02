package com.security.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.security.util.CRCUtils;

public class TestCRCUtils {

	@Test
	public void test_convertStringToInt() {
		int[] intList = CRCUtils.getInstance().convertStringToInt("love");
		assertEquals(12, intList[0]);
		assertEquals(15, intList[1]);
		assertEquals(22, intList[2]);
		assertEquals(5, intList[3]);
	}

	@Test
	public void test_convertTo8BitBinaryBlocks() {
		int[] intList = new int[] { 1, 5, 8, 26 };
		String[] binaries = CRCUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		assertEquals("00000001", binaries[0]);
		assertEquals("00000101", binaries[1]);
		assertEquals("00001000", binaries[2]);
		assertEquals("00011010", binaries[3]);
	}

	@Test
	public void test_flip8BitBlocks() {
		int[] intList = new int[] { 1, 5, 8, 26 };
		String[] binaries = CRCUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] flippedBinaries = CRCUtils.getInstance().flip8BitBlocks(binaries);
		assertEquals("11111110", flippedBinaries[0]);
		assertEquals("11111010", flippedBinaries[1]);
		assertEquals("11110111", flippedBinaries[2]);
		assertEquals("11100101", flippedBinaries[3]);
	}

	@Test
	public void test_swapBlocks_even() {
		int[] intList = new int[] { 1, 5, 8, 26 };
		String[] binaries = CRCUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] swapped = CRCUtils.getInstance().swapBlocks(binaries);
		assertEquals("00000101", swapped[0]);
		assertEquals("00000001", swapped[1]);
		assertEquals("00011010", swapped[2]);
		assertEquals("00001000", swapped[3]);
	}

	@Test
	public void test_swapBlocks_odd() {
		int[] intList = new int[] { 1, 5, 8, 26, 3 };
		String[] binaries = CRCUtils.getInstance().convertTo8BitBinaryBlocks(intList);
		String[] swapped = CRCUtils.getInstance().swapBlocks(binaries);
		assertEquals("00000101", swapped[0]);
		assertEquals("00000001", swapped[1]);
		assertEquals("00011010", swapped[2]);
		assertEquals("00001000", swapped[3]);
		assertEquals("00000011", swapped[4]);
	}

	@Test
	public void test_encrypt_1() {
		String[] binaries = CRCUtils.getInstance().encrypt("love", 1);
		assertEquals("11110000", binaries[0]);
		assertEquals("11110011", binaries[1]);
		assertEquals("11111010", binaries[2]);
		assertEquals("11101001", binaries[3]);
	}

	@Test
	public void test_encrypt_2() {
		String[] binaries = CRCUtils.getInstance().encrypt("love", 2);
		assertEquals("00001100", binaries[0]);
		assertEquals("00001111", binaries[1]);
		assertEquals("00010110", binaries[2]);
		assertEquals("00000101", binaries[3]);
	}

	@Test
	public void test_encrypt_3() {
		String[] binaries = CRCUtils.getInstance().encrypt("love", 3);
		assertEquals("11110000", binaries[0]);
		assertEquals("11110011", binaries[1]);
		assertEquals("11111010", binaries[2]);
		assertEquals("11101001", binaries[3]);
	}

	@Test
	public void test_decrypt_1() {
		String[] encryptedBinaries = CRCUtils.getInstance().encrypt("love", 1);
		String originalMessage = CRCUtils.getInstance().decrypt(encryptedBinaries, 1);
		assertEquals("love", originalMessage);
	}

	@Test
	public void test_decrypt_2() {
		String[] encryptedBinaries = CRCUtils.getInstance().encrypt("love", 2);
		String originalMessage = CRCUtils.getInstance().decrypt(encryptedBinaries, 2);
		assertEquals("love", originalMessage);
	}

	@Test
	public void test_decrypt_8() {
		String[] encryptedBinaries = CRCUtils.getInstance().encrypt("love", 8);
		String originalMessage = CRCUtils.getInstance().decrypt(encryptedBinaries, 8);
		assertEquals("love", originalMessage);
	}

}
