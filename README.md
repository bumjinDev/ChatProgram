# 📡 실시간 채팅 프로그램 (Real-Time Chat Program)

<img src="https://github.com/user-attachments/assets/68c5b846-0b38-42c4-8aeb-67b20354cabb" width="100" height="100" />

---

## 📌 프로젝트 개요

WebSocket 프로토콜을 활용하여 사용자 간 실시간 메시지 송수신이 가능한 1:N 구조의 채팅 시스템입니다. 사용자는 채팅방을 생성하거나 기존 채팅방에 참여할 수 있으며, 닉네임 기반으로 채팅 세션을 구분합니다. 서버는 Spring 기반으로 구성되었으며, OracleDB와 연동하여 사용자 관리 및 방 관리 기능을 제공합니다.

---

## ⛏ 개발 환경

| 구분       | 스택/기술                         |
|------------|------------------------------------|
| Frontend   | HTML, CSS, JavaScript              |
| Backend    | Java 8, Spring Framework, JSP      |
| WebSocket  | Spring WebSocket API (STOMP 미사용) |
| Database   | Oracle 11g                         |
| 배포       | AWS EC2 (현재 중단됨)              |

---

## 📁 프로젝트 구조

<pre>
RealTimeChatProgram
   └── src
       ├── websocket                
       ├── waitroom
       │   ├── service
       │   └── model
       ├── SessionResource
       ├── main
       │   ├── service
       │   └── model
       ├── Log
       │   └── model
       ├── ExitRoom
       │   ├── service
       │   └── model
       ├── dao
       ├── createroom
       │   └── service
       └── controller
            └── chatroom
                ├── service
                └── model
</pre>

---

## 🕒 개발 기간 및 인원

- **개발 기간**: 2024.06.25 ~ 2024.07.06
- **참여 인원**: 1명 (개인 프로젝트)

---

## 🖥 페이지별 주요 기능

### 🔹 [초기 화면]
- 사용자 닉네임 입력 시 대기방으로 이동
- 닉네임 미입력 시 유효성 검사로 입장 제한

<img src="https://github.com/user-attachments/assets/0881862b-7f02-4183-9e98-80da9f0f1d94" />

---

### 🔹 [대기방 페이지]
- 전체 활성 채팅방 목록 조회
- 채팅방 입장 또는 신규 생성 기능 제공
- 사용자 닉네임은 세션 기반으로 유지됨

<img src="https://github.com/user-attachments/assets/4a4c1ef9-51b6-450c-9693-a83bb1c2a63e" />

---

### 🔹 [실시간 채팅 페이지]
- 특정 채팅방 내 실시간 채팅 구현
- 메시지 전송 시 WebSocket을 통해 전체 사용자에게 브로드캐스트
- 서버 측 세션 기반 사용자 구분 및 로그 저장 처리

<img src="https://github.com/user-attachments/assets/8349f555-630f-46a2-9c2c-eb0227ef9056" />

---

## ⚙ 핵심 기술 및 설계

- **WebSocket API 직접 구현**: STOMP 미사용, 핸드셰이크 및 메시지 처리 수동 구성
- **세션 관리**: 사용자별 세션 ID 저장 후, 채팅방 기준으로 세션 그룹핑 처리
- **멀티스레드 처리**: `ExecutorService` 활용한 메시지 병렬 전송 구조
- **메시지 로그 관리**: 채팅 내역은 서버 메모리에 저장하며, 향후 DB 연동 확장 가능 구조 설계
- **채팅방 상태 유지**: 동시 접속자 수 추적 및 채팅방 동적 생성/제거

---

## 🧭 개선 예정 사항

1. **STOMP 및 SockJS 적용**
   - 브라우저 호환성과 안정성을 확보하고자 WebSocket 프로토콜 위에 STOMP 프로토콜 적용 예정

2. **채팅 이력 저장 기능 강화**
   - Oracle 연동 기반의 영속적 메시지 저장 기능 개발 예정

3. **비동기 확장**
   - `CompletableFuture`를 활용한 비동기 전송 처리로 응답 병목 해소 예정

4. **멀티 프로세싱 처리**
   - 사용자 수 급증 시 대비하여 WebSocket 처리 서버를 N개 인스턴스로 분산 예정 (Load Balancer 연계 구조 준비)

5. **UI 개선 및 React 전환 고려**
   - 클라이언트 구조를 React 기반 SPA로 변환하여 컴포넌트화 및 상태 관리 개선

---

## ✅ 현재 상태

- 기본 채팅 기능 완전 구현
- 세션 기반 사용자 관리, 채팅방 관리 로직 완료
- 배포 환경은 중단된 상태이나 로컬 테스트 완전 가능

---

## 🔗 프로젝트 기여자

| 이름   | GitHub 링크 |
|--------|-------------|
| 정범진 | [@bumjinDev](https://github.com/bumjinDev) |
