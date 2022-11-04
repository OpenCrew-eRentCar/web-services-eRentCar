package com.acme.webserviceserentcar.car.domain.persistence;

import com.acme.webserviceserentcar.car.domain.model.entity.CarComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarCommentRepository extends JpaRepository<CarComment, Long> {
}
