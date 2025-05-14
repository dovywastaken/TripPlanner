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
		System.out.println("===========================================================================================");
		System.out.println("PostController : postForm(GET)으로 매핑되었습니다");
		
		return "post/postForm";
		
		
	}
		
	@PostMapping("/saveForm")
    public String saveForm(@RequestParam("myListData") String myListData, Model model) 
	{
		System.out.println("===========================================================================================");
        // JSON 문자열을 필요한 형태로 파싱하거나 처리
        // 여기서는 단순히 모델에 추가
		System.out.println("PostController : saveForm(POST)으로 매핑되었습니다");
        model.addAttribute("myListData", myListData);
        return "post/postForm";
    }
	
	
	
	
	
	 @PostMapping("/postCreate")
		public String postCreate(@ModelAttribute Post post, // contents, isPrivate 등은 ModelAttribute로 자동 매핑됨
		                       HttpSession session,
		                       RedirectAttributes redirect)
		{
		   System.out.println("===========================================================================================");
		   System.out.println("PostController : postCreate(Post)으로 매핑되었습니다");
		   try
		   {
	           Member member = (Member) session.getAttribute("user");
	           System.out.println("로그인한 회원 dto: " + member);
	           if (member == null) {
					// 로그인 안된 경우 처리
					return "redirect:/login"; // 실제 로그인 페이지 경로로 수정
				}

	           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	           System.out.println("postCreate(Post) : Timestamp 생성");
	           post.setId(member.getEmail());
	           System.out.println("postCreate(Post) : 작성자 ID 설정: " + post.getId());
	           post.setNickname(member.getNickname());
	           System.out.println("postCreate(Post) : 작성자 닉네임 설정: " + post.getNickname());
	           post.setPublishDate(timestamp);
	           System.out.println("postCreate(Post) : 게시일 설정");

	           // ====[ fileImage 필드 설정 로직 ]====
	           // contents HTML에서 서버 저장 파일명(UUID.확장자) 리스트를 추출하여
	           // Post 객체의 fileImage 필드(List<String>)에 설정합니다.
	           List<String> storedFileNamesFromContent = new ArrayList<>();
	           String contentsHtml = post.getContents(); // ModelAttribute를 통해 받아온 contents 사용
	           if (contentsHtml != null && !contentsHtml.isEmpty()) {
	               Document doc = Jsoup.parse(contentsHtml);
	               System.out.println("파싱할 HTML 내용: " + contentsHtml);
	               Elements images = doc.select("img[src*='/uploads/']");
	               //Elements images = doc.select("img[src^='/uploads/']"); // /uploads/로 시작하는 img 태그만 선택
	               System.out.println("postCreate(Post) : 내용에서 이미지 태그 " + images.size() + "개 찾음");
	               for (Element img : images) {
	                   String src = img.attr("src"); // 예: /uploads/uuid.jpg 또는 /testChamber/uploads/uuid.jpg
	                   if (src != null && src.contains("/uploads/")) {
	                       // 마지막 '/' 뒤의 문자열(파일명)만 추출
	                       String filename = src.substring(src.lastIndexOf('/') + 1);
	                       storedFileNamesFromContent.add(filename);
	                       System.out.println(" - 추출된 서버 저장 파일명: " + filename);
	                   }
	               }
	           }
	           // 추출한 파일명 리스트를 Post 객체의 fileImage 필드에 설정
	           post.setFileImage(storedFileNamesFromContent);
	           System.out.println("postCreate(Post) : Post DTO의 fileImage 필드 설정 완료: " + storedFileNamesFromContent);
	           // ====[ fileImage 필드 설정 로직 끝 ]====

	           // 서비스 호출하여 게시글 저장
	           // PostRepository는 내부적으로 post.getFileImage()를 호출하여
	           // List<String>을 받아 String.join(",")으로 imageNames 컬럼에 저장할 것임
	           postService.createPost(post);
	           System.out.println("postCreate(Post) : DB에 게시글 정보 저장 완료 (contents 및 imageNames 포함)");

	           // 최신 게시글 ID 가져오기
	           int postId = postService.getLatestPostId(member.getEmail());
	           System.out.println("생성된 게시글 ID: " + postId);

	           // contents에서 장소 정보 처리 (기존 로직 유지)
	           if (contentsHtml != null && !contentsHtml.isEmpty())
	           {
	                Document doc = Jsoup.parse(contentsHtml);
	                Elements locationButtons = doc.select(".location-name-btn");
	                System.out.println("postCreate(Post) : 내용에서 장소 버튼 " + locationButtons.size() + "개 찾음");
	                for (Element button : locationButtons) {
	                    // ... (기존 Tour 정보 처리 로직) ...
	                    // postId 변수 사용 확인
	                     String dataInfo = button.attr("data-info");
	                     if (dataInfo != null && !dataInfo.isEmpty()) {
	                         JSONObject jsonObject = new JSONObject(dataInfo);
	                         Tour tour = new Tour();
	                         // ... Tour 객체 속성 설정 ...
	                         tour.setP_unique(postId); // 생성된 게시글 ID 사용
	                         tour.setCreated_at(timestamp);
	                         postService.updatetour(tour);
	                         System.out.println(" - 장소 정보 처리 완료: " + tour.getTitle());
	                     }
	                }
	           }

	           // 페이지 번호 가져오기 및 리다이렉트 (기존 로직 유지)
	           int page = postService.pageSearch(postId);
	           redirect.addAttribute("num", postId);
	           redirect.addAttribute("page", page);
	           System.out.println("postCreate(Post) : postView로 리다렉션");

	           return "redirect:/postView";

		   }
		   catch (Exception e)
		   {
	           System.err.println("[ERROR] postCreate 예외 발생: " + e.getMessage());
		       e.printStackTrace();
	           redirect.addFlashAttribute("errorMessage", "게시글 작성 중 오류가 발생했습니다.");
	           return "redirect:/postForm"; // 에러 시 작성 폼으로
		   }
		}
	
	
	
	
	
	/*

	@PostMapping("/postCreate")
	public String postCreate(@ModelAttribute Post post,
	                       HttpSession session,
	                       RedirectAttributes redirect,
	                       @RequestParam(required = false) String contents) 
	{
	   System.out.println("===========================================================================================");
	   System.out.println("PostController : postCreate(Post)으로 매핑되었습니다");
	   try 
	   {
		   System.out.println("postCreate(Post) : try로 들어옴");
	       Member member = (Member)session.getAttribute("user");
	       System.out.println("postCreate(Post) : 세션에서 로그인한 회원 데이터를 멤버 dto에 저장함");
	       System.out.println("로그인한 회원 dto 들고왔는지 체크하기 : "+ member);
	       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	       System.out.println("postCreate(Post) : 현재 시간 측정 위한 Timestamp객체 생성");
	       post.setId(member.getEmail());
	       System.out.println("postCreate(Post) : 게시글의 아이디를 로그인한 유저의 이메일인 " + post.getId() + "로 설정함");
	       post.setNickname(member.getNickname());
	       System.out.println("postCreate(Post) : 나중에 게시판에 표시할 닉네임인 " + post.getNickname() + " 가져옴");
	       post.setPublishDate(timestamp);
	       System.out.println("postCreate(Post) : 게시글 등록일을 timestamp객체로 현재 시간으로 등록함");

	       // 이미지가 없을 경우를 위한 빈 리스트 초기화
	       List<String> imageUrls = new ArrayList<>();
	       System.out.println("postCreate(Post) : 이미지가 없을 수 있으니 일단 그래도 이미지를 담는 빈 리스트를 만들어 놓음");

	       // contents에서 이미지 URL과 장소 정보 추출
	       if (contents != null && !contents.isEmpty()) 
	       {
	    	   System.out.println("postCreate(Post) : 게시글 내용이 존재하고 빈 상태가 아니라면");
	           Document doc = Jsoup.parse(contents);
	           System.out.println("postCreate(Post) : Jsoup객체로 게시글을 파싱해서 Document 객체에 담음");
	           
	           // 이미지 URL 수집
	           Elements images = doc.select("img");
	           System.out.println("postCreate(Post) : img라고 되어있는 HTML요소를 Elements라는 객체에 담음");
	           for (Element img : images) 
	           {
	        	   System.out.println("postCreate(Post) : 게시글에 있는 이미지를(images) 반복문을 통해 img라는 Element 객체에 집어넣음");
	               String imageUrl = img.attr("src");
	               System.out.println("postCreate(Post) : 첨부된 이미지의 src속성을 꺼내서 imageURL이라는 문자열에 담음");
	               if (!imageUrl.isEmpty()) 
	               {
	            	   System.out.println("postCreate(Post) : 첨부된 이미지가 존재하므로 if문으로 들어옴");
	                   imageUrls.add(imageUrl);
	                   System.out.println("postCreate(Post) : imageUrls라는 리스트에 각각의 이미지를 담음");
	               }
	           }
	       }
	       
	       // 이미지 URL 리스트 설정 (비어있어도 설정)
	       post.setFileImage(imageUrls);
	       System.out.println("postCreate(Post) : Post dto에 이미지의 URL이 담긴 리스트를 넣는다");

	       postService.createPost(post);
	       System.out.println("postCreate(Post) : db에 방금 만든 Post DTO를 넣는다");

	       // 최신 게시글 ID 가져오기
	       int postId = postService.getLatestPostId(member.getEmail());
	       System.out.println(postId);
	       System.out.println("1");
	       // contents에서 장소 정보 처리
	       if (contents != null && !contents.isEmpty()) 
	       {
	    	   System.out.println("2");
	           Document doc = Jsoup.parse(contents);
	           System.out.println("3");
	           Elements locationButtons = doc.select(".location-name-btn");
	           System.out.println(locationButtons);
	           System.out.println("4");

	           for (Element button : locationButtons) {
	        	   System.out.println("5");
	               String dataInfo = button.attr("data-info");
	               System.out.println("6");
	               if (dataInfo != null && !dataInfo.isEmpty()) {
	            	   System.out.println("7");

	                   JSONObject jsonObject = new JSONObject(dataInfo);
	                   System.out.println("8");

	                   Tour tour = new Tour();
	                   System.out.println("9");
	                   tour.setTitle(jsonObject.optString("id", ""));
	                   System.out.println("10");
	                   tour.setAddr1(jsonObject.optString("addr", ""));
	                   System.out.println("11");
	                   tour.setMapy(jsonObject.optDouble("latitude", 0.0));
	                   System.out.println("12");
	                   tour.setMapx(jsonObject.optDouble("longitude", 0.0));
	                   System.out.println("13");
	                   tour.setContentid(jsonObject.optString("contentid", ""));
	                   System.out.println("14");
	                   tour.setContenttypeid(jsonObject.optString("contenttypeid", ""));
	                   System.out.println("15");
	                   tour.setFirstimage(jsonObject.optString("img", ""));
	                   System.out.println("16");
	                   tour.setCat2(jsonObject.optString("catagory2", ""));
	                   System.out.println("17");
	                   tour.setCat3(jsonObject.optString("catagory3", ""));
	                   System.out.println("18");
	                   tour.setCreated_at(timestamp);
	                   System.out.println("19");
	                   tour.setP_unique(postId);
	                   System.out.println("20");

	                   postService.updatetour(tour);
	                   System.out.println("21");
	               }
	           }
	       }

	       // 페이지 번호 가져오기
	       System.out.println("게시글 고유번호 존재 여부 : "+ postId + "번");
	       int page = postService.pageSearch(postId);
	       System.out.println("22");
	   
	       // 리다이렉트 속성 설정
	       redirect.addAttribute("num", postId);
	       System.out.println("23");
	       redirect.addAttribute("page", page);
	       System.out.println("24");

	       System.out.println("postCreate(Post) : postView로 리다렉션 합니다.");
	       
	       return "redirect:/postView";

	   }
	   catch (Exception e) 
	   {
	       System.out.println("[ERROR] 예외 발생: " + e.getMessage());
	       e.printStackTrace();
	       
	       return "errorPage";
	   }
	}
    
	
	*/
	
	
	
	
	
	
    @GetMapping("/postView")
    public String postview(@RequestParam int num,
                           @RequestParam int page,
                           Model model, HttpSession session) 
    {
    	System.out.println("===========================================================================================");
    	System.out.println("PostController : postView(GET)으로 매핑되었습니다");
    	Member member=(Member) session.getAttribute("user");
    	int isLike=0;
    	if(member !=null) 
    	{
    		isLike = postService.getIdisLike(member.getEmail(),num);
    	}
    	Post onepost = postService.getPostById(num);
        
        int commentCount = commentService.countCommentsByPostId(num);
        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) commentCount / pageSize);
        
        String postdate = new DateFormatter().formatBoardDate(onepost.getPublishDate());
        model.addAttribute("isLike",isLike);
        model.addAttribute("onepost", onepost);
        model.addAttribute("postdate", postdate);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postId", num);
        model.addAttribute("currentPage", page); // 현재 페이지 번호 유지
        model.addAttribute("member",member);
        
        return "post/postView";
    }

    
    
    
    
    
    
    
    
    @PostMapping(value= "/{postId}/like",produces = "application/json")
    @ResponseBody
    public Map<String, Object> likePost(@PathVariable int postId, HttpSession session) 
    {
    	System.out.println("===========================================================================================");
    	System.out.println("PostController : /{postId}/like(POST)으로 ASYNC 매핑되었습니다");
    	Timestamp timestamp=new Timestamp(System.currentTimeMillis());
    	Member member=(Member)session.getAttribute("user");
    	Likes likes=new Likes();
    	Map<String, Object> response = new HashMap<>();
    	List<Integer> result = new ArrayList<Integer>();
        if(member!=null) 
        {	
	        String id = member.getEmail();
	        likes.setId(id);
	        likes.setP_unique(postId);
	        likes.setLikesDate(timestamp);
	        result= postService.incrementPostLike(likes);
	        int isLiked =result.get(0);
	        int totalLikes =result.get(1);
	        response.put("isLiked", isLiked);
	        response.put("totalLikes",totalLikes);
	        response.put("id",id);
	        
	        return response;
        }
        else 
        {
	        String id=null;
	        response.put("id", id);
	        
	        return response;
        }
        
        
    }

	
	
	
	
	
	
	
	@GetMapping("/postView/update")
	public String toUpdatePost(@RequestParam int num,Model model) 
	{
		System.out.println("===========================================================================================");
		System.out.println("PostController : /postView/update(GET)으로 매핑되었습니다");
		Post result=(Post) postService.getPostById(num);
		model.addAttribute("result",result);
		
		return "post/updateForm";
	}
	
	
	
	
	
	
	
	@PostMapping("/postView/updatePost")
	public String fromUpdatePost(@ModelAttribute Post post,
	                       HttpSession session,
	                       RedirectAttributes redirect,
	                       @RequestParam(required = false) String contents) 
	{
		System.out.println("===========================================================================================");
		System.out.println("PostController : /postView/updatePost(POST)으로 매핑되었습니다");
	   try 
	   {
       // 기존 게시글 정보 가져오기 
       Post existingPost = postService.getPostById(post.getP_unique());
       List<String> imageUrls = new ArrayList<>();
       if (contents != null && !contents.isEmpty()) 
       {
           Document doc = Jsoup.parse(contents);
	           
           // 이미지 URL 수집
           Elements images = doc.select("img");
           for (Element img : images) 
           {
               String imageUrl = img.attr("src");
               if (!imageUrl.isEmpty()) 
               {
                   imageUrls.add(imageUrl);
               }
           }
       }
	       
       post.setFileImage(imageUrls);
       post.setPublishDate(existingPost.getPublishDate()); // 기존 작성일 유지
       postService.updatePost(post);
 

       if (contents != null && !contents.isEmpty()) 
       {
           Document doc = Jsoup.parse(contents);
           Elements locationButtons = doc.select(".location-name-btn");

           for (Element button : locationButtons) 
           {
               String dataInfo = button.attr("data-info");
               if (dataInfo != null && !dataInfo.isEmpty()) 
               {
                   JSONObject jsonObject = new JSONObject(dataInfo);
                   
                   Tour tour = new Tour();
                   tour.setTitle(jsonObject.optString("id", ""));
                   tour.setAddr1(jsonObject.optString("addr", ""));
                   tour.setMapy(jsonObject.optDouble("latitude", 0.0));
                   tour.setMapx(jsonObject.optDouble("longitude", 0.0));
                   tour.setContentid(jsonObject.optString("contentid", ""));
                   tour.setContenttypeid(jsonObject.optString("contenttypeid", ""));
                   tour.setFirstimage(jsonObject.optString("img", ""));
                   tour.setCat2(jsonObject.optString("catagory2", ""));
                   tour.setCat3(jsonObject.optString("catagory3", ""));
                   tour.setCreated_at(post.getPublishDate());
                   tour.setP_unique(post.getP_unique());

                   postService.updatetour(tour);
               }
           }
       }

       // 페이지 번호 가져오기
       int page = postService.pageSearch(post.getP_unique());
       redirect.addAttribute("num", post.getP_unique());
       redirect.addAttribute("page", page);

       return "redirect:/postView";

	   }
	   catch (Exception e) 
	   {
	        e.printStackTrace(); // 예외의 스택 트레이스를 출력
	        return "An error occurred: " + e.getMessage(); // 에러 메시지 반환
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
		System.out.println("===========================================================================================");
		System.out.println("PostController : /submitForm(POST)으로 ASYNC 매핑되었습니다");
        session.setAttribute("myListData", myListData);
        return "success"; // 클라이언트로 성공 메시지 반환
    }
	
	
	//Delete
	@GetMapping("/postView/delete")
	public String postDelete(@RequestParam int num, Model model) 
	{
		System.out.println("===========================================================================================");
		System.out.println("PostController : /postView/delete(GET)으로 매핑되었습니다");
		
	    postService.deletePost(num);
	    
	    return "redirect:/board/all?page=1";
	}
}


