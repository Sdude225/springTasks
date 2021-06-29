package com.example.springTask.config;

import com.example.springTask.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private enum Roles {
        POWER_USER,
        TEACHER,
        STUDENT
    }

    @Autowired
    UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/teachers**", "/students**").hasAuthority(Roles.POWER_USER.name())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/students**").hasAuthority(Roles.STUDENT.name())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/students**").hasAuthority(Roles.TEACHER.name())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/teachers**").hasAuthority(Roles.TEACHER.name())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/students**").hasAuthority(Roles.TEACHER.name())
                .and()
                .authorizeRequests().antMatchers("/oauth/token")
                .permitAll().anyRequest().authenticated();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }
}
