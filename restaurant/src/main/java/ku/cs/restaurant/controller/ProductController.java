package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.product.ProductDeleteDto;
import ku.cs.restaurant.dto.product.ProductQtyUpdateDto;
import org.springframework.web.multipart.MultipartFile;
import ku.cs.restaurant.entity.Product;
import ku.cs.restaurant.entity.Status;
import ku.cs.restaurant.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    // สร้างเมนูใหม่ (insert) C
    @PostMapping("/product")
    public ResponseEntity<Product> createMenu(@RequestPart("product") Product product, @RequestPart("image") MultipartFile image) {
        try {
            String imagePath = saveImage(image);
            product.setImagePath(imagePath); // set path ในผลิตภัณฑ์

            Product createdProduct = service.createMenu(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String FOLDER_PATH = "src/main/resources/images";
        // สร้างไดเรกทอรีถ้ายังไม่มี
        File dir = new File(FOLDER_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // ตั้งชื่อไฟล์
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(FOLDER_PATH, fileName);
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return path.toString(); // ส่งคืนเส้นทางของภาพที่จัดเก็บ
    }

    // ดูเมนู (select) R
    // ดูตามสถานะ
    @GetMapping("/product/status/{status}")
    public ResponseEntity<List<Product>> getByStatus(@PathVariable String status) {
        try {
            Status productStatus = Status.valueOf(status.toUpperCase());
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
        String imageBaseUrl = "http://localhost:8080/images/";

        for (Product product : products) {
            String imagePath = product.getImagePath().replace("\\", "/");

            String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);

            product.setImagePath(imageBaseUrl + fileName);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Update จำนวนเมนู (update) U
    @PatchMapping("/product")
    public ResponseEntity<Product> increaseMenu(@RequestBody ProductQtyUpdateDto updateDto) {
        try {
            Optional<Product> updatedProduct = service.updateMenu(updateDto.getId(), updateDto.getQty());
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
