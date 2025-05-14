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
    <form id="postForm" action="${pageContext.request.contextPath}/postCreate" method="POST" enctype="multipart/form-data">
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
                <textarea id="summernote" name="contents"> </textarea>
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
    
    <!-- 서머노트 에디터 관련 코드입니다. -->
    
    <script>
    	const contextPath = "${pageContext.request.contextPath}";
        document.addEventListener('DOMContentLoaded', function ()
     	{
            $('#summernote').summernote
            (
        	{
                lang: 'ko-KR',
                height: 500,
                toolbar: 
               	[
                	/* 기존 툴바 설정 유지 */ 
                	['style', ['bold', 'italic', 'underline', 'clear']],
                    ['font', ['strikethrough', 'superscript', 'subscript']],
                    ['fontsize', ['fontsize']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    ['insert', ['picture']],
               	],
                // ====[ 이미지 업로드 콜백 추가/수정 ]====
                callbacks: {
                    onImageUpload : function(files) {
                        for (let i = 0; i < files.length; i++) {
                            uploadSummernoteImageFile(files[i], this);
                        }
                    }
                    // onMediaDelete 콜백은 필요하면 추가
                } // end of callbacks
            } // end of summernote initialization
        	);

            // ... (기존 지도 관련, 폼 임시저장 관련 JS 코드 유지) ...

            // ====[ 이미지 업로드 AJAX 함수 정의 (새로 추가) ]====
            function uploadSummernoteImageFile(file, editor) {
                let data = new FormData();
                data.append("file", file); // Controller의 @RequestParam("file")과 이름 일치

                $.ajax({
                    url: contextPath + "/uploadSummernoteImage", // 변경된 서버 API 경로
                    type: "POST",
                    data: data,
                    cache: false,
                    contentType: false,
                    processData: false,
                    dataType: "json",

                    success: function(response) {
                        if (response && response.url) {
                            // ★ 중요: 에디터에 이미지 삽입 (URL 사용)
                        	let fullImageUrl = contextPath + response.url;
                        
                            $(editor).summernote('insertImage', fullImageUrl);
                            console.log("이미지 삽입 성공:", response.url);

                            // ★ 중요: 저장된 파일명을 숨겨진 필드 등으로 관리 (선택적이지만 권장)
                            //    게시글 저장 시 imageNames 컬럼에 넣기 위해
                            //    예시: 숨겨진 필드에 값 추가
                            // let hiddenInput = '<input type="hidden" class="uploadedImageName" value="' + response.savedFilename + '">';
                            // $('#postForm').append(hiddenInput);

                        } else {
                             alert("이미지 업로드 실패: 서버 응답 오류");
                             console.error("서버 응답 오류:", response);
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error("이미지 업로드 AJAX 오류:", textStatus, errorThrown);
                        let errorMsg = "이미지 업로드 실패: " + ( (jqXHR.responseJSON && jqXHR.responseJSON.error) || errorThrown || textStatus );
                        alert(errorMsg);
                    }
                }); // end of $.ajax
            } // end of uploadSummernoteImageFile function
            // ====[ AJAX 함수 정의 끝 ]====

        }); // end of DOMContentLoaded
    </script>
    
    
    
    
    
    
    <!-- 
    이전코드
    <script>
    	const contextPath = "${pageContext.request.contextPath}";
        document.addEventListener('DOMContentLoaded', function () 
     	{
            $('#summernote').summernote
            (
        	{
                lang: 'ko-KR',
                height: 500,
                toolbar: 
                [
                    ['style', ['bold', 'italic', 'underline', 'clear']],
                    ['font', ['strikethrough', 'superscript', 'subscript']],
                    ['fontsize', ['fontsize']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    ['insert', ['picture']],
                ]
            }
        	);

            const openMapButton = document.getElementById("open-map");

            openMapButton.addEventListener('click', function () {
                const tempData = {
                    title: document.getElementById("titles").textContent.trim(), // titles 요소의 id를 올바르게 지정
                    content: $('#summernote').summernote('code'),
                    isPrivate: document.querySelector('input[name="isPrivate"]:checked').value,
                    commentIsAllowed: document.querySelector('input[name="commentIsAllowed"]:checked').value
                };

                sessionStorage.setItem('tempPostData', JSON.stringify(tempData)); // sessionStorage 오타 수정
                window.location.href = contextPath + '/map';
            });
            renderMyList()
        });
    </script>
    
    -->
</body>
</html>