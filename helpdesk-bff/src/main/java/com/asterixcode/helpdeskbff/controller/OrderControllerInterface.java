package com.asterixcode.helpdeskbff.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import models.exceptions.StandardError;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order Controller", description = "Controller responsible for managing orders")
@RequestMapping("/api/v1/orders")
public interface OrderControllerInterface {

  @Operation(summary = "Find order by ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECHNICIAN')")
  @GetMapping("/{id}")
  ResponseEntity<OrderResponse> findById(
      @NotNull(message = "Order ID must be informed")
          @Parameter(description = "Order ID", required = true, example = "2")
          @PathVariable(name = "id")
          final Long id);

  @Operation(summary = "Find all orders")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orders found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECHNICIAN')")
  @GetMapping
  ResponseEntity<List<OrderResponse>> findAll();

  @Operation(summary = "Create new order")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Order created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECHNICIAN')")
  @PostMapping
  ResponseEntity<Void> save(@Valid @RequestBody final CreateOrderRequest request);

  @Operation(summary = "Update order")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Order updated"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECHNICIAN')")
  @PutMapping("/{id}")
  ResponseEntity<OrderResponse> update(
      @Parameter(description = "Order ID", required = true, example = "2")
          @PathVariable(name = "id")
          Long id,
      @Parameter(description = "Update order request", required = true) @Valid @RequestBody
          final UpdateOrderRequest request);

  @Operation(summary = "Delete order")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Order deleted"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECHNICIAN')")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(
      @NotNull(message = "Order ID must be informed")
          @Parameter(description = "Order ID", required = true, example = "2")
          @PathVariable(name = "id")
          final Long id);

  @Operation(summary = "Find all orders paginated")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orders found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
      })
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECHNICIAN')")
  @GetMapping("/paginated")
  ResponseEntity<Page<OrderResponse>> findAllPaginated(
      @Parameter(description = "Page number", required = true, example = "0")
          @RequestParam(name = "page", defaultValue = "0")
          final Integer page,
      @Parameter(description = "Lines per page", required = true, example = "12")
          @RequestParam(name = "linesPerPage", defaultValue = "12")
          final Integer linesPerPage,
      @Parameter(description = "Order direction", required = true, example = "ASC")
          @RequestParam(name = "direction", defaultValue = "ASC")
          final String direction,
      @Parameter(description = "Order by field", required = true, example = "id")
          @RequestParam(name = "orderBy", defaultValue = "id")
          final String orderBy);
}
