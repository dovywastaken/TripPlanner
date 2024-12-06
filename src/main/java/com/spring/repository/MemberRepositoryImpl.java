package com.spring.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository 
{
    private JdbcTemplate template;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) 
    {
    	System.out.println("[MemberRepository : setJdbcTemplate 메서드 호출]");
    	System.out.println("db연결");
        this.template = new JdbcTemplate(dataSource);
    }

    //Create
    
    @Override
    public void createMember(Member member) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : createMember 메서드 호출]");
        String sql = "INSERT INTO t_member (name, id, pw, email, region, sex, phone1, phone2, phone3, birthday) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
       
        template.update(sql, 
        			member.getName(), member.getId(), member.getPw(), member.getEmail(),
        			member.getRegion(), member.getSex(), 
                    member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getBirthday());
        System.out.println("폼태그에 작성한 데이터를 dto에 넣고 그것을 db에 집어넣습니다");
        System.out.println("[MemberRepository : createMember 메서드 종료]");
    }

    //Read
    
    @SuppressWarnings("deprecation")
    @Override
    public Member findById(String id) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : findById 메서드 호출]");
        String sql = "SELECT * FROM t_member WHERE id = ?";
        try {
        	System.out.println("입력한 id에 맞는 dto를 가져옵니다");
        	System.out.println("[MemberRepository : findById 메서드 종료]");
            return template.queryForObject(sql, new Object[]{id}, new MemberMapper());
        } catch (Exception e) {
        	System.out.println("[MemberRepository : findById 메서드 예외발생]");
            return null; // 예외가 발생하면 null 반환
        }
    }
    
    @Override
    public List<Member> readAllMember() 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : readAllMember 메서드 호출]");
        String sql = "SELECT * FROM t_member order by name";
        
        System.out.println("모든 Member를 dto 가져옵니다");
        System.out.println("[MemberRepository : readAllMember 메서드 종료]");
        return template.query(sql, new MemberMapper());
    }

    @Override
    public List<Member> searchMember(String name) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : searchMember 메서드 호출]");
        String sql = "SELECT * FROM t_member WHERE name LIKE ? order by name";
        String searchName = "%" + name + "%";
        System.out.println("입력한 이름에 맞는 dto 가져옵니다");
        
        System.out.println("[MemberRepository : searchMember 메서드 종료]");
        return template.query(sql, new Object[]{searchName}, new MemberMapper());
    }

    //Update
    
    @Override
    public void updateMember(Member member) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : updateMember 메서드 호출]");
        String sql = "UPDATE t_member SET pw = ?, region = ?, phone1 = ?, phone2 = ?, phone3 = ? WHERE id = ?";    
        template.update(sql, member.getPw(), member.getRegion(), member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getId());
        System.out.println("로그인한 사용자의 정보를 수정하도록 dto에서 변수값을 가져옵니다");
        
        System.out.println("[MemberRepository : updateMember 메서드 종료]");
    }

    //Delete
    
    @Override
    public void deleteMember(Member member) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : deleteMember 메서드 호출]");
        String sql = "DELETE FROM t_member WHERE id = ?";
        template.update(sql, member.getId());
        System.out.println("로그인한 사용자의 정보를 삭제합니다");
        System.out.println("[MemberRepository : deleteMember 메서드 종료]");
    }
}
