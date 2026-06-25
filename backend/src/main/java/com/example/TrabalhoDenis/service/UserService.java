package com.example.TrabalhoDenis.service;

import com.example.TrabalhoDenis.model.User;
import com.example.TrabalhoDenis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // <--- Chama lombok

public class UserService { // O service é onde tem a lógica chamada pelo controller/endpoint.

    private final UserRepository userRepository; // Final é o tipo gerado pelo lombok que faz um constructor automático, muito convencional
    private final PasswordEncoder passwordEncoder;

    public List<User> listAll() { // Lista todos os <User> do DB (Isso tem em todos os Services)
        return userRepository.findAll(); // Usa a interface para se comunicar com o DB. essa é a função do Repository.
    }

    public User findById(Long id) { // Find By: Encontrar por (findBy é meio que uma convenção do spring para muitas coisas)
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User findByEmail(String email) { // FindBy Email
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public User save(User user) { // Chamado pelo POST, põe o usuário no DB
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void delete(Long id) { // Chamado pelo Delete, Deleta tal id do DB
        userRepository.deleteById(id);
    }

    public User update(Long id, User user) { // Chamado pelo PUT, ele atualiza informação
        User usuario = findById(id);
        usuario.setEmail(user.getEmail());
        usuario.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(usuario);
    }

}