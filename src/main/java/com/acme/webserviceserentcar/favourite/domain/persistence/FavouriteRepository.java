package com.acme.webserviceserentcar.favourite.domain.persistence;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findByClientId(Long clientId);
    boolean existsByCarIdAndClientId(Long carId, Long clientId);
    boolean existsByIdAndClientId(Long id, Long clientId);
}
