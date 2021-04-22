package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Consumer;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public abstract class ConsumerRepository implements CrudRepository<Consumer, Long> {
}
