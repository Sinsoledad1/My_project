package org.echoiot.config;


import org.echoiot.interceptor.AdminInterceptor;
import org.echoiot.interceptor.LoginInterceptor;
import org.echoiot.interceptor.SuperAdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Alfalfa99
 * @version 1.0
 * @date 2020/10/22 21:30
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    private final LoginInterceptor loginInterceptor;
    private final SuperAdminInterceptor superAdminInterceptor;
    private final AdminInterceptor adminInterceptor;

    public InterceptorConfig(LoginInterceptor loginInterceptor, SuperAdminInterceptor superAdminInterceptor, AdminInterceptor adminInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.superAdminInterceptor = superAdminInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    /**
     * 注册拦截器
     *
     * @param registry 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有目录，除了通向login的接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/admin/**")
                .excludePathPatterns("/**/login/**", "/**/verifyCode/**","/**/register/**")
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css")
                .excludePathPatterns("/**/*.jpg","/**/*.png","/**/*,jpeg");
        registry.addInterceptor(superAdminInterceptor).addPathPatterns("/**/sAd/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/**/Ad/**");
    }
}
