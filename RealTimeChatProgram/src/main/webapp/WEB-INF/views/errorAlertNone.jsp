<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alert Page</title>
<script>
    // 페이지가 로드되면 알림 표시
    window.onload = function() {
        alert("현재 채팅방이 존재하지 않으므로 방에 입장이 불가능 합니다.");
        // 알림이 닫히면 이전 페이지로 돌아감
        history.back();
    };
</script>
</head>
<body>
    <!-- 본문 내용 -->
</body>
</html>
