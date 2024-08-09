window.onload = function() {
    console.log("chatPlay.js 페이지 로드 시작!");

    var users = document.querySelectorAll(".user"); // 채팅 사용자 닉네임 총 10개 표현 가능, 즉 세션 생성 당시 설정한 닉네임 반환.
    var chatValue = document.querySelectorAll(".chatValue"); // 채팅 내역을 사용자 닉네임 별로 띄우기
    var nickname = document.getElementById("nickName"); // 닉네임 값
    var inputChat = document.getElementById("inputchat"); // input 태그, 사용자 채팅 입력 내용.
    var chatBtn = document.getElementById("chatbtn"); // 사용자 채팅 전송 버튼.

    var pgPlag = 1;

    /* 나가기 버튼 구현. */
    var exitChat = document.getElementById("exitchat"); // 나가기 버튼
    var roomnumber = document.getElementById("roomNumber"); // 방 번호 요소
    
    console.log("사용자 닉네임 요소: ", nickname);
    console.log("사용자 닉네임: ", nickname);
    console.log("방 번호: ", roomnumber);

    var socket = new WebSocket("ws://43.202.178.156:8181/chat/ws?roomnumber=" + roomnumber.value + "&username=" + nickname.value);

    socket.onopen = function(event) {
        console.log("세션 성립!");

        exitChat.addEventListener("click", function() {
            alert("현재 방을 나갑니다.");
            
            pgPlag = 0;

            window.location.href = "../exitChatPage/" + roomnumber.value;
        });

        /* 채팅 input 태그 내 데이터 입력한 데이터를 전송하기 위한 메서드 */
        chatBtn.addEventListener("click", function() {
            var chatvalue = inputChat.value; // 현재 페이지에서 사용자가 입력한 내용
            console.log("채팅 전달 내용: " + chatvalue);
            socket.send(chatvalue); // socket.send() : WebSocket 세션 통해 데이터 전송.
            inputChat.value = "";
        });
    };

    socket.onmessage = function(requestData) { // 해당 이벤트 발생 시 생성되는 이벤트 객체를 매개변수 "eventobj" 이름으로 받는다.
        console.log("서버로부터 받은 채팅 내용: " + requestData.data);

        let reqMessage = JSON.parse(requestData.data);

        console.log("user: " + reqMessage.user);
        console.log("chatValues: " + reqMessage.chatValues);

        /* 실제 메시지를 화면 내 띄우기:
            1. 전체 10개 span 태그 중 가장 아랫 칸인 9번 인덱스부터 채워 넣기
            2. 새로운 채팅이 생기면 가장 최근 글과 닉네임을 한 칸 위로 올리고 채워 넣기
            3. 만약에 0번 인덱스조차 가득 찬 상태라면 0번 인덱스는 덮어 씌워진다.
         */
        
        for (var i = 0; i < 12; i++) { // 1번 인덱스는 0번 덮어 씌우고 2번 인덱스는 1번 덮어 씌우고....
            users[i].innerText = users[i + 1].innerText;
            chatValue[i].innerText = chatValue[i + 1].innerText;
            console.log("i: " + i);
        }

        users[12].innerText = reqMessage.user;
        chatValue[12].innerText = reqMessage.chatValues;
    };

    socket.onerror = function(errorEvent) {
        alert("연결 오류 발생. 페이지 재 접속 하세요. 채팅 대기방 페이지로 이동합니다. ");
        window.location.href = "../loadWaitPage"; // WebSocket 세션 객체 생성 실패 시 바로 채팅 대기방 페이지 이동.
    };

    /* 새로 고침 하면 js 컨텍스트 초기화 되기 전에 새로 고침 이벤트 처리 보장을 위해
    웹 페이지 리소스(html, css) 만 닫고 서버로 새로고침 이벤트 처리 후에 랜더링 받고 나서
    js 컨텍스트를 종료하는 순서로 동작하기에 가능함.  */
    window.addEventListener('beforeunload', function (event) {

        if(pgPlag === 1){

            alert("새로고침!");
            // 새로고침을 감지하고 서버로 알림
            fetch('../refresh', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ refresh: true })
            });
        }

        pgPlag = 1;
    });
};
