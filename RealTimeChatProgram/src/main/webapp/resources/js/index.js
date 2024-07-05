/*
  * 기능 : 실시간 채팅 시작 버튼 누를 시 채팅 대기방 페이지 "waitpage.jsp" 호출 하며 이때 닉네임을 세션 객체에 저장하기 위함이다.
*/

window.onload = function(){

    var nickName = document.getElementById("nickname");
    var startBtn = document.getElementById("startBtn");
    var startChat = document.getElementById("startChat");

    startBtn.addEventListener("click", function(){
        
        if(nickName.value){

            console.log("nickName : " + nickName.value);
            startChat.submit();
        } else {
            alert("닉네임이 입력되지 않았습니다.");
        }
    
    });
}
