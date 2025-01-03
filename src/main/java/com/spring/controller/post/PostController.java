package com.spring.controller.post;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Controller
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	ServletContext servletContext;
	
	@GetMapping("/postform")
	public String postform() {
		return "post/PostForm";
		
		
	}
	
	@PostMapping("/saveforem")
    public String saveMyList(@RequestParam("myListData") String myListData, Model model) {
        // JSON 문자열을 필요한 형태로 파싱하거나 처리
        // 여기서는 단순히 모델에 추가
        model.addAttribute("myListData", myListData);
        return "post/PostForm"; // write.jsp로 포워딩
    }
	@PostMapping("/postcreate")
	public String postcreate(@ModelAttribute Post post,
	                       HttpSession session,
	                       @RequestParam("fileImg") List<MultipartFile> files,
	                       RedirectAttributes redirect,
	                       @RequestParam(required = false) String contents) {

	   try {
	       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	       Member member = (Member) session.getAttribute("user");
	       post.setId(member.getId());
	       post.setPublishDate(timestamp);
	       List<String> savedFileNames = new ArrayList<>();
	       String saveDir = session.getServletContext().getRealPath("/resources/upload/");
	       File dir = new File(saveDir);
	       if (!dir.exists()) {
	           dir.mkdirs();
	       }

	       for (MultipartFile file : files) {
	           if (file != null && !file.isEmpty()) {
	               String originalFilename = file.getOriginalFilename();
	               String savedFileName = UUID.randomUUID() + "_" + originalFilename;
	               String savePath = saveDir + savedFileName;
	               file.transferTo(new File(savePath));
	               savedFileNames.add(savedFileName);
	           }
	       }
	       
	       post.setFileImage(savedFileNames);
	       postService.createPost(post);
	       int postId = postService.getLatestPostId(member.getId());
	       int page = postService.pageserch(postId);
	       redirect.addAttribute("num", postId);
	       redirect.addAttribute("page", page);

	       if (contents != null && !contents.isEmpty()) {
	           Document doc = Jsoup.parse(contents);
	           Elements locationButtons = doc.select(".location-btn");

	           for (Element button : locationButtons) {
	               String dataInfo = button.attr("data-info");
	               if (dataInfo != null && !dataInfo.isEmpty()) {
	                   try {
	                       JSONObject jsonObject = new JSONObject(dataInfo);
	                       
	                       String itemId = jsonObject.optString("id", "");
	                       String itemAddr = jsonObject.optString("addr", "");
	                       Double latitude = jsonObject.optDouble("latitude", 0.0);
	                       Double longitude = jsonObject.optDouble("longitude", 0.0);
	                       String itemContentId = jsonObject.optString("contentid", "");
	                       String itemContentTypeId = jsonObject.optString("contenttypeid", "");
	                       String itemImg = jsonObject.optString("img", "");
	                       String itemCategory2 = jsonObject.optString("catagory2", "");
	                       String itemCategory3 = jsonObject.optString("catagory3", "");

	                       Tour tour = new Tour();
	                       tour.setTitle(itemId);
	                       tour.setAddr1(itemAddr);
	                       tour.setMapy(latitude);
	                       tour.setMapx(longitude);
	                       tour.setContentid(itemContentId);
	                       tour.setContenttypeid(itemContentTypeId);
	                       tour.setFirstimage(itemImg);
	                       tour.setCat2(itemCategory2);
	                       tour.setCat3(itemCategory3);
	                       tour.setCreated_at(timestamp);
	                       tour.setP_unique(postId);
	                       postService.updatetour(tour);
	                        
	                       
	                   } catch (JSONException e) {
	                       System.out.println("JSON 파싱 오류: " + e.getMessage());
	                       continue;
	                   }
	               }
	           }
	       }

	       

	       return "redirect:/postview";

	   } catch (Exception e) {
	       e.printStackTrace();
	       return "error";
	   }
	}
	
	
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/postview")
    public String postview(@RequestParam int num,
                           @RequestParam int page,
                           Model model, HttpSession session) {
    	Member member=(Member) session.getAttribute("user");
    	int isLike=0;
    	if(member !=null) {
    	isLike=postService.getIdisLike(member.getId(),num);
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
        model.addAttribute("member",member) ;
        return "post/Postview";
    }
    

    @PostMapping(value= "/{postId}/like",produces = "application/json")
    @ResponseBody
    public Map<String, Object> likePost(@PathVariable int postId, HttpSession session) {
    	System.out.println("Request received for postId: " + postId);
    	
    	Timestamp timestamp=new Timestamp(System.currentTimeMillis());
    	Member member=(Member)session.getAttribute("user");
    	Likes likes=new Likes();
    	Map<String, Object> response = new HashMap<>();
    	List<Integer> result = new ArrayList<Integer>();
        if(member!=null) {	
        String id=member.getId();
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
        }else {
        String id=null;
        response.put("id", id);
        return response;
        }
        
        
    }
	
	@GetMapping("/postview/delete")
	public String postDelete(@RequestParam int num, Model model) {
	    postService.deletePost(num);
	    return "redirect:/Allboard?page=1";
	}

	@GetMapping("/postview/update")
	public String postupdate(@RequestParam int num,Model model) {
		Post result=(Post) postService.getPostById(num);
		model.addAttribute("result",result);
		return "post/updateForm";
	}
	
	@PostMapping("/postview/updatePost")
	public String postupdate(@ModelAttribute Post post,
	                        HttpSession session,
	                        @RequestParam("fileImg") List<MultipartFile> files,
	                        RedirectAttributes redirect,
	                        @RequestParam(required = false) String contents) {
	    try {
	        // 작성자 확인
	    	
	        Member member = (Member) session.getAttribute("user");
	        if (!post.getId().equals(member.getId())) {
	        	System.out.println("여왓냐");
	        }
	        System.out.println("12"+post.getContents());
	        // 기존 게시글 정보 가져오기 
	        Post existingPost = postService.getPostById(post.getP_unique());
	        
	        // 기존 파일 리스트에 새 파일 추가
	        List<String> savedFileNames = new ArrayList<>();
	        if (existingPost.getFileImage() != null && !existingPost.getFileImage().isEmpty()) {
	            savedFileNames.addAll(existingPost.getFileImage());
	        }

	        // 새 파일 업로드
	        String saveDir = session.getServletContext().getRealPath("/resources/upload/");
	        File dir = new File(saveDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        for (MultipartFile file : files) {
	            if (file != null && !file.isEmpty()) {
	                String originalFilename = file.getOriginalFilename();
	                String savedFileName = UUID.randomUUID() + "_" + originalFilename;
	                String savePath = saveDir + savedFileName;
	                file.transferTo(new File(savePath));
	                savedFileNames.add(savedFileName);
	            }
	        }

	        // 게시글 정보 업데이트
	        post.setFileImage(savedFileNames);
	        post.setPublishDate(existingPost.getPublishDate()); // 기존 작성일 유지
	        postService.updatePost(post);

	        // 투어 정보 처리 - contents에서 찾은 투어 정보로 update
	        if (contents != null && !contents.isEmpty()) {
	            Document doc = Jsoup.parse(contents);
	            Elements locationButtons = doc.select(".location-btn");

	            for (Element button : locationButtons) {
	                String dataInfo = button.attr("data-info");
	                if (dataInfo != null && !dataInfo.isEmpty()) {
	                    try {
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
	                        
	                        // update 메소드로 처리
	                        postService.updatetour(tour);
	                    } catch (JSONException e) {
	                        System.out.println("JSON 파싱 오류: " + e.getMessage());
	                        continue;
	                    }
	                }
	            }
	        }

	        int page = postService.pageserch(post.getP_unique());
	        redirect.addAttribute("num", post.getP_unique());
	        redirect.addAttribute("page", page);

	        return "redirect:/postview";

	    } catch (Exception e) {
	        e.printStackTrace(); // 예외의 스택 트레이스를 출력
	        return "An error occurred: " + e.getMessage(); // 에러 메시지 반환
	    }
	}
	
	@PostMapping(value="/submitForm",produces = "application/json")
    @ResponseBody
    public String submitForm(@RequestBody List<Map<String, Object>> myListData, HttpSession session) {
        session.setAttribute("myListData", myListData);
        return "success"; // 클라이언트로 성공 메시지 반환
    }
	
}


