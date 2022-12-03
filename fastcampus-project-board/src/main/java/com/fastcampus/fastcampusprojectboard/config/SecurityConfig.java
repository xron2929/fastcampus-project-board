package com.fastcampus.fastcampusprojectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebConfig 이거 안넣어도 스프링 부트에서 시큐리티 쓸 떄는 Auto COnfig로 등록됨
// 예전에는  extends SecurityFilterChain 상속받고 authroizeRequests였지만
// 지금은 authorizeHttpRequests로 처리하고,
@Configuration

public class SecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // auth를 받아서 어떤 리퀘스트든 다 허용인듯?
                .formLogin() // 다 로그인 페이지 만들고
                .and().build(); // build로 마무리


    }

}
