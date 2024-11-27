package com.colak.springtutorial.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final KeycloakClientRoleConverter keycloakClientRoleConverter;

    @Bean
    public SecurityFilterChain configurePaths(HttpSecurity http,
                                              @Value("${security.authentication.unsecure.paths}") List<String> springSecurityAllowedPaths)
            throws Exception {
        AntPathRequestMatcher[] allowedPaths = springSecurityAllowedPaths.stream().map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);

        // no session will be created or maintained on the server side.
        http.sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests.requestMatchers(allowedPaths).permitAll()
                        .anyRequest().authenticated()
        );

        // Configure the application to use OAuth 2.0 resource server with JWT tokens.
        // We set to the JwtAuthenticationConverter that uses a converter named KeycloakClientRoleConverter to convert JWT to roles
        http.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(keycloakClientRoleConverter);
        return jwtConverter;
    }

}
