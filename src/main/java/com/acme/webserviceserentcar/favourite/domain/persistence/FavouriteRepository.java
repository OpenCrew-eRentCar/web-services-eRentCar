package com.acme.webserviceserentcar.favourite.domain.persistence;

import com.acme.webserviceserentcar.favourite.domain.model.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
}
