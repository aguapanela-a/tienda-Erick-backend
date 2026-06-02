package com.acm.tiendaerick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TiendaErickApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaErickApplication.class, args);
    }

}
