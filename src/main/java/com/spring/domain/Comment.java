package com.spring.domain;

import java.sql.Timestamp;

public class Comment {
	private String id;
	private String comments;
	private Timestamp commentDate;
	private int commentLikes;
	private int c_unique;
	private int p_unique;
	
	public Comment() {};
	
	public String getComments() {
		return comments;
	}
	public void setComments(String contents) {
		this.comments = contents;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Timestamp getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}
	public int getCommentLikes() {
		return commentLikes;
	}
	public int getP_unique() {
		return p_unique;
	}
	public void setP_unique(int p_unique) {
		this.p_unique = p_unique;
	}
	public void setCommentLikes(int commentLikes) {
		this.commentLikes = commentLikes;
	}
	public int getC_unique() {
		return c_unique;
	}
	public void setC_unique(int c_unique) {
		this.c_unique = c_unique;
	}
	
	
	
}
