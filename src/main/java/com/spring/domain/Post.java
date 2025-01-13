package com.spring.domain;

import java.sql.Timestamp;
import java.util.List;



public class Post 
{
	public Post() {}
	
	private String id;
	private String title;
	private String contents;
	private Timestamp publishDate;
	private int views;
	private int likes;
	private String region;
	private boolean isPrivate;
	private int p_unique;
	private boolean commentIsAllowed;
	private int satisfaction;
	private List<String> fileImage;
	private int commentCount;
	
	
	public List<String> getFileImage() {
		return fileImage;
	}
	public void setFileImage(List<String> savedFileNames) {
		this.fileImage = savedFileNames;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Timestamp timestamp) {
		this.publishDate = timestamp;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(String isPrivate) {
	    if(isPrivate != null) {
	        this.isPrivate = "1".equals(isPrivate);
	    }
	}

	public int getP_unique() {
		return p_unique;
	}
	public void setP_unique(int p_unique) {
		this.p_unique = p_unique;
	}

	public boolean isCommentIsAllowed() {
		return commentIsAllowed;
	}
	public void setCommentIsAllowed(String commentIsAllowed) {
		  if(commentIsAllowed != null) {
		        this.commentIsAllowed = "1".equals(commentIsAllowed);
		    }
	}
	public int getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}	
	
}
