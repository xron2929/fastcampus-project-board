package com.fastcampus.fastcampusprojectboard.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {
// 타임리프 Resolver를 등록함
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());

        return defaultTemplateResolver;
    }
    // 기존에 있는 configuration은 가져오고,

    @RequiredArgsConstructor
    @Getter
    @ConstructorBinding
    // 생성자로 안의 객체나 여러가지에 대ㅐㅎ thymelef3 빈 주입하는듯
    // setter로 받으면 중간에 바꿔버릴 위험이 있어서
    @ConfigurationProperties("spring.thymeleaf3")
    // 이걸로 Build.gradle에서 새로 하나 불러올 수 있다는 점
    // application.properties에 hello = 이렇게 해서 하나를 잡을 수도 있고
    // 이렇게 Configuration으로 등록할 수도 있고
    // 빈 호출되면 boolean decoupledLogic;를 생성자로 주입받는 역할
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic;
    }

}