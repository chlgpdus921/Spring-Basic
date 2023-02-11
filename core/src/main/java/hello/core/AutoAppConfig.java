package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//       basePackages = "hello.core.member", //  어디서부터 찾을지 시작위치 지정할 수 있음
//        basePackageClasses = AutoAppConfig.class, // 지정한 클래스의 package 위치를 탐색 시작함
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {



}
