package com.makersacademy.acebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.makersacademy.acebook.security.JWTAuthenticationFilter;
import com.makersacademy.acebook.security.JWTAuthorizationFilter;
import com.makersacademy.acebook.services.UserLoginDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserLoginDetailsService userLoginDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(UserLoginDetailsService userService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userLoginDetailsService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoginDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // allow access to /login and log in from there

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/users").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    // final UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();

    // CorsConfiguration corsConfiguration = new
    // CorsConfiguration().applyPermitDefaultValues();
    // source.registerCorsConfiguration("/**", corsConfiguration);

    // return source;
    // }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}