package com.spring.repository;

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

    @Override
    public Member findById(String id) {
        String sql = "SELECT * FROM t_member WHERE id = ?";
        try {
            return template.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Member member = new Member();
                member.setId(rs.getString("id"));
                member.setPw(rs.getString("pw"));
                member.setName(rs.getString("name"));
                member.setRegion(rs.getString("region"));
                member.setSex(rs.getString("sex"));
                member.setPhone1(rs.getString("phone1"));
                member.setPhone2(rs.getString("phone2"));
                member.setPhone3(rs.getString("phone3"));
                member.setBirthday(rs.getString("birthday"));
                return member;
            });
        } catch (Exception e) {
            return null; // 예외가 발생하면 null 반환 (회원이 없거나 오류 발생)
        }
    }
    
    
}
