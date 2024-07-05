/*
 * 주요 기능 :
 *  1. 채팅 방 나가기 버튼 구현.
 *  2. 채팅 입력 기능 구현.
 */

window.onload=function(){

    var exitChat = document.getElementById("exitchat");     // 나가기 버튼
    var inputChat = document.getElementById("inputchat");   // 채팅 입력 버튼

    var roomnumber = document.getElementById("roomNumber").value; // 채팅 창 나가기 버튼 위한 방 번호.

    exitChat.addEventListener("click", function(){

        alert("현재 방을 나갑니다.");
        window.location.href ="../exitChatPage/" + roomnumber;
    });
}