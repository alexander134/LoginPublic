package com.sistema.logindos.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sistema.logindos.seguridad.CustomUserDetailsService;
import com.sistema.logindos.seguridad.JwtAuthenticationEntryPoint;
import com.sistema.logindos.seguridad.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public JwtAuthenticationFilter authenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET,"/api/**")
		.permitAll().antMatchers("/api/auth/**").permitAll()
		.anyRequest()
		.authenticated();
		
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests().antMatchers(HttpMethod.GET,"/api/**")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}*/
	
	/*@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails alexander = User.builder().username("Alexander").password(passwordEncoder().encode("password")).roles("USER").build();
		
		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
		
		return new InMemoryUserDetailsManager(alexander,admin);
	}*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
}
