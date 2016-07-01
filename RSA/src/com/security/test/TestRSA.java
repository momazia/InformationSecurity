package com.security.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.security.util.RSA;

public class TestRSA {

	@Test
	public void testGeneratePrimeNumber() {
		RSA rsa = new RSA();
		Integer primeNumber = rsa.generatePrimeNumber(2);
		assertEquals(Integer.valueOf(2), primeNumber);
		primeNumber = rsa.generatePrimeNumber(3);
		assertEquals(Integer.valueOf(3), primeNumber);
		primeNumber = rsa.generatePrimeNumber(4);
		assertEquals(Integer.valueOf(5), primeNumber);
		primeNumber = rsa.generatePrimeNumber(5);
		assertEquals(Integer.valueOf(5), primeNumber);
		primeNumber = rsa.generatePrimeNumber(6);
		assertEquals(Integer.valueOf(7), primeNumber);
		primeNumber = rsa.generatePrimeNumber(7);
		assertEquals(Integer.valueOf(7), primeNumber);
	}

	@Test
	public void testIsPrime() {
		assertTrue(new RSA().isPrime(BigInteger.valueOf(2)));
		assertTrue(new RSA().isPrime(BigInteger.valueOf(3)));
		assertFalse(new RSA().isPrime(BigInteger.valueOf(4)));
		assertTrue(new RSA().isPrime(BigInteger.valueOf(5)));
		assertFalse(new RSA().isPrime(BigInteger.valueOf(6)));
		assertTrue(new RSA().isPrime(BigInteger.valueOf(7)));
		assertFalse(new RSA().isPrime(BigInteger.valueOf(8)));
		assertFalse(new RSA().isPrime(BigInteger.valueOf(9)));
		assertFalse(new RSA().isPrime(BigInteger.valueOf(10)));
		assertTrue(new RSA().isPrime(BigInteger.valueOf(11)));
		assertFalse(new RSA().isPrime(BigInteger.valueOf(12)));
		assertTrue(new RSA().isPrime(BigInteger.valueOf(13)));
	}

	@Test
	public void testGenerateRandomNumberInbound() {
		RSA rsa = new RSA();
		Integer randomNumber = rsa.generateRandomNumberInbound(4, 20);
		assertTrue("randomNumber: [" + randomNumber + "]", randomNumber > 4 && randomNumber < 20);
	}

	@Test
	public void testGeneratePQ() {
		RSA rsa = new RSA();
		rsa.generatePQ(1000, 3000);
		assertTrue(rsa.isPrime(rsa.getP()));
		assertTrue(rsa.isPrime(rsa.getQ()));
	}

	@Test
	public void testCalculateN() {
		RSA rsa = new RSA(BigInteger.valueOf(3), BigInteger.valueOf(5));
		rsa.calculateN();
		assertEquals(BigInteger.valueOf(15), rsa.getN());
	}

	@Test
	public void testCalculateZ() {
		RSA rsa = new RSA(BigInteger.valueOf(3), BigInteger.valueOf(5));
		rsa.calculateZ();
		assertEquals(BigInteger.valueOf(8), rsa.getZ());
	}

	@Test
	public void testCalculateE() {
		RSA rsa = new RSA(BigInteger.valueOf(3), BigInteger.valueOf(5));
		rsa.calculateN();
		rsa.calculateZ();
		rsa.calculateE();
		assertTrue("Wrong value: " + rsa.getE(), Arrays.asList(new Integer[] { 3, 5, 7, 9, 11, 13 }).contains(rsa.getE().intValue()));
	}

	@Test
	public void testHaveCommonFactor() {
		assertTrue(new RSA().haveCommonFactor(BigInteger.valueOf(5), BigInteger.valueOf(5)));
		assertFalse(new RSA().haveCommonFactor(BigInteger.valueOf(5), BigInteger.valueOf(6)));
		assertTrue(new RSA().haveCommonFactor(BigInteger.valueOf(10), BigInteger.valueOf(6)));
		assertFalse(new RSA().haveCommonFactor(BigInteger.valueOf(11), BigInteger.valueOf(60)));
		assertFalse(new RSA().haveCommonFactor(BigInteger.valueOf(17), BigInteger.valueOf(43)));
		assertFalse(new RSA().haveCommonFactor(BigInteger.valueOf(7), BigInteger.valueOf(8)));
	}

	@Test
	public void testCalculateD1() {
		RSA rsa = new RSA(BigInteger.valueOf(11), BigInteger.valueOf(5));
		rsa.setE(BigInteger.valueOf(7));
		rsa.calculateN();
		rsa.calculateZ();
		rsa.calculateD();
		assertEquals(BigInteger.valueOf(23), rsa.getD());
	}

	@Test
	public void testCalculateD2() {
		RSA rsa = new RSA(BigInteger.valueOf(5), BigInteger.valueOf(7));
		rsa.setE(BigInteger.valueOf(5));
		rsa.calculateN();
		rsa.calculateZ();
		rsa.calculateD();
		assertEquals(BigInteger.valueOf(5), rsa.getD());
	}

	@Test
	public void testEncryptDecrypt() {
		RSA rsa = new RSA(BigInteger.valueOf(13), BigInteger.valueOf(17));
		rsa.setE(BigInteger.valueOf(5));
		rsa.calculateN();
		rsa.calculateZ();
		rsa.calculateD();
		String message = "love";
		List<BigInteger> encryptedMessage = rsa.encrypt(message);
		String originalMessage = rsa.decrypt(encryptedMessage);
		assertEquals(message, originalMessage);
	}

	@Test
	public void testEncryptDecrypt_BigNumbers() {
		RSA rsa = new RSA(40, 1000);
		String message = "love";
		List<BigInteger> encryptedMessage = rsa.encrypt(message);
		String originalMessage = rsa.decrypt(encryptedMessage);
		assertEquals(message, originalMessage);
	}

	@Test
	public void testConvertToString() {
		List<BigInteger> encryptedIntegers = new ArrayList<>();
		encryptedIntegers.add(BigInteger.valueOf(108));
		encryptedIntegers.add(BigInteger.valueOf(111));
		encryptedIntegers.add(BigInteger.valueOf(86));
		encryptedIntegers.add(BigInteger.valueOf(69));
		assertEquals("loVE", new RSA().convertToString(encryptedIntegers));
	}

}
