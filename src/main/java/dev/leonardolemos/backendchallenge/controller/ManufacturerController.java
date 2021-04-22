package dev.leonardolemos.backendchallenge.controller;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.core.HttpHeaders;
import java.net.URI;

@Controller("/manufacturers")
@Validated
public class ManufacturerController {

    @Inject
    ManufacturerRepository manufacturerRepository;

    @Get("{?pageable*}")
    @Operation(operationId = "listAllManufacturers", summary = "Return a list of all registered Manufacturers", description = "Manufacturer listing")
    @ApiResponse(description = "A Page with Manufacturers and Page metadata", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid query criteria", responseCode = "400")
    @Tag(name = "Manufacturer")
    public Page<Manufacturer> listAll(
            @Parameter(description = "Query, Paging and Sorting Criteria") Pageable pageable) {
        return manufacturerRepository.findAll(pageable);
    }

    @Get("/{id}")
    @Operation(operationId = "findManufacturerById", summary = "Return a Manufacturer by it's ID", description = "Manufacturer retrieving")
    @ApiResponse(description = "A Manufacturer", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid ID supplied", responseCode = "400")
    @Tag(name = "Manufacturer")
    public Manufacturer getById(@Parameter(description = "A Manufacturer ID") @Min(1L) long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Post
    @Operation(operationId = "saveManufacturer", summary = "Saves a Manufacturer", description = "Manufacturer saving")
    @ApiResponse(description = "A Manufacturer with a valid ID", responseCode = "201", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid Manufacturer supplied", responseCode = "400")
    @Tag(name = "Manufacturer")
    public HttpResponse<Manufacturer> save(@RequestBody(description = "A Manufacturer") @Valid @Body Manufacturer manufacturer) {
        final Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);

        return HttpResponse
                .created(savedManufacturer, location(savedManufacturer.getId()));
    }

    protected URI location(Long id) {
        return URI.create("/manufacturers/" + id);
    }
}
