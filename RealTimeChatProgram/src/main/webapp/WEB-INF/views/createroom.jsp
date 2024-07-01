<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방 생성 화면</title>
	<link rel="stylesheet" type="text/css" href="/chat/css/createroom.css">
</head>
<body>
	<button id="exitroom">채팅 대기방 이동</button>
	
	<!-- 방 생성 시 입력 정보인 방 제목 및 최대 인원 수 -->
	<div class="inforoom">
		<label class ="title" for="title">방 제목</label>
			<input type="text" id="title"></input><br><br>
		<label class="people" for="people">최대 인원 수</label>
			<select name="people" id="people">
			  <option value="1">1</option>
			  <option value="2">2</option>
			  <option value="3">3</option>
			  <option value="4">4</option>
			  <option value="5">5</option>
			  <option value="6">6</option>
			  <option value="7">7</option>
			  <option value="8">8</option>
			  <option value="9">9</option>
			  <option value="10">10</option>
			</select>
			
	</div>
	
	<button id="createroom">방 생성</button>
	
</body>
</html>