package edu.nau.coe_stic_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
   {
      http.authorizeHttpRequests()
              .requestMatchers("/login")
              .permitAll()
              .anyRequest()
              .authenticated()
              .and()
              .oauth2Login()
              //.loginPage("/login")
              .defaultSuccessUrl("/login-success");
      return http.build();
   }
}
