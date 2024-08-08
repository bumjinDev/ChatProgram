<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String nickName = (String) request.getAttribute("nickName");
	int currentPeople = (Integer) request.getAttribute("currentPeople") ;	/* 서버 측에서 제공하는 현재 방 인원수는 페이지 랜더링 타이밍과 클라이언트 js의 세션 객체 생성 타이밍에 대한 동기화를 위해 현재 접속 사용자를 레포지토리 빈에서 추가를																					하지 않고 페이지를 랜더링 해주기 때문에 클라이언트 페이지에서 자체적으로 +1 을 하여 보여준다. 그리고 브라우저 랜더링 후에 소켓 객체를 생성 함으로써																		이후에 서버 측에서 현재 접속자를 포함한 채팅 방 입장수를 올바르게 저장한다. */
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
	  <label for="roomNumber">방 번호</label>
	  <input type="text" id="roomNumber" class="inputspan" readonly value="<%=roomNumber %>">
	  <label for="currentPeople">참여 인원 수</label>
	  <input type="text" id="currentPeople" class="inputspan" readonly value="<%=currentPeople %>">
	  <label for="nickName">닉네임</label>
	  <input type="text" id="nickName" class="inputspan" readonly value="<%=nickName %>">
	  <button id="exitchat">채팅 방 나가기</button>
	</div>

	<!-- 실제 채팅 입력 및 유저 간 채팅 내역 공유 창 -->
	<div class="showchat">
		
		<!-- 입력된 채팅 공유하는 부분 -->
		<div class="showscreen">
			<div class="shownickname">	<!-- 채팅 입력한 사용자 닉네임 -->
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
				<span class="user"></span>
			</div>
			<div class="showcontent">	<!-- 각 닉네임 별 입력한 채팅 내용. -->
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
				<span class="chatValue"></span>
			</div>
		</div>
		<!-- 실제 입력하는 부분 -->
		<div class="userchat">
			<input type="text" id="inputchat"><button id="chatbtn" class="chatbtn">채팅 입력</button>
		</div>
	</div>
	<script type="text/javascript" src="../js/chatPlay.js?ver=2.9"></script>
		<!-- webSocket 을 활용한 실시간 채팅 페이지 구현. -->
</body>
</html>
