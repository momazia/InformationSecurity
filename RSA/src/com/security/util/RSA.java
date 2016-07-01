package com.security.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RSA encryption class
 * 
 * @author Max
 *
 */
public class RSA {

	private BigInteger p;
	private BigInteger q;

	private BigInteger n;
	private BigInteger z;

	private BigInteger e;
	private BigInteger d;

	/**
	 * Using the given P and Q, it prepares the encryption by calculating the necessary fields.
	 * 
	 * @param p
	 * @param q
	 */
	public RSA(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;
		calculateN();
		calculateZ();
		calculateE();
		calculateD();
	}

	public RSA() {

	}

	/**
	 * Generates two random P and Q which are more than minimum number passed. It also prepares the encryption by calculating the necessary fields.
	 * 
	 * @param minNumber
	 */
	public RSA(Integer minNumber, Integer maxNumber) {
		generatePQ(minNumber, maxNumber);
		calculateN();
		calculateZ();
		calculateE();
		calculateD();
	}

	/**
	 * Assigns a random number to E which is less than n and has no common factor with z.
	 */
	public void calculateE() {
		boolean hasCommonFactor = true;
		BigInteger num_2 = BigInteger.valueOf(2);
		BigInteger neg_num_2 = BigInteger.valueOf(-2);
		while (hasCommonFactor) {
			e = getRandomBigInteger(n.add(neg_num_2)).add(num_2); // To make sure it is bigger than 2, smaller than n
			hasCommonFactor = haveCommonFactor(e, z);
		}
	}

	/**
	 * Returns a random number which is smaller than max given.
	 * 
	 * @param max
	 * @return
	 */
	public BigInteger getRandomBigInteger(BigInteger max) {
		Random rnd = new Random();
		do {
			BigInteger i = new BigInteger(max.bitLength(), rnd);
			if (i.compareTo(max) < 0)
				return i;
		} while (true);
	}

	/**
	 * Checks to see if the numbers given have common factors.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean haveCommonFactor(BigInteger a, BigInteger b) {
		// If the numbers are the same, then they have common factors
		if (a.compareTo(b) == 0) {
			return true;
		}
		BigInteger max = a.compareTo(b) > 0 ? a : b;
		for (BigInteger i = BigInteger.valueOf(2); i.compareTo(max.divide(BigInteger.valueOf(2))) <= 0; i = i.add(BigInteger.ONE)) {
			if (a.mod(i).compareTo(BigInteger.ZERO) == 0 && b.mod(i).compareTo(BigInteger.ZERO) == 0) {
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
	public void generatePQ(int minNumber, int maxNumber) {
		Integer randInt1 = generateRandomNumberInbound(minNumber, maxNumber);
		Integer randInt2 = generateRandomNumberInbound(minNumber, maxNumber);
		this.p = BigInteger.valueOf(generatePrimeNumber(randInt1));
		this.q = BigInteger.valueOf(generatePrimeNumber(randInt2));
	}

	/**
	 * Calculates n = p * q
	 */
	public void calculateN() {
		n = p.multiply(q);
	}

	/**
	 * Calculates z = (p - 1) * (q - 1)
	 */
	public void calculateZ() {
		BigInteger p_1 = p.add(BigInteger.valueOf(-1));
		BigInteger q_1 = q.add(BigInteger.valueOf(-1));
		z = p_1.multiply(q_1);
	}

	/**
	 * Calculates D using Modular multiplicative inverse
	 */
	public void calculateD() {
		d = e.modInverse(z);
	}

	/**
	 * Generates a random number between minNumber and maxNumber
	 * 
	 * @param minNumber
	 *            inclusive
	 * @param maxNumber
	 *            exclusive
	 */
	public Integer generateRandomNumberInbound(int minNumber, int maxNumber) {
		Integer difference = maxNumber - minNumber - 1;
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
			if (isPrime(BigInteger.valueOf(i))) {
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
	public boolean isPrime(BigInteger n) {
		// Handling the only even prime number
		BigInteger num_2 = BigInteger.valueOf(2);
		if (n.compareTo(num_2) == 0) {
			return true;
		}
		// check if n is a multiple of 2, this way we eliminate 50% of the
		// numbers
		if (n.mod(num_2).compareTo(BigInteger.ZERO) == 0) {
			return false;
		}
		for (BigInteger i = BigInteger.valueOf(3); i.multiply(i).compareTo(n) <= 0; i = i.add(num_2)) {
			if (n.mod(i).compareTo(BigInteger.ZERO) == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Encrypts the message given by running (m^e) % n formula on each of the characters' ASCII code.
	 * 
	 * @param message
	 * @return
	 */
	public List<BigInteger> encrypt(String message) {
		List<BigInteger> result = new ArrayList<>();
		for (char chr : message.toCharArray()) {
			BigInteger m = BigInteger.valueOf((int) chr);
			BigInteger m_e = m.pow(e.intValue());
			BigInteger m_e_mod_n = m_e.mod(n);
			result.add(m_e_mod_n);
		}
		return result;
	}

	/**
	 * Decrypts the given encrypted message
	 * 
	 * @param encryptedMessage
	 * @return
	 */
	public String decrypt(List<BigInteger> encryptedMessage) {
		// Decrypting the message
		List<BigInteger> decryptedIntegers = getDecryptedIntegers(encryptedMessage);
		// Convert to String
		return convertToString(decryptedIntegers);
	}

	/**
	 * Decrypts the message given, character by character using formula: (c^d) % n based on the ASCII key.
	 * 
	 * @param encryptedMessage
	 * @return
	 */
	private List<BigInteger> getDecryptedIntegers(List<BigInteger> encryptedMessage) {
		List<BigInteger> result = new ArrayList<>();
		for (BigInteger c : encryptedMessage) {
			BigInteger c_d = c.pow(d.intValue());
			BigInteger c_d_mod_n = c_d.mod(n);
			result.add(c_d_mod_n);
		}
		return result;
	}

	/**
	 * Converts the list of encrypted characters to a string
	 * 
	 * @param encryptedIntegers
	 * @return
	 */
	public String convertToString(List<BigInteger> encryptedIntegers) {
		StringBuffer result = new StringBuffer();
		for (BigInteger bigInteger : encryptedIntegers) {
			result.append((char) bigInteger.intValue());
		}
		return result.toString();
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getZ() {
		return z;
	}

	public BigInteger getE() {
		return e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}

}
