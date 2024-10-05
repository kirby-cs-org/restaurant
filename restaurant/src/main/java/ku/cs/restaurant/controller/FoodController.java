package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.food.FoodDeleteDto;
import org.springframework.web.multipart.MultipartFile;
import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.Status;
import ku.cs.restaurant.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    // สร้างเมนูใหม่
    @PostMapping("/foods")
    public ResponseEntity<Food> createMenu(@RequestPart("food") Food food, @RequestPart("image") MultipartFile image) {
        try {
            String imagePath = saveImage(image);
            food.setImagePath(imagePath); // set path ในผลิตภัณฑ์

            Food createdFood = service.createFood(food);
            return new ResponseEntity<>(createdFood, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String folderPath = "src/main/resources/images/foods";
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(folderPath, fileName);
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return path.toString();
    }

    // ดูตามสถานะ
    @GetMapping("/foods/status/{status}")
    public ResponseEntity<List<Food>> getByStatus(@PathVariable String status) {
        try {
            Status productStatus = Status.valueOf(status.toUpperCase());
            List<Food> foods = service.getFoodsByStatus(productStatus);
            return new ResponseEntity<>(foods, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูทั้งหมด
    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getAll() {
        List<Food> foods = service.getAllFoods();
        String imageBaseUrl = "http://localhost:8088/images/foods/";

        for (Food food : foods) {
            String imagePath = food.getImagePath().replace("\\", "/");

            String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);

            food.setImagePath(imageBaseUrl + fileName);
        }

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // ลบอาหาร
    @DeleteMapping("/foods")
    public ResponseEntity<Void> deleteMenu(@RequestBody FoodDeleteDto deleteDto) {
        try {
            service.deleteFoodById(deleteDto.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
