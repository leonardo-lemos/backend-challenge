package dev.leonardolemos.backendchallenge.repository;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

@Repository
public abstract class ManufacturerRepository implements PageableRepository<Manufacturer, Long> {
}
