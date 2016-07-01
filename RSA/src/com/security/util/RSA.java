package com.security.util;

import java.math.BigInteger;
import java.util.Random;

/**
 * RSA encryption class
 * 
 * @author Max
 *
 */
public class RSA {

	private Integer p;
	private Integer q;

	private Integer n;
	private Integer z;

	private Integer e;
	private Integer d;

	public RSA(int p, int q) {
		this.p = p;
		this.q = q;
	}

	/**
	 * Default constructor.
	 */
	public RSA() {

	}

	/**
	 * Assigns a random number to E which is less than n and has no common factor with z.
	 */
	public void calculateE() {
		boolean hasCommonFactor = true;
		Random random = new Random();
		while (hasCommonFactor) {
			setE(random.nextInt(n - 2) + 2); // To make sure it is bigger than 2, smaller than n
			hasCommonFactor = haveCommonFactor(getE(), z);
		}
	}

	/**
	 * Checks to see if the numbers given have common factors.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean haveCommonFactor(Integer a, Integer b) {
		// If the numbers are the same, then they have common factors
		if (a == b) {
			return true;
		}
		int max = a > b ? a : b;
		for (int i = 2; i <= max / 2; i++) {
			if (a % i == 0 && b % i == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates two random P and Q which are higher than the min number passed.
	 * 
	 * @param minNumber
	 */
	public void generatePQ(int minNumber) {
		Integer randInt1 = generateRandomNumberInbound(minNumber);
		Integer randInt2 = generateRandomNumberInbound(minNumber);
		this.p = generatePrimeNumber(randInt1);
		this.q = generatePrimeNumber(randInt2);
	}

	/**
	 * Calculates n = p * q
	 */
	public void calculateN() {
		n = p * q;
	}

	/**
	 * Calculates z = (p - 1) * (q - 1)
	 */
	public void calculateZ() {
		z = (p - 1) * (q - 1);
	}

	/**
	 * Calculates D using Modular multiplicative inverse
	 */
	public void calculateD() {
		d = BigInteger.valueOf(getE()).modInverse(BigInteger.valueOf(z)).intValue();
	}

	/**
	 * Generates a random number between minNumber and Integer.MAX_VALUE
	 * 
	 * @param minNumber
	 */
	public Integer generateRandomNumberInbound(int minNumber) {
		Integer difference = Integer.MAX_VALUE - minNumber - 1;
		Random random = new Random();
		int randomInt = random.nextInt(difference);
		return randomInt + minNumber;
	}

	/**
	 * Generate a Prime number with a minimum number to start with
	 * 
	 * @param minNumber
	 * @return
	 */
	public Integer generatePrimeNumber(int minNumber) {
		for (int i = minNumber; i < Integer.MAX_VALUE; i++) {
			if (isPrime(i)) {
				return i;
			}
		}
		throw new IllegalArgumentException("There is no prime number after minNumber [" + minNumber + "] passed");
	}

	/**
	 * Checks to see if the given number is prime or not.
	 * 
	 * @param n
	 * @return
	 */
	public boolean isPrime(int n) {
		// Handling the only even prime number
		if (n == 2) {
			return true;
		}
		// check if n is a multiple of 2, this way we eliminate 50% of the
		// numbers
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public Integer getN() {
		return n;
	}

	public Integer getZ() {
		return z;
	}

	public Integer getE() {
		return e;
	}

	public Integer getD() {
		return d;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public Integer getP() {
		return p;
	}

	public Integer getQ() {
		return q;
	}
}
