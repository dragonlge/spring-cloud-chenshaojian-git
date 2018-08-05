package com.demo.merchant.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@ComponentScan(basePackages = "com.demo")
@EnableFeignClients(basePackages = "com.demo")
public class MerchantWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MerchantWebApplication.class, args);
    }

}
