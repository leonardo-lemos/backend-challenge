package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.ProductOrder;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public abstract class ProductOrderRepository implements CrudRepository<ProductOrder, Long> {
}
