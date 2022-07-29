package com.lml.jwtlogin.config;

import com.lml.jwtlogin.interceptors.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                //拦截的路径 需要进行token验证的路径
                .addPathPatterns("/**")
                //放行的路径
                .excludePathPatterns("/user/login","/user/register","/facereport/static/captured/");
    }
}
