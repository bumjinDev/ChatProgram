/* 채팅 대기방 페이지 'waitpage.jsp' 의 동작 구현. */
window.onload=function(){

    var pageIndex = document.getElementById("pageindex");      // 메인 화면 이동 버튼
    var createroom = document.getElementById("createroom");    // 방 생성 요청 버튼

    /* 방 입장 버튼 가져오기 : 대기방 페이지 내 표현된 개수 만큼 전부 가져오기 */
    var entranceBtns = document.querySelectorAll(".entranceBtn");

    for(let i = 0; i < entranceBtns.length; i++){

        /* 각 방 입장 버튼을 클릭 시 해당 방 번호에 따른 채팅방 페이지를 리다이렉션. */
        entranceBtns[i].addEventListener("click", function(){
            console.log("btn clicked : " + this.value);

            window.location.href = "./loadChatPage/"+entranceBtns[i].value;
        });
    }

    /* 버튼 "메인 화면 이동" 클릭 시 메인화면으로 이동하며 기존 websock 세션은 index.js 에서 . */
    pageIndex.addEventListener("click", function(){

        window.location.href ="./";
    });

    /* 채팅 방 생성 페이지 요청 */
    createroom.addEventListener("click", function(){

        window.location.href ="./loadCreatePage";
    });
}