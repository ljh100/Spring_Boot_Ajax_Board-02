package com.community.rest;


//import com.community.rest.event.BoardEventHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootCommunityWebRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCommunityWebRestApplication.class, args);
	}
	
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		
		  @Bean 
		  InMemoryUserDetailsManager userDetailsManager() {
		  
		  User.UserBuilder commonUser = User.withUsername("commonUser");
		  User.UserBuilder havi = User.withUsername("havi");
		  
		  List<UserDetails> userDetailsList = new ArrayList<>();
		  userDetailsList.add(commonUser.password("{noop}common").roles("USER").build()
		  ); userDetailsList.add(havi.password("{noop}test").roles("USER",
		  "ADMIN").build());
		  
		  return new InMemoryUserDetailsManager(userDetailsList); }
		
		/*
		 * @Bean public CommandLineRunner runner(UserRepository userRepository,
		 * BoardRepository boardRepository) { return (args) -> { User user =
		 * userRepository.save(User.builder() .name("havi") .password("test")
		 * .email("havi@gmail.com") .createdDate(LocalDateTime.now()) .build());
		 * 
		 * IntStream.rangeClosed(1, 200).forEach(index ->
		 * boardRepository.save(Board.builder() .title("게시글"+index)
		 * .subTitle("순서"+index) .content("컨텐츠") .boardType(BoardType.free)
		 * .createdDate(LocalDateTime.now()) .updatedDate(LocalDateTime.now())
		 * .user(user).build()) ); }; }
		 */

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.addAllowedOrigin("*");
			configuration.addAllowedMethod("*");
			configuration.addAllowedHeader("*");
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);

			http.httpBasic()
					.and().authorizeRequests()
					//.antMatchers(HttpMethod.POST, "/Boards/**").hasRole("ADMIN")
					.anyRequest().permitAll()
					.and().cors().configurationSource(source)
					.and().csrf().disable();
		}
	}

}
