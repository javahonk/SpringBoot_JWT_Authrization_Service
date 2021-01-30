package com.digital.realty.jwt.config;

import com.digital.realty.jwt.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${health.check.token}")
    private String healthToken;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return (path.startsWith("/health") || path.startsWith("/info")
                || path.startsWith("/metrics") || path.startsWith("/logfile")
                || path.startsWith("/env") || path.startsWith("/liquibase")
                || path.startsWith("/dump") || path.startsWith("/jolokia")
                || path.startsWith("/auditevents") || path.startsWith("/flyway")
                || path.startsWith("/heapdump") || path.startsWith("/trace") || path.startsWith("/testGet"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //final String requestTokenHeader = request.getHeader("Authorization");
        String requestTokenHeader = request.getHeader("Authorization");

        /*String path = request.getServletPath();
        if (path.startsWith("/health") || path.startsWith("/info")
                || path.startsWith("/metrics") || path.startsWith("/logfile")
                || path.startsWith("/env") || path.startsWith("/liquibase")
                || path.startsWith("/dump") || path.startsWith("/jolokia")
                || path.startsWith("/auditevents") || path.startsWith("/flyway")
                || path.startsWith("/heapdump") || path.startsWith("/trace")){
                //requestTokenHeader = healthToken;
        }*/

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null) {
            jwtToken = request.getHeader("Authorization");
            try {
                username = tokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                //throw e;
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                //throw e;
            } catch (Exception exception) {
                exception.printStackTrace();
                try {
                    throw exception;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            logger.warn("Request does have token");
        }

        //Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            try {
                if (tokenUtil.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                try {
                    throw exception;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request, response);
    }

}
