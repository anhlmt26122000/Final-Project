package com.final_project.service;

import com.final_project.dto.request.CategoryRequest;
import com.final_project.entity.Category;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
import com.final_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public Category createCategory(CategoryRequest request){
        Category category=new Category();
        if(categoryRepository.findCategoriesByName(request.getName()).isPresent()){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImage(request.getImage());
        return categoryRepository.save(category);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(String name){
        return categoryRepository.findCategoriesByName(name).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
    }

    public Category updateCategory(String categoryID, CategoryRequest request){
        Category category = categoryRepository.findById(categoryID).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImage(request.getImage());
        return categoryRepository.save(category);
    }

    public void deleteCategory(String categoryID){
        Category category = categoryRepository.findById(categoryID).orElseThrow(()->
                new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryRepository.delete(category);

    }
}
