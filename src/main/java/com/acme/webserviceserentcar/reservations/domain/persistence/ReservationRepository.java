package com.acme.webserviceserentcar.reservations.domain.persistence;

import com.acme.webserviceserentcar.reservations.domain.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByClientId(Long clientId);
}
