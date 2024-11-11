package com.software.modsen.ratingservice.security;

import com.software.modsen.ratingservice.util.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationConverterRating implements Converter<Jwt, Collection<GrantedAuthority>>{
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, List<String>> resourceAccess = jwt.getClaim(SecurityConstants.REALM_ACCESS);
        List<String> resourceRoles;

        if (resourceAccess == null || (resourceRoles = resourceAccess.get(SecurityConstants.ROLES)) == null) {
            return Set.of();
        }
        Collection<GrantedAuthority> authorities = resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority(SecurityConstants.PREFIX_ROLE + role))
                .collect(Collectors.toList());

        setAuthentication(jwt, authorities);

        return authorities;
    }

    private void setAuthentication(Jwt jwt, Collection<GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(jwt.getSubject(), jwt, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
