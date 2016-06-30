package com.security.util;

public class RSA {

	private Integer p;
	private Integer q;
	private Integer z;
	private Integer e;
	private Integer d;

	public RSA(int p, int q) {
		this.setP(p);
		this.setQ(q);
	}

	public RSA() {

	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}
	
	public void generatePQ(){
		
	}

}
