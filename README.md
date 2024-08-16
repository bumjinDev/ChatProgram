<img src="https://github.com/user-attachments/assets/68c5b846-0b38-42c4-8aeb-67b20354cabb" width="100" height="100" /> | <h1>실시간 채팅 프로그램</h1> |
--- | --- |

---

+ 배포 URL : http://43.202.178.156:8181/chat/

---

## 프로젝트 소개
----
+ 실시간 채팅 프로그램 입니다.
+ HTTP 기반 WebSocket 프로토콜 적용하였습니다.

## 1. 개발 환경
---
+ Front : HTML, CSS, JavaScript
+ Back-end : SpringFrameWork
+ DataBase : Oracle

## 2. 배포 환경
---
+ AWS

## 3. 프로젝트 구조
---
<pre>
RealTimeChatProgram
   └─ src
       	├─ websocket
       	├─ waitroom
      	│        ├─ service
      	│        └─ model
       	├─ SessionResource
       	├─ main
       	│     ├─ service
       	│     └─ model
       	├─ Log
      	│     └─ model
       	├─ ExitRoom
       	│        ├─ service
       	│        └─ model
        ├─ dao
        ├─ createroom
        │        └─ service
        └─ controller
                 └─ chatroom
                         ├─ service
                         └─ model
</pre>
<br><br>

# 4. 개발 기간 및 작업 관리
---
### 개발 기간
      2024.06.25 ~ 2024.07.06

# 5. 페이지 별 기능
---
[초기화면]
   + 서비스 접속 초기화면으로 사용자 닉네임 설정 시 실시간 채팅 접속 가능합니다.
   + 유효성 검사 로직 적용하여 닉네임 미 입력 시 실시간 채팅 서비스 제한 됩니다.
     
![first user](https://github.com/user-attachments/assets/0881862b-7f02-4183-9e98-80da9f0f1d94)
<br><br>
[대기방 페이지]
   + 사용자가 닉네임을 입력한 후 최초로 접속하는 페이지입니다.
   + 전체 활성화된 채팅방 목록을 확인할 수 있습니다.
   + 각 채팅방을 선택하여 접속할 수 있습니다.
   + 새로운 채팅 방을 생성할 수 있습니다.

![first user](https://github.com/user-attachments/assets/4a4c1ef9-51b6-450c-9693-a83bb1c2a63e)
<br>
<br>

[실시간 채팅 페이지]
   + 사용자간 실시간 채팅 메시지로 채팅하는 페이지 입니다.
   + 한 사용자가 메시지 전송 시 모든 사용자에게 일괄 전송 되는 방식 입니다.
     
![first user](https://github.com/user-attachments/assets/8349f555-630f-46a2-9c2c-eb0227ef9056)
<br><br>
