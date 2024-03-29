package com.acme.webserviceserentcar.car.persistence;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
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

        //Find by category
        Predicate categoryOfCarCriteria = builder.in(car.get("category")).value(searchCarFilters.getCategories());

        //Find by price range
        Predicate priceRangeCriteria = builder.disjunction();
        for (PriceRange range : searchCarFilters.getPriceRanges()) {
            Predicate rangePrice = builder.between(car.get("rentAmountDay"), range.getMin(), range.getMax());
            priceRangeCriteria = builder.or(priceRangeCriteria, rangePrice);
        }

        //Find by Client id
        Predicate clientCriteria = builder.notEqual(car.get("client").get("id"), clientId);

        query.select(car).where(priceRangeCriteria, categoryOfCarCriteria, clientCriteria);
        return entityManager.createQuery(query).getResultList();
    }

    public boolean isActiveSOAT(String licensePlate, InsuranceType insuranceType) {
        return true;
    }
}
