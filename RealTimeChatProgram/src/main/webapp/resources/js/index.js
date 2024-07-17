window.onload = function(){

    var nickName = document.getElementById("nickname");
    var startBtn = document.getElementById("startBtn");

    startBtn.addEventListener("click", function(){
        
        if(nickName.value){

            console.log("입력한 닉네임 : " + nickName.value);

            // /* WebSocket 세션 성립 및 성립 이벤트 'onopen' 내 비즈니스 로직 구현. */
            // window.socket = new WebSocket("ws://localhost:8181/chat/ws?username=" + nickName.value);
            // window.socket.onopen = function(event){

            //     alert("채팅 서비스 접속 합니다.");
            //     window.location.href = "./loadWaitPage";
            // }

            // window.socket.onerror = function(event){

            //     alert("오류가 있으니 페이지 재 접속 바랍니다.");
            // }
           
            window.location.href = "./loadWaitPage?username=" + nickName.value;

        } else {
            alert("닉네임이 입력되지 않았습니다.");
        }
    
    });
}
