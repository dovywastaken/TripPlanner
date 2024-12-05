package com.spring.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.domain.Member;

public class MemberMapper implements RowMapper<Member>
{
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Member member = new Member();
		member.setName(rs.getString(1));
		member.setId(rs.getString(2));
		member.setPw(rs.getString(3));
		member.setEmail(rs.getString(4));
		member.setRegion(rs.getString(5));
		member.setSex(rs.getString(6));
		member.setPhone1(rs.getString(7));
		member.setPhone2(rs.getString(8));
		member.setPhone3(rs.getString(9));
		member.setBirthday(rs.getString(10));

		return member;
	}
}
