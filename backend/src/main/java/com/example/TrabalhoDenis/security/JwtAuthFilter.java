package com.example.TrabalhoDenis.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Todas as requisições a api passam por aqui pra validar o token
 * Fluxo do código:
 *   1. Pega o header/cabeçalho que vai receber do fetch (Authorization: Bearer <token>)
 *   2. Verifica e valida o token, feito lá no JwtUtil
 *   3. Carrega o usuário do banco de dados
 *   4. Registra a autenticação no SecurityContext do Spring
 * A classe usa OncePerRequestFilter, isso garante que o filtro é executado apenas uma vez por requisição */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        // Lê o header "Authorization" da requisição
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Verifica se o header existe e começa com "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Remove o prefixo "Bearer " para ficar apenas com o token
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extrairEmail(token);
            } catch (Exception e) {
                // Token inválido/expirado: continua sem autenticar (retorna 401 mais adiante)
                logger.warn("Token JWT inválido: " + e.getMessage());
            }
        }

        // Se temos o email e ainda não há autenticação no contexto atual
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Valida o token contra os dados do usuário
            if (jwtUtil.validarToken(token, userDetails)) {
                // Cria o objeto de autenticação do Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // roles do usuário / credentials (não necessárias após autenticação));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Registra a autenticação no SecurityContext (válido para a thread atual)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response); // Passa para o próximo filtro na cadeia
    }
}
