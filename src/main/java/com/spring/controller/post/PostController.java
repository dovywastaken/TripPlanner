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
import com.spring.service.post.CommentService;
import com.spring.service.post.PostService;

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
	                         RedirectAttributes redirect
			) {
		
	    try {
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        Member member = (Member) session.getAttribute("user");
	        post.setId(member.getId());
	        post.setPublishDate(timestamp);

	        // 저장된 파일 이름 리스트
	        List<String> savedFileNames = new ArrayList<>();

	        // 파일 저장 디렉토리 확인
	        String saveDir = session.getServletContext().getRealPath("/resources/upload/");
	        File dir = new File(saveDir);
	        if (!dir.exists()) {
	            dir.mkdirs(); // 경로가 없으면 생성
	        }
	        
	        // 업로드된 파일 처리
	        for (MultipartFile file : files) {
	            if (file != null && !file.isEmpty()) {
	                String originalFilename = file.getOriginalFilename();
	                String savedFileName = UUID.randomUUID() + "_" + originalFilename;

	                String savePath = saveDir + savedFileName;
	                System.out.println(savePath);
	                file.transferTo(new File(savePath));

	                // 저장된 파일 이름 추가
	                savedFileNames.add(savedFileName);
	            }
	        }
	       
	        // Post 객체에 파일 이름 리스트 설정
	        post.setFileImage(savedFileNames);
	        // 게시물 저장 서비스 호출
	        postService.createPost(post);
	        int postId = postService.getLatestPostId(member.getId());
	       
	        
	        redirect.addAttribute("num", postId);
	        redirect.addAttribute("page", 1);
	        
	        
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
	    return "redirect:/post/Allboard?page=1";
	}

	@GetMapping("/postview/update")
	public String postupdate(@RequestParam int num,Model model) {
		Post result=(Post) postService.getPostById(num);
		model.addAttribute("result",result);
		return "post/updateForm";
	}
	
	@PostMapping("/postview/updatePost")
	public String postupdate(@RequestParam int num, @ModelAttribute Post post,Model model) {
		postService.updatePost(post,num);
		System.out.println(num);
		Post result=(Post) postService.getPostById(num);
		model.addAttribute("result",result);
		return "redirect:/post/Postview?num="+num;
	}
	
	@PostMapping(value="/submitForm",produces = "application/json")
    @ResponseBody
    public String submitForm(@RequestBody List<Map<String, Object>> myListData, HttpSession session) {
        System.out.println("여기는 왓음?");
        session.setAttribute("myListData", myListData);
        return "success"; // 클라이언트로 성공 메시지 반환
    }
	
}
