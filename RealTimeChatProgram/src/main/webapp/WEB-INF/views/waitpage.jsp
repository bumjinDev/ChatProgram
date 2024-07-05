<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String nickName = (String)request.getAttribute("nickName");
%>
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
		<button class="pageindex" id="pageindex">메인 화면 이동</button>
		<button class="createroom" id="createroom">방 생성</button>
		<label class="shownickname" for="nickname">닉네임</label>
		<span id="nickname"><%=nickName %></span><br>	<!-- 사용자 입력 정보를 세션 객체 통해 불러들인다. -->
	</div>
	<br>
	<!-- 방 정보 표현 -->
	<div class="roominfo">
		<span class="roomNumber">방 번호</span><span class="roomTitle">방 제목</span><span class="roomPeople">참여 인원 / 최대 인원</span>
	</div>
	
	<!--  채팅 방 목록, 방 순서대로 각 방 별 방 번호, 방 제목, 방 내 현재 인원수를 -->
	<div class="roomlist">
		<c:forEach var="loadWaitRoom" items="${loadWaitRooms}" varStatus="status">
			<div class="roomEntity">
			    <span id ="${status.count}" class="Number" >${loadWaitRoom.roomNumber}</span> <span class="Title">${loadWaitRoom.roomTitle}</span> <span class="People">${loadWaitRoom.currentPeople} / ${loadWaitRoom.maxPeople}</span>
		    	<button class="entranceBtn" value="${loadWaitRoom.roomNumber}">입장</button>	<!-- 입장 버튼 누르면 각 버튼 별 속성 'value' 값 따른 입장 요청 -->
		    </div>
		</c:forEach>
	</div>

	<script src="./js/waitjs.js" type="text/javascript"></script>
</body>
</html>