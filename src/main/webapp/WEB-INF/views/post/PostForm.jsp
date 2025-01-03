<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성/수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/postForm.css">
    <script src="${pageContext.request.contextPath}/resources/js/editor.js" defer></script>
</head>
<body>
    <div class="container">
        <!-- 왼쪽 마이리스트 -->
        <div class="left">
            <div class="my-list">
                <h3>마이 리스트</h3>
                <div id="myListContainer">
                    <p>저장된 장소가 없습니다.</p>
                </div>
            </div>
            <button id="open-map" class="map-button">지도</button>
        </div>

        <!-- 가운데 에디터 영역 -->
        <div class="center">
            <h1>게시글 작성/수정</h1>
            <form action="${pageContext.request.contextPath}/postcreate" method="POST" enctype="multipart/form-data">
                <div id="titles" class="title-input" contenteditable="true" placeholder="제목을 입력하세요"></div>
                
                <div class="editor-toolbar">
                    <select class="toolbar-item font-size" onchange="formatText('fontSize', this.value)">
                        <option value="">글자 크기</option>
                        <option value="1">10px</option>
                        <option value="2">13px</option>
                        <option value="3">16px</option>
                        <option value="4">18px</option>
                        <option value="5">24px</option>
                        <option value="6">32px</option>
                        <option value="7">48px</option>
                    </select>
                    <select class="toolbar-item font-family" onchange="formatText('fontName', this.value)">
                        <option value="">글꼴</option>
                        <option value="Arial">Arial</option>
                        <option value="Verdana">Verdana</option>
                        <option value="Helvetica">Helvetica</option>
                        <option value="Tahoma">Tahoma</option>
                        <option value="Nanum Gothic">나눔고딕</option>
                    </select>
                    <div class="toolbar-group">
                        <button type="button" class="toolbar-item" onclick="formatText('bold')"><strong>B</strong></button>
                        <button type="button" class="toolbar-item" onclick="formatText('italic')"><em>I</em></button>
                        <button type="button" class="toolbar-item" onclick="formatText('underline')"><u>U</u></button>
                    </div>
                    <div class="toolbar-group">
                        <button type="button" class="toolbar-item" onclick="formatText('justifyLeft')">왼쪽</button>
                        <button type="button" class="toolbar-item" onclick="formatText('justifyCenter')">가운데</button>
                        <button type="button" class="toolbar-item" onclick="formatText('justifyRight')">오른쪽</button>
                    </div>
                    <div class="toolbar-group">
                        <button type="button" class="toolbar-item" onclick="formatText('insertOrderedList')">번호</button>
                        <button type="button" class="toolbar-item" onclick="formatText('insertUnorderedList')">글머리</button>
                    </div>
                    <div class="color-buttons">
                        <button type="button" class="color-btn" style="background: #000000" onclick="formatText('foreColor', '#000000')"></button>
                        <button type="button" class="color-btn" style="background: #FF0000" onclick="formatText('foreColor', '#FF0000')"></button>
                        <button type="button" class="color-btn" style="background: #00FF00" onclick="formatText('foreColor', '#00FF00')"></button>
                        <button type="button" class="color-btn" style="background: #0000FF" onclick="formatText('foreColor', '#0000FF')"></button>
                        <button type="button" class="color-btn" style="background: #FFFF00" onclick="formatText('foreColor', '#FFFF00')"></button>
                        <button type="button" class="color-btn" style="background: #FF00FF" onclick="formatText('foreColor', '#FF00FF')"></button>
                        <input type="color" class="toolbar-item color-picker" onchange="formatText('foreColor', this.value)" title="다른 색상">
                    </div>
                </div>

                <div id="editor" contenteditable="true" placeholder="내용을 입력하세요"></div>

                <div class="bottom-controls">
                    <div class="upload-section">
                        <label for="fileImage" class="file-upload-btn">이미지 업로드</label>
                        <input type="file" id="fileImage" name="fileImg" accept="image/*" multiple>
                    </div>

                    <div class="options-section">
                        <div class="option-group">
                            <label>공개 설정:</label>
                            <div class="radio-group">
                                <input type="radio" name="isPrivate" id="public" value="1" checked>
                                <label for="public">공개</label>
                                <input type="radio" name="isPrivate" id="private" value="0">
                                <label for="private">비공개</label>
                            </div>
                        </div>
                        <div class="option-group">
                            <label>댓글 설정:</label>
                            <div class="radio-group">
                                <input type="radio" name="commentIsAllowed" id="allow" value="1" checked>
                                <label for="allow">허용</label>
                                <input type="radio" name="commentIsAllowed" id="disallow" value="0">
                                <label for="disallow">비허용</label>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="submit-btn">작성하기</button>
                </div>

                <textarea id="title" name="title" style="display:none;"></textarea>
                <textarea id="contents" name="contents" style="display:none;"></textarea>
            </form>
        </div>
    </div>
</body>
</html>