package com.acme.webserviceserentcar.car.persistence;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.resource.searchFilters.PriceRange;
import com.acme.webserviceserentcar.car.resource.searchFilters.SearchCarFilters;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CarRepositoryCustom {
    private final EntityManager entityManager;

    public CarRepositoryCustom(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Car> findBySearchFilters(SearchCarFilters searchCarFilters, Long clientId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> car = query.from(Car.class);

        Predicate priceRangeCriteria = builder.conjunction();

        for (PriceRange range : searchCarFilters.getPriceRanges()) {
            Predicate rangePrice = builder.between(car.get("rentAmountDay"), range.getMin(), range.getMax());
            priceRangeCriteria = builder.or(priceRangeCriteria, rangePrice);
        }

        Predicate categoryOfCarCriteria = builder.conjunction();

        if (searchCarFilters.getCategories().size() > 0) {
            Predicate category = builder.in(car.get("category")).value(searchCarFilters.getCategories());
            categoryOfCarCriteria = builder.and(categoryOfCarCriteria, category);
        }

        Predicate clientCriteria = builder.conjunction();

        if (clientId != null) {
            Predicate clientCar = builder.notEqual(car.get("client").get("id"), clientId);
            clientCriteria = builder.and(clientCriteria, clientCar);
        }

        query.select(car).where(priceRangeCriteria, categoryOfCarCriteria, clientCriteria);
        return entityManager.createQuery(query).getResultList();
    }
}
