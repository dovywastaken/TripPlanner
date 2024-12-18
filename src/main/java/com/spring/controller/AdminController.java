package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.Member;
import com.spring.service.MemberService;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
    @Autowired
    private MemberService memberService;

    
//==========================================================================================================================================================
    
   //1. page의 값은 전체 회원 수에서 회원 표시 수를 나눈 값으로 만약 회원의 수가 총 100명이고 표시 수가 10이라면 페이지의 값은 10이 된다.
   //	기본 값은 반드시 1로 설정해야 offset의 값이 음수가 된다. *
   //2. 키워드는 검색창에서 가져올 값을 의미한다
    
    //*offset은 페이지에 보여줄 회원의 순서를 의미한다. offset이 0이면 1번째 회원부터 5면 6번째 회원부터 페이지에 표시를 한다
    @GetMapping("/dashboard") // 어드민 페이지 메인 화면 및 검색 처리
    public String dashboard(@RequestParam(value = "page", defaultValue = "1") int page, //1번
                            @RequestParam(value = "keyword", defaultValue = "") String keyword, //2번
                            Model model) 
    {
        System.out.println("===========================================================================================");
        System.out.println("AdminController : admin/dashboard 로 매핑");
        int limit = 5; // 한 페이지에 표시할 회원 수

        // 키워드가 비어 있으면 null로 설정
        String searchKeyword = null; //일단 검색어를 null로 설정하고
        if (!keyword.isEmpty()) { //만약 검색어가 있다면 검색어를 searchKeyword 값에 대입한다
            searchKeyword = keyword;
        }

        // 페이징 데이터 설정
        setPagingData(model, page, searchKeyword, limit); //이렇게 되면 멤버 리스트, 페이지의 값, 키워드의 값, 한 페이지당 보여줄 회원수를 해당 함수에 파라미터로 전달한다
        
        System.out.println("AdminController : admin.jsp 로 이동");
        return "admin";
    }

//==========================================================================================================================================================

    private void setPagingData(Model model, int page, String keyword, int limit) //들고온 회원의 수에 따라 페이징 처리를 해주는 함수
    {
        System.out.println("===========================================================================================");
        System.out.println("AdminController : setPagingData 메서드 호출");
        int offset = (page - 1) * limit; // 몇 번 째 회원부터 페이지에 표시할 지를 정해준다
        List<Member> memberList; //회원 명단을 담을 리스트를 하나 만들어준다

        // 검색어 유무에 따라 데이터를 가져옴
        if (keyword == null) //만약 검색어가 없다면
        {
            memberList = memberService.readAllMemberPaging(limit, offset); //모든 회원 정보를 리스트에 저장하고
        } 
        else //검색어가 있으면
        {
            memberList = memberService.searchMember(limit, offset,keyword); //검색어에 맞는 회원 정보 리스트를 저장한다
        }

        int totalMemberCount = memberService.getTotalMemberCount(keyword); // 검색어에 부합하는 회원의 수를 구하고
        int totalPages = (int) Math.ceil((double) totalMemberCount / limit); // 회원의 수에서 limit를 나눠 페이지가 얼마나 나올지 계산한다

        model.addAttribute("memberList", memberList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        System.out.println("AdminController : setPagingData 메서드 종료");
    }
}
