package com.acme.webserviceserentcar.favourite.domain.persistence;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Page<Favourite> findByClientId(Long clientId, Pageable pageable);

}
