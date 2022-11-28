package com.example.stonks.service;

import java.util.List;
import com.example.stonks.dto.CategoryDto;
import com.example.stonks.dto.UserDto;

public interface CategoryService {
    void saveCategory(CategoryDto categoryDto);
    List<CategoryDto> findCategoriesForUser(UserDto userDto);
}
