package com.security.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

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
		assertTrue(new RSA().isPrime(2));
		assertTrue(new RSA().isPrime(3));
		assertFalse(new RSA().isPrime(4));
		assertTrue(new RSA().isPrime(5));
		assertFalse(new RSA().isPrime(6));
		assertTrue(new RSA().isPrime(7));
		assertFalse(new RSA().isPrime(8));
		assertFalse(new RSA().isPrime(9));
		assertFalse(new RSA().isPrime(10));
		assertTrue(new RSA().isPrime(11));
		assertFalse(new RSA().isPrime(12));
		assertTrue(new RSA().isPrime(13));
	}

	@Test
	public void testGenerateRandomNumberInbound() {
		RSA rsa = new RSA();
		Integer randomNumber = rsa.generateRandomNumberInbound(4);
		assertTrue(randomNumber > 4 && randomNumber < Integer.MAX_VALUE);
	}

	@Test
	public void testGeneratePQ() {
		RSA rsa = new RSA();
		rsa.generatePQ(1000000);
		assertTrue(rsa.isPrime(rsa.getP()));
		assertTrue(rsa.isPrime(rsa.getQ()));
	}

	@Test
	public void testCalculateN() {
		RSA rsa = new RSA(3, 5);
		rsa.calculateN();
		assertEquals(Integer.valueOf(15), rsa.getN());
	}

	@Test
	public void testCalculateZ() {
		RSA rsa = new RSA(3, 5);
		rsa.calculateZ();
		assertEquals(Integer.valueOf(8), rsa.getZ());
	}

	@Test
	public void testCalculateE() {
		RSA rsa = new RSA(3, 5);
		rsa.calculateN();
		rsa.calculateZ();
		rsa.calculateE();
		assertTrue("Wrong value: " + rsa.getE(), Arrays.asList(new Integer[] { 3, 5, 7, 9, 11, 13 }).contains(rsa.getE()));
	}

	@Test
	public void testHaveCommonFactor() {
		assertTrue(new RSA().haveCommonFactor(5, 5));
		assertFalse(new RSA().haveCommonFactor(5, 6));
		assertTrue(new RSA().haveCommonFactor(10, 6));
		assertFalse(new RSA().haveCommonFactor(11, 60));
		assertFalse(new RSA().haveCommonFactor(17, 43));
		assertFalse(new RSA().haveCommonFactor(7, 8));
	}

	@Test
	public void testCalculateD() {
		RSA rsa = new RSA(11, 5);
		rsa.calculateN();
		rsa.calculateZ();
		rsa.setE(7);
		rsa.calculateD();
		assertEquals(Integer.valueOf(23), rsa.getD());
	}

}
