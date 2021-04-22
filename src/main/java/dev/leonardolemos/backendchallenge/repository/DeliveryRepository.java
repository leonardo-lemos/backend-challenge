package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Delivery;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public abstract class DeliveryRepository implements CrudRepository<Delivery, Long> {
}
