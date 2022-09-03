package com.acme.webserviceserentcar.rent.domain.persistence;

import com.acme.webserviceserentcar.rent.domain.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findAllByClientId(Long clientId);
    @Query("SELECT r FROM Rent r JOIN Car c ON r.car.id = c.id WHERE c.client.id = ?1")
    List<Rent> findAllByCarOwner(Long carOwnerId);
}
