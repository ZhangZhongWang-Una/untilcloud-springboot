package com.una.uc.config;

import com.una.uc.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        registry.addMapping("/**")
                .allowCredentials(true) //带上cookie信息
                .allowedOrigins("http://localhost:8080") //允许跨域的域名，可以用*表示允许任何域名使用
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") //允许任何方法（post、get等）
                .allowedHeaders("*") //允许任何请求头
                .maxAge(3600); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
    }

    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register")
                .excludePathPatterns("/api/verifyVerificationCode")
//                .excludePathPatterns("/api/getVerificationCode")
                .excludePathPatterns("/api/logout");
    }

}
