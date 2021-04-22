package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;

@Repository
public abstract class ManufacturerRepository implements PageableRepository<Manufacturer, Long> {
    public abstract Optional<Manufacturer> findByName(String name);
}
