package com.hmdp.config;

import com.hmdp.utils.LoginInterceptor;
import com.hmdp.utils.ReflashTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final StringRedisTemplate stringRedisTemplate;
    public WebMvcConfig(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //InterceptorRegistry注册器对象
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/user/login"
                        , "/user/code"
                        , "/shop/**"
                        , "/shop-type/**"
                        , "/blog/hot"
                        , "/voucher/**"
                        , "upload/**"
                ).order(1);

        registry.addInterceptor(new ReflashTokenInterceptor(stringRedisTemplate)).order(0);
    }
}
