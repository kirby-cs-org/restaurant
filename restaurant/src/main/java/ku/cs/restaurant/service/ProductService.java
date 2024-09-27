package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Product;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.Status;
import ku.cs.restaurant.repository.ProductRepository; // Assuming you have a repository
import ku.cs.restaurant.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    // สร้างเมนูใหม่ (insert) C
    public Product createMenu(Product product) {
        return productRepository.save(product);
    }

    // ดูตามสถานะ
    public List<Product> getByStatus(Status status) {
        return productRepository.findByStatus(status);
    }

    // ดูทั้งหมด
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // update จำนวนเมนู (update) U
    public Optional<Product> updateMenu(UUID id, int amount) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            int newQty = product.getQty() + amount;

            if (newQty < 0) newQty = 0;

            product.setQty(newQty);

            if (newQty > 0)
                product.setStatus(Status.AVAILABLE);
            else
                product.setStatus(Status.OUT_OF_STOCK);

            productRepository.save(product);
            return Optional.of(product);
        }

        return Optional.empty();
    }

    // ลบอาหาร (delete) D
    public void deleteMenu(UUID id) {
        productRepository.deleteById(id);
    }
}
