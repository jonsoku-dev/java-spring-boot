package com.example.validationexample.controller;

import com.example.validationexample.entitiy.Product;
import com.example.validationexample.form.ProductForm;
import com.example.validationexample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody() ProductForm productForm) {
        Product product = new Product();
        product.setTitle(productForm.getTitle());
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/images")
    public ResponseEntity<Product> createProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(productService.createProductImage(id, file), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(productService.updateProductImage(id, file), HttpStatus.OK);
    }

    @GetMapping("/images/{filename}.{fileExtension}")
    public ResponseEntity<FileSystemResource> getProductImage(@PathVariable String filename, @PathVariable String fileExtension) {
        String imagePath = "images/" + filename + "." + fileExtension;
        System.out.println(imagePath);
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            HttpHeaders headers = new HttpHeaders();

            // Set the Content-Type based on the fileExtension
            if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (fileExtension.equalsIgnoreCase("png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (fileExtension.equalsIgnoreCase("gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            } else {
                // Handle unsupported file types
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new FileSystemResource(imageFile));
        } else {
            // Handle the case when the image file doesn't exist
            return ResponseEntity.notFound().build();
        }
    }
}
