<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>수정 페이지</title>
    <link rel="stylesheet" href="/TripPlanner/resources/css/postForm.css">
    <script src="<%= request.getContextPath() %>/resources/js/editor.js" defer="defer"></script>
</head>
<body>
    <div class="container">
        <!-- 왼쪽: 마이리스트 및 지도 버튼 -->
        <div class="left">
            <div class="my-list">
                <h3>마이 리스트</h3>
                <div id="myListContainer">
                    <p>저장된 장소가 없습니다.</p>
                </div>
            </div>
            <button id="open-map">지도</button>
        </div>

        <!-- 가운데: 제목 입력 및 내용 작성 -->
        <div class="center">
            <h1>게시글 작성</h1>
            <form action="${pageContext.request.contextPath}/postview/updatePost" method="POST" enctype="multipart/form-data">
    
                <div id="titles" contenteditable="true">${result.title}</div>
               		<input type="hidden" name="p_unique" value="${result.p_unique}">
					<input type="hidden" name="id" value="${result.id}">
					<input type="hidden" name="publishDate" value="${result.publishDate}">
				<div class="editor-toolbar">
				    <select class="toolbar-item" onchange="formatText('fontSize', this.value)">
				        <option value="">글자 크기</option>
				        <option value="1">10px</option>
				        <option value="2">13px</option>
				        <option value="3">16px</option>
				        <option value="4">18px</option>
				        <option value="5">24px</option>
				        <option value="6">32px</option>
				        <option value="7">48px</option>
				    </select>
				    
				    <select class="toolbar-item" onchange="formatText('fontName', this.value)">
				        <option value="">글꼴</option>
				        <option value="Arial">Arial</option>
				        <option value="Verdana">Verdana</option>
				        <option value="Helvetica">Helvetica</option>
				        <option value="Tahoma">Tahoma</option>
				        <option value="Nanum Gothic">나눔고딕</option>
				    </select>
				
				    <div class="color-buttons">
				        <button type="button" class="color-btn" style="background: #000000" onclick="formatText('foreColor', '#000000')"></button>
				        <button type="button" class="color-btn" style="background: #FF0000" onclick="formatText('foreColor', '#FF0000')"></button>
				        <button type="button" class="color-btn" style="background: #00FF00" onclick="formatText('foreColor', '#00FF00')"></button>
				        <button type="button" class="color-btn" style="background: #0000FF" onclick="formatText('foreColor', '#0000FF')"></button>
				        <button type="button" class="color-btn" style="background: #FFFF00" onclick="formatText('foreColor', '#FFFF00')"></button>
				        <button type="button" class="color-btn" style="background: #FF00FF" onclick="formatText('foreColor', '#FF00FF')"></button>
				        <input type="color" class="toolbar-item" onchange="formatText('foreColor', this.value)" title="다른 색상">
				    </div>
				    
				    <button type="button" class="toolbar-item" onclick="formatText('bold')"><strong>B</strong></button>
				    <button type="button" class="toolbar-item" onclick="formatText('italic')"><em>I</em></button>
				    <button type="button" class="toolbar-item" onclick="formatText('underline')"><u>U</u></button>
				    
				    <button type="button" class="toolbar-item" onclick="formatText('justifyLeft')">왼쪽</button>
				    <button type="button" class="toolbar-item" onclick="formatText('justifyCenter')">가운데</button>
				    <button type="button" class="toolbar-item" onclick="formatText('justifyRight')">오른쪽</button>
				    
				    <button type="button" class="toolbar-item" onclick="formatText('insertOrderedList')">번호</button>
				    <button type="button" class="toolbar-item" onclick="formatText('insertUnorderedList')">글머리</button>
				</div>
                <div id="editor" contenteditable="true">${result.contents}</div>

                <!-- 이미지 업로드 -->
                <label for="fileImage">이미지 업로드</label>
                <input type="file" id="fileImage" name="fileImg" accept="image/*" multiple>
    
                <!-- 내용을 동기화할 textarea -->
                <textarea id="title" name="title" style="display:none"></textarea>
                <textarea id="contents" name="contents" style="display:none"></textarea>

                <!-- 공개 설정 -->
				
                <div class="right">
                    <div class="radio-group">
                        <label>공개:</label> 
                          	<input type="radio" name="isPrivate" value="1" ${result.isPrivate == true ? 'checked' : '' }> 공개
                          	<input type="radio" name="isPrivate" value="0" ${result.isPrivate == false ? 'checked': '' }> 비공개

                    </div>
                    <div class="radio-group">
                        <label>댓글 허용:</label>
                        <input type="radio" name="commentIsAllowed" value="1" ${result.commentIsAllowed == true ? 'checked': '' }> 허용
                        <input type="radio" name="commentIsAllowed" value="0" ${result.commentIsAllowed == false ? 'checked': '' }> 허용 안함
                    </div>
                </div>
                    
                <button type="submit">작성</button>
            </form>
        </div>
    </div>
</body>
</html>
