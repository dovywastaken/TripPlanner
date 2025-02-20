package com.spring.controller.post;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
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
import com.spring.service.post.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
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
        return "post/postForm"; // write.jsp로 포워딩
    }
	
	

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
	       post.setId(member.getNickname());
	       System.out.println("postCreate(Post) : 게시글의 아이디를 로그인한 유저의 닉네임(원래는 이메일이었음)으로 설정함");
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
	            	   System.out.println("postCreate(Post) : 첨부된 이미지의 src속성이 비어있어서 if문으로 들어옴");
	                   imageUrls.add(imageUrl);
	                   System.out.println("postCreate(Post) : 왜 하는진 모르겠지만 일단 src속성이 빈 이미지를 앞서 만들어 놓은 이미지를 담는 빈 리스트에 담음");
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
	
	@PostMapping("/uploadImage")
	@ResponseBody
	public List<String> uploadImage(@RequestParam("fileImg") MultipartFile[] files, HttpServletRequest request)
	{
		System.out.println("==========================================================================================="); // 로그 구분선을 출력하여 로그를 시각적으로 구분합니다.
	    System.out.println("PostController : uploadImage(Post)으로 ASYNC 매핑되었습니다"); // 해당 메소드가 호출되었음을 로그로 남깁니다. (ASYNC 매핑은 이 코드에서는 직접적으로 드러나지 않지만, 주석은 원래 코드의 주석을 반영)
	    List<String> uploadedImageUrls = new ArrayList<>(); // 업로드된 이미지의 URL을 저장할 리스트를 생성합니다.
	    System.out.println("전달받은 파일 정보(MultipartFile 배열) : " + files); // 클라이언트로부터 전송된 파일 배열(MultipartFile[])을 콘솔에 출력하여 파일 정보를 확인합니다. (디버깅 목적)

	    try {
	        System.out.println("서블릿 컨텍스트에서 '/resources/img' 경로의 실제 파일 시스템 경로를 가져오기 시작합니다."); // 경로 획득 시작 로그
	        String uploadDir = request.getServletContext().getRealPath("/resources/img"); // 서블릿 컨텍스트를 통해 "/resources/img" 경로의 실제 서버 파일 시스템 경로를 가져옵니다.
	        System.out.println("이미지 저장 경로 : " + uploadDir); // 이미지가 저장될 실제 경로를 로그로 출력합니다.
	        File uploadPath = new File(uploadDir); // 실제 저장 경로를 기반으로 File 객체를 생성합니다.
	        System.out.println("File 객체 생성 완료: " + uploadPath); // File 객체 생성 완료 로그

	        if (!uploadPath.exists()) // 해당 경로에 디렉토리가 존재하는지 확인합니다.
	        {
	        	System.out.println("이미지를 저장할 디렉토리가 존재하지 않습니다."); // 디렉토리 존재 여부 확인 로그
	        	System.out.println("디렉토리가 없으므로 " + uploadPath + "에 디렉토리를 생성합니다."); // 디렉토리 생성 로그 메시지
	            uploadPath.mkdirs(); // 지정된 경로에 디렉토리를 생성합니다. (mkdirs()는 중간 경로도 모두 생성)
	            System.out.println("디렉토리 생성 완료: " + uploadPath); // 디렉토리 생성 완료 로그
	        } else {
	        	System.out.println("이미지를 저장할 디렉토리가 이미 존재합니다."); // 디렉토리 이미 존재 로그
	            System.out.println("이미 디렉토리가 있어 : " + uploadPath + "에 이미지를 저장합니다"); // 디렉토리가 이미 존재하는 경우 로그를 출력합니다.
	        }


	        System.out.println("전달받은 파일 배열을 순회하며 파일 처리 시작."); // 파일 처리 시작 로그
	        for (MultipartFile file : files) // 전송된 파일 배열을 순회하며 각 파일에 대한 처리를 진행합니다.
	        {
	        	System.out.println("현재 파일 처리 시작: " + file.getOriginalFilename()); // 현재 처리 파일명 로그
	            System.out.println("UUID를 사용하여 고유한 파일 이름 생성 및 확장자 추출."); // 파일 이름 생성 로그
	            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); // UUID를 사용하여 고유한 파일 이름을 생성하고, 원래 파일의 확장자를 유지합니다.
	            System.out.println("생성된 파일 이름: " + fileName); // 생성된 파일 이름 로그
	            System.out.println("파일 저장 시작: " + uploadPath + File.separator + fileName); // 파일 저장 시작 로그
	            file.transferTo(new File(uploadPath + File.separator + fileName)); // 생성된 파일 이름으로 파일을 지정된 경로에 저장합니다.
	            System.out.println("파일 저장 완료: " + uploadPath + File.separator + fileName); // 파일 저장 완료 로그
	            String imageUrl = request.getContextPath() + "/resources/img/" + fileName; // 웹에서 접근 가능한 이미지 URL 생성
	            System.out.println("생성된 이미지 URL: " + imageUrl); // 이미지 URL 로그
	            System.out.println("생성된 이미지 URL을 리스트에 추가: " + imageUrl); // URL 리스트 추가 로그
	            uploadedImageUrls.add(imageUrl); // 웹에서 접근 가능한 이미지 URL을 생성하여 uploadedImageUrls 리스트에 추가합니다.
	            // request.getContextPath()는 웹 애플리케이션의 컨텍스트 경로를 반환하며, "/resources/img/" + fileName은 이미지 파일의 상대 경로입니다.
	        }
	        System.out.println("모든 파일 처리 완료."); // 모든 파일 처리 완료 로그
	    }
	    catch (Exception e) // 파일 업로드 중 발생할 수 있는 예외를 처리하기 위한 catch 블록입니다.
	    {
	    	System.out.println("파일 업로드 중 예외 발생!"); // 예외 발생 로그
	        e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력하여 디버깅에 활용합니다.
	        // 실제 운영 환경에서는 e.printStackTrace() 대신 로깅 라이브러리를 사용하여 로그를 기록하는 것이 좋습니다.
	        System.out.println("예외 발생으로 인해 빈 리스트를 반환합니다."); // 예외 발생시 빈 리스트 반환 로그
	        return Collections.emptyList(); // 예외 발생 시 빈 리스트를 반환하여 클라이언트에게 에러 상황을 알립니다.
	        // (실제 서비스에서는 에러 코드나 메시지를 반환하는 것이 더 나은 사용자 경험을 제공할 수 있습니다.)
	    }

	    System.out.println("업로드된 이미지 URL 리스트 반환: " + uploadedImageUrls); // 최종 결과 반환 로그
	    return uploadedImageUrls; // 최종적으로 업로드된 이미지의 URL 리스트를 반환합니다.
	}

	
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
	    
	    return "redirect:/allBoard?page=1";
	}
}


