/**
 *  버튼을 입력해서 채팅을 서버에 보내거나 또는 다른 사람의 채팅을 받아서 화면 
*/

window.onload = function(){

    console.log("chatPlay.js 페이지 로드 시작!");

    var users = document.querySelectorAll(".user"); // 채팅 사용자 닉네임 총 10개 표현 가능, 즉 세션 생성 당시 설정한 닉네임 반환.
    var chatValue = document.querySelectorAll(".chatValue"); // 채팅 내역을 사용자 닉네임 별로 띄우기
    var nickName = document.getElementById("nickName");  // 웹 소켓 재 연결(서버에서는 기존 소켓 재 활용) 시 닉네임 전달 안해주면 null 값 남.  
    var inputChat = document.getElementById("inputchat");   // input 태그, 사용자 채팅 입력 내용.
    var chatBtn = document.getElementById("chatbtn");   // 사용자 채팅 전송 버튼.

    /* 나가기 버튼 구현. */
    var exitChat = document.getElementById("exitchat");     // 나가기 버튼
    var roomnumber = document.getElementById("roomNumber").value; // 채팅 창 나가기 버튼 위한 방 번호.

    console.log("documnet.getElementById(nickName) : " + document.getElementById("nickName"));
    console.log("채팅 방 입장 후 웹 소켓 생성 위한 파라메터인 'roomnumber' : " + roomnumber);

    exitChat.addEventListener("click", function() {

        alert("현재 방을 나갑니다.");
        window.location.href ="../exitChatPage/" + roomnumber;
    });

    var socket = new WebSocket("ws://localhost:8181/chat/ws?roomnumber=" + roomnumber);
    /* socket.onmessage : WebSocket의 세션 내 서버로부터 메시지가 브라우저에
        도착 했을 때 내부적으로 발생시키는 이벤트, 모든 사람의 채팅 내용을
        json으로 받아서 처리 */

    socket.onopen = function(event){

        console.log("세션 성립!");

        /* 채팅 input 태그 내 데이터 입력한 데이터를 전송하기 위한 메서드*/
        chatBtn.addEventListener("click", function(){

            var chatvalue = inputChat.value; // 현재 페이지에서 사용자가 입력한 내용
            console.log("채팅 전달 내용 : " + chatvalue);
            socket.send(chatvalue);     // socket.send() : WebSocket 세션 통해 데이터 전송.
        });
    };

    socket.onmessage = function(requestObj){  // 해당 이벤트 발생 시 생성되는 이벤트 객체를 매개변수 "eventobj" 이름으로 받는다.

        console.log("requestObj.data : " + requestObj.data);
        let reqMessage = JSON.parse(requestObj.data);
        console.log("user : " + reqMessage.user);
        console.log("chatValues : " + reqMessage.chatValues);

        /* 실제 메시지를 화면 내 띄우기 :
            1. 전체 10개 span 태그 중 가장 아랫 칸인 9번 인덱스 부터 채워 넣기
            2. 새로운 채팅이 생기면 가장 최근 글과 닉네임을 한칸 위로 올리고 채워 넣기
            3. 만약에 0번 인덱스 조차 가득 찬 상태라면 0번 인덱스는 덮혀 씌워진다.
         */
        
        for(var i = 0; i < 12; i++){  // 1번 인덱스는 0번 덮어 씌우고 2번 인덱스는 1번 덮어 씌우고....      
            
            users[i].innerText = users[i+1].innerText;
            chatValue[i].innerText = chatValue[i+1].innerText;
            console.log("i : " + i);
        };

        users[12].innerText = reqMessage.user;
        chatValue[12].innerText = reqMessage.chatValues;
    };

    socket.onerror = function(errorEvent){

        alert("연결 오류 발생. 페이지 재 접속 하세요");
        window.location.href = "../loadWaitPage";       // WebSocket 세션 객체 생성 실패 시 바로 채팅 대기방 페이지 이동.
    };
};