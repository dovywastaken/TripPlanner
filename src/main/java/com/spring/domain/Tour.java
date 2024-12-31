package com.spring.domain;

public class Tour 
{
	private int id; //순번
	private String contentid; //명소 고유 번호
	private String contenttypeid; //명소 분류 번호
	private String title; //명소 이름
	private String tel; //전화번호
	private String homepage; //홈페이지 있으면 저장
	private String firstimage; //사진
	private String areacode; //지역 코드
	private String sigungucode; //시군구 코드
	private String addr1; //주소
	private String addr2; //상세 주소
	private String zipcode; //우편번호
	private String overview; //설명
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getFirstimage() {
		return firstimage;
	}
	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getSigungucode() {
		return sigungucode;
	}
	public void setSigungucode(String sigungucode) {
		this.sigungucode = sigungucode;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
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