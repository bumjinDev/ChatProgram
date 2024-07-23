package com.example.websocketrefresh;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.Map;

/* 클라이언트에서 새로 고침 시 페이지 리소스 해제 후에 js 의 새로고침 이벤트 발생 등의 동적인 기능을 서버와의 동작 보장을 위해
 * js 컨텍스트는 브라우저 내 페이지 리소스(html,css) 를 별도의 우선 순서로서 리소스 해제 후에 서버의 랜더링 페이지를 받은 후에 
 * js 컨텍스트를 websocekt 과 함께 종료한다..이때 새로고침 이벤트 발생 시 서버가 이를 받아서 Websoceket에 적용한다. */

@RestController
public class RefreshRestController {

    @PostMapping("/refresh")
    public void handleRefresh(@RequestBody Map<String, Boolean> payload, HttpSession httpSession) {
        	System.out.println("페이지 재부팅 되어 호출!");
            
        	httpSession.setAttribute("refresh", true);
            System.out.println("httpSession.getAttribute('refresh') : " + httpSession.getAttribute("refresh"));
    }
}
