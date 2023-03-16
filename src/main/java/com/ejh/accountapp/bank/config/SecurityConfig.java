package com.ejh.accountapp.bank.config;

import com.ejh.accountapp.bank.config.jwt.JwtAuthenticationFilter;
import com.ejh.accountapp.bank.config.jwt.JwtAuthorizationFilter;
import com.ejh.accountapp.bank.domain.user.UserRole;
import com.ejh.accountapp.bank.util.CommonResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new JwtAuthenticationFilter(authenticationManager));
            http.addFilter(new JwtAuthorizationFilter(authenticationManager));
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().disable(); // 추후에 찾아볼 것
        http.csrf().disable();
        http.cors().configurationSource(configurationSource());

        // 로그인 & 권한 익셉션 핸들링
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                CommonResponseUtil.fail(response, "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED));
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) ->
                CommonResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();

        // JWT 필터 적용
        http.apply(new CustomSecurityFilterManager());

        http.authorizeHttpRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(UserRole.ADMIN + "")
                .anyRequest().permitAll();
        return http.build();
    }

    // CORS 설정
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
