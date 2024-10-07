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
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest orderRequest,
                                                       @RequestHeader("Authorization") String jwt) {
        try {
            // Extract the username from the JWT
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            Optional<User> optionalUser = userService.getUserByUsername(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Check if there are food orders
                List<FoodOrder> foodOrders = orderRequest.getFoods();
                if (foodOrders.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                // Check if the ingredients are sufficient
                for (FoodOrder foodOrder : foodOrders) {
                    UUID foodId = foodOrder.getFood().getId(); // Get food ID
                    Optional<Food> optionalFood = foodService.getFoodById(foodId); // Use findById to fetch food

                    if (optionalFood.isPresent()) {
                        Food food = optionalFood.get();
                        List<Recipe> recipes = food.getRecipes(); // Fetch the recipes for the food item

                        if (recipes.isEmpty()) {
                            System.out.println("No recipes found for food: " + foodId);
                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                        }

                        for (Recipe recipe : recipes) {
                            int ingredientRequiredQty = foodOrder.getQuantity() * recipe.getQty();
                            int ingredientAvailableQty = recipe.getIngredient().getQty();

                            // Check if there are enough ingredients
                            if (ingredientRequiredQty > ingredientAvailableQty) {
                                System.out.println("Insufficient ingredient: " + recipe.getIngredient().getId() +
                                        " | Required: " + ingredientRequiredQty + ", Available: " + ingredientAvailableQty);
                                return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
                            }
                        }
                    } else {
                        System.out.println("Food not found for ID: " + foodId);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }

                // Create the receipt and order if all checks pass
                Receipt receipt = receiptService.createReceipt(orderRequest.calculateTotal());
                Order createdOrder = orderService.createOrder(orderRequest.calculateTotal(), user, receipt);

                for (FoodOrder foodOrder : foodOrders) {
                    // Create order lines
                    orderLineService.createOrderLine(foodOrder.getQuantity(), createdOrder, foodOrder.getFood());

                    // Update ingredient quantities after the order is created
                    UUID foodId = foodOrder.getFood().getId();
                    Optional<Food> optionalFood = foodService.getFoodById(foodId);

                    if (optionalFood.isPresent()) {
                        Food food = optionalFood.get();
                        List<Recipe> recipes = food.getRecipes();
                        for (Recipe recipe : recipes) {
                            Ingredient ingredient = recipe.getIngredient();
                            int ingredientUsedAmount = foodOrder.getQuantity() * recipe.getQty();

                            // Update the ingredient quantity
                            ingredientService.updateQty(ingredient.getId(), ingredient.getQty() - ingredientUsedAmount);
                        }
                    }
                }

                // Create payment link
                PaymentResponse response = paymentService.createPaymentLink(createdOrder);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // ดูทั้งหมด
    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.findOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/{id}/user")
    public ResponseEntity<UserResponse> getUserByOrderId(@PathVariable("id") UUID id) {
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

            return new ResponseEntity<>(userResponse, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //แสดงใบเสร็จ
    @GetMapping("/order/{id}/receipt")
    public ResponseEntity<Receipt> getReceiptByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            Receipt receipt = order.getReceipt();
            return new ResponseEntity<>(receipt, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/{id}/food")
    public ResponseEntity<FoodListDto> getFoodByOrderId(@PathVariable("id") UUID id) {
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

            return ResponseEntity.ok(foodListDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ดูตามสถานะ
    @GetMapping("/order/status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestBody OrderStatus status) {
        try {
            List<Order> orders = orderService.findOrderByStatus(status);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // อัพเดทสถานะ
    @PatchMapping("/order")
    public ResponseEntity<Order> updateOrderStatusById(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Order> optionalUpdatedOrder = orderService.updateOrderStatusById(request.getId(),
                    request.getStatus());
            if (optionalUpdatedOrder.isPresent()) {
                Order updatedOrder = optionalUpdatedOrder.get();
                return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
