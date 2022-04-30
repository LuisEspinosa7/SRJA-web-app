/**
 * 
 */
package com.sevensoftware.domotica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author LUIS
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	UserDetailsService userDetailsService;
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http		
		.authorizeRequests()
		.antMatchers("/dashboard/**").access("hasRole('ADMIN') or hasRole('PROPIETARIO') or hasRole('INTEGRANTE')")		
		.antMatchers("/login").permitAll()
		.antMatchers("/logout").permitAll()
		.antMatchers("/**").permitAll()		
		//.antMatchers(HttpMethod.POST, "/api/pedido/**").permitAll()
		//.antMatchers(HttpMethod.GET, "/api/producto/categoria/**").permitAll()
		//.antMatchers(HttpMethod.GET, "/api/imagen/**").permitAll()		
		.anyRequest().authenticated()
		.and()		
		.formLogin()		
		.loginPage("/login")
        .loginProcessingUrl("/j_spring_security_check")
        .usernameParameter("j_username") 
        .passwordParameter("j_password")		        		        
        .failureUrl("/login?error")		        
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .invalidateHttpSession(true)
        .logoutSuccessUrl("/")				
		.and()
		.csrf()
		.disable();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(12);
		return encoder;
	}
	

}
