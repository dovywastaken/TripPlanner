package com.spring.service;

import com.spring.domain.Member;

public interface MemberService 
{
	void createMember(Member member);
	Member findById(String id);
	void updateMember(Member member);
	void deleteMember(Member member);
}
