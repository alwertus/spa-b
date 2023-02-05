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
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final CorsProperties corsConfig;

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

                        .requestMatchers("/phone","/phone/**", "phone/sms/**").hasAnyAuthority("PAGE_PHONE")
                        .requestMatchers("/api/feeding/*").hasAnyAuthority("PAGE_FEEDING")
                        .requestMatchers("/info", "/info/*").hasAnyAuthority("PAGE_INFO")

                        .anyRequest().authenticated()
                ).build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfig(corsConfig);
    }
}
