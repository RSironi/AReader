package com.tcc.areader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.tcc.areader.security.SecurityConfigurationBasicAuth;

@SpringBootApplication
@Import(SecurityConfigurationBasicAuth.class)
public class AReaderApplication {
	public static void main(String[] args) {
		SpringApplication.run(AReaderApplication.class, args);
	}
}