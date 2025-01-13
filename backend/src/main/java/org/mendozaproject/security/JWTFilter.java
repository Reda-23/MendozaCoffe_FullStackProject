package org.mendozaproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//        if (authHeader != null && authHeader.startsWith("Bearer ")){
//            token = authHeader.substring(7);
//            username = jwtService.extractUsername(token);
//            logger.info("Authorization Header: " + request.getHeader("Authorization"));
//        }
//        Claims claims = jwtService.extractAllClaims(token);
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
//            if (jwtService.validateToken(token,userDetails)){
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource()
//                        .buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request,response);
//
//    }
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = jwtService.extractUsername(token);
        logger.info("Authorization Header: " + request.getHeader("Authorization"));
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if (jwtService.validateToken(token)) {
            Claims claims = jwtService.extractAllClaims(token);

            // Extract roles from the claims
            List<SimpleGrantedAuthority> authorities = ((List<Map<String, String>>) claims.get("roles")).stream()
                    .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                    .toList();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.info("Extracted Username: " + username);
            logger.info("Extracted Roles: " + claims.get("roles"));
        }
    }

    filterChain.doFilter(request, response);
}

}
