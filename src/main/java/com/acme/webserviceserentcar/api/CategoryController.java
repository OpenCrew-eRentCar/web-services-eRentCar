package com.acme.webserviceserentcar.api;

import com.acme.webserviceserentcar.domain.service.CategoryService;
import com.acme.webserviceserentcar.mapping.CategoryMapper;
import com.acme.webserviceserentcar.resource.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<CategoryResource> getAllCategories(Pageable pageable) {
        return mapper.modelListToPage(categoryService.getAll(), pageable);
    }

    @GetMapping("{categoryId}")
    public CategoryResource getCategoryById(@PathVariable Long categoryId) {
        return mapper.toResource(categoryService.getById(categoryId));
    }

    @PostMapping
    public CategoryResource createCategory(@Valid @RequestBody CreateCategoryResource request) {
        return mapper.toResource(categoryService.create(mapper.toModel(request)));
    }

    @PutMapping("{categoryId}")
    public CategoryResource updateCategory(@PathVariable Long categoryId, @Valid @RequestBody UpdateCategoryResource request) {
        return mapper.toResource(categoryService.update(categoryId, mapper.toModel(request)));
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) { return categoryService.delete(categoryId); }
}
