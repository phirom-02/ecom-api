package com.firom.ecom_api.security;

import com.firom.ecom_api.util.HttpRequestPropertiesUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// TODO: implement stricter token checking
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService
//            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = customUserDetailsService;
//        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

//        try {
            final String jwtToken = HttpRequestPropertiesUtils.extractBearerToken(request);
            if (jwtToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            final String username = jwtService
                    .extractUsername(jwtToken);
            final Authentication currentAuth = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            if (username != null && currentAuth == null) {
                authenticateUser(username, jwtToken, request);
            }

            filterChain.doFilter(request, response);
//        } catch (Exception exception) {
//            handlerExceptionResolver.resolveException(request, response, null, exception);
//        }
    }

    private void authenticateUser(String username, String token, HttpServletRequest request) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);
        }
    }
}
