package com.spring.repository.member;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository 
{
	
	private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryImpl.class);
    private JdbcTemplate template;

    //db연결
    @Autowired
    public void setJdbcTemplate(DataSource dataSource) 
    {
    	logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : setJdbcTemplate 메서드 호출]");
    	logger.info("db연결");

        this.template = new JdbcTemplate(dataSource);
    }

    //Create
    
    //회원가입
    @Override
    public void createMember(Member member) 
    {
    	logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : createMember 메서드 호출]");
        String sql = "INSERT INTO members (nickname, id, pw, phone1, phone2, phone3, birthday, emailCheck, registerDate ,loginDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?)";  
        Date date = new Date(System.currentTimeMillis());
        logger.info("getEmail - Repository : " + member.getEmail());
        template.update(sql, 
        			member.getNickname(), member.getEmail(), member.getPw(),
        			member.getPhone1(), member.getPhone2(), member.getPhone3(),
        			member.getBirthday(), date, date);
        logger.info("폼태그에 작성한 데이터를 dto에 넣고 db에 집어넣기");
        logger.info("[MemberRepository : createMember 메서드 종료]");
    }
    
  //회원가입 시 입력한 아이디가 db에 존재해서 리턴이 false가 된다면 중복된 아이디를 표시
  	@Override
  	public boolean idCheckUp(String id) 
  	{
  		logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : checkUp 메서드 호출]");
  	    String sql = "select count(*) from members where id = ?";
  	    int count = template.queryForObject(sql, Integer.class, id);
  	    logger.info("총 " + count + "개의 중복된 아이디가 있습니다");
  	    return count >= 1;  // 이미 존재하는 값이 있으면 true
  	}
  	
    //Read

    //로그인 시 입력한 아이디에 맞는 아이디를 db에서 가져오기
    @Override
    public Member findById(String id)  
    {
    	logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : findById 메서드 호출]");
    	if(id.trim() == null || id.trim().isEmpty()) 
    	{
    		return null;
    	}
    	
        String sql = "SELECT * FROM members WHERE id = ?";
        String updateSql = "UPDATE members SET loginDate = ? WHERE id = ?";
        Date date = new Date(System.currentTimeMillis());
        try {
        	logger.info("입력한 id에 맞는 dto를 가져옵니다");
        	logger.info("들고온 아이디는 " + id);
        	template.update(updateSql,date,id);
        	logger.info("[MemberRepository : findById 메서드 종료]");
            return template.queryForObject(sql, new MemberMapper(), new Object[]{id});
        } catch (Exception e) {
        	logger.error("[MemberRepository : findById 메서드 예외발생]");
        	e.printStackTrace();
            return null; // 예외가 발생하면 null 반환
        }
    }
    
    //어드민 페이지에서 검색한 멤버 찾아오기
    @Override
    public List<Member> searchMember(int limit, int offset, String email) // 검색으로 조회
    {
        logger.info("+++++++++++++++++++++++++++++++++++++++");
        logger.info("[MemberRepository : searchMember 메서드 호출]");
        String sql = "SELECT * FROM members WHERE id LIKE ? ORDER BY id LIMIT ? OFFSET ?";
        String searchEmail = "%" + email + "%";
        logger.info("입력한 이메일에 맞는 dto 가져옵니다");

        logger.info("[MemberRepository : searchMember 메서드 종료]");
        return template.query(sql, new MemberMapper(), searchEmail, limit, offset);
    }

    //어드민 페이지 내 모든 회원 목록 가져오기
    @Override
    public List<Member> readAllMemberPaging(int limit, int offset) // 페이징 적용 전부 조회
    {
        logger.info("+++++++++++++++++++++++++++++++++++++++");
        logger.info("[MemberRepository : readAllMemberPaging 메서드 호출]");
        String sql = "SELECT * FROM members ORDER BY nickname LIMIT ? OFFSET ?";

        logger.info("[MemberRepository : readAllMemberPaging 메서드 종료]");
        return template.query(sql, new MemberMapper(), limit, offset);
    }

    //어드민 페이지 페이징을 위해 검색어 여부에 따른 회원 숫자 가져오기
	@Override
	public int getTotalMemberCount(String keyword)  //페이징 내비게이션 숫자 처리
	{
		logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : getTotalMemberCount 메서드 호출]");
		String sql = "select count(*) from members";
		String sql2 = "select count(*) from members where nickname like ?";
		
		if(keyword == null || keyword.isEmpty()) //검색어 입력 안했으면 테이블 내 모든 회원의 수 계산해서 리턴
		{
			logger.warn("검색어가 없습니다");
			logger.warn("[MemberRepository : getTotalMemberCount 메서드 종료]");
			return template.queryForObject(sql, Integer.class); 
			//Integer.class는 queryForObject로 들고온 데이터 값을 Integer 클래스로 바꿔주고 자동으로 나중에 데이터 타입을 int로 형변환 시켜주는 역할을 한다
			//이게 없으면 리턴값이 int 타입이 아니라 Object가 되기 때문에 또 캐스팅하거나 에러가 날 수 있다
		}else //검색어 입력했으면 테이블 내 keyword가 이름인 목록 전부 계산해서 리턴
		{
			logger.info(keyword + "로 목록을 띄웁니다");
			String searchKeyword = "%" + keyword + "%";
			logger.info("[MemberRepository : getTotalMemberCount 메서드 종료]");
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
	    	logger.warn("아이디가 존재하지 않습니다.");

	        return null; // 해당 id가 없을 경우 null 반환
	    }
	}
	
	
    //Update
   
	//회원 개인정보 변경
	@Override
	public void updateMember(Member member) 
	{
	    logger.info("+++++++++++++++++++++++++++++++++++++++");
	    logger.info("[MemberRepository : updateMember 메서드 호출]");

	    // 회원 정보 업데이트 쿼리
	    String sql = "UPDATE members SET nickname = ?, phone1 = ?, phone2 = ?, phone3 = ? WHERE id = ?";    
	    template.update(sql, member.getNickname() ,member.getPhone1(), member.getPhone2(), member.getPhone3(),member.getEmail());
	    logger.info("[MemberRepository : updateMember 메서드 종료]");

	}

	
	//회원 비밀번호 변경
	public void updatePw(String pw, String id) 
	{
		logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : updatePw 메서드 호출]");
    	String sql = "UPDATE members SET pw = ? WHERE id = ?";    
        template.update(sql, pw, id); //아이디에 맞는 비밀번호만 수정
        
        logger.info("[MemberRepository : updatePw 메서드 종료]");
	}

	//회원 이메일 인증
	@Override
	public void updateEmail(String id) 
	{
		String sql = "UPDATE members SET emailCheck = 1 WHERE id = ?"; 
		logger.info("이메일 update sql문"+sql + id);
        template.update(sql,id);
        logger.info("update sql문 실행");

	}
	
    //Delete
    
	//회원 탈퇴
    @Override
    public void deleteMember(Member member) 
    {
    	logger.info("+++++++++++++++++++++++++++++++++++++++");
    	logger.info("[MemberRepository : deleteMember 메서드 호출]");
        String sql = "DELETE FROM members WHERE id = ?";
        template.update(sql, member.getEmail());
        logger.info("로그인한 사용자의 정보를 삭제합니다");
        logger.info("[MemberRepository : deleteMember 메서드 종료]");
    }
}
