<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int totalRoom = (Integer) request.getAttribute("totalRoom");
	int totalUser = (Integer) request.getAttribute("totalUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat MainPage</title>
<link rel="stylesheet" type="text/css" href="/chat/css/index.css">

</head>
<body>
	<h1 class="titles">실시간 채팅 프로그램</h1>

	<!-- 사용자가 index.jsp 파일을 받았을 때 전체 채팅 방 수, 전체 참여 인원 수를 실시간으로 조회  -->
	<div class = "information">
		<label for="totalroom">전체 채팅 방 개수</label>
		<span id="totalroom" ><%=totalRoom %></span><br>
		<label for="totaluser">전체 사용자 수</label>
		<span id="totaluser" ><%=totalUser %></span>
	</div>
	<br><br>
	
	<!-- <form id="startChat" method="GET" action="./loadWaitPage"> --> <!-- 채팅 대기방 페이지 단순 요청이니 'GET' 사용 => 기존 폼 태그 방식에서 일부 매개변수만 사용해서 리다이렉션 적용. -->
		<!-- 사용자 입력 닉네임 받아들이는 항목 -->
		<div class = "inputnickname">
			<!-- 채팅 대기방 페이지 'waitpage.jsp' 요청 시 닉네임을 해당 HTTPRequest 세션 객체 내 속성 값으로 저장하며
				 해당 페이지 내 닉네임 정보 포함하여 'waitpage.jsp' 랜더링 제공. -->
			<label class="nicklable" for="nickname">사용할 닉네임</label>
			<input name="nickName" type="text" id="nickname"></input>
		</div>
	<!-- </form>  -->
	
	<button id="startBtn">실시간 채팅 시작하기</button>
	<script src="./js/index.js" type="text/javascript"></script>
</body>
</html>