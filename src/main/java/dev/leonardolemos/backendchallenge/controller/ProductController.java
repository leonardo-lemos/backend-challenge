package dev.leonardolemos.backendchallenge.controller;

import dev.leonardolemos.backendchallenge.model.Product;
import dev.leonardolemos.backendchallenge.model.dto.ProductDTO;
import dev.leonardolemos.backendchallenge.repository.ProductRepository;
import dev.leonardolemos.backendchallenge.service.ProductService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
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
import java.util.List;

@Controller("/products")
@Validated
public class ProductController {

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductService productService;

    @Get("{?pageable*}")
    @Operation(operationId = "listAllProducts", summary = "Return a list of all registered Products", description = "Product listing")
    @ApiResponse(description = "A Page with Orders and Page metadata", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid query criteria", responseCode = "400")
    @Tag(name = "Product")
    public List<ProductDTO> listAll(
            @Parameter(description = "Query, Paging and Sorting Criteria") Pageable pageable) {
        return productRepository.listAllProductDTO(pageable);
    }

    @Get("/{id}")
    @Operation(operationId = "findProductById", summary = "Return a Product by it's ID", description = "Product retrieving")
    @ApiResponse(description = "A Product", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid ID supplied", responseCode = "400")
    @Tag(name = "Product")
    public Product findById(@Parameter(description = "A Product ID") @Min(1L) long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Post
    @Operation(operationId = "saveProduct", summary = "Saves a Product", description = "Product saving")
    @ApiResponse(description = "A Product with a valid ID", responseCode = "201", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid Product supplied", responseCode = "400")
    @Tag(name = "Product")
    public HttpResponse<Product> save(@RequestBody(description = "A Product") @Valid @Body Product product) {
        final Product savedProduct = productService.save(product);

        return HttpResponse
                .created(savedProduct)
                .header(HttpHeaders.LOCATION, location(savedProduct.getId()).getPath());
    }

    @Put
    @Operation(operationId = "updateProduct", summary = "Updates a Product", description = "Product updating")
    @ApiResponse(description = "A updated Product", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid Product supplied", responseCode = "400")
    @Tag(name = "Product")
    public HttpResponse update(@RequestBody(description = "A Product") @Valid @Body Product product) {
        try {
            product = productRepository.update(product);

            return HttpResponse
                    .ok(product)
                    .header(HttpHeaders.LOCATION, location(product.getId()).getPath());
        } catch (Exception e) {
            return HttpResponse.serverError("Cannot save Product with name: ' " + product.getName() + "' because: '" + e.getMessage() + "'.");
        }
    }

    @Delete("/{id}")
    @Operation(operationId = "deleteProductById", summary = "Delete a Product by it's ID", description = "product confirming")
    @ApiResponse(description = "No content", responseCode = "203", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid ID supplied", responseCode = "400")
    @Tag(name = "Product")
    public HttpResponse delete(@Parameter(description = "A Product ID") @Min(1L) long id) {
        try {
            productRepository.deleteById(id);

            return HttpResponse.noContent();
        } catch (Exception e) {
            return HttpResponse.serverError("Cannot save Product with id: ' " + id + "' because: '" + e.getMessage() + "'.");
        }
    }

    protected URI location(Long id) {
        return URI.create("/products/" + id);
    }
}
