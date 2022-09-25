package junitStudy.junit.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class HttpBoController{

    @PostMapping("/body1")
    public String x_www_form_urlencoded(String username) {
        log.info("username", username);
        return "key=value 전송됨";
    }

    @PostMapping("/body4")
    public String applicationJson(@RequestBody User username) {
        log.info("username", username.getUsername());
        return "json 전송됨";
    }



    @GetMapping("/body2/{username}")
    public String getPathVariable(@PathVariable String username) {
        log.info("username", username);
        return "주소 변수 매핑";
    }
}
