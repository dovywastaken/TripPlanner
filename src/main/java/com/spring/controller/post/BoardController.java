package com.spring.controller.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.domain.Tour;
import com.spring.service.post.BoardService;

@Controller
@RequestMapping("/board")
@PropertySource("classpath:properties/API.key.properties") 
public class BoardController 
{
	PaginationHelper paginationHelper = new PaginationHelper();

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService boardService;
	
	private void setBoardModelAttributes(Map<String,Object> result, int page, Model model)
	{
        logger.info("setBoardModelAttributes 호출. 현재 페이지: {}", page);
        logger.debug("전달받은 결과 맵: {}", result); // result 맵 전체는 DEBUG 레벨로

	    List<Post> postList = (List<Post>) result.get("postList");
	    int postSize = (int)result.get("postSize");
        logger.info("조회된 게시글 목록 (postList) 크기: {}, 전체 게시글 수 (postSize): {}", postList != null ? postList.size() : "null", postSize);


        // 페이지당 게시글 수(10)는 상수로 관리하는 것이 좋습니다.
	    ArrayList<Integer> getTotalPages = paginationHelper.getTotalPages(postSize, 10);
	    ArrayList<Integer> getpostnumber = paginationHelper.getpostnumber(postSize, page, 10);
        logger.info("총 페이지 정보 (getTotalPages): {}, 현재 페이지의 게시글 번호 목록 (getpostnumber): {}", getTotalPages, getpostnumber);


	    DateFormatter dateFormatter = new DateFormatter(); // DateFormatter 인스턴스 생성
	    ArrayList<String> date = new ArrayList<String>(); // 날짜 목록 초기화
	    for (Post post : postList)
	    {
	           date.add(dateFormatter.formatBoardDate(post.getPublishDate()));
	    }

	    // System.out.println("postList size: " + postList.size()); // 로그 출력 추가
	    logger.info("처리된 postList size: {}", postList.size());
	    // System.out.println("date list size: " + date.size());   // 로그 출력 추가
	    logger.info("처리된 date list size: {}", date.size());
        logger.debug("포맷팅된 날짜 목록: {}", date); // 날짜 목록 상세 정보는 DEBUG 레벨로


	    model.addAttribute("date", date);
	    model.addAttribute("getTotalPages", getTotalPages);
	    model.addAttribute("getpostnumber", getpostnumber);
	    model.addAttribute("postList", postList);
        logger.info("Model에 게시글 관련 속성 추가 완료.");
        logger.debug("Model attributes: date={}, getTotalPages={}, getpostnumber={}, postList size={}",
                     date, getTotalPages, getpostnumber, postList.size()); // 모델 속성 상세 로깅

	}
	
	
	 @GetMapping("/all")
		public String allBoard(@RequestParam(value = "page",defaultValue = "1")int page, Model model)
		{ //페이지 파라미터 받아오기
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/all(GET)으로 매핑되어 전체 글 목록을 표시할 준비가 되었습니다.");
	        logger.info("BoardController : board/all (GET) 매핑. 전체 게시글 목록 표시 준비 완료. 요청 페이지: {}", page);

			Map<String,Object> result = boardService.allBoard(page);
			// System.out.println("db에서 들고온 전체 게시글 수 : " + result.get("postSize"));
			logger.info("DB에서 조회된 전체 게시글 수 (result.get(\"postSize\")): {}", result.get("postSize"));

			setBoardModelAttributes(result,page,model); // 이 메소드의 내부 로깅은 별도로 처리되어야 합니다.

			int totalPosts = (int) result.get("postSize");
			// System.out.println("전체 게시글 수 : "+ totalPosts);
			logger.info("최종적으로 계산된 전체 게시글 수: {}", totalPosts);

	        // 페이지 당 게시글 수와 페이지 블록 크기는 상수로 관리하거나 설정에서 가져오는 것이 좋습니다.
	        int postsPerPage = 10;
	        int pageBlockSize = 5;
			Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, postsPerPage, pageBlockSize);

			model.addAttribute("currentPage", page);
			model.addAttribute("pagination", pagination);

			// System.out.println("게시글 날짜 : " + model.getAttribute("date"));
			logger.debug("게시글 날짜 (model.getAttribute(\"date\")): {}", model.getAttribute("date"));
			// System.out.println("총 페이지 갯수 : " + model.getAttribute("getTotalPages"));
			logger.info("총 페이지 갯수 (model.getAttribute(\"getTotalPages\")): {}", model.getAttribute("getTotalPages"));
			// System.out.println("getPostNumber로 가져온 값 : " + model.getAttribute("getpostnumber"));
			logger.debug("getPostNumber로 가져온 값 (model.getAttribute(\"getpostnumber\")): {}", model.getAttribute("getpostnumber"));
			// System.out.println("총 게시글 리스트 : " + model.getAttribute("postList"));
			logger.debug("총 게시글 리스트 (model.getAttribute(\"postList\")): {}", model.getAttribute("postList")); // 리스트 내용은 DEBUG 레벨로
			// System.out.println("페이지 번호 : " + page);
			logger.info("현재 페이지 번호: {}", page);
			// System.out.println("페이지네이션 : " + pagination);
			logger.info("페이지네이션 정보: {}", pagination); // 페이지네이션 맵 전체를 INFO 레벨로

