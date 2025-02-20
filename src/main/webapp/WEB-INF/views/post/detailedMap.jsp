<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 	<script> window.contextPath = '${pageContext.request.contextPath}'; </script>
	<script src="${pageContext.request.contextPath}/resources/js/tourApi.js" defer="defer"></script>
    <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${mapAPIKey}&libraries=services"></script>
   	<script type="module" src="${pageContext.request.contextPath}/resources/js/detailedMap.js"></script>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/detailedMap.css">
</head>
<body>
	<div id="detail-box" class="detail-scroll"></div>
	<div id="map" data-info='${info}'></div>
</body>
</html>