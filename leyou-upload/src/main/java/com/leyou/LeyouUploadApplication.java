package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liang
 * @create 2020/4/28 17:36
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LeyouUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUploadApplication.class);
    }
}
