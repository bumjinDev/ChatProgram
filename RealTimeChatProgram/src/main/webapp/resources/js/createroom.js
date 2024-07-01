/**
 * 1. 방 생성 페이지 'createroom.jsp" 내 입력한 데이터에 대한 유효성 검사
   2. 사용자 입력 데이터를 서버로 전송 및 채팅 방 페이지인 'chatroom.jsp' 응답.
 */
 
 window.onload = function(){

    var createroom = document.getElementById("createroom");
 };

createroom.addEventListener("click", function() {

  /* 입력한 방 제목과 최대 인원 수 입력. */
  var title = document.querySelector("#title").value;
  var people = document.getElementById("people").value;
  //var form = document.querySelector("#reg_frm");

  /* 요청 사용하여 방 생성에 필요한 정보를 서버로 요청 및 결과로
      생성한 페이지의 대기방 페이지 전달 받는다. */
  if(title.length >= 1 && people.length  >= 1){

    alert("title.value : "  + title);
    alert("방 생성 합니다.");
    document.getElementById("reg_frm").submit();
    
  } else {

      alert("입력되지 않은 값이 있습니다.");
  }
});

 