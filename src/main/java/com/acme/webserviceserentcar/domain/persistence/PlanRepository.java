package com.acme.webserviceserentcar.domain.persistence;

import com.acme.webserviceserentcar.domain.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
