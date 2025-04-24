package com.inTrack.spring.repository.AgencyRepo.agencyDropDown;

import com.inTrack.spring.dto.AgencyDTO;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.store.ConstantStore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BoilerCogenAgencyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<AgencyDTO> findByColumnTrue(final String columnName) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<AgencyDTO> cq = cb.createQuery(AgencyDTO.class);
        final Root<BoilerCogenAgency> root = cq.from(BoilerCogenAgency.class);
        final Predicate predicate = getPredicateForColumn(cb, root, columnName);
        cq.select(cb.construct(AgencyDTO.class, root.get("id"), root.get("type"))).where(predicate);
        return entityManager.createQuery(cq).getResultList();
    }

    private Predicate getPredicateForColumn(final CriteriaBuilder cb, final Root<BoilerCogenAgency> root, final String columnName) {
        switch (columnName) {
            case ConstantStore.BOILER:
                return cb.isTrue(root.get("isBoiler"));
            case ConstantStore.KITCHEN_HOOD_FIR_SUPPRESSION:
                return cb.isTrue(root.get("isKitchen"));
            case ConstantStore.COGEN_ENGINE_TURBINE:
                return cb.isTrue(root.get("isCogen"));
            case ConstantStore.FIRE_ALARM:
                return cb.isTrue(root.get("isFireAlarm"));
            case ConstantStore.FUME_HOOD:
                return cb.isTrue(root.get("isFumeHood"));
            case ConstantStore.PAIN_SPRAY_BOOTH:
                return cb.isTrue(root.get("isPaintSprayBooth"));
            case ConstantStore.RPZ:
                return cb.isTrue(root.get("isRpz"));
            case ConstantStore.HYDRAULIC_STORAGE_TANK:
                return cb.isTrue(root.get("isHydraulic"));
            case ConstantStore.PORTABLE_FIRE_EXTINGUISHER:
                return cb.isTrue(root.get("isPortable"));
            case ConstantStore.ELEVATOR:
                return cb.isTrue(root.get("isElevator"));
            case ConstantStore.EMERGENCY_EGRESS_AND_LIGHTING:
                return cb.isTrue(root.get("isEmergency"));
            case ConstantStore.LAND_FILL:
                return cb.isTrue(root.get("isLandFill"));
            case ConstantStore.PRINTING_PRESS:
                return cb.isTrue(root.get("isPrintingPress"));
            default:
                throw new IllegalArgumentException("Invalid column name: " + columnName);
        }
    }

    public BoilerCogenAgency findById(final Long boilerCogenAgencyId) {
        // Initialize CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BoilerCogenAgency> criteriaQuery = criteriaBuilder.createQuery(BoilerCogenAgency.class);

        // Define the root entity to query
        Root<BoilerCogenAgency> root = criteriaQuery.from(BoilerCogenAgency.class);

        // Set the condition for the query to match the ID
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("id"), boilerCogenAgencyId));

        // Execute the query and get the result
        try {
            return entityManager.createQuery(criteriaQuery)
                    .getSingleResult();  // Use getSingleResult to return a single entity
        } catch (Exception e) {
            // Handle cases where the entity is not found or any other exception
            return null;
        }
    }

}
