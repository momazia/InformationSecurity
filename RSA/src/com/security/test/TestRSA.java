package com.security.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.security.util.RSA;

public class TestRSA {

	@Test
	public void testGeneratePQ() {
		RSA rsa = new RSA();
		rsa.generatePQ();
		assertTrue("Generated P" + rsa.getP(), isPrime(rsa.getP()));
	}

	private boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

}
