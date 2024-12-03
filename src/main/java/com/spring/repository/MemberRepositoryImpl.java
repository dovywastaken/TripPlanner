package com.spring.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private JdbcTemplate template;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createMember(Member member) 
    {
        String sql = "INSERT INTO t_member (id, name, pw, region, sex, phone1, phone2, phone3, birthday) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        	template.update(sql, 
                    member.getId(), member.getName(), member.getPw(), member.getRegion(),
                    member.getSex(), member.getPhone1(), member.getPhone2(), member.getPhone3(),
                    member.getBirthday());
    }

    @SuppressWarnings("deprecation")
    @Override
    public Member findById(String id) {
        String sql = "SELECT * FROM t_member WHERE id = ?";
        try {
        	System.out.println("findById 함수 실행");
            return template.queryForObject(sql, new Object[]{id}, new MemberMapper());
        } catch (Exception e) {
        	System.out.println("에러 발생");
            return null; // 예외가 발생하면 null 반환 (회원이 없거나 오류 발생)
        }
    }

	
    @Override
	public void updateMember(Member member) 
	{
		String sql = "update t_member set pw = ?, region = ?, phone1 = ?, phone2 = ?, phone3 = ? where id = ?";
		template.update(sql, member.getPw(), member.getRegion(),member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getId());
	}

	
    @Override
	public void deleteMember(Member member) 
	{
		String sql = "delete from t_member where id = ?";
		template.update(sql, member.getId());
	}

	@Override
	public List<Member> readAllMember() 
	{
		String sql = "select * from t_member";
		List<Member> listOfBooks = template.query(sql, new MemberMapper());
		System.out.println(listOfBooks.size());
		
		return listOfBooks;
	}
    
	@Override
	public List<Member> readAllMemberSorted(String sortBy) {
	    String sql = "SELECT * FROM t_member ORDER BY " + sortBy;
	    
	    return template.query(sql, new MemberMapper());
	}


}
