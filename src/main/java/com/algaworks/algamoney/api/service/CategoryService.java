package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    //UPDATE FULL ENTITY USING ALL FIELDS/ATTRIBUTES
    public Category updateCategory(Integer id , Category category){

        Category savedCategory = getCategoryById(id);
        BeanUtils.copyProperties(category,savedCategory,"id");
        return categoryRepository.save(savedCategory);
    }

    private Category getCategoryById(Integer id) {
        Category savedCategory = categoryRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return savedCategory;
    }

}
