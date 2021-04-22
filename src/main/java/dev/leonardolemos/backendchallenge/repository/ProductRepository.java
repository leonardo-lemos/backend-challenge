package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Product;
import dev.leonardolemos.backendchallenge.model.dto.ProductDTO;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class ProductRepository implements PageableRepository<Product, Long> {
    public List<ProductDTO> listAllProductDTO(Pageable pageable) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        Iterable<Product> products = findAll(pageable);

        for (Product p : products) {
            ProductDTO dto = new ProductDTO(p.getId(), p.getName());
            productDTOList.add(dto);
        }

        return productDTOList;
    }
}
