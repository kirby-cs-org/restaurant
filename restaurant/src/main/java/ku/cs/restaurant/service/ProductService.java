package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Product;
import ku.cs.restaurant.entity.ProductStatus;
import ku.cs.restaurant.repository.ProductRepository; // Assuming you have a repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository; // Assuming you have a ProductRepository

    // สร้างเมนูใหม่ (insert) C
    public Product createMenu(Product product) {
        return productRepository.save(product);
    }

    // ดูเมนู (select) R
    // ดูตามสถานะ
    public List<Product> getByStatus(ProductStatus status) {
        return productRepository.findByStatus(status); // Implement this in your repository
    }

    // ดูทั้งหมด
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // เพิ่มจำนวนเมนู (update) U
    public Optional<Product> increaseMenu(UUID id, int amount) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setQty(product.getQty() + amount);

            if (product.getQty() > 0 && product.getStatus() == ProductStatus.OUT_OF_STOCK) {
                product.setStatus(ProductStatus.AVAILABLE);
            }

            productRepository.save(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    // ลดจำนวนเมนู (update) U
    public Optional<Product> decreaseMenu(UUID id, int amount) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int newQuantity = product.getQty() - amount;
            if (newQuantity >= 0) {
                product.setQty(newQuantity);

                if (newQuantity == 0) {
                    product.setStatus(ProductStatus.OUT_OF_STOCK);
                }

                productRepository.save(product);
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    // ลบอาหาร (delete) D
    public void deleteMenu(UUID id) {
        productRepository.deleteById(id);
    }
}
