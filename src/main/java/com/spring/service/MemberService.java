package com.spring.service;

import java.util.List;

import com.spring.domain.Member;

public interface MemberService 
{
	void createMember(Member member);
	Member findById(String id);
	List<Member> readAllMember();
	List<Member> readAllMemberSorted(String sortBy);
	void updateMember(Member member);
	void deleteMember(Member member);
}
