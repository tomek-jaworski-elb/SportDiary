package com.jaworski.sportdiary.config.websecurity;

import com.jaworski.sportdiary.config.security.UserEntityPrincipalDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserEntityPrincipalDetailService userEntityPrincipalDetailService;

    public SecurityConfiguration(UserEntityPrincipalDetailService userEntityPrincipalDetailService) {
        this.userEntityPrincipalDetailService = userEntityPrincipalDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.userDetailsService(inMemoryUserDetailsManager());
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        users.put("user", "{noop}user,ROLE_USER,enabled");
        users.put("u", "{noop}u,ROLE_PUBLIC,enabled");
        users.put("admin", "{noop}admin,ROLE_ADMIN,enabled");
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userEntityPrincipalDetailService);
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/img/**", "/bootstrap/**", "/js/**").permitAll()
                .antMatchers("/welcome", "/", "/test").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/anonymous/**").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/public/**").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/users").permitAll()
                .antMatchers("/acts","/acts/**").permitAll()
                .antMatchers("/add").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/user/list", false)
                .failureUrl("/login?error=true")
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/user")
                .and()
                .exceptionHandling().accessDeniedPage("/403");

//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                .antMatchers("/index", "/", "/login*").permitAll()
//                .antMatchers("/img/**", "/bootstrap/**", "/webjars/**").permitAll()
//                .antMatchers("/anonymous*").anonymous()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/index", true)
//                .failureUrl("/login?error=true")
////                .failureHandler(authenticationFailureHandler())
//                .and()
//                .logout()
//                .permitAll()
//                .logoutUrl("/logout")
//                .deleteCookies("JSESSIONID");
////                .getLogoutSuccessHandler(logoutSuccessHandler());
    }
}
