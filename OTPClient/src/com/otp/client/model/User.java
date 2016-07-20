package com.otp.client.model;

/**
 * A model which holds user's information
 * 
 * @author Max
 *
 */
public class User {

	private String seed;
	private int counter;

	public User() {
		super();
	}

	public User(String seed, int counter) {
		super();
		this.seed = seed;
		this.counter = counter;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
