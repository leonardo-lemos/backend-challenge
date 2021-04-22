package dev.leonardolemos.backendchallenge.service;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import dev.leonardolemos.backendchallenge.model.Product;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import dev.leonardolemos.backendchallenge.repository.ProductRepository;

import javax.inject.Inject;

public class ProductService {
    @Inject
    ProductRepository productRepository;
    @Inject
    ManufacturerRepository manufacturerRepository;

    public Product save(Product product) {
        Manufacturer manufacturer = manufacturerRepository.findById(product.getManufacturer().getId()).orElse(null);

        if (manufacturer == null) {
            throw new RuntimeException("Cannot save the Product with name '" + product.getName() + "': there's no Manufacturer with id '" + product.getManufacturer().getId() + "'.");
        }

        try {
            productRepository.save(product);

            return product;
        } catch (Exception e) {
            throw new RuntimeException("Cannot save the Product with name '" + product.getName() + "': '" + e.getMessage() + "'.");
        }
    }
}
