$(document).ready(function() {
    let currentPageNum = 1;

    // 초기 댓글 로딩
    if (typeof totalPages !== 'undefined' && totalPages > 0) {
        loadComments(postId, totalPages);
    } else {
        loadComments(postId, 1);
    }

    // 댓글 작성
    $("#submitCommentBtn").on("click", function() {
        if (!sessionId) {
            alert("로그인이 필요합니다.");
            return;
        }

        var commentContent = $("#commentContent").val().trim();
        if (commentContent === "") {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        $.ajax({
            url: contextPath+"/api/comments",
            method: "POST",
            dataType: "json",
            data: {
                postId: postId,
                id: sessionId,
                comments: commentContent
            },
            success: function(response) {
                if (response.message === "success") {
                    $("#commentContent").val("");
                    doubleLoadLastPage();
                } else {
                    alert("댓글 작성에 실패했습니다.");
                }
            },
            error: function() {
                alert("댓글 작성 중 오류가 발생했습니다.");
            }
        });
    });

    // 페이지네이션 클릭
    $(document).on("click", ".page-link", function(e) {
        e.preventDefault();
        var page = $(this).data("page");
        loadComments(postId, page);
    });

    // 댓글 삭제
    $(document).on("click", ".deleteBtn", function() {
        var c_unique = $(this).closest(".comment").data("id");
        if (!confirm("댓글을 삭제하시겠습니까?")) return;

        $.ajax({
            url: contextPath+"/api/comments/" + c_unique,
            method: "DELETE",
            dataType: "json",
            success: function(res) {
                if (res.message === "deleted") {
                    doubleLoadLastPage();
                } else {
                    alert("댓글 삭제에 실패했습니다.");
                }
            },
            error: function() {
                alert("댓글 삭제 중 오류가 발생했습니다.");
            }
        });
    });

    // 댓글 수정 모드
    $(document).on("click", ".updateBtn", function() {
        var commentDiv = $(this).closest(".comment");
        var originalContent = commentDiv.find(".comment-content").text().trim();
        
        // 기존 내용 숨기기
        commentDiv.find(".comment-text").hide();
        commentDiv.find(".comment-controls").hide();
        
        // 수정 폼 추가
        var editForm = $('<div class="edit-form">' +
            '<textarea class="edit-input">' + originalContent + '</textarea>' +
            '<div class="edit-buttons">' +
            '<button class="saveBtn">저장</button> ' +
            '<button class="cancelBtn">취소</button>' +
            '</div></div>');
        
        commentDiv.append(editForm);
    });

    // 댓글 수정 취소
    $(document).on("click", ".cancelBtn", function() {
        var commentDiv = $(this).closest(".comment");
        commentDiv.find(".comment-text").show();
        commentDiv.find(".comment-controls").show();
        commentDiv.find(".edit-form").remove();
    });

    // 댓글 수정 저장
    $(document).on("click", ".saveBtn", function() {
        var commentDiv = $(this).closest(".comment");
        var c_unique = commentDiv.data("id");
        var updatedContent = commentDiv.find(".edit-input").val().trim();

        if (updatedContent === "") {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        $.ajax({
            url: contextPath+"/api/comments/" + c_unique + "/update",
            method: "POST",
            data: {
                content: updatedContent
            },
            success: function(res) {
                if (res.message === "updated") {
                    loadComments(postId, currentPageNum);
                } else {
                    alert("댓글 수정에 실패했습니다.");
                }
            },
            error: function() {
                alert("댓글 수정 중 오류가 발생했습니다.");
            }
        });
    });

    // 댓글 좋아요
    $(document).on("click", ".likeBtn", function() {
        const commentElement = $(this).closest(".comment");
        const c_unique = commentElement.data("id");
        const likeBtn = $(this);

        $.ajax({
            url: contextPath+`/api/comments/${c_unique}/like`,
            method: "POST",
            dataType: "json",
            success: function(res) {
                if (!res.id) {
                    alert("로그인이 필요합니다.");
                    return;
                }
                if (res.islike === 1) {
                    likeBtn.html("<i class='fa-solid fa-thumbs-up'></i>");
                } else {
                    likeBtn.html("<i class='fa-regular fa-thumbs-up'></i>");
                }
                commentElement.find(".likenum").text(`${res.totallike}`);
            },
            error: function() {
                alert("좋아요 처리 중 오류가 발생했습니다.");
            }
        });
    });

    // 게시글 좋아요
	// 게시글 좋아요 수정된 버전
	$("#postLikeBtn").on("click", function() {
	    $.ajax({
	        url: contextPath + "/" + postId + "/like",  // 원래 URL 구조 유지
	        method: "POST",
	        dataType: "json",
	        success: function(res) {           
	            if(res.id === null) {
	                alert("로그인이 필요합니다.");
	                return;
	            }

	            const likenum = document.getElementById("postLikes");
	            if(likenum) {
	                if(res.isLiked === 1) {
	                    document.getElementById("postLikes").textContent = res.totalLikes;
	                    document.getElementById("postLikeBtn").innerHTML = "<i class='fa-solid fa-thumbs-up'></i>";
	                } else if(res.isLiked === 0) {
	                    document.getElementById("postLikes").textContent = res.totalLikes;
	                    document.getElementById("postLikeBtn").innerHTML = "<i class='fa-regular fa-thumbs-up'></i>";
	                }
	            }
	        },
	        error: function() {
	            alert("포스트 좋아요 처리 중 오류가 발생했습니다.");
	        }
	    });
	});

    // 댓글 목록 로드
    function loadComments(postId, page, callback) {
        $.ajax({
            url: contextPath+"/api/comments",
            method: "GET",
            dataType: "json",
            data: { postId: postId, page: page },
            success: function(res) {
                if (!res.comments || res.comments.length === 0) {
                    $("#commentSection").html("<p class='no-comments'>댓글이 없습니다.</p>");
                    $("#pagination").empty();
                    totalPages = 0;
                    currentPageNum = 1;
                } else {
                    renderComments(res.comments, res.commentisLike,res.commentDate);
                    renderPagination(res.currentPage, res.totalPage);
                    totalPages = res.totalPage;
                    currentPageNum = res.currentPage;
                }
                if (typeof callback === 'function') {
                    callback(res);
                }
            },
            error: function() {
                alert("댓글을 불러오는 중 오류가 발생했습니다.");
            }
        });
    }

    // 댓글 렌더링
	function renderComments(comments, commentisLike,commentDate) {
	    var section = $("#commentSection");
	    section.empty();

	    comments.forEach((comment, i) => {
	        var html = `
	            <div class="comment" data-id="${comment.c_unique}">
	                <div class="comment-header">
	                    <span class="comment-author">${comment.email}</span>
	                    <span class="comment-date">${commentDate[i]}</span>
	                </div>
	                <div class="comment-content">${comment.comments}</div>
	                <div class="comment-controls">
	                    ${sessionId === comment.id ? 
	                        `<button class="updateBtn"></button>
	                         <button class="deleteBtn"></button>` :
	                        `<button class="likeBtn">
	                            <i class="${commentisLike[i] === 1 ? 'fa-solid' : 'fa-regular'} fa-thumbs-up"></i>
	                        </button>
	                        <span class="likenum">${comment.commentLikes}</span>`
	                    }
	                </div>
	            </div>`;
	        section.append(html);
	    });
	}
    // 페이지네이션 렌더링
    function renderPagination(currentPage, totalPage) {
        var pagination = $("#pagination");
        pagination.empty();

        if (totalPage <= 1) return;

        var html = '<div class="pagination-container">';
        for (var i = 1; i <= totalPage; i++) {
            if (i === currentPage) {
                html += `<span class="page-current">${i}</span>`;
            } else {
                html += `<a href="#" class="page-link" data-page="${i}">${i}</a>`;
            }
        }
        html += '</div>';
        pagination.html(html);
    }

    // 더블 로딩 (마지막 페이지 갱신)
    function doubleLoadLastPage() {
        loadComments(postId, 999999, function(res) {
            loadComments(postId, res.totalPage);
        });
    }
});