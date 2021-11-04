package com.acme.webserviceserentcar.api;

import com.acme.webserviceserentcar.domain.service.CategoryService;
import com.acme.webserviceserentcar.mapping.CategoryMapper;
import com.acme.webserviceserentcar.resource.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get All Categories", description = "Get All Categories on pages", tags = {"Categories"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Categories returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public Page<CategoryResource> getAllCategories(Pageable pageable) {
        return mapper.modelListToPage(categoryService.getAll(), pageable);
    }

    @Operation(summary = "Get Category by Id", description = "Get Category by Id", tags = {"Categories"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{categoryId}")
    public CategoryResource getCategoryById(@PathVariable Long categoryId) {
        return mapper.toResource(categoryService.getById(categoryId));
    }

    @Operation(summary = "Create Category", description = "Create Category", tags = {"Categories"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public CategoryResource createCategory(@Valid @RequestBody CreateCategoryResource request) {
        return mapper.toResource(categoryService.create(mapper.toModel(request)));
    }

    @Operation(summary = "Update Category", description = "Update Category", tags = {"Categories"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("{categoryId}")
    public CategoryResource updateCategory(@PathVariable Long categoryId, @Valid @RequestBody UpdateCategoryResource request) {
        return mapper.toResource(categoryService.update(categoryId, mapper.toModel(request)));
    }

    @Operation(summary = "Delete Category", description = "Delete Category", tags = {"Categories"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) { return categoryService.delete(categoryId); }
}
