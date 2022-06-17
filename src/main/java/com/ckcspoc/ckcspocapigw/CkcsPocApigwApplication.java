package com.ckcspoc.ckcspocapigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CkcsPocApigwApplication {

	public static void main(String[] args) {
		SpringApplication.run(CkcsPocApigwApplication.class, args);
	}

}
