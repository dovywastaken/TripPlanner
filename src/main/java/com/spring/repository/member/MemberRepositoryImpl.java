package com.spring.repository.member;

import java.sql.Date;
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

    //db연결
    @Autowired
    public void setJdbcTemplate(DataSource dataSource) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : setJdbcTemplate 메서드 호출]");
    	System.out.println("db연결");
        this.template = new JdbcTemplate(dataSource);
    }

    //Create
    
    //회원가입
    @Override
    public void createMember(Member member) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : createMember 메서드 호출]");
        String sql = "INSERT INTO members (name, id, pw, email, region, sex, phone1, phone2, phone3, birthday, emailCheck, registerDate ,loginDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?)";  
        Date date = new Date(System.currentTimeMillis());
        template.update(sql, 
        			member.getName(), member.getId(), member.getPw(), member.getEmail(), member.getRegion(), 
        			member.getSex(), member.getPhone1(), member.getPhone2(), member.getPhone3(),member.getBirthday(),
                    date, date);
        System.out.println("폼태그에 작성한 데이터를 dto에 넣고 그것을 db에 집어넣습니다");
        System.out.println("[MemberRepository : createMember 메서드 종료]");
    }
    
  //회원가입 시 입력한 아이디가 db에 존재해서 리턴이 false가 된다면 중복된 아이디를 표시
  	@Override
  	public boolean idCheckUp(String id) 
  	{
  		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : checkUp 메서드 호출]");
  	    String sql = "select count(*) from members where id = ?";
  	    int count = template.queryForObject(sql, Integer.class, id);
  	    System.out.println("총 " + count + "개의 중복된 아이디가 있습니다");
  	    return count >= 1;  // 이미 존재하는 값이 있으면 true
  	}


  //회원가입 시 입력한 아이디가 db에 존재해서 리턴이 false가 된다면 중복된 아이디를 표시
  	@Override
  	public boolean emailCheckUp(String email) 
  	{
  		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : checkUp 메서드 호출]");
  	    String sql = "select count(*) from members where email = ?";
  	    int count = template.queryForObject(sql, Integer.class, email);
  	    System.out.println("총 " + count + "개의 중복된 이메일이 있습니다");
  	    return count >= 1;  // 이미 존재하는 값이 있으면 true
  	}
    

    //Read

    //로그인 시 입력한 아이디에 맞는 아이디를 db에서 가져오기
    @Override
    public Member findById(String id)  
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : findById 메서드 호출]");
        String sql = "SELECT * FROM members WHERE id = ?";
        String updateSql = "UPDATE members SET loginDate = ? WHERE id = ?";
        Date date = new Date(System.currentTimeMillis());
        try {
        	System.out.println("입력한 id에 맞는 dto를 가져옵니다");
        	System.out.println("들고온 아이디는 " + id);
        	template.update(updateSql,date,id);
        	System.out.println("[MemberRepository : findById 메서드 종료]");
            return template.queryForObject(sql, new MemberMapper(), new Object[]{id});
        } catch (Exception e) {
        	System.out.println("[MemberRepository : findById 메서드 예외발생]");
            return null; // 예외가 발생하면 null 반환
        }
    }
    
    //어드민 페이지에서 검색한 멤버 찾아오기
    @Override
    public List<Member> searchMember(int limit, int offset, String name) // 검색으로 조회
    {
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        System.out.println("[MemberRepository : searchMember 메서드 호출]");
        String sql = "SELECT * FROM members WHERE name LIKE ? ORDER BY name LIMIT ? OFFSET ?";
        String searchName = "%" + name + "%";
        System.out.println("입력한 이름에 맞는 dto 가져옵니다");

        System.out.println("[MemberRepository : searchMember 메서드 종료]");
        return template.query(sql, new MemberMapper(), searchName, limit, offset);
    }

    //어드민 페이지 내 모든 회원 목록 가져오기
    @Override
    public List<Member> readAllMemberPaging(int limit, int offset) // 페이징 적용 전부 조회
    {
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        System.out.println("[MemberRepository : readAllMemberPaging 메서드 호출]");
        String sql = "SELECT * FROM members ORDER BY name LIMIT ? OFFSET ?";

        System.out.println("[MemberRepository : readAllMemberPaging 메서드 종료]");
        return template.query(sql, new MemberMapper(), limit, offset);
    }

    //어드민 페이지 페이징을 위해 검색어 여부에 따른 회원 숫자 가져오기
	@Override
	public int getTotalMemberCount(String keyword)  //페이징 내비게이션 숫자 처리
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : getTotalMemberCount 메서드 호출]");
		String sql = "select count(*) from members";
		String sql2 = "select count(*) from members where name like ?";
		
		if(keyword == null || keyword.isEmpty()) //검색어 입력 안했으면 테이블 내 모든 회원의 수 계산해서 리턴
		{
			System.out.println("검색어가 없습니다");
			System.out.println("[MemberRepository : getTotalMemberCount 메서드 종료]");
			return template.queryForObject(sql, Integer.class); 
			//Integer.class는 queryForObject로 들고온 데이터 값을 Integer 클래스로 바꿔주고 자동으로 나중에 데이터 타입을 int로 형변환 시켜주는 역할을 한다
			//이게 없으면 리턴값이 int 타입이 아니라 Object가 되기 때문에 또 캐스팅하거나 에러가 날 수 있다
		}else //검색어 입력했으면 테이블 내 keyword가 이름인 목록 전부 계산해서 리턴
		{
			System.out.println(keyword + "로 목록을 띄웁니다");
			String searchKeyword = "%" + keyword + "%";
			System.out.println("[MemberRepository : getTotalMemberCount 메서드 종료]");
			return template.queryForObject(sql2, Integer.class, searchKeyword);
		}
	}

	//비밀번호 변경 페이지에서 현재 비밀번호 확인 할 때 쓰는 메서드
	@Override
	public String getPasswordById(String id) 
	{
	    String sql = "select pw from members where id = ?";
	    try 
	    {
	        return template.queryForObject(sql, String.class,new Object[]{id});
	    } 
	    catch (Exception e) 
	    {
	        return null; // 해당 id가 없을 경우 null 반환
	    }
	}
	
	
    //Update
   
	//회원 개인정보 변경
	@Override
	public void updateMember(Member member) 
	{
	    System.out.println("+++++++++++++++++++++++++++++++++++++++");
	    System.out.println("[MemberRepository : updateMember 메서드 호출]");
	    
	    // 기존 이메일 가져오기
	    String existingEmailSql = "SELECT email FROM members WHERE id = ?";
	    String existingEmail = template.queryForObject(existingEmailSql, String.class, member.getId());

	    // 이메일 변경 여부 확인
	    int emailCheck = existingEmail.equals(member.getEmail()) ? 1 : 0;
	    member.setEmailCheck(emailCheck); // DTO에 emailCheck 값 설정

	    // 회원 정보 업데이트 쿼리
	    String sql = "UPDATE members SET email = ?, region = ?, phone1 = ?, phone2 = ?, phone3 = ?, emailCheck = ? WHERE id = ?";    
	    template.update(sql, member.getEmail(), member.getRegion(), member.getPhone1(), member.getPhone2(), member.getPhone3(), emailCheck, member.getId());

	    System.out.println("이메일 변경 여부: " + (emailCheck == 1 ? "변경 없음" : "변경됨"));
	    System.out.println("업데이트된 정보: " + member.getEmail() + ", " + member.getRegion() + ", " + member.getPhone1() + ", " + member.getPhone2() + ", " + member.getPhone3() + ", emailCheck=" + emailCheck);
	    System.out.println("[MemberRepository : updateMember 메서드 종료]");
	}

	
	//회원 비밀번호 변경
	public void updatePw(String pw, String id) 
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : updatePw 메서드 호출]");
    	String sql = "UPDATE members SET pw = ? WHERE id = ?";    
        template.update(sql, pw, id); //아이디에 맞는 비밀번호만 수정
        
        System.out.println("[MemberRepository : updatePw 메서드 종료]");
	}

	//회원 이메일 인증
	@Override
	public void updateEmail(String id) 
	{
		String sql = "UPDATE members SET emailCheck = 1 WHERE id = ?"; 
		System.out.println("이메일 update sql문"+sql + id);
        template.update(sql,id);
        System.out.println("update sql문 실행");
	}
	
    //Delete
    
	//회원 탈퇴
    @Override
    public void deleteMember(Member member) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++");
    	System.out.println("[MemberRepository : deleteMember 메서드 호출]");
        String sql = "DELETE FROM members WHERE id = ?";
        template.update(sql, member.getId());
        System.out.println("로그인한 사용자의 정보를 삭제합니다");
        System.out.println("[MemberRepository : deleteMember 메서드 종료]");
    }
}
