package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.Status;
import ku.cs.restaurant.repository.FoodRepository; // Assuming you have a repository
import ku.cs.restaurant.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // สร้างเมนูใหม่ (insert) C
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    // ดูตามสถานะ
    public List<Food> getFoodsByStatus(Status status) {
        return foodRepository.findFoodsByStatus(status);
    }

    // ดุจากไอดี
    public Optional<Food> getFoodById(UUID id) {
        return foodRepository.findFoodById(id);
    }

    // ดูทั้งหมด
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // ลบอาหาร (delete) D
    public void deleteFoodById(UUID id) {
        foodRepository.deleteById(id);
    }
}
