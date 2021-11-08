package com.acme.webserviceserentcar.client.domain.persistence;

import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
