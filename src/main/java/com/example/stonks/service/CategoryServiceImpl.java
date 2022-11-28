package com.example.stonks.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.stonks.dto.CategoryDto;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.Category;
import com.example.stonks.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        //Category category = new Category();
        // TO DO


        //categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findCategoriesForUser(UserDto userDto) {
        List<Category> categories = categoryRepository.findByUserId(userDto.getId());
        return categories.stream()
                    .map((category) -> mapToCategoryDto(category))
                    .collect(Collectors.toList());
    }

    private CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setType(category.getType());
        return categoryDto;
    }
}
