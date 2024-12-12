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
        String sql = "INSERT INTO t_member (name, id, pw, email, region, sex, phone1, phone2, phone3, birthday, emailCheck) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";  
        template.update(sql, 
        			member.getName(), member.getId(), member.getPw(), member.getEmail(),
        			member.getRegion(), member.getSex(), 
                    member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getBirthday());
        System.out.println("폼태그에 작성한 데이터를 dto에 넣고 그것을 db에 집어넣습니다");
        System.out.println("[MemberRepository : createMember 메서드 종료]");
    }

    //Read

    @Override
    public Member findById(String id)  //로그인 시 사용
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : findById 메서드 호출]");
        String sql = "SELECT * FROM t_member WHERE id = ?";
        try {
        	System.out.println("입력한 id에 맞는 dto를 가져옵니다");
        	System.out.println("[MemberRepository : findById 메서드 종료]");
            return template.queryForObject(sql, new MemberMapper(), new Object[]{id});
        } catch (Exception e) {
        	System.out.println("[MemberRepository : findById 메서드 예외발생]");
            return null; // 예외가 발생하면 null 반환
        }
    }
    
    @Override
    public List<Member> searchMember(String name, int limit, int offset)  //검색으로 조회
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : searchMember 메서드 호출]");
    	String sql = "SELECT * FROM t_member WHERE name LIKE ? ORDER BY name LIMIT ? OFFSET ?";
        String searchName = "%" + name + "%";
        System.out.println("입력한 이름에 맞는 dto 가져옵니다");
        
        System.out.println("[MemberRepository : searchMember 메서드 종료]");
        return template.query(sql, new MemberMapper(), new Object[]{searchName, limit, offset} );
    }
    
    @Override
	public List<Member> readAllMemberPaging(int limit, int offset, String keyword) //페이징 적용 전부 조회
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : readAllMemberPaging 메서드 호출]");
    	String sql = "select * from t_member order by name limit ? offset ?";

    	System.out.println("[MemberRepository : readAllMemberPaging 메서드 종료]");
		return template.query(sql, new MemberMapper(), new Object[] {limit,offset});
	}

	@Override
	public int getTotalMemberCount(String value)  //페이징 내비게이션 숫자 처리
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : getTotalMemberCount 메서드 호출]");
		String sql = "select count(*) from t_member";
		String sql2 = "select count(*) from t_member where name like ?";
		
		if(value == null || value.isEmpty()) 
		{
			System.out.println("검색어가 없습니다");
			System.out.println("[MemberRepository : readAllMemberPaging 메서드 종료]");
			return template.queryForObject(sql, Integer.class); 
			//Integer.class는 queryForObject로 들고온 데이터 값을 Integer 클래스로 바꿔주고 자동으로 나중에 데이터 타입을 int로 형변환 시켜주는 역할을 한다
			//이게 없으면 리턴값이 int 타입이 아니라 Object가 되기 때문에 또 캐스팅하거나 에러가 날 수 있다
		}else 
		{
			System.out.println(value + "로 목록을 띄웁니다");
			System.out.println("[MemberRepository : readAllMemberPaging 메서드 종료]");
			String searchKeyword = "%" + value + "%";
			return template.queryForObject(sql2, Integer.class, searchKeyword);
		}
	}

    //Update
   
	@Override
    public void updateMember(Member member) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : updateMember 메서드 호출]");
        String sql = "UPDATE t_member SET email = ?, region = ?, phone1 = ?, phone2 = ?, phone3 = ? WHERE id = ?";    
        template.update(sql, member.getEmail(), member.getRegion(), member.getPhone1(), member.getPhone2(), member.getPhone3(), member.getId());
        System.out.println("로그인한 사용자의 정보를 수정하도록 dto에서 변수값을 가져옵니다");
        
        System.out.println("[MemberRepository : updateMember 메서드 종료]");
    }
	
	public void updatePw(String pw, String id) 
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : updatePw 메서드 호출]");
    	String sql = "UPDATE t_member SET pw = ? WHERE id = ?";    
        template.update(sql, pw, id); //아이디에 맞는 비밀번호만 수정
        
        System.out.println("[MemberRepository : updatePw 메서드 종료]");
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

    //중복 검사
    
	@Override
	public boolean checkUp(String field, String value) 
	{
		String sql = "select count(*) from t_member where " + field + " = ?";
		int count = template.queryForObject(sql, Integer.class, value);
		if(count == 0) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	@Override
	public String getPasswordById(String id) {
	    String sql = "select pw from t_member where id = ?";
	    try {
	        return template.queryForObject(sql, String.class,new Object[]{id});
	    } catch (Exception e) {
	        return null; // 해당 id가 없을 경우 null 반환
	    }
	}

    
}
