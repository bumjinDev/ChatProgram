/**
 *  버튼을 입력해서 채팅을 서버에 보내거나 또는 다른 사람의 채팅을 받아서 화면 
 */

window.onload = function(){

    /* websocket 사용하여 세션 서버와 연결 및 채팅 준비. */
    let socket = new WebSocket("ws://localhost:8181/ws");

    /* socket.onpen : WebSocket 라이브러리 통한 WebSocket 세션 생성 완료 시
         내부적으로 호출되는 이벤트 
    */
    socket.onopen = function(){

        console.log("WebSocket connection established!");

        /* 실시간 채팅 기능 구현하는 html 태그 DOM 요소들은 webSocket 세션 구현 시 기능 적용. */
        var showNickname = document.querySelector(".shownickname"); // 채팅 유저 닉네임.
        var showContent = document.querySelector(".showcontent");   // 채팅 유저 별 내용.
        var inputChat = document.getElementById("inputchat");   // input 태그, 사용자 채팅 입력 내용.
        
        /* 채팅 input 태그 내 데이터 입력 후  */
        inputChat.addEventListener("click", function(){

            var chatvalue = this.value; // 채팅 내용 가져오기.
            socket.send(chatvalue);     // socket.send() : WebSocket 세션 통해 데이터 전송.
        });
    };

    /* socket.onmessage : WebSocket의 세션 내 서버로부터 메시지가 브라우저에
        도착 했을 때 내부적으로 발생시키는 이벤트, 모든 사람의 채팅 내용을
        받아서 처리 */
    socket.onmessage = function(eventobj){  // 해당 이벤트 발생 시 생성되는 이벤트 객체를 매개변수 "eventobj" 이름으로 받는다.

        let message = eventobj.data;
    };

   

}