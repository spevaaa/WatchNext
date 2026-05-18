package hr.tvz.watchnext.watchnextapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final String ADMIN = "ADMIN";
    private final String USER = "USER";
    private final String GUEST = "GUEST";

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/series/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").hasAnyRole(ADMIN, USER, GUEST)
                        .requestMatchers(HttpMethod.GET, "/api/genre/**").hasAnyRole(ADMIN, USER, GUEST)
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole(ADMIN, USER)
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}