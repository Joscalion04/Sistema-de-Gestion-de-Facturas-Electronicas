package org.segundahacienda.segundahacienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@SpringBootApplication
public class SegundaHaciendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegundaHaciendaApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("securityFilterChain")
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		var chain = http
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers("/api/login/login").permitAll()
						.requestMatchers("/api/login/signup").permitAll()
						.requestMatchers("/api/login/logout").authenticated()
						.requestMatchers(HttpMethod.POST,"/api/**").hasAnyAuthority("ADM","CLI")
						.requestMatchers("/api/**").hasAnyAuthority("ADM","CLI")
						.requestMatchers("/**").permitAll()
				)
				.exceptionHandling(customizer -> customizer
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.csrf(AbstractHttpConfigurer::disable)
				.build();
		return chain;
	}

}
