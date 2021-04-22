package dev.leonardolemos.backendchallenge.controller;

import dev.leonardolemos.backendchallenge.model.Order;
import dev.leonardolemos.backendchallenge.model.enumeration.OrderStatus;
import dev.leonardolemos.backendchallenge.repository.OrderRepository;
import dev.leonardolemos.backendchallenge.service.OrderService;
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

@Controller("/orders")
@Validated
public class OrderController {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderService orderService;

    @Get("{?pageable*}")
    @Operation(operationId = "listAllOrders", summary = "Return a list of all registered Orders", description = "Order listing")
    @ApiResponse(description = "A Page with Orders and Page metadata", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid query criteria", responseCode = "400")
    @Tag(name = "Order")
    public Iterable<Order> listAll(
            @Parameter(description = "Query, Paging and Sorting Criteria") Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Get("/{id}")
    @Operation(operationId = "findOrderById", summary = "Return an Order by it's ID", description = "Order retrieving")
    @ApiResponse(description = "An Order", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid ID supplied", responseCode = "400")
    @Tag(name = "Order")
    public Order findById(@Parameter(description = "An Order ID") @Min(1L) long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Post
    @Operation(operationId = "saveOrder", summary = "Saves an Order", description = "Order saving")
    @ApiResponse(description = "An Order with a valid ID", responseCode = "201", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid Order supplied", responseCode = "400")
    @Tag(name = "Order")
    public HttpResponse<Order> save(@RequestBody(description = "An Order") @Valid @Body Order order) {
        final Order savedOrder = orderService.saveOrder(order.getProducts(), order.getConsumer(), order.getPayment(), order.getDelivery());

        return HttpResponse
                .created(savedOrder)
                .header(HttpHeaders.LOCATION, location(savedOrder.getId()).getPath());
    }

    @Put("/{id}/cancel")
    @Operation(operationId = "cancelOrder", summary = "Cancel an Order by changing it's status to 'cancelled'", description = "Order cancelling")
    @ApiResponse(description = "No content", responseCode = "203", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid ID supplied", responseCode = "400")
    @Tag(name = "Order")
    public HttpResponse cancel(@Parameter(description = "An Order ID") @Min(1L) long id) {
        orderRepository.update(id, OrderStatus.CANCELLED);

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(id).getPath());
    }

    @Put("/{id}/confirm")
    @Operation(operationId = "confirmOrder", summary = "Confirms an Order by changing it's status to 'confirmed'", description = "Order confirming")
    @ApiResponse(description = "No content", responseCode = "203", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "Invalid ID supplied", responseCode = "400")
    @Tag(name = "Order")
    public HttpResponse confirm(@Parameter(description = "An Order ID") @Min(1L) long id) {

        orderService.confirmOrder(id);

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(id).getPath());
    }

    protected URI location(Long id) {
        return URI.create("/orders/" + id);
    }
}
