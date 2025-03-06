package com.final_project.service;

import com.final_project.dto.request.ProductRequest;
import com.final_project.entity.Category;
import com.final_project.entity.Product;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;

import com.final_project.repository.CategoryRepository;
import com.final_project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(ProductRequest request){
        //get category by id
        Category category=categoryRepository.findById(String.valueOf(request.getCategoryId())).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        // Lấy mã danh mục (giả sử category có trường `code`)
        String categoryCode = String.valueOf(category.getId());

        // Tìm sản phẩm mới nhất trong danh mục đó để lấy số thứ tự
        String latestProductCode = productRepository.findLatestProductCodeByCategoryId(category.getId());

        // Xử lý số thứ tự
        int newNumber = 1; // Mặc định nếu chưa có sản phẩm nào
        if (latestProductCode != null && latestProductCode.matches(".+_\\d{6}")) {
            String numberPart = latestProductCode.split("_")[1]; // Lấy phần số
            newNumber = Integer.parseInt(numberPart) + 1; // Tăng giá trị
        }

        // Định dạng số thứ tự thành 6 chữ số
        String productCode = String.format("%s_%06d", categoryCode, newNumber);
        // Tạo sản phẩm mới
        Product product = new Product();
        product.setCode(productCode);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
    }

    public Product updateProduct(String id, ProductRequest request){
        Product product = productRepository.findById(id).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        Category category=categoryRepository.findById(String.valueOf(request.getCategoryId())).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        return productRepository.save(product);
    }
    public void deleteProduct(String id){
        Product product = productRepository.findById(id).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        productRepository.delete(product);
    }
}
