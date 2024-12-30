package com.spring.controller.post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

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
        String id=null;
        if(member!=null) {
        id =member.getId();
        response = commentService.getCommentsByPostId(postId, page, pageSize,id);
        }else {
        response = commentService.getCommentsByPostId(postId, page, pageSize,id);	
        }
        List<Comment> comments=(List<Comment>) response.get("comments");
        List<Integer> commentisLike=(List<Integer>) response.get("isLike");
        
        response.put("commentisLike", commentisLike);
        response.put("comments", comments);
        response.put("currentPage", page);
        response.put("totalPage", totalPage);
        response.put("totalCount", totalCount);

        return response;
    }

    @PostMapping
    public Map<String,Object> createComment(@RequestParam("postId") int postId,
                                            @RequestParam("id") String userId,
                                            @RequestParam("comments") String commentText) {
    	
    	Timestamp timestamp =new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setP_unique(postId);
        comment.setId(userId);
        comment.setComments(commentText);
        comment.setCommentDate(timestamp);
        comment.setCommentLikes(0);

        commentService.insertComment(comment);

        Map<String,Object> response = new HashMap<>();
        response.put("message", "success");
        return response;
    }

    @DeleteMapping("/{c_unique}")
    public Map<String,Object> deleteComment(@PathVariable int c_unique) {
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
    	String id=null;
    	if(member!=null) {
    	 id=member.getId();
    	 like.setId(id);
    	 like.setC_unique(c_unique);
    	 like.setLikesDate(timestamp);
    	 result=commentService.incrementCommentLikes(like);
    	 int islike=result.get(0);
    	 int totallike=result.get(1);
    	 
    	 System.out.println("눌림:"+islike);
    	 System.out.println("총수"+totallike);
    	 response.put("id", id);
    	 response.put("islike", islike);
    	 response.put("totallike", totallike);
    	 return response;
    	}else {
    	 response.put("id", id);
    	 return response;
    	}
       
    }
}

