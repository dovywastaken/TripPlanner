package com.spring.domain;

import java.sql.Timestamp;

public class Likes {
	private String id;
	private int p_unique;
	private int c_unique;
	private Timestamp likesDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getP_unique() {
		return p_unique;
	}
	public void setP_unique(int p_unique) {
		this.p_unique = p_unique;
	}
	public int getC_unique() {
		return c_unique;
	}
	public void setC_unique(int c_unique) {
		this.c_unique = c_unique;
	}
	public Timestamp getLikesDate() {
		return likesDate;
	}
	public void setLikesDate(Timestamp likesDate) {
		this.likesDate = likesDate;
	}
}
