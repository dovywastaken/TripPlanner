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
        this.template = new JdbcTemplate(dataSource);
    }

    //Create
    
    @Override
    public void createMember(Member member) 
    {
        String sql = "INSERT INTO t_member (name, id, pw, email, region, sex, phone1, phone2, phone3, birthday) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, 
        			member.getName(), member.getId(), member.getPw(), member.getEmail(),
        			member.getRegion(), member.getSex(), 
                    member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getBirthday());
    }

    //Read
    
    @SuppressWarnings("deprecation")
    @Override
    public Member findById(String id) 
    {
        String sql = "SELECT * FROM t_member WHERE id = ?";
        try {
            return template.queryForObject(sql, new Object[]{id}, new MemberMapper());
        } catch (Exception e) {
            return null; // 예외가 발생하면 null 반환
        }
    }
    
    @Override
    public List<Member> readAllMember() 
    {
        String sql = "SELECT * FROM t_member order by name";
        return template.query(sql, new MemberMapper());
    }

    @Override
    public List<Member> searchMember(String name) 
    {
        String sql = "SELECT * FROM t_member WHERE name LIKE ? order by name";
        String searchName = "%" + name + "%";
        return template.query(sql, new Object[]{searchName}, new MemberMapper());
    }

    //Update
    
    @Override
    public void updateMember(Member member) 
    {
        String sql = "UPDATE t_member SET pw = ?, region = ?, phone1 = ?, phone2 = ?, phone3 = ? WHERE id = ?";
        template.update(sql, member.getPw(), member.getRegion(), member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getId());
    }

    //Delete
    
    @Override
    public void deleteMember(Member member) 
    {
        String sql = "DELETE FROM t_member WHERE id = ?";
        template.update(sql, member.getId());
    }
}
