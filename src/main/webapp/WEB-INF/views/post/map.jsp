<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script>
         window.contextPath = '${pageContext.request.contextPath}';
    </script>
<script  src="${pageContext.request.contextPath}/resources/js/tourApi.js" defer="defer"></script>
    
   <script  type="module" src="${pageContext.request.contextPath}/resources/js/map.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/map.css">
</head>
<body>
<div id="detail-box" class="detail-scroll">
</div>
<div id="map" data-info='${info}'></div>

  <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=21c423a4331e55883c3eb46115b12e02&libraries=services"></script>
</body>
</html>