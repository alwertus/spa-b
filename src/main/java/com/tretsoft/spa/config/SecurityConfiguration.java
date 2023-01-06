package com.tretsoft.spa.config;

import com.tretsoft.spa.config.filter.JwtAuthFilter;
import com.tretsoft.spa.config.props.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final CorsProperties corsConfig;
//    private final CustomUserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())    // defaults use bean "corsConfigurationSource"
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/user/login",
                                "/user/emailConfirm",
                                "/user"
                        ).permitAll()

                        .requestMatchers("/phone","/phone/**").hasAnyAuthority("PAGE_PHONE")

                        .anyRequest().authenticated()
                ).build();
    }

    /*
    TODO: remove comment below
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }*/

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfig(corsConfig);
    }
}
