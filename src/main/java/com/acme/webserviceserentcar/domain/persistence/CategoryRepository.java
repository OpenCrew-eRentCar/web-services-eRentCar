package com.acme.webserviceserentcar.domain.persistence;

import com.acme.webserviceserentcar.domain.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
