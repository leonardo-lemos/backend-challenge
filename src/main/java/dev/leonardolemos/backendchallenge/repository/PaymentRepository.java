package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Payment;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public abstract class PaymentRepository implements CrudRepository<Payment, Long> {
}
