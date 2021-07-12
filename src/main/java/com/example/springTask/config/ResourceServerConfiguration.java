package com.example.springTask.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resourse-server-rest-api";

    private enum Roles {
        POWER_USER,
        TEACHER,
        STUDENT
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        configurer.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/students**").hasAnyRole(Roles.STUDENT.name(), Roles.POWER_USER.name(), Roles.TEACHER.name())
                .antMatchers(HttpMethod.GET, "/teachers/**").hasAnyRole(Roles.TEACHER.name(), Roles.POWER_USER.name())
                .antMatchers(HttpMethod.POST, "/students**").hasAnyRole(Roles.TEACHER.name(), Roles.POWER_USER.name())
                .antMatchers(HttpMethod.POST, "/teachers**").hasRole(Roles.POWER_USER.name())
                .antMatchers(HttpMethod.PUT, "/students/**").hasAnyRole(Roles.TEACHER.name(), Roles.POWER_USER.name())
                .antMatchers(HttpMethod.PUT, "/teachers/**").hasRole(Roles.POWER_USER.name())
                .antMatchers(HttpMethod.DELETE, "/students/**").hasAnyRole(Roles.TEACHER.name(), Roles.POWER_USER.name())
                .antMatchers(HttpMethod.DELETE, "/teachers/**").hasAnyRole(Roles.POWER_USER.name())
                .anyRequest().fullyAuthenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
