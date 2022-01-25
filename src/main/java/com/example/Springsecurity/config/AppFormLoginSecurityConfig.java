package com.example.Springsecurity.config;

import com.example.Springsecurity.SpringsecurityApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


//@Configuration
//@EnableWebSecurity
public class AppFormLoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    SpringsecurityApplication springsecurityApplication;

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(springsecurityApplication.getEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }


    // to play with H2 database
//    To enable access to the H2 database console under Spring Security you need to change three things:
//
// Allow all access to the url path /console/*.
//Disable CRSF (Cross-Site Request Forgery). By default, Spring Security will protect against CRSF attacks.
//Since the H2 database console runs inside a frame, you need to enable this in in Spring Security.
//The following Spring Security Configuration will:
//
//Allow all requests to the root url (“/”) (Line 12)
//Allow all requests to the H2 database console url (“/console/*”) (Line 13)
//Disable CSRF protection (Line 15)
//Disable X-Frame-Options in Spring Security (Line 16)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().permitAll().and()
                .authorizeRequests()
                .antMatchers("/h2/**","/user")
                .permitAll().
                anyRequest().authenticated()
                .and().headers().frameOptions().disable().and().logout().permitAll();
    }

}
