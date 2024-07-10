<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String nickName = (String) request.getAttribute("nickName");
	int currentPeople = (Integer) request.getAttribute("currentPeople");
	int roomNumber = (Integer) request.getAttribute("roomNumber");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chat page</title>
<link rel="stylesheet" type="text/css" href="/chat/css/chatroom.css">
</head>
<body>
	<!-- 닉네임 표시, 채팅 방 나가기 버튼, 참여 인원 수 표시, 방 번호 표시 -->
	<div class="menu">
		<label for="roomnumber">방 번호</label>
			<span><%=roomNumber %></span>
			<input type="hidden" id="roomNumber" value="<%=roomNumber %>"></input>
		<label for="chatpeopen">참여 인원 수</label>
			<span id="nickanme"><%=currentPeople %></span>
		<label for="nickname">닉네임</label>	
			<span ><%=nickName %></span>
		<button id="exitchat">채팅 방 나가기</button>
	</div>
	<!-- 실제 채팅 입력 및 유저 간 채팅 내역 공유 창 -->
	<div class="showchat">
		
		<!-- 입력된 채팅 공유하는 부분 -->
		<div class="showscreen">
			<div class="shownickname">	<!-- 채팅 입력한 사용자 닉네임 -->
			</div>
			<div class="showcontent">	<!-- 각 닉네임 별 입력한 채팅 내용. -->
			</div>
		</div>
		<!-- 실제 입력하는 부분 -->
		<div class="userchat">
			<input type="text" id="inputchat"></input><button class="chatbtn">채팅 입력</button>
		</div>
	</div>
	<script src="../js/chatpage.js" type="text/javascript"></script>		<!-- 단순 방 나가기 등 버튼 구현 -->
	<script src="../js/chatPlay.js" type="text/javascript"></script><!-- webSocket 을 활용한 실시간 채팅 페이지 구현. -->
</body>
</html>