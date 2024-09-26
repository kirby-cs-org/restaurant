package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.product.ProductDeleteDto;
import ku.cs.restaurant.dto.product.ProductQtyUpdateDto;
import ku.cs.restaurant.dto.product.ProductStatusUpdateDto;
import ku.cs.restaurant.entity.Product;
import ku.cs.restaurant.entity.ProductStatus;
import ku.cs.restaurant.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    // สร้างเมนูใหม่ (insert) C
    @PostMapping("/product")
    public ResponseEntity<Product> createMenu(@RequestBody Product product) {
        try {
            Product createdProduct = service.createMenu(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูเมนู (select) R
    // ดูตามสถานะ
    @GetMapping("/product/status/{status}")
    public ResponseEntity<List<Product>> getByStatus(@PathVariable String status) {
        try {
            ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
            List<Product> products = service.getByStatus(productStatus);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูทั้งหมด
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = service.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // เพิ่มจำนวนเมนู (update) U
    @PatchMapping("/product/increase")
    public ResponseEntity<Product> increaseMenu(@RequestBody ProductQtyUpdateDto updateDto) {
        try {
            Optional<Product> updatedProduct = service.increaseMenu(updateDto.getId(), updateDto.getQty());
            return updatedProduct.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ลดจำนวนเมนู (update) U
    @PatchMapping("/product/decrease")
    public ResponseEntity<Product> decreaseMenu(@RequestBody ProductQtyUpdateDto updateDto) {
        try {
            Optional<Product> updatedProduct = service.decreaseMenu(updateDto.getId(), updateDto.getQty());
            return updatedProduct.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ลบอาหาร (delete) D
    @DeleteMapping("/product")
    public ResponseEntity<Void> deleteMenu(@RequestBody ProductDeleteDto deleteDto) {
        try {
            service.deleteMenu(deleteDto.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
