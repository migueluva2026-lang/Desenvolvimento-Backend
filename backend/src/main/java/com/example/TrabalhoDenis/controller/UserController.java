package com.example.TrabalhoDenis.controller;

import com.example.TrabalhoDenis.model.User;
import com.example.TrabalhoDenis.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController // Controller define um endpoint e as suas rotas. Nesse caso a rota é /api/users e seus métodos são Get, Post, Put e Delete.
{
    private final UserService userService;

    @GetMapping
    public List<User> listAll() // Ao chamar a API pelo /api/users/ com o header GET, vem pra essa função
    {
        return userService.listAll(); // Chama a função listAll do userService, onde tem a lógica
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) // GET, só que quando se passa um ID ele chama essa, logo seu retorno é diferente
    {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user)  // POST -> Cria algo
    {
        return ResponseEntity.status(201).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) // PUT -> Atualiza algo (sobreesrevendo)
    {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) // Delete
    {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}