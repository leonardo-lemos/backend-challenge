package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Order;
import dev.leonardolemos.backendchallenge.model.enumeration.OrderStatus;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;

@Repository
public abstract class OrderRepository implements PageableRepository<Order, Long> {

    public abstract int update(@Id Long id, OrderStatus status);

    public abstract Optional<OrderStatus> findStatusById(Long id);
}
