package com.acme.webserviceserentcar.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Category;
import com.acme.webserviceserentcar.car.domain.persistence.CategoryRepository;
import com.acme.webserviceserentcar.car.domain.service.CategoryService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String ENTITY = "Category";
    private final CategoryRepository categoryRepository;
    private final Validator validator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Validator validator) {
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    @Override
    public List<Category> getAll() { return categoryRepository.findAll(); }

    @Override
    public Page<Category> getAll(Pageable pageable) { return categoryRepository.findAll(pageable); }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, categoryId));
    }

    @Override
    public Category create(Category request) {
        Set<ConstraintViolation<Category>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return categoryRepository.save(request);
    }

    @Override
    public Category update(Long categoryId, Category request) {
        Set<ConstraintViolation<Category>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return categoryRepository.findById(categoryId).map(category ->
                categoryRepository.save(category.withName(request.getName())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, categoryId));
    }

    @Override
    public ResponseEntity<?> delete(Long categoryId) {
        return categoryRepository.findById(categoryId).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, categoryId));
    }
}
