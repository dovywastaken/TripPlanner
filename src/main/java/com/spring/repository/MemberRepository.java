package com.spring.repository;

import com.spring.domain.Member;

public interface MemberRepository 
{
	void createMember(Member member);
	Member findById(String id);
	void updateMember(Member member);
	void deleteMember(Member member);
}