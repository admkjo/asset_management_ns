package com.gh.gov.ns.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/fonts/**", "/assets/**", "/images/**", "/js2/**");
	}

	// @formatter:off
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
      //.anyRequest().hasAnyRole("ADMIN", "INSTITUTION")
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
            	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403_error")
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login")
                /*
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry())*/;
        //http.csrf().disable();
    }
	// @formatter:on

	// @formatter:off
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth
    	/*.inMemoryAuthentication()
		.withUser("admin")
		.password("admin")
		.roles("ADMIN");*/
    	.userDetailsService(userDetailsService);

    }
    // @formatter:on
}

