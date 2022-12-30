package com.example.stonks.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.stonks.dto.CategoryDto;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.Category;
import com.example.stonks.model.User;
import com.example.stonks.repository.CategoryRepository;
import com.example.stonks.repository.UserRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveCategory(CategoryDto categoryDto, UserDto userDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setType(categoryDto.getType());

        User user = userRepository.findByEmail(userDto.getEmail());
        category.setUser(user);

        categoryRepository.save(category);
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
