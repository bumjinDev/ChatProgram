<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<span id="totalroom" ></span><br>
		<label for="totaluser">전체 사용자 수</label>
		<span id="totaluser" ></span>
	</div>
	<br><br>
	<!-- 사용자 입력 닉네임 받아들이는 항목 -->
	<div class = "inputnickname">
		<label class="nicklable" for="nicklabel">사용할 닉네임</label>
		<input type="text" id="nickname" ></input>
	</div>
	
	<button >실시간 채팅 시작하기</button>
</body>
</html>