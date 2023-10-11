package com.tcc.areader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@SpringBootApplication
public class AReaderApplication {
	public static void main(String[] args) {
		SpringApplication.run(AReaderApplication.class, args);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().permitAll())
				.csrf(AbstractHttpConfigurer::disable)
				.headers(AbstractHttpConfigurer::disable);
		return http.build();
	}
}