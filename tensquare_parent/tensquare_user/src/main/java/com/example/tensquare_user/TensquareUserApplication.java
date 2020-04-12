package com.example.tensquare_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.IdWorker;

@SpringBootApplication
public class TensquareUserApplication {
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }

    public static void main(String[] args) {
        SpringApplication.run(TensquareUserApplication.class, args);
    }

}
