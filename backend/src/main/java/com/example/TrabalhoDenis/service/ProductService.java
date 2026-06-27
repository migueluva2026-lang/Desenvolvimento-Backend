package com.example.TrabalhoDenis.service;

import com.example.TrabalhoDenis.messaging.EventPublisher;
import com.example.TrabalhoDenis.model.Product;
import com.example.TrabalhoDenis.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService { // Contem a lógica para um produto (O CRUD). Criar(Create/C), procurar (ler/R) atualizar(Put/U), excluir(Delete/D)

    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id)
    {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Product create(Product product) {
        Product saved = productRepository.save(product);
        eventPublisher.publish("CREATE", "PRODUCT", saved.getId(), "Produto criado: " + saved.getName());
        return saved;
    }

    public Product update(Long id, Product updated)
    {
        Product produto = findById(id);
        produto.setName(updated.getName());
        produto.setDescription(updated.getDescription());
        produto.setPrice(updated.getPrice());
        produto.setStockQuantity(updated.getStockQuantity());
        produto.setCategory(updated.getCategory());
        Product saved = productRepository.save(produto);
        eventPublisher.publish("UPDATE", "PRODUCT", saved.getId(), "Produto atualizado: " + saved.getName());
        return saved;
    }

    public void delete(Long id)
    {
        productRepository.deleteById(id);
        eventPublisher.publish("DELETE", "PRODUCT", id, "Produto deletado: id " + id);
    }
}