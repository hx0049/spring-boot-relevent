package com.hx;

import org.hibernate.dialect.MySQL57Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class,args);
    }
}
