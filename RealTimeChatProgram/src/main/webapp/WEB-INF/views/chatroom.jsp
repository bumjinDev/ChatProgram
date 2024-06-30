<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chat page</title>
<link rel="stylesheet" type="text/css" href="/chat/css/chatroom.css">
</head>
<body>
	<!-- 닉네임 표시, 채팅 방 나가기 버튼, 참여 인원 수 표시, 방 번호 표시 -->
	<div>
		<label for="nickname">닉네임</label>
		<span id="nickanme">-</span>
		<button id="exitchat">채팅 방 나가기</button>
		<label for="chatpeopen">참여 인원 수</label>
		<span id="chatpeopen">-</span>
		<label for="roomnumber">방 번호</label>
		<span id="roomnumber">-</span>
	</div>
	<!-- 실제 채팅 입력 및 유저 간 채팅 내역 공유 창 -->
	<div>
		<!-- 입력된 채팅 공유하는 부분 -->
		<div class="showchat">
			<div class="shownickname">
			</div>
			<div class="showcontent">
			</div>
		</div>
		<!-- 실제 입력하는 부분 -->
		<div class="userchat">
			<input type="text" id="inputcaht" ></input><button>채팅 입력</button>
		</div>
		
	</div>
</body>
</html>