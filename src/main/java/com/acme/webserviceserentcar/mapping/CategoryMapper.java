package com.acme.webserviceserentcar.mapping;

import com.acme.webserviceserentcar.domain.model.entity.Category;
import com.acme.webserviceserentcar.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class CategoryMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public CategoryResource toResource(Category model) { return mapper.map(model, CategoryResource.class); }

    public Page<CategoryResource> modelListToPage(List<Category> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, CategoryResource.class), pageable, modelList.size());
    }

    public Category toModel(CreateCategoryResource resource) { return mapper.map(resource, Category.class); }
    public Category toModel(UpdateCategoryResource resource) { return mapper.map(resource, Category.class); }
}