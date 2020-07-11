package com.leyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author liang
 * @create 2020/4/22 17:48
 */
@Configuration
public class LeyouCorsConfiguration {



    @Bean
    public CorsFilter corsFilter(){
        //初始化cors配置对象
        CorsConfiguration  configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);//允许携带cookie
        //允许跨域的域名，如果携带cookie，不能写*，*：代表所有域名都可以跨域访问
        configuration.addAllowedOrigin("http://manage.leyou.com");
        configuration.addAllowedHeader("*");//代表可以携带任何头部信息
        configuration.addAllowedMethod("*");//代表可以允许所有请求方式，GET POST PUT Delete....


        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);

//        返回corsFilter实例，参数：cors配置源参数
        return new CorsFilter(configurationSource);
    }
}
