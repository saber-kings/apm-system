package cn.saberking.jvav.apm.web.config;

import cn.saberking.jvav.apm.web.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //本地文件上传路径
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + ApmConfig.getProfile() + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //放行路径
        List<String> patterns = new ArrayList<>();
        patterns.add(Constants.RESOURCE_PREFIX + "/**");
        patterns.add("/user/login");
        patterns.add("/user/registerByPhone");
        patterns.add("/product/*");
        patterns.add("/product/list");
        patterns.add("/product/web/*");
        patterns.add("/product/web/list");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
