package com.spring.controller.post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.spring.domain.Comment;
import com.spring.domain.Likes;
import com.spring.domain.Member;
import com.spring.service.post.CommentService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public Map<String, Object> getComments(@RequestParam("postId") int postId,
                                           @RequestParam(value="page", defaultValue="1") int page,
                                           HttpSession session) {
    	
    	Member member=(Member) session.getAttribute("user");
    	Map<String, Object> response = new HashMap<>();
        int pageSize = 10;
        int totalCount = commentService.countCommentsByPostId(postId);
        int totalPage = (int)Math.ceil((double)totalCount / pageSize);
        String email = null;
        if(member!=null) 
        {
        	email = member.getEmail();
        	response = commentService.getCommentsByPostId(postId, page, pageSize,email);
        }
        else 
        {
        	response = commentService.getCommentsByPostId(postId, page, pageSize,email);	
        }
        List<Comment> comments = (List<Comment>) response.get("comments");
        List<String> commentDate = new ArrayList<String>();
        DateFormatter dateFormatter = new DateFormatter();
        for(int i=0;i<comments.size();i++) 
        {
        	String time=dateFormatter.formatBoardDate(comments.get(i).getCommentDate());
        	commentDate.add(time);
        }
        List<Integer> commentisLike=(List<Integer>) response.get("isLike");
        
        response.put("commentDate", commentDate);
        response.put("commentisLike", commentisLike);
        response.put("comments", comments);
        response.put("currentPage", page);
        response.put("totalPage", totalPage);
        response.put("totalCount", totalCount);

        return response;
    }

    @PostMapping
    public Map<String,Object> createComment(@RequestParam("postId") int postId,
                                            @RequestParam("id") String userEmail,
                                            @RequestParam("comments") String commentText) 
    {
    	
    	Timestamp timestamp =new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setP_unique(postId);
        comment.setEmail(userEmail);
        comment.setComments(commentText);
        comment.setCommentDate(timestamp);
        comment.setCommentLikes(0);

        commentService.insertComment(comment);

        Map<String,Object> response = new HashMap<>();
        response.put("message", "success");
        
        return response;
    }

    @DeleteMapping("/{c_unique}")
    public Map<String,Object> deleteComment(@PathVariable int c_unique) 
    {
        commentService.deleteComment(c_unique);
        Map<String,Object> response = new HashMap<>();
        response.put("message", "deleted");
        
        return response;
    }

    @PostMapping(value ="/{c_unique}/like",produces = "application/json")
    public Map<String,Object> likeComment(@PathVariable int c_unique,HttpSession session) {
    	Member member =(Member) session.getAttribute("user");
    	Map<String,Object> response=new HashMap<String, Object>();
    	Timestamp timestamp=new Timestamp(System.currentTimeMillis());
    	Likes like=new Likes();
    	List<Integer> result=new ArrayList<Integer>();
    	String email = null;
    	
    	if(member!=null) 
    	{
	    	email = member.getEmail();
	    	like.setId(email);
	    	like.setC_unique(c_unique);
	    	like.setLikesDate(timestamp);
	    	result=commentService.incrementCommentLikes(like);
	    	int islike=result.get(0);
	    	int totallike=result.get(1);

	    	response.put("id", email);
	    	response.put("islike", islike);
	    	response.put("totallike", totallike);
	    	
	    	return response;
    	}
    	else 
    	{
    	 response.put("id", email);
    	 
    	 return response;
    	}
       
    }
    
    @PostMapping("/{c_unique}/update")
    public ResponseEntity<Map<String, Object>> updateComment(
            @PathVariable int c_unique,
            @RequestParam String content,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 세션 체크
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            response.put("message", "unauthorized");
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            // 댓글 존재 여부 및 작성자 확인
            Comment comment = commentService.getCommentById(c_unique);
            if (comment == null) {
                response.put("message", "not_found");
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // 작성자 본인 확인
            if (!comment.getEmail().equals(member.getEmail())) {
                response.put("message", "forbidden");
                
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            // 댓글 내용 업데이트
            comment.setComments(content);
            //commentService.updateComment(c_unique,comment);

            response.put("message", "updated");
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "error");
            response.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
   


