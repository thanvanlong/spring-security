package com.example.login.security.config;

import com.example.login.appuser.AppUserService;
import com.example.login.security.PassEncoder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AppUserService appUserService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/*","/js/*","/images/*","/signup","/confirm")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/register")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin()
                .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/home")
                    .failureForwardUrl("/login?message=Email or password invalid!")
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(1209600)
                    .key("remember-me")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                .deleteCookies("remember-me");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);

        return provider;
    }
}