	        logger.info("board/allBoard 뷰로 이동합니다.");
			return "board/allBoard";
		}
	
	
	 @GetMapping("/all/search")
		public String searchBoard(@RequestParam(value = "page", defaultValue = "1") int page,
		                          @RequestParam("type") String type,
		                          @RequestParam("keyword") String keyword,
		                          Model model)
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/all/search(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/all/search (GET) 매핑. 검색 요청. 페이지: {}, 타입: {}, 키워드: {}", page, type, keyword);

		    Map<String, Object> result = boardService.allBoardSearch(type, keyword, page);
		    // System.out.println("db에서 가져온 결과 값" + result);
		    logger.debug("DB에서 가져온 검색 결과: {}", result); // 검색 결과 맵 전체는 DEBUG 레벨로 상세 로깅

		    setBoardModelAttributes(result, page, model); // 이 메소드 내부 로깅은 별도 처리
		    int totalPosts = (int) result.get("postSize");
	        logger.info("검색 결과로 조회된 전체 게시글 수: {}", totalPosts);

	        // 페이지 당 게시글 수와 페이지 블록 크기는 상수로 관리하거나 설정에서 가져오는 것이 좋습니다.
	        int postsPerPage = 10;
	        int pageBlockSize = 5;
		    Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, postsPerPage, pageBlockSize);
		    logger.info("페이지네이션 정보 생성 완료.");

		    model.addAttribute("type", type);
		    model.addAttribute("keyword", keyword);
		    model.addAttribute("currentPage", page);
		    model.addAttribute("pagination", pagination);
	        logger.info("Model에 검색 관련 속성 추가 완료. 뷰 이름: board/allBoardSearch");
	        logger.debug("Model attributes: type={}, keyword={}, currentPage={}, pagination={}", type, keyword, page, pagination); // 모델 속성 상세 로깅

		    return "board/allBoardSearch";
		}
	
	 @GetMapping("/hot")
		public String toHotBoard(@RequestParam(value = "page",defaultValue = "1")int page, Model model)
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/hot(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/hot (GET) 매핑. 인기 게시글 목록 표시 준비 완료. 요청 페이지: {}", page);

			int size = 10;
	        logger.info("인기 게시글 조회 서비스 호출. 페이지 당 개수: {}, 요청 페이지: {}", size, page);
			Map<String,Object> result=boardService.hotBoard(size, page); //
	        logger.debug("boardService.hotBoard 결과: {}", result); // result 맵 전체는 DEBUG 레벨로

			setBoardModelAttributes(result,page,model);
	        logger.info("setBoardModelAttributes 메소드 호출 완료.");

			int totalPosts = (int) result.get("postSize");
	        logger.info("인기 게시글 전체 개수: {}", totalPosts);

	        // 페이지 당 게시글 수와 페이지 블록 크기는 상수로 관리하거나 설정에서 가져오는 것이 좋습니다.
	        int postsPerPage = 10;
	        int pageBlockSize = 5;
			Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, postsPerPage, pageBlockSize);
	        logger.info("페이지네이션 정보 생성 완료: {}", pagination);

			model.addAttribute("currentPage", page);
			model.addAttribute("pagination", pagination);
	        logger.info("Model에 'currentPage' ({}) 및 'pagination' 속성 추가 완료. 뷰 이름: board/hotBoard", page);

			return "board/hotBoard";
		}
	
	
	 @GetMapping("/myBoard")
		public String toMyBoard(HttpSession session, Model model, @RequestParam(value = "page", defaultValue = "1") int page)
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/myBoard(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/myBoard (GET) 매핑. 내 게시글 목록 표시 준비 완료. 요청 페이지: {}", page);

			Member member = (Member)session.getAttribute("user");
			if(member != null)
			{
	            logger.info("세션에서 로그인 사용자 정보 확인됨: {}", member.getEmail());
				Map<String,Object> result = boardService.myBoard(member.getEmail(), page);
	            logger.debug("boardService.myBoard 결과: {}", result); // result 맵 전체는 DEBUG 레벨로

				int Allpostgetnum = (int) result.get("postSize");
	            logger.info("내 게시글 전체 개수: {}", Allpostgetnum);

	            // 페이지 당 게시글 수와 페이지 블록 크기는 상수로 관리하거나 설정에서 가져오는 것이 좋습니다.
	            int postsPerPage = 10;
	            int pageBlockSize = 5;
				Map<String, Object> pagination = paginationHelper.getPagination(page, Allpostgetnum, postsPerPage, pageBlockSize);
	            logger.info("페이지네이션 정보 생성 완료: {}", pagination);

				setBoardModelAttributes(result, page, model); // 이 메소드 내부 로깅은 별도 처리
	            logger.info("setBoardModelAttributes 메소드 호출 완료.");

				model.addAttribute("pagination", pagination);
				model.addAttribute("currentPage", page);
	            logger.info("Model에 'pagination' 및 'currentPage' ({}) 속성 추가 완료. 뷰 이름: board/myBoard", page);

				return "board/myBoard";
			}
			else
			{
	            logger.warn("비로그인 사용자의 '내 게시글' 접근 시도. errorPage로 리다이렉트합니다.");
	            // 실제 애플리케이션에서는 로그인 페이지로 리다이렉트하거나 적절한 에러 메시지를 보여주는 것이 좋습니다.
	            // 예: return "redirect:/login";
				return "errorPage";
			}
		}
	
	
	
	 @GetMapping("/myBoard/search")
		public String mysearchBoard(@RequestParam(value = "page", defaultValue = "1") int page,
		                          @RequestParam("keyword") String keyword,
		                          HttpSession session,
		                          Model model
		                          )
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/myBoard/search(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/myBoard/search (GET) 매핑. 내 게시글 검색 요청. 페이지: {}, 키워드: {}", page, keyword);

		    if(session != null) { // session 자체가 null인 경우는 드물고, session.getAttribute("user")가 null인 경우가 일반적입니다.
	            Member member = (Member)session.getAttribute("user");
	            if (member == null) {
	                logger.warn("비로그인 사용자의 '내 게시글 검색' 접근 시도. errorPage로 리다이렉트합니다.");
	                return "errorPage"; // 또는 "redirect:/login"
	            }
	            logger.info("세션에서 로그인 사용자 정보 확인됨: {}", member.getEmail());

		        String id = member.getEmail();
	            logger.info("사용자 '{}'의 게시글 검색 서비스 호출. 키워드: {}, 페이지: {}", id, keyword, page);
		        Map<String, Object> result = boardService.myBoardSearch(id, keyword, page);
	            logger.debug("boardService.myBoardSearch 결과: {}", result); // 검색 결과 맵 전체는 DEBUG 레벨로

		        setBoardModelAttributes(result, page, model); // 이 메소드 내부 로깅은 별도 처리
	            logger.info("setBoardModelAttributes 메소드 호출 완료.");

		        int totalPosts = (int) result.get("postSize");
	            logger.info("검색 결과로 조회된 내 게시글 총 개수: {}", totalPosts);

	            // 페이지 당 게시글 수와 페이지 블록 크기는 상수로 관리하거나 설정에서 가져오는 것이 좋습니다.
	            int postsPerPage = 10;
	            int pageBlockSize = 5;
		        Map<String, Object> pagination = paginationHelper.getPagination(page, totalPosts, postsPerPage, pageBlockSize);
	            logger.info("페이지네이션 정보 생성 완료: {}", pagination);

		        model.addAttribute("keyword", keyword);
		        model.addAttribute("currentPage", page);
		        model.addAttribute("pagination", pagination);
	            logger.info("Model에 검색 관련 속성 추가 완료. 뷰 이름: board/myBoardSearch");
	            logger.debug("Model attributes: keyword={}, currentPage={}, pagination={}", keyword, page, pagination); // 모델 속성 상세 로깅

		        return "board/myBoardSearch";
		    } else { // 이 else 블록은 사실상 member == null 인 경우와 동일하게 동작할 가능성이 높습니다.
	            logger.warn("세션 자체가 null이거나 예상치 못한 비로그인 상태. errorPage로 리다이렉트합니다.");
				return "errorPage";
			}
		}

	
	
	 @GetMapping("/festival")
		public String toBoardFestival(@RequestParam(value = "page", defaultValue = "1") int page, Model model, HttpSession session)
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/festival(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/festival (GET) 매핑. 요청 페이지: {}", page);

		    int limit = 12; // 한 페이지당 표시할 관광지 수
		    int offset = (page - 1) * limit;
		    String type = "15"; // 축제 contenttypeid
	        logger.info("축제 목록 조회 준비. limit: {}, offset: {}, type: {}", limit, offset, type);

		    // DB에서 전체 관광지 리스트를 가져옴
		    List<Tour> festivals = boardService.hotSpots(type, limit, offset);
		    // System.out.println("축제(15): " + festivals.size() + "개");
		    logger.info("축제 (type: {}) 조회 완료. {}개 항목 발견.", type, festivals.size());
	        logger.debug("조회된 축제 목록: {}", festivals); // 목록 내용은 DEBUG 레벨로

		    // 모델에 각 카테고리별 리스트 추가
		    model.addAttribute("member", new Member()); // 빈 Member 객체를 추가하는 목적을 확인해 보세요. 로그인 정보가 필요하다면 session에서 가져와야 합니다.
		    model.addAttribute("festivals", festivals);
		    model.addAttribute("totalCount", festivals.size());
	        logger.info("Model에 'member', 'festivals' ({}개), 'totalCount' ({}) 속성 추가 완료. 뷰 이름: board/boardFestival", festivals.size(), festivals.size());

		    return "board/boardFestival";
		}

		@GetMapping("/tour")
		public String toBoardTour(@RequestParam(value = "page", defaultValue = "1") int page, Model model)
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/tour(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/tour (GET) 매핑. 요청 페이지: {}", page);

		    int limit = 12; // 한 페이지당 표시할 관광지 수
		    int offset = (page - 1) * limit;
		    String type = "12"; // 관광지 contenttypeid
	        logger.info("관광지 목록 조회 준비. limit: {}, offset: {}, type: {}", limit, offset, type);

		    // DB에서 전체 관광지 리스트를 가져옴
		    List<Tour> tourSpots = boardService.hotSpots(type, limit, offset);
		    logger.info("관광지 (type: {}) 조회 완료. {}개 항목 발견.", type, tourSpots.size());
	        logger.debug("조회된 관광지 목록: {}", tourSpots); // 목록 내용은 DEBUG 레벨로

		    // 모델에 각 카테고리별 리스트 추가
		    model.addAttribute("tourSpots", tourSpots);
		    model.addAttribute("totalCount", tourSpots.size());
	        logger.info("Model에 'tourSpots' ({}개), 'totalCount' ({}) 속성 추가 완료. 뷰 이름: board/boardTour", tourSpots.size(), tourSpots.size());

		    return "board/boardTour";
		}

		@GetMapping("/restaurant")
		public String toBoardRestaurant(@RequestParam(value = "page", defaultValue = "1") int page, Model model)
		{
			// System.out.println("===========================================================================================");
	        logger.info("===========================================================================================");
	        // System.out.println("BoardController : board/restaurant(GET)으로 매핑되었습니다.");
	        logger.info("BoardController : board/restaurant (GET) 매핑. 요청 페이지: {}", page);

		    int limit = 12; // 한 페이지당 표시할 관광지 수
		    int offset = (page - 1) * limit;
		    String type = "39"; // 음식점 contenttypeid
	        logger.info("음식점 목록 조회 준비. limit: {}, offset: {}, type: {}", limit, offset, type);

		    // DB에서 전체 관광지 리스트를 가져옴
		    List<Tour> restaurants = boardService.hotSpots(type, limit, offset);
		    logger.info("음식점 (type: {}) 조회 완료. {}개 항목 발견.", type, restaurants.size());
	        logger.debug("조회된 음식점 목록: {}", restaurants); // 목록 내용은 DEBUG 레벨로

		    // 모델에 각 카테고리별 리스트 추가
		    model.addAttribute("restaurants", restaurants);
		    model.addAttribute("totalCount", restaurants.size());
	        logger.info("Model에 'restaurants' ({}개), 'totalCount' ({}) 속성 추가 완료. 뷰 이름: board/boardRestaurant", restaurants.size(), restaurants.size());

		    return "board/boardRestaurant";
		}
	
	@Value("${TourAPI.key}")
	private String tourAPIKey;
	
	@GetMapping("/detailedInfo")
	public String toDetailedPage(@RequestParam String contentTypeId, @RequestParam String contentId, Model model)
	{
		// System.out.println("===========================================================================================");
        logger.info("===========================================================================================");
        // System.out.println("BoardController : board/detailedInfo(GET)으로 매핑되었습니다.");
        logger.info("BoardController : board/detailedInfo (GET) 매핑. 상세 정보 요청. contentTypeId: {}, contentId: {}", contentTypeId, contentId);

		model.addAttribute("tourAPIKey", tourAPIKey);
		model.addAttribute("contenttypeid", contentTypeId);
		model.addAttribute("contentid", contentId);
		// System.out.println(model.getAttribute("tourAPIKey"));
		logger.info("Model에 API 키 및 콘텐츠 정보 추가 완료. tourAPIKey: (masked), contenttypeid: {}, contentid: {}", contentTypeId, contentId);
        logger.debug("실제 tourAPIKey 값: {}", tourAPIKey); // API 키 자체는 DEBUG 레벨에서만 로깅하는 것이 보안상 좋습니다.

        logger.info("board/detailedPage 뷰로 이동합니다.");
		return "board/detailedPage";
	}
}

