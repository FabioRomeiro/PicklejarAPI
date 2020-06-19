package com.romeiro.picklejar.config.security;

import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenFilterAuthentication extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public TokenFilterAuthentication(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        boolean isValid = tokenService.isTokenValid(token);

        if (isValid) {
            authenticateClient(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateClient(String token) {
        Integer userId = tokenService.getUserId(token);
        User user = userRepository.findById(userId).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return tokenService.getFormattedToken(token);
    }
}
