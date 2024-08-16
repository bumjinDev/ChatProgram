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
      	│    └─ model
       	├─ ExitRoom
       	│        ├─ service
       	│        └─ model
         ├─ dao
      ├─ createroom
       	│        └─ service
      ├─ controller
      └─ chatroom
       	        ├─ service
       	        └─ model
</pre>
<br><br>
