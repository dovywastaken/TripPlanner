package com.spring.repository.member;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.domain.Member;

public class MemberMapper implements RowMapper<Member>
{
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Member member = new Member();
		member.setNickname(rs.getString(1));
		member.setId(rs.getString(2));
		member.setPw(rs.getString(3));
		member.setPhone1(rs.getString(4));
		member.setPhone2(rs.getString(5));
		member.setPhone3(rs.getString(6));
		member.setBirthday(rs.getString(7));
		member.setEmailCheck(rs.getInt(8));
		member.setRegisterDate(rs.getDate(9));
		member.setLoginDate(rs.getDate(10));

		return member;
	}
}
