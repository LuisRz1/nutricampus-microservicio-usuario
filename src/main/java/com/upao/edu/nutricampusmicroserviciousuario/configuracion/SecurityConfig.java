package com.upao.edu.nutricampusmicroserviciousuario.configuracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private LoggingFilter loggingFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        logger.debug("SecurityConfig initialized.");
        // We don't need CSRF for this example
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                // don't authenticate this particular request
                .authorizeHttpRequests((it) -> it.requestMatchers(publicEndPoints()).permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated())
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling((it) -> it.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((it) -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add a filter to log the request-response of every request
        httpSecurity.addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class);
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.debug("AuthenticationManager invoked.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("PasswordEncoder invoked.");
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }

    private RequestMatcher publicEndPoints(){
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/autenticacion/**")
        );
    }

    /*
    Used for customizing Web Security
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }*/
}
