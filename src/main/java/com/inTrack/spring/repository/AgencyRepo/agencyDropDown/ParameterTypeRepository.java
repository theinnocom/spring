package com.inTrack.spring.repository.AgencyRepo.agencyDropDown;

import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.ParameterType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vijayan M
 */
@Repository
public class ParameterTypeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<ParameterType> findAll() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ParameterType> cq = cb.createQuery(ParameterType.class);
        final Root<ParameterType> root = cq.from(ParameterType.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }
}
