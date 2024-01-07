package br.com.vendas.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.vendas.service.impl.UsuarioServiceImpl;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioServiceImpl userServiceImple;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            String isValid = jwtService.tokenValido(token);

            if(!isValid.isEmpty()) {
                String userLogin = jwtService.tokenValido(token);
                UserDetails user = userServiceImple.loadUserByUsername(userLogin);
                UsernamePasswordAuthenticationToken userPassword = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                userPassword.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userPassword);
            }
        }
        filterChain.doFilter(request,response);
    }


}
