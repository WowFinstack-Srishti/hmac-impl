package com.wowfinstack.hmac.filter;

import com.wowfinstack.hmac.service.HmacService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class HmacRequestFilter extends OncePerRequestFilter {

    @Autowired
    private HmacService hmacService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String signature = request.getHeader("Sign");
        if (signature == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing HMAC Signature");
            return;
        }

        String payload = request.getReader().lines().collect(Collectors.joining());
        if (!hmacService.isValidSignature(payload, signature)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid HMAC Signature");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
