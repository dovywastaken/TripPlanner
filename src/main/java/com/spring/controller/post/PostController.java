package com.spring.controller.post;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.Member;
import com.spring.domain.Post;
import com.spring.service.post.CommentService;
import com.spring.service.post.PostService;

@Controller
public class PostController {
	
	@Autowired
	PostService postService;

	
	@GetMapping("/postform")
	public String postform() {
		return "post/PostForm";
		
		
	}
	@PostMapping("/postcreate")
	public String postcreate(@ModelAttribute Post post,HttpSession session) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Member member = (Member)session.getAttribute("user");
		post.setId(member.getId());
		post.setPublishDate(timestamp);
		postService.createPost(post);
		return "post/Postview";
	}
	
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/postview")
    public String postview(@RequestParam int num,
                           @RequestParam int page,
                           Model model, HttpSession session) {
        Post onepost = postService.getPostById(num);
        System.out.println("여기"+page);
        int commentCount = commentService.countCommentsByPostId(num);
        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) commentCount / pageSize);
 
        String postdate = new DateFormatter().formatBoardDate(onepost.getPublishDate());
        model.addAttribute("onepost", onepost);
        model.addAttribute("postdate", postdate);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postId", num);
        model.addAttribute("currentPage", page); // 현재 페이지 번호 유지
        
        Member member = (Member) session.getAttribute("user");
        model.addAttribute("member", member);

        return "post/Postview";
    }
    

    @PostMapping("/{postId}/like")
    public Map<String, Object> likePost(@PathVariable int postId) {
        postService.incrementPostLike(postId);
        Post updated = postService.getPostById(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "liked");
        response.put("likes", updated.getLikes());
        return response;
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
	
}
