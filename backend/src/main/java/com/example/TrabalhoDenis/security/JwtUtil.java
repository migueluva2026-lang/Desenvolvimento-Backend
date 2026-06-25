//
// Código Feito por: Policarpo
// Gera e assina na criação, também verifica na validação
// JWT (JSON Web Token) é um padrão para transmitir informações entre partes, nesse caso a estrutura dele é um header, payload e uma assinatura

package com.example.TrabalhoDenis.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Estrutura do token: Header, Payload e Signature(assinatura)
// Header usa o algoritmo de assinatura HS256 (Vai ser passado pelo fetch)
// Payload: dados do usuário (claims): email, cargo/função (role) e expiração
// Assinatura: Garante que o token não foi alterado
// é gerada com uma chave secreta que só o servidor conhece, então ninguém pode forjar um token sem essa chave.

@Component
public class JwtUtil {

    // Segredo lido do application.properties para assinar os tokens
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /** Tempo de expiração em milissegundos (ex: 86400000 = 24h) */
    @Value("${app.jwt.expiration}")
    private Long jwtExpiration;

    // Gera um token JWT ao autenticar o usuário
    public String gerarToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().toString()); // Adiciona o papel (role) como claim extra no payload do token
        return criarToken(claims, userDetails.getUsername()); // Retorna um token como String
    }

    // Isso daqui monta o token gerado com um header, um payload e a assinatura
    private String criarToken(Map<String, Object> claims, String subject)
    {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)  // Email do usuário no campo subject (que seria sujeito)
                .setIssuedAt(agora) // Data de emissão
                .setExpiration(expiracao) // Data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinatura HMAC-SHA256
                .compact(); // Compacta a chave
    }

    // Converte o segredo String em uma chave criptografada segura
    private Key getSigningKey()
    {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extrai o email do token
    public String extrairEmail(String token)
    {
        return extrairClaims(token).getSubject();
    }

    // Valida o Token. Retorna true se for valido
    public boolean validarToken(String token, UserDetails userDetails)
    {
        String email = extrairEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpirado(token);
    }

    // Verifica se o token já expirou
    private boolean isTokenExpirado(String token)
    {
        return extrairClaims(token).getExpiration().before(new Date());
    }

    // Faz o parsing do token e retorna os claims (payload). Lança JwtException se o token for inválido ou expirado
    private Claims extrairClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
}
