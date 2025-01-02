package com.spring.domain;

public class Tour 
{
	private int id; //순번
	private String contentid; //명소 고유 번호
	private String contenttypeid; //명소 분류 번호
	private String title; //명소 이름
	private String firstimage; //사진 
	private String addr1; //주소
	private String cat2;
	private String cat3;
	private long mapx; //x좌표
	private long mapy; //y좌표
	private int citation_count; //인용수
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getContenttypeid() {
		return contenttypeid;
	}
	public void setContenttypeid(String contenttypeid) {
		this.contenttypeid = contenttypeid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstimage() {
		return firstimage;
	}
	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getCat2() {
		return cat2;
	}
	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}
	public String getCat3() {
		return cat3;
	}
	public void setCat3(String cat3) {
		this.cat3 = cat3;
	}
	public long getMapx() {
		return mapx;
	}
	public void setMapx(long mapx) {
		this.mapx = mapx;
	}
	public long getMapy() {
		return mapy;
	}
	public void setMapy(long mapy) {
		this.mapy = mapy;
	}
	public int getCitation_count() {
		return citation_count;
	}
	public void setCitation_count(int citation_count) {
		this.citation_count = citation_count;
	}
}