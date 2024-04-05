package com.terramas.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
		this.jwtAuthFilter = jwtAuthFilter;
		this. authenticationProvider = authenticationProvider;
	}
	
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(
//        		new AntPathRequestMatcher("/h2-console/**"),
//        		new AntPathRequestMatcher("/registration"),
//        		new AntPathRequestMatcher("/authenticate"));
//    }    
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(csrf -> csrf.disable())
			.headers((headers) -> headers.frameOptions().disable())
	        .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/registration/**").permitAll()
	        		.requestMatchers("/authenticate").permitAll()
	        		.requestMatchers("/h2-console/**").permitAll()
	        		.requestMatchers("/setAdmin/**").permitAll()
	        		.requestMatchers("/fetchUser").permitAll()
	        		.requestMatchers("/recoverPassword/**").permitAll()
	        		.requestMatchers("/resetpassword/**").permitAll()
	        		.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
	                .anyRequest().authenticated()
//	    			.anyRequest().permitAll()
	            )
	        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .cors();
			
		
		return http.build();
		
	}
}
