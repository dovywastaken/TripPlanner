package com.spring.controller.post;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.domain.Likes;
import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.domain.Tour;
import com.spring.service.post.CommentService;
import com.spring.service.post.FileStorageService;
import com.spring.service.post.PostService;

import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PostController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@GetMapping("/postForm")
	public String postForm() 
	{
		logger.info("===========================================================================================");		
	 	logger.info("PostController : postForm(GET)으로 매핑되었습니다");
	 	logger.info("PostController: postForm(GET)으로 매핑되어 postForm.jsp로 이동합니다.");
		return "post/postForm";
	}
		
	@PostMapping("/saveForm")
    public String saveForm(@RequestParam("myListData") String myListData, Model model) 
	{
		logger.info("===========================================================================================");	
		logger.info("PostController : saveForm(POST)으로 매핑되었습니다");
        // JSON 문자열을 필요한 형태로 파싱하거나 처리
        // 여기서는 단순히 모델에 추가
        model.addAttribute("myListData", myListData);
        return "post/postForm";
    }
	

	@PostMapping("/postCreate")
    public String postCreate(@ModelAttribute Post post, // contents, isPrivate 등은 ModelAttribute로 자동 매핑됨
                           HttpSession session,
                           RedirectAttributes redirect)
    {
       logger.info("===========================================================================================");
       logger.info("PostController : postCreate(Post)으로 매핑되었습니다");
       try
       {
           Member member = (Member) session.getAttribute("user");
           // System.out.println("로그인한 회원 dto: " + member);
           logger.info("로그인한 회원 정보: {}", member); // 파라미터화된 로깅 사용 권장
           if (member == null) {
                // 로그인 안된 경우 처리
                logger.warn("postCreate(Post) : 로그인되지 않은 사용자의 접근 시도. 로그인 페이지로 리다이렉트합니다.");
                return "redirect:/login"; // 실제 로그인 페이지 경로로 수정
            }

           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           // System.out.println("postCreate(Post) : Timestamp 생성");
           logger.info("postCreate(Post) : Timestamp 생성됨: {}", timestamp);
           post.setId(member.getEmail());
           // System.out.println("postCreate(Post) : 작성자 ID 설정: " + post.getId());
           logger.info("postCreate(Post) : 작성자 ID 설정: {}", post.getId());
           post.setNickname(member.getNickname());
           // System.out.println("postCreate(Post) : 작성자 닉네임 설정: " + post.getNickname());
           logger.info("postCreate(Post) : 작성자 닉네임 설정: {}", post.getNickname());
           post.setPublishDate(timestamp);
           // System.out.println("postCreate(Post) : 게시일 설정");
           logger.info("postCreate(Post) : 게시일 설정됨");

           // ====[ fileImage 필드 설정 로직 ]====
           // contents HTML에서 서버 저장 파일명(UUID.확장자) 리스트를 추출하여
           // Post 객체의 fileImage 필드(List<String>)에 설정합니다.
           List<String> storedFileNamesFromContent = new ArrayList<>();
           String contentsHtml = post.getContents(); // ModelAttribute를 통해 받아온 contents 사용
           if (contentsHtml != null && !contentsHtml.isEmpty()) {
               // System.out.println("파싱할 HTML 내용: " + contentsHtml);
               logger.debug("파싱할 HTML 내용: {}", contentsHtml); // HTML 내용은 길 수 있으므로 DEBUG 레벨로
               Document doc = Jsoup.parse(contentsHtml);
               Elements images = doc.select("img[src*='/uploads/']");
               //Elements images = doc.select("img[src^='/uploads/']"); // /uploads/로 시작하는 img 태그만 선택
               // System.out.println("postCreate(Post) : 내용에서 이미지 태그 " + images.size() + "개 찾음");
               logger.info("postCreate(Post) : 내용에서 이미지 태그 {}개 찾음", images.size());
               for (Element img : images) {
                   String src = img.attr("src"); // 예: /uploads/uuid.jpg 또는 /testChamber/uploads/uuid.jpg
                   if (src != null && src.contains("/uploads/")) {
                       // 마지막 '/' 뒤의 문자열(파일명)만 추출
                       String filename = src.substring(src.lastIndexOf('/') + 1);
                       storedFileNamesFromContent.add(filename);
                       // System.out.println(" - 추출된 서버 저장 파일명: " + filename);
                       logger.info(" - 추출된 서버 저장 파일명: {}", filename);
                   }
               }
           }
           // 추출한 파일명 리스트를 Post 객체의 fileImage 필드에 설정
           post.setFileImage(storedFileNamesFromContent);
           // System.out.println("postCreate(Post) : Post DTO의 fileImage 필드 설정 완료: " + storedFileNamesFromContent);
           logger.info("postCreate(Post) : Post DTO의 fileImage 필드 설정 완료: {}", storedFileNamesFromContent);
           // ====[ fileImage 필드 설정 로직 끝 ]====

           // 서비스 호출하여 게시글 저장
           // PostRepository는 내부적으로 post.getFileImage()를 호출하여
           // List<String>을 받아 String.join(",")으로 imageNames 컬럼에 저장할 것임
           postService.createPost(post);
           // System.out.println("postCreate(Post) : DB에 게시글 정보 저장 완료 (contents 및 imageNames 포함)");
           logger.info("postCreate(Post) : DB에 게시글 정보 저장 완료 (contents 및 imageNames 포함)");

           // 최신 게시글 ID 가져오기
           int postId = postService.getLatestPostId(member.getEmail());
           // System.out.println("생성된 게시글 ID: " + postId);
           logger.info("생성된 게시글 ID: {}", postId);

           // contents에서 장소 정보 처리 (기존 로직 유지)
           if (contentsHtml != null && !contentsHtml.isEmpty())
           {
                Document doc = Jsoup.parse(contentsHtml);
                Elements locationButtons = doc.select(".location-name-btn");
                // System.out.println("postCreate(Post) : 내용에서 장소 버튼 " + locationButtons.size() + "개 찾음");
                logger.info("postCreate(Post) : 내용에서 장소 버튼 {}개 찾음", locationButtons.size());
                for (Element button : locationButtons) {
                    // ... (기존 Tour 정보 처리 로직) ...
                    // postId 변수 사용 확인
                     String dataInfo = button.attr("data-info");
                     if (dataInfo != null && !dataInfo.isEmpty()) {
                         JSONObject jsonObject = new JSONObject(dataInfo);
                         Tour tour = new Tour();
                         /*addr1, cat2, cat3, mapx, mapy */
                         tour.setP_unique(postId); // 생성된 게시글 ID 사용
                         tour.setCreated_at(timestamp);
                         tour.setContentid(jsonObject.getString("contentid"));
                         tour.setContenttypeid(jsonObject.getString("contenttypeid"));
                         tour.setTitle(jsonObject.getString("id"));
                         tour.setFirstimage(jsonObject.getString("img"));
                         tour.setAddr1(jsonObject.getString("addr"));
                         tour.setCat2(jsonObject.getString("cat2"));
                         tour.setCat3(jsonObject.getString("cat3"));
                         tour.setMapx(jsonObject.getDouble("x"));
                         tour.setMapy(jsonObject.getDouble("y"));

                         postService.updatetour(tour);
                         // System.out.println(" - 장소 정보 처리 완료: " + tour.getTitle());
                         logger.info(" - 장소 정보 처리 완료: {}", tour.getTitle());
                     }
                }
           }

           // 페이지 번호 가져오기 및 리다이렉트 (기존 로직 유지)
           int page = postService.pageSearch(postId);
           redirect.addAttribute("num", postId);
           redirect.addAttribute("page", page);
           // System.out.println("postCreate(Post) : postView로 리다렉션");
           logger.info("postCreate(Post) : postView로 리다이렉션 (postId: {}, page: {})", postId, page);

           return "redirect:/postView";

       }
       catch (Exception e)
       {
           // System.err.println("[ERROR] postCreate 예외 발생: " + e.getMessage());
           // e.printStackTrace();
           logger.error("[ERROR] postCreate 예외 발생: {}", e.getMessage(), e); // 예외 객체를 함께 전달하여 스택 트레이스 로깅
           redirect.addFlashAttribute("errorMessage", "게시글 작성 중 오류가 발생했습니다.");
           return "redirect:/postForm"; // 에러 시 작성 폼으로
       }
    }
	
	@GetMapping("/postView")
    public String postview(@RequestParam int num,
                           @RequestParam int page,
                           Model model, HttpSession session)
    {
        // System.out.println("===========================================================================================");
        // System.out.println("PostController : postView(GET)으로 매핑되었습니다");
        logger.info("===========================================================================================");
        logger.info("PostController : postView(GET)으로 매핑되었습니다. 요청 파라미터 - num: {}, page: {}", num, page);

        Member member = (Member) session.getAttribute("user");
        int isLike = 0;
        if (member != null)
        {
            logger.info("세션에서 사용자 정보 확인됨: {}", member.getEmail());
            isLike = postService.getIdisLike(member.getEmail(), num);
            logger.info("사용자 '{}'의 게시글 {}에 대한 '좋아요' 상태: {}", member.getEmail(), num, isLike);
        } else {
            logger.info("세션에 사용자 정보 없음 (비로그인 사용자).");
        }

        Post onepost = postService.getPostById(num);
        if (onepost == null) {
            logger.warn("ID {}에 해당하는 게시글을 찾을 수 없습니다. 목록 또는 에러 페이지로 리다이렉트 고려.", num);
            // 게시글이 없을 경우의 처리 (예: 에러 페이지로 리다이렉트 또는 목록으로 이동)
            // return "redirect:/postList"; // 예시
            // 또는
            // model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            // return "errorPage"; // 예시
        } else {
            logger.info("ID {}에 해당하는 게시글 조회 완료: {}", num, onepost.getTitle()); // 예시로 게시글 제목 로깅
        }


        int commentCount = commentService.countCommentsByPostId(num);
        logger.info("게시글 {}의 댓글 수: {}", num, commentCount);
        int pageSize = 10; // 페이지 크기는 상수로 관리하거나 설정에서 가져올 수 있습니다.
        int totalPages = (int) Math.ceil((double) commentCount / pageSize);
        logger.info("댓글 전체 페이지 수: {}", totalPages);

        String postdate = new DateFormatter().formatBoardDate(onepost.getPublishDate());
        logger.info("게시글 {}의 포맷된 게시일: {}", num, postdate);

        model.addAttribute("isLike", isLike);
        model.addAttribute("onepost", onepost);
        model.addAttribute("postdate", postdate);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postId", num);
        model.addAttribute("currentPage", page); // 현재 페이지 번호 유지
        model.addAttribute("member", member);
        logger.info("Model에 속성 추가 완료. 뷰 이름: post/postView");

        return "post/postView";
    }

    
	@PostMapping(value= "/{postId}/like",produces = "application/json")
    @ResponseBody
    public Map<String, Object> likePost(@PathVariable int postId, HttpSession session)
    {
    	// System.out.println("===========================================================================================");
        logger.info("===========================================================================================");
    	// System.out.println("PostController : /{postId}/like(POST)으로 ASYNC 매핑되었습니다");
        logger.info("PostController : /{postId}/like (POST) ASYNC 매핑. postId: {}", postId);

    	Timestamp timestamp=new Timestamp(System.currentTimeMillis());
    	Member member=(Member)session.getAttribute("user");
    	Likes likes=new Likes(); // Likes 객체 생성 위치는 그대로 유지
    	Map<String, Object> response = new HashMap<>();
    	List<Integer> result = new ArrayList<Integer>(); // 초기화는 그대로 유지

        if(member!=null)
        {
            logger.info("세션 사용자 확인: {}", member.getEmail());
	        String id = member.getEmail();
	        likes.setId(id);
	        likes.setP_unique(postId);
	        likes.setLikesDate(timestamp);
            logger.debug("Likes 객체 생성: {}", likes); // Likes 객체 정보 로깅
	        result= postService.incrementPostLike(likes);
	        int isLiked =result.get(0);
	        int totalLikes =result.get(1);
            logger.info("좋아요 처리 결과 - isLiked: {}, totalLikes: {}", isLiked, totalLikes);
	        response.put("isLiked", isLiked);
	        response.put("totalLikes",totalLikes);
	        response.put("id",id);
            logger.debug("응답 데이터 (로그인 사용자): {}", response);
	        return response;
        }
        else
        {
            logger.warn("비로그인 사용자의 좋아요 시도. postId: {}", postId);
	        String id=null; // 비로그인 시 id는 null
	        response.put("id", id);
            logger.debug("응답 데이터 (비로그인 사용자): {}", response);
	        return response;
        }
    }

	
	
	
	
	
	
	
	@GetMapping("/postView/update")
	public String toUpdatePost(@RequestParam int num, Model model)
	{
		// System.out.println("===========================================================================================");
		logger.info("===========================================================================================");
		// System.out.println("PostController : /postView/update(GET)으로 매핑되었습니다");
		logger.info("PostController : /postView/update (GET) 매핑. 요청 파라미터 - num: {}", num);

		Post result = (Post) postService.getPostById(num); // 형변환이 꼭 필요한지 확인 필요, getPostById가 Post를 반환한다면 형변환 불필요

		if (result != null) {
			logger.info("ID {}에 해당하는 게시글 조회 성공. 수정 폼으로 전달합니다.", num);
			// logger.debug("조회된 게시글 정보: {}", result); // 필요시 게시글 상세 정보 로깅 (DEBUG 레벨)
		} else {
			logger.warn("ID {}에 해당하는 게시글을 찾을 수 없어 수정 폼으로 전달할 데이터가 없습니다.", num);
			// 게시글이 없을 경우의 처리 (예: 에러 페이지로 리다이렉트 또는 메시지 전달)
            // model.addAttribute("errorMessage", "수정할 게시글을 찾을 수 없습니다.");
            // return "errorPage"; // 또는 적절한 다른 뷰
		}
		model.addAttribute("result", result);
		logger.info("Model에 'result' 속성으로 게시글 정보 추가 완료.");

		return "post/updateForm";
	}
	
	
	
	
	
	
	
	@PostMapping("/postView/updatePost")
    public String fromUpdatePost(@ModelAttribute Post post,
                               HttpSession session,
                               RedirectAttributes redirect,
                               @RequestParam(required = false) String contents)
    {
        // System.out.println("===========================================================================================");
        logger.info("===========================================================================================");
        // System.out.println("PostController : /postView/updatePost(POST)으로 매핑되었습니다");
        logger.info("PostController : /postView/updatePost (POST) 매핑. 업데이트할 게시글 ID: {}", post.getP_unique());
        logger.debug("수정 요청 받은 Post DTO: {}", post);

        try
        {
            // 기존 게시글 정보 가져오기
            Post existingPost = postService.getPostById(post.getP_unique());
            if (existingPost == null) 
            {
                logger.warn("게시글 ID {}을 찾을 수 없습니다. 업데이트 실패.", post.getP_unique());
                redirect.addFlashAttribute("errorMessage", "수정할 게시글을 찾을 수 없습니다.");
                return "redirect:/postList"; // 또는 적절한 에러 페이지
            }
            logger.info("기존 게시글 (ID: {}) 정보 조회 완료.", post.getP_unique());

            List<String> imageUrls = new ArrayList<>();
            if (contents != null && !contents.isEmpty())
            {
                logger.debug("업데이트할 contents HTML 내용: {}", contents); // HTML 내용은 DEBUG 레벨로
                Document doc = Jsoup.parse(contents);

                // 이미지 URL 수집
                Elements images = doc.select("img[src*='/uploads/']"); // /uploads/가 포함된 이미지 태그만 선택
                logger.info("Contents에서 발견된 이미지 태그 수: {}", images.size());
                for (Element img : images)
                {
                    String src = img.attr("src");
                    if (src != null && src.contains("/uploads/")) 
                    {
                        String filename = src.substring(src.lastIndexOf('/') + 1);
                        imageUrls.add(filename);
                        logger.debug(" - 추출된 서버 저장 파일명: {}", filename);
                    }
                }
            } 
            else 
            {
                logger.info("contents 필드가 비어있거나 null입니다. 이미지 URL 수집 건너뜜.");
            }

            post.setFileImage(imageUrls);
            logger.info("Post DTO의 fileImage 필드 설정 완료: {}", imageUrls);

            post.setPublishDate(existingPost.getPublishDate()); // 기존 작성일 유지
            logger.info("게시글 {}의 작성일은 기존 값으로 유지됩니다: {}", post.getP_unique(), post.getPublishDate());

            postService.updatePost(post);
            logger.info("게시글 (ID: {}) 업데이트 완료 (contents 및 imageNames 포함).", post.getP_unique());


            if (contents != null && !contents.isEmpty())
            {
                Document doc = Jsoup.parse(contents);
                Elements locationButtons = doc.select(".location-name-btn");
                logger.info("Contents에서 발견된 장소 버튼 수: {}", locationButtons.size());

                for (Element button : locationButtons)
                {
                    String dataInfo = button.attr("data-info");
                    if (dataInfo != null && !dataInfo.isEmpty())
                    {
                        JSONObject jsonObject = new JSONObject(dataInfo);

                        Tour tour = new Tour();
                        tour.setTitle(jsonObject.optString("id", ""));
                        tour.setAddr1(jsonObject.optString("addr", ""));
                        tour.setMapy(jsonObject.optDouble("y", 0.0)); // 'latitude' 대신 'y' 사용 (postCreate와 일관성)
                        tour.setMapx(jsonObject.optDouble("x", 0.0)); // 'longitude' 대신 'x' 사용 (postCreate와 일관성)
                        tour.setContentid(jsonObject.optString("contentid", ""));
                        tour.setContenttypeid(jsonObject.optString("contenttypeid", ""));
                        tour.setFirstimage(jsonObject.optString("img", ""));
                        tour.setCat2(jsonObject.optString("cat2", "")); // 'catagory2' 대신 'cat2' 사용 (postCreate와 일관성)
                        tour.setCat3(jsonObject.optString("cat3", "")); // 'catagory3' 대신 'cat3' 사용 (postCreate와 일관성)
                        tour.setCreated_at(post.getPublishDate()); // 업데이트 시에는 게시일 유지
                        tour.setP_unique(post.getP_unique());

                        postService.updatetour(tour);
                        logger.info(" - 장소 정보 처리 완료: {}", tour.getTitle());
                        logger.debug("처리된 Tour 객체: {}", tour); // 상세 Tour 객체 정보는 DEBUG 레벨로
                    } 
                    else 
                    {
                        logger.debug(" - data-info 속성이 비어있거나 null입니다. 장소 정보 처리 건너뜜.");
                    }
                }
            } 
            else 
            {
                logger.info("contents 필드가 비어있거나 null이므로 장소 정보 처리 건너뜜.");
            }

            // 페이지 번호 가져오기
            int page = postService.pageSearch(post.getP_unique());
            redirect.addAttribute("num", post.getP_unique());
            redirect.addAttribute("page", page);
            logger.info("업데이트된 게시글 (ID: {}) 조회 페이지로 리다이렉트 (page: {}).", post.getP_unique(), page);

            return "redirect:/postView";

        }
        catch (Exception e)
        {
            // e.printStackTrace(); // 예외의 스택 트레이스를 출력
            logger.error("[ERROR] 게시글 업데이트 중 예외 발생: {}", e.getMessage(), e); // 예외 객체를 함께 전달하여 스택 트레이스 로깅
            // return "An error occurred: " + e.getMessage(); // 에러 메시지 반환 (클라이언트에게 직접 표시될 수 있으므로 권장되지 않음)
            redirect.addFlashAttribute("errorMessage", "게시글 수정 중 오류가 발생했습니다.");
            return "redirect:/postView/update?num=" + post.getP_unique(); // 수정 폼으로 다시 이동하거나 에러 페이지로 리다이렉트
        }
    }
	
	
	@PostMapping("/uploadSummernoteImage") // URL 경로 변경 (기존 /uploadImage 대신 사용)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadSummernoteImage(@RequestParam("file") MultipartFile file) { // 단일 파일 파라미터
        logger.info("===========================================================================================");
        logger.info("PostController : /uploadSummernoteImage (POST) 매핑됨");
        Map<String, Object> response = new HashMap<>();

        if (file == null || file.isEmpty()) {
        	logger.warn("업로드된 파일이 없습니다.");
            response.put("error", "업로드할 파일이 없습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        logger.info("전달받은 파일: " + file.getOriginalFilename());

        try {
            // 1. FileStorageService를 사용하여 파일 저장하고 저장된 파일명(UUID.확장자) 받기
            String savedFilename = fileStorageService.storeFile(file); // 서비스 호출
            logger.info("파일 저장 성공, 저장된 파일명: [{}]", savedFilename);

            // 2. 웹 접근 URL 생성 (리소스 핸들러 매핑 경로 기준)
            String imageUrl = "/uploads/" + savedFilename;
            logger.info("생성된 이미지 웹 경로: [{}]", imageUrl);

            // 3. 성공 JSON 응답 구성 (Summernote가 사용할 URL 포함)
            response.put("url", imageUrl);
            response.put("savedFilename", savedFilename); // 저장된 파일명도 전달 (선택적이지만 유용)
            response.put("success", true);
            logger.info("Summernote 이미지 업로드 성공 - URL: [{}] 반환", imageUrl);

            return ResponseEntity.ok(response);

        }  catch (IOException e) { // 파일 입출력 관련 명시적 예외
            logger.error("파일 업로드 처리 중 IOException 발생 - 파일명: [{}], 오류: [{}]", file.getOriginalFilename(), e.getMessage(), e); // 예외 객체도 함께 로깅
            response.put("error", "파일 업로드 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (IllegalStateException e) { // 파일 상태 관련 명시적 예외
            logger.error("파일 업로드 처리 중 IllegalStateException 발생 - 파일명: [{}], 오류: [{}]", file.getOriginalFilename(), e.getMessage(), e);
            response.put("error", "파일 업로드 처리 중 내부 상태 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) { // 그 외 모든 예외
             logger.error("Summernote 이미지 업로드 중 알 수 없는 오류 발생 - 파일명: [{}], 오류: [{}]", file.getOriginalFilename(), e.getMessage(), e);
             response.put("error", "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요.");
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            logger.info("POST /uploadSummernoteImage 요청 처리 종료");
            logger.info("===========================================================================================");
        }
    }
/*
	@PostMapping("/uploadImage")
	@ResponseBody
	public List<String> uploadImage(@RequestParam("fileImg") MultipartFile[] files, HttpServletRequest request) {
	    System.out.println("===========================================================================================");
	    System.out.println("PostController : uploadImage(Post)으로 ASYNC 매핑되었습니다");
	    List<String> uploadedImageUrls = new ArrayList<>();
	    System.out.println("전달받은 파일 정보(MultipartFile 배열) : " + files);

	    try {
	        // ServletContext를 가져옵니다.
	        ServletContext context = request.getServletContext();
	        System.out.println("ServletContext 획득: " + context);

	        // 상대 경로를 지정합니다. (webapp/resources/uploads)
	        String relativePath = "/resources/uploads";
	        System.out.println("상대 경로: " + relativePath);

	        // ServletContext.getRealPath()를 사용하여 절대 경로를 얻습니다.
	        String uploadDir = context.getRealPath(relativePath);
	        System.out.println("실제 이미지 저장 경로 (절대 경로): " + uploadDir);


	        File uploadPath = new File(uploadDir);
	        System.out.println("File 객체 생성 완료: " + uploadPath);

	        if (!uploadPath.exists()) {
	            System.out.println("이미지를 저장할 디렉토리가 존재하지 않습니다.");
	            System.out.println("디렉토리가 없으므로 " + uploadPath + "에 디렉토리를 생성합니다.");
	            uploadPath.mkdirs();
	            System.out.println("디렉토리 생성 완료: " + uploadPath);
	        } else {
	            System.out.println("이미지를 저장할 디렉토리가 이미 존재합니다.");
	            System.out.println("이미 디렉토리가 있어 : " + uploadPath + "에 이미지를 저장합니다");
	        }


	        System.out.println("전달받은 파일 배열을 순회하며 파일 처리 시작.");
	        for (MultipartFile file : files) {
	            System.out.println("현재 파일 처리 시작: " + file.getOriginalFilename());
	            System.out.println("UUID를 사용하여 고유한 파일 이름 생성 및 확장자 추출.");
	            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	            System.out.println("생성된 파일 이름: " + fileName);

	            // uploadPath (절대 경로)를 사용하여 파일을 저장합니다.
	            System.out.println("파일 저장 시작: " + uploadPath + File.separator + fileName);
	            file.transferTo(new File(uploadPath, fileName)); // 더 간결하게 File 생성
	            System.out.println("파일 저장 완료: " + uploadPath + File.separator + fileName);

	            // 클라이언트에게 반환할 URL을 생성합니다. (상대 경로 사용)
	            String imageUrl = request.getContextPath() + relativePath + "/" + fileName;  // 수정된 부분
	            System.out.println("생성된 이미지 URL: " + imageUrl);
	            System.out.println("생성된 이미지 URL을 리스트에 추가: " + imageUrl);
	            uploadedImageUrls.add(imageUrl);
	        }
	        System.out.println("모든 파일 처리 완료.");
	    } catch (Exception e) {
	        System.out.println("파일 업로드 중 예외 발생!");
	        e.printStackTrace();
	        System.out.println("예외 발생으로 인해 빈 리스트를 반환합니다.");
	        return Collections.emptyList();
	    }

	    System.out.println("업로드된 이미지 URL 리스트 반환: " + uploadedImageUrls);
	    return uploadedImageUrls;
	}
*/
	
	@PostMapping(value="/submitForm",produces = "application/json")
    @ResponseBody
    public String submitForm(@RequestBody List<Map<String, Object>> myListData, HttpSession session)
	{
		// System.out.println("===========================================================================================");
		logger.info("===========================================================================================");
		// System.out.println("PostController : /submitForm(POST)으로 ASYNC 매핑되었습니다");
        logger.info("PostController : /submitForm (POST) ASYNC 매핑되었습니다.");
        logger.debug("수신된 myListData: {}", myListData); // 수신된 데이터는 DEBUG 레벨로 상세 로깅

        session.setAttribute("myListData", myListData);
        logger.info("myListData를 세션에 저장 완료. 세션 ID: {}", session.getId());

        return "success"; // 클라이언트로 성공 메시지 반환
    }
	
	
	//Delete
	@GetMapping("/postView/delete")
	public String postDelete(@RequestParam int num, Model model)
	{
		// System.out.println("===========================================================================================");
		logger.info("===========================================================================================");
		// System.out.println("PostController : /postView/delete(GET)으로 매핑되었습니다");
		logger.info("PostController : /postView/delete (GET) 매핑. 삭제 요청 게시글 ID: {}", num);

		try {
	        postService.deletePost(num);
	        logger.info("게시글 ID {} 삭제 성공.", num);
		} catch (Exception e) {
			logger.error("[ERROR] 게시글 ID {} 삭제 중 오류 발생: {}", num, e.getMessage(), e);
			// 오류 발생 시 사용자에게 메시지를 전달하거나 다른 페이지로 리다이렉트하는 것이 좋습니다.
			// 예: redirect.addFlashAttribute("errorMessage", "게시글 삭제 중 오류가 발생했습니다.");
			//     return "redirect:/postView?num=" + num; // 삭제하려던 게시글 뷰로 다시 이동
		}

		logger.info("게시글 삭제 후 /board/all?page=1로 리다이렉트합니다.");
	    return "redirect:/board/all?page=1";
	}
}


