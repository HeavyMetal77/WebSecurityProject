package ua.tarastom.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    //with deprecated method - при настройке вручную пароли захаркоджены в коде вариант 1 deprecated
    //https://stackoverflow.com/questions/49847791/java-spring-security-user-withdefaultpasswordencoder-is-deprecated
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // add our users for in memory authentication
//        UserBuilder users = User.withDefaultPasswordEncoder(); //deprecated
//
//        auth.inMemoryAuthentication()
//                .withUser(users.username("john").password("test123").roles("EMPLOYEE"))
//                .withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
//                .withUser(users.username("susan").password("test123").roles("MANAGER", "ADMIN"));
//    }

    @Autowired
    private DataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* при настройке вручную пароли захаркоджены в коде вариант 2
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("john")
                .password(passwordEncoder().encode("test123"))
                .roles("EMPLOYEE");
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("mary")
                .password(passwordEncoder().encode("test123"))
                .roles("EMPLOYEE", "MANAGER");
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("susan")
                .password(passwordEncoder().encode("test123"))
                .roles("MANAGER", "ADMIN");
        */

        auth.jdbcAuthentication().dataSource(securityDataSource); //для чтения юзеров с базы данных

    }

    /* при настройке вручную пароли захаркоджены в коде вариант 2
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                    .anyRequest().authenticated() //для любого идентифицированного пользователя (который вошел по логину)
                .antMatchers("/"). hasAnyRole("EMPLOYEE","MANAGER", "ADMIN")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/showMyLoginPage")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}

