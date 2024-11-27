package com.colak.springtutorial.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class KeycloakClientRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES = "roles";
    private static final String REALM_ACCESS = "realm_access";

    //  Extract the roles from the jwt object and convert them into a list of SimpleGrantedAuthority objects.
    //  These roles are then automatically set into the SecurityContext of Spring Security.
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = Optional.ofNullable(jwt.getClaimAsMap(REALM_ACCESS))
                .orElse(Collections.emptyMap());

        return Optional.ofNullable((List<?>) realmAccess.get(ROLES))
                .orElse(Collections.emptyList())
                .stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}