<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jstl 구현 : 컨트롤러 메소드 'loadWaitPage()' 에서 채팅 방 목록을 List로써 반환 받아 랜더링 할 때에 
		'c:forEach' 적용해서 순차적으로 랜더링 하기 위함. -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<c:forEach var="item" items="${items}">
		    <!-- 여기에 각 항목을 처리하는 코드를 작성합니다. -->
		    <span>${item}</span> <span>${item}</span> <span>${item}</span> <button>입장 </button>
		    <br/>
		</c:forEach>
	</div>
</body>
</html>