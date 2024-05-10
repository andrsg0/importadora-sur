package com.abcg.service;

import com.abcg.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Product save(Product product);
    public Optional<Product> get(Integer id);
    public void update(Product product);
    public void delete(Integer id);
    public List<Product> findAll();
}
