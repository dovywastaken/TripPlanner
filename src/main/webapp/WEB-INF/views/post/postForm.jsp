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
    	const contextPath = "${pageContext.request.contextPath}";
    
        document.addEventListener('DOMContentLoaded', function () {
            $('#summernote').summernote({
                lang: 'ko-KR',
                height: 500,
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
</body>
</html>