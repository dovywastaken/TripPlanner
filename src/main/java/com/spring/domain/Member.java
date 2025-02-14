package com.spring.domain;

import java.sql.Date;

public class Member 
{
    private String id;       // 사용자 ID
    private String name;     // 이름
    private String pw;       // 비밀번호
    private String region;   // 지역
    private String sex;      // 성별
    private String phone1;   // 전화번호 앞부분
    private String phone2;   // 전화번호 중간부분
    private String phone3;   // 전화번호 뒷부분
    private String birthday; // 생년월일
    private int emailCheck; //이메일 인증 여부
    private Date registerDate; //가입일
    private Date loginDate; //최근 접속일
    
    // 생성자, 게터, 세터들

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getPhone1() {
        return phone1;
    }
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }
    public String getPhone2() {
        return phone2;
    }
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
    public String getPhone3() {
        return phone3;
    }
    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
	public int getEmailCheck() 
	{
		return emailCheck;
	}
	
	public void setEmailCheck(int emailCheck) 
	{
		this.emailCheck = emailCheck;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}
