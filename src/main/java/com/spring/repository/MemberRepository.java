package com.spring.repository;

import java.util.List;

import com.spring.domain.Member;

public interface MemberRepository 
{
	void createMember(Member member);
	Member findById(String id);
	List<Member> searchMember(String name,int limit, int offset);
	List<Member> readAllMemberPaging(int limit, int offset);
	int getTotalMemberCount(String value);
	void updateMember(Member member);
	void deleteMember(Member member);
	boolean checkUp(String field, String value);
}