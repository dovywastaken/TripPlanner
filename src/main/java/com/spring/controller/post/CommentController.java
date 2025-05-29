package com.spring.controller.post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
    @Autowired
    private CommentService commentService;

    @GetMapping
    public Map<String, Object> getComments(@RequestParam("postId") int postId,
                                           @RequestParam(value="page", defaultValue="1") int page,
                                           HttpSession session) {

        logger.info("===========================================================================================");
        logger.info("CommentController : getComments (GET) 매핑. 댓글 조회 요청 - postId: {}, page: {}", postId, page);

    	Member member=(Member) session.getAttribute("user");
        String email = null;
        if(member!=null)
        {
        	email = member.getEmail();
            logger.info("세션에서 사용자 정보 확인됨: {}", email);
        }
        else
        {
            logger.info("세션에 사용자 정보 없음 (비로그인 사용자).");
        }

    	Map<String, Object> response = new HashMap<>();
        int pageSize = 10;
        logger.debug("한 페이지당 댓글 수 (pageSize): {}", pageSize);

        int totalCount = commentService.countCommentsByPostId(postId);
        logger.info("게시글 {}의 총 댓글 수: {}", postId, totalCount);

        int totalPage = (int)Math.ceil((double)totalCount / pageSize);
        logger.info("총 댓글 페이지 수: {}", totalPage);

        // email 값에 따라 commentService 호출
        response = commentService.getCommentsByPostId(postId, page, pageSize, email);
        logger.info("댓글 목록 및 좋아요 정보 조회 완료.");

        List<Comment> comments = (List<Comment>) response.get("comments");
        List<String> commentDate = new ArrayList<String>();
        DateFormatter dateFormatter = new DateFormatter();

        if (comments != null && !comments.isEmpty()) {
            logger.info("댓글 {}개에 대한 날짜 포맷팅 시작.", comments.size());
            for(int i=0; i<comments.size(); i++)
            {
                String time=dateFormatter.formatBoardDate(comments.get(i).getCommentDate());
                commentDate.add(time);
                logger.debug(" - 댓글 (ID: {}) 날짜 포맷팅: {}", comments.get(i).getC_unique(), time); // 상세 정보는 DEBUG 레벨로
            }
            logger.info("댓글 날짜 포맷팅 완료.");
        } else {
            logger.info("조회된 댓글이 없습니다.");
        }

        List<Integer> commentisLike=(List<Integer>) response.get("isLike");
        logger.debug("댓글별 좋아요 상태 목록: {}", commentisLike);

        response.put("commentDate", commentDate);
        response.put("commentisLike", commentisLike);
        response.put("comments", comments);
        response.put("currentPage", page);
        response.put("totalPage", totalPage);
        response.put("totalCount", totalCount);
        logger.info("최종 응답 Map에 데이터 설정 완료.");
        logger.debug("최종 응답 Map: {}", response); // 최종 응답 데이터는 DEBUG 레벨로 상세 로깅

        return response;
    }

    @PostMapping
    public Map<String,Object> createComment(@RequestParam("postId") int postId,
                                            @RequestParam("id") String userEmail,
                                            @RequestParam("comments") String commentText)
    {
        logger.info("===========================================================================================");
        logger.info("CommentController : createComment (POST) 매핑. 댓글 생성 요청 받음.");
        logger.info(" - postId: {}, userEmail: {}, commentText 길이: {}", postId, userEmail, commentText.length());
        logger.debug(" - commentText 내용: {}", commentText); // 실제 댓글 내용은 DEBUG 레벨로

    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setP_unique(postId);
        comment.setEmail(userEmail);
        comment.setComments(commentText);
        comment.setCommentDate(timestamp);
        comment.setCommentLikes(0); // 초기 좋아요 수는 0으로 설정

        logger.debug("생성할 Comment DTO: {}", comment);

        Map<String,Object> response = new HashMap<>();
        try {
            commentService.insertComment(comment);
            logger.info("댓글 성공적으로 DB에 저장됨 (postId: {}, userEmail: {}).", postId, userEmail);
            response.put("message", "success");
        } catch (Exception e) {
            logger.error("[ERROR] 댓글 저장 중 예외 발생: {}", e.getMessage(), e);
            response.put("message", "fail");
            response.put("errorMessage", "댓글 생성 중 오류가 발생했습니다.");
            // 필요에 따라 HTTP Status Code를 변경할 수도 있습니다.
            // 예: throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 생성 실패", e);
        }

        logger.debug("최종 응답 Map: {}", response);
        return response;
    }

    @DeleteMapping("/{c_unique}")
    public Map<String,Object> deleteComment(@PathVariable int c_unique)
    {
        logger.info("===========================================================================================");
        logger.info("CommentController : deleteComment (DELETE) 매핑. 댓글 삭제 요청 받음. 댓글 ID: {}", c_unique);

        Map<String,Object> response = new HashMap<>();
        try {
            commentService.deleteComment(c_unique);
            logger.info("댓글 ID {} 성공적으로 삭제됨.", c_unique);
            response.put("message", "deleted");
        } catch (Exception e) {
            logger.error("[ERROR] 댓글 ID {} 삭제 중 예외 발생: {}", c_unique, e.getMessage(), e);
            response.put("message", "failed"); // 삭제 실패 메시지
            response.put("errorMessage", "댓글 삭제 중 오류가 발생했습니다.");
            // 필요에 따라 HTTP Status Code를 변경할 수도 있습니다.
            // 예: throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "댓글 삭제 실패", e);
        }

        logger.debug("최종 응답 Map: {}", response);
        return response;
    }

    @PostMapping(value ="/{c_unique}/like",produces = "application/json")
    public Map<String,Object> likeComment(@PathVariable int c_unique,HttpSession session) {

        logger.info("===========================================================================================");
        logger.info("CommentController : /{}/like (POST) 매핑. 댓글 좋아요 요청 받음. 댓글 ID: {}", c_unique, c_unique);

    	Member member =(Member) session.getAttribute("user");
    	Map<String,Object> response = new HashMap<String, Object>();
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	Likes like = new Likes();
    	List<Integer> result = new ArrayList<Integer>(); // 결과 값을 담을 리스트

    	String email = null;

    	if(member!=null)
    	{
            email = member.getEmail();
            logger.info("세션에서 사용자 정보 확인됨: {}", email);

            like.setId(email);
            like.setC_unique(c_unique);
            like.setLikesDate(timestamp);
            logger.debug("Likes 객체 생성: {}", like);

            try {
                result = commentService.incrementCommentLikes(like);
                int isLikeResult = result.get(0); // 좋아요 상태 (1: 좋아요, 0: 좋아요 취소)
                int totalLikes = result.get(1); // 총 좋아요 수

                logger.info("댓글 ID {}에 대한 좋아요 처리 결과: isLike={}, totalLikes={}", c_unique, isLikeResult, totalLikes);

                response.put("id", email);
                response.put("islike", isLikeResult);
                response.put("totallike", totalLikes);

            } catch (Exception e) {
                logger.error("[ERROR] 댓글 ID {}에 대한 좋아요 처리 중 예외 발생: {}", c_unique, e.getMessage(), e);
                response.put("id", email); // 오류 발생 시에도 사용자 ID는 포함
                response.put("islike", -1); // 오류를 나타내는 값
                response.put("totallike", -1); // 오류를 나타내는 값
                response.put("errorMessage", "댓글 좋아요 처리 중 오류가 발생했습니다.");
            }
    	}
    	else
    	{
            logger.warn("비로그인 사용자의 댓글 좋아요 시도. 댓글 ID: {}", c_unique);
            // email은 기본값인 null로 유지됩니다.
    	    response.put("id", email); // id는 null이 될 것임
    	    response.put("islike", -1); // 비로그인 상태를 나타내는 값
    	    response.put("totallike", -1); // 비로그인 상태를 나타내는 값
    	    response.put("errorMessage", "로그인이 필요합니다.");
    	}

        logger.debug("최종 응답 Map: {}", response);
        return response;
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
   


