package ru.sd.app.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sd.app.services.LoginService;
import ru.sd.web.dto.Account;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = Logger.getLogger(AppSecurityConfig.class);
    @Autowired
    LoginService loginService;

    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.info("Config web security");
        web
                .ignoring().antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Config http security");
        http.headers().frameOptions().disable();
        http.csrf().ignoringAntMatchers("/console/**", "/books/**");
        http
                .authorizeRequests()
                .antMatchers("/login/registration").not().fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/login/registration/sign_in").not().authenticated()
                .antMatchers("/login*", "errors/404").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/auth")
                .defaultSuccessUrl("/books/shelf", true)
                .failureForwardUrl("/login/auth")
                .and()
                .logout().logoutUrl("/books/logout").permitAll();
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("Config from loginService");
        auth
                .userDetailsService(loginService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username,password,'true' as " +
                        "enabled" +
                        " FROM " +
                        "accounts WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username,role FROM roles " +
                        "WHERE username = ?")
        ;
    }
}
