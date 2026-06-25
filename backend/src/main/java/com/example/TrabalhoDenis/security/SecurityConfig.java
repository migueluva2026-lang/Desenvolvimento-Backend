//
// Código Feito por: Policarpo
//
// Arquivo principal de configuração de segurança do Spring Security
// Define Rotas públicas (sem autenticação) vs. protegidas
// Política Stateless: (sem cookies, já que estamos usando JWT)
// Filtro personalizado para token
// Codificador de senha BCrypt
// CORS para o frontend (De onde isso pode ser chamado, evita alguns GETs por exemplo, usa bastante POST)

package com.example.TrabalhoDenis.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

 @RequiredArgsConstructor // Lombok (facilita o DI)
 @Configuration
 @EnableWebSecurity
 @EnableMethodSecurity
 public class SecurityConfig {
     private final JwtAuthFilter jwtAuthFilter;
     private final UsuarioDetailsService usuarioDetailsService;

    // Configura CORS para permitir chamadas do frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));

        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

     // Define as regras de autorização HTTP e a cadeia de filtros.
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
             // Desativa Tokens padrão "CSRF" por não precisar mais já que temos JWT (sem cookies de sessão)
             .csrf(csrf -> csrf.disable())

             // Configura CORS com as origens permitidas
             .cors(cors -> cors.configurationSource(corsConfigurationSource()))

             // Define quais rotas são públicas e quais precisam de autenticação
             .authorizeHttpRequests(auth -> auth

                     // Rotas públicas, qualquer um pode acessar
                     .requestMatchers("/api/auth/**").permitAll()
                     .requestMatchers("/h2-console/**").permitAll() // Dev only

                     // GET em produtos e categorias é público
                     .requestMatchers(HttpMethod.GET, "/api/produtos/**").permitAll()
                     .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()

                     // Operações de escrita exigem autenticação
                     .anyRequest().authenticated()
             )

             // Política "Stateless": sem sessões no servidor, cada requisição deve ter JWT
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

             // Registra o nosso filtro JWT ANTES do filtro padrão de usuario/senha
             .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

             // Provider que integra o nosso UserDetailsService com o Spring
             .authenticationProvider(authenticationProvider());

         return http.build();
     }

    // Liga o UserDetailsService + PasswordEncoder
    // O Spring usa isso para verificar email/senha no login.
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // BCrypt é o algoritmo padrão para hash de senhas
    // Força 10 (2^10 = 1024 iterações de hash), deve ser bastante coisa
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(10);
    }

    // É usado no AuthController para autenticar o login.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
    }
}
