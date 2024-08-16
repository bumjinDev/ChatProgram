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

[대기방 페이지]
   + 사용자가 닉네임 입력 후 최초 접속되는 페이지로 전체 활성화된 채팅방 목록 확인 가능합니다.
   + 각 채팅방을 선택적하여 접속 가능합니다.
   + 새로운 채팅 방 생성 가능합니다.

