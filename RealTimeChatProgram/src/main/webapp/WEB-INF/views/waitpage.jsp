<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 대기방</title>
<link rel="stylesheet" type="text/css" href="/chat/css/waitroom.css">
</head>
<body>

	<!-- 메뉴 : 메인 화면 이동 버튼, 방 생성 버튼, 닉네임 확인. -->
	<div class="menu">
		<button class="pageindex">메인 화면 이동</button>
		<button class="createroom">방 생성</button>
		<label class="shownickname" for="shownickname">닉네임</label>
		<span id="nickname"></span><br>	<!-- 사용자 입력 정보를 세션 객체 통해 불러들인다. -->
	</div>
	<br>
	<!-- 방 정보 표현 -->
	<div class="roominfo">
		<span class="roomnumber">방 번호</span><span class="roomtitle">방 제목</span><span class="people">참여 인원 수</span>
	</div>
	
	<!--  채팅 방 목록 -->
	<div class="roomlist">
		
	</div>
</body>
</html>