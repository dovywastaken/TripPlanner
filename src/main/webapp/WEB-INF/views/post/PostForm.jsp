<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성/수정</title>
<!-- Summernote CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-lite.min.css" rel="stylesheet">

<!-- PostForm CSS -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/postForm.css">

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- jQuery UI -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

<!-- Summernote JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-lite.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/lang/summernote-ko-KR.min.js"></script>

<!-- Editor JS 파일 (module 제거) -->
<script src="${pageContext.request.contextPath}/resources/js/editor.js"></script>

</head>
<body>
    <%@ include file="../header.jsp" %>
       <form id="postForm" action="${pageContext.request.contextPath}/postcreate" method="POST" enctype="multipart/form-data">
    <div class="container">
        <div class="left">
            <button type="button" id="open-map" class="map-button">지도</button>
            <div class="my-list">
                <div id="myListContainer">
                    <p>저장된 장소가 없습니다.</p>
                </div>
            </div>
        </div>

          <div class="center">
          		<div id="toolbar-container"></div>
                <div id="titles" contenteditable="true" class="title-div" style="border: 1px solid #ccc; padding: 8px; margin-bottom: 10px;">
                    제목을 입력하세요
                </div>
                <input type="hidden" id="title" name="title" class="title-input" placeholder="제목을 입력하세요">
                <textarea id="summernote" name="contents"></textarea>
        </div>

        <div class="right">
            <div class="settings-box">
                <div class="settings-group">
                    <h4>공개 설정</h4>
                    <div class="radio-group">
                        <div class="radio-item">
                            <input type="radio" name="isPrivate" id="public" value="0" checked>
                            <label for="public">공개</label>
                        </div>
                        <div class="radio-item">
                            <input type="radio" name="isPrivate" id="private" value="1">
                            <label for="private">비공개</label>
                        </div>
                    </div>
                </div>

                <div class="settings-group">
                    <h4>댓글 설정</h4>
                    <div class="radio-group">
                        <div class="radio-item">
                            <input type="radio" name="commentIsAllowed" id="allow" value="1" checked>
                            <label for="allow">허용</label>
                        </div>
                        <div class="radio-item">
                            <input type="radio" name="commentIsAllowed" id="disallow" value="0">
                            <label for="disallow">비허용</label>
                        </div>
                    </div>
                </div>

                <button type="submit" form="postForm" class="submit-btn">작성하기</button>
            </div>
        </div>
    </div>
     </form>

    <%@ include file="../footerCompact.jsp" %>
<script>
$(document).ready(function () {
	 $('#summernote').summernote({
	        lang: 'ko-KR',
	        height: 500,
	        callbacks: {  // 여기만 추가
	            onImageUpload: function(files) {
	                for(let file of files) {
	                    const formData = new FormData();
	                    formData.append("fileImg", file);
	                    $.ajax({
	                        data: formData,
	                        type: "POST",
	                        url: "/TripPlanner/uploadImage",
	                        contentType: false,
	                        processData: false,
	                        success: (data) => {
	                            if(data && data.length > 0) {
	                                const imgNode = $('<img>').attr('src', data[0]);
	                                $(this).summernote('insertNode', imgNode[0]);
	                            } else {
	                                alert('이미지 업로드 실패');
	                            }
	                        },
	                        error: function() {
	                            alert('이미지 업로드 실패');
	                        }
	                    });
	                }
	            }
	        },
	        toolbar: [
	            ['style', ['bold', 'italic', 'underline', 'clear']],
	            ['font', ['strikethrough', 'superscript', 'subscript']],
	            ['fontsize', ['fontsize']],
	            ['color', ['color']],
	            ['para', ['ul', 'ol', 'paragraph']],
	            ['height', ['height']],
	            ['insert', ['link', 'picture', 'video']],
	            ['view', ['fullscreen', 'codeview', 'help']]
	        ]
	    });

	    $('#open-map').click(function () {
	        // 현재 작성 중인 내용을 객체로 구성
	        const tempData = {
	            title: $('#titles').text().trim(),
	            content: $('#summernote').summernote('code'),
	            isPrivate: $('input[name="isPrivate"]:checked').val(),
	            commentIsAllowed: $('input[name="commentIsAllowed"]:checked').val()
	        };

	        // sessionStorage에 저장
	        sessionStorage.setItem('tempPostData', JSON.stringify(tempData));
	        
	        // 지도 페이지로 이동
	        window.location.href = '/TripPlanner/Maps';
	    });

	    // ✅ 렌더링 함수 호출
	    renderMyList();

	    // ✅ 이미지 업로드 Ajax 처리 - 이 부분은 이제 필요 없어서 제거
	});
var contextPath = "${request.getContextPath()}";
</script>



</body>
</html>
