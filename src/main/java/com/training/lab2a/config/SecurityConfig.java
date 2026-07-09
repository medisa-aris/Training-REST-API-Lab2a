package com.training.lab2a.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity              // enables @PreAuthorize (Task 2)
public class SecurityConfig {
 
  @Bean
  SecurityFilterChain chain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())               // stateless API
      .authorizeHttpRequests(a -> a
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        .requestMatchers("/v1/**").authenticated()
        .anyRequest().denyAll())
      .oauth2ResourceServer(o -> o.jwt(jwt ->
        jwt.jwtAuthenticationConverter(jwtConverter())))
      .sessionManagement(sm ->
        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .build();
  }

  @Bean
  KeyPair rsaKeyPair() throws Exception {
    var gen = KeyPairGenerator.getInstance("RSA");
    gen.initialize(2048);
    return gen.generateKeyPair();   // demo only — in prod the auth server holds this
  }

  @Bean
  JwtDecoder jwtDecoder(KeyPair kp) {
    return NimbusJwtDecoder.withPublicKey((RSAPublicKey) kp.getPublic()).build();
  }

  @Bean
  JwtEncoder jwtEncoder(KeyPair kp) {
    var jwk = new RSAKey.Builder((RSAPublicKey) kp.getPublic())
        .privateKey((RSAPrivateKey) kp.getPrivate()).build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
  }

  @Bean
  JwtAuthenticationConverter jwtConverter() {
    var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

    var authenticationConverter = new JwtAuthenticationConverter();
    authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return authenticationConverter;
  }

}
