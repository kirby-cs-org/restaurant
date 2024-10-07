package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.Payment.PaymentResponse;
import ku.cs.restaurant.dto.food.FoodDto;
import ku.cs.restaurant.dto.food.FoodListDto;
import ku.cs.restaurant.dto.order.FoodOrder;
import ku.cs.restaurant.dto.order.OrderRequest;
import ku.cs.restaurant.dto.order.UpdateStatusRequest;
import ku.cs.restaurant.dto.order.UserResponse;
import ku.cs.restaurant.entity.*;
import ku.cs.restaurant.service.*;
import ku.cs.restaurant.utils.JwtUtils;
import ku.cs.restaurant.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final ReceiptService receiptService;
    private final JwtUtils jwtUtils;
    private final OrderLineService orderLineService;
    private final IngredientService ingredientService;
    private final FoodService foodService;

    public OrderController(OrderService orderService, PaymentService paymentService, UserService userService,
                           JwtUtils jwtUtils, ReceiptService receiptService, OrderLineService orderLineService,
                           IngredientService ingredientService, FoodService foodService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.receiptService = receiptService;
        this.orderLineService = orderLineService;
        this.ingredientService = ingredientService;
        this.foodService = foodService;
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<PaymentResponse>> createOrder(@RequestBody OrderRequest orderRequest,
                                                                    @RequestHeader("Authorization") String jwt) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            Optional<User> optionalUser = userService.getUserByUsername(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<FoodOrder> foodOrders = orderRequest.getFoods();

                if (foodOrders.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(new ApiResponse<>(false, "No food orders provided.", null));
                }

                for (FoodOrder foodOrder : foodOrders) {
                    UUID foodId = foodOrder.getFood().getId();
                    Optional<Food> optionalFood = foodService.getFoodById(foodId);

                    if (optionalFood.isPresent()) {
                        Food food = optionalFood.get();
                        List<Recipe> recipes = food.getRecipes();

                        if (recipes.isEmpty()) {
                            return ResponseEntity.badRequest()
                                    .body(new ApiResponse<>(false, "No recipes found for food: " + foodId, null));
                        }

                        for (Recipe recipe : recipes) {
                            int ingredientRequiredQty = foodOrder.getQuantity() * recipe.getQty();
                            int ingredientAvailableQty = recipe.getIngredient().getQty();

                            if (ingredientRequiredQty > ingredientAvailableQty) {
                                return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE)
                                        .body(new ApiResponse<>(false, "Insufficient ingredient: " + recipe.getIngredient().getId() +
                                                " | Required: " + ingredientRequiredQty + ", Available: " + ingredientAvailableQty, null));
                            }
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<>(false, "Food not found for ID: " + foodId, null));
                    }
                }

                Receipt receipt = receiptService.createReceipt(orderRequest.calculateTotal());
                Order createdOrder = orderService.createOrder(orderRequest.calculateTotal(), user, receipt);

                for (FoodOrder foodOrder : foodOrders) {
                    orderLineService.createOrderLine(foodOrder.getQuantity(), createdOrder, foodOrder.getFood());
                    UUID foodId = foodOrder.getFood().getId();
                    Optional<Food> optionalFood = foodService.getFoodById(foodId);

                    if (optionalFood.isPresent()) {
                        Food food = optionalFood.get();
                        List<Recipe> recipes = food.getRecipes();
                        for (Recipe recipe : recipes) {
                            Ingredient ingredient = recipe.getIngredient();
                            int ingredientUsedAmount = foodOrder.getQuantity() * recipe.getQty();
                            ingredientService.updateQty(ingredient.getId(), ingredient.getQty() - ingredientUsedAmount);
                        }
                    }
                }

                PaymentResponse response = paymentService.createPaymentLink(createdOrder);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponse<>(true, "Order created successfully.", response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/order")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        try {
            List<Order> orders = orderService.findOrders();
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched successfully.", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/order/{id}/user")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            User user = userService.getUserById(order.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setPhone(user.getPhone());
            userResponse.setRole(user.getRole());

            return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully.", userResponse));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/order/{id}/receipt")
    public ResponseEntity<ApiResponse<Receipt>> getReceiptByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            Receipt receipt = order.getReceipt();
            return ResponseEntity.ok(new ApiResponse<>(true, "Receipt fetched successfully.", receipt));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/order/{id}/food")
    public ResponseEntity<ApiResponse<FoodListDto>> getFoodByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            List<OrderLine> orderLines = order.getOrderLines();
            FoodListDto foodListDto = new FoodListDto();

            List<FoodDto> foodDtos = orderLines.stream()
                    .map(ol -> {
                        FoodDto foodDto = new FoodDto();
                        foodDto.setFood(ol.getFood());
                        foodDto.setQty(ol.getQty());
                        return foodDto;
                    })
                    .collect(Collectors.toList());

            foodListDto.setFoods(foodDtos);

            return ResponseEntity.ok(new ApiResponse<>(true, "Foods fetched successfully.", foodListDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @GetMapping("/order/status")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByStatus(@RequestBody OrderStatus status) {
        try {
            List<Order> orders = orderService.findOrderByStatus(status);
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched successfully.", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PatchMapping("/order")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatusById(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Order> optionalUpdatedOrder = orderService.updateOrderStatusById(request.getId(),
                    request.getStatus());
            if (optionalUpdatedOrder.isPresent()) {
                Order updatedOrder = optionalUpdatedOrder.get();
                return ResponseEntity.ok(new ApiResponse<>(true, "Order status updated successfully.", updatedOrder));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Order not found.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}
