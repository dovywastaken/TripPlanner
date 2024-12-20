package com.spring.service.member;

import java.util.List;

import com.spring.domain.Member;

public interface MemberService 
{
	//Create
	void createMember(Member member);
	//Read
	Member findById(String id);
	List<Member> searchMember(int limit, int offset, String name);
	List<Member> readAllMemberPaging(int limit, int offset);
	int getTotalMemberCount(String keyword);
	String getPasswordById(String id);
	//Update
	void updateMember(Member member);
	void updatePw(String pw, String id);
	void updateEmail(String id);
	//Delete
	void deleteMember(Member member);
	//Validation
	boolean idCheckUp(String id);
	boolean emailCheckUp(String email);
}
