package com.spring.service;

import java.util.List;

import com.spring.domain.Member;

public interface MemberService 
{
		//Create
		void createMember(Member member);
		//Read
		Member findById(String id);
		List<Member> searchMember(String name,int limit, int offset);
		List<Member> readAllMemberPaging(int limit, int offset, String keyword);
		int getTotalMemberCount(String value);
		String getPasswordById(String id);
		//Update
		void updateMember(Member member);
		void updatePw(String pw, String id);
		//Delete
		void deleteMember(Member member);
		//Validation
		boolean checkUp(String field, String value);
}
