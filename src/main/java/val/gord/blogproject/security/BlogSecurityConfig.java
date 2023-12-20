package val.gord.blogproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BlogSecurityConfig {
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                //security rules:
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("api/v1/auth/**").permitAll();
                    auth.requestMatchers("api/v1/**").authenticated();
                    auth.anyRequest().permitAll();
                })
                .httpBasic(a->{})
                .build();
    }

    //Password encoder Bean:
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }



}
