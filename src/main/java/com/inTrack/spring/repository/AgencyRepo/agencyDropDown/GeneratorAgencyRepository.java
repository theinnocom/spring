package com.inTrack.spring.repository.AgencyRepo.agencyDropDown;//package com.inTrack.spring.repository.AgencyRepo.agencyDropDown;
//
//import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.GeneratorAgency;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @author Vijayan M
// */
//@Repository
//public class GeneratorAgencyRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public List<GeneratorAgency> findAll() {
//        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        final CriteriaQuery<GeneratorAgency> cq = cb.createQuery(GeneratorAgency.class);
//        final Root<GeneratorAgency> root = cq.from(GeneratorAgency.class);
//        cq.select(root);
//        return entityManager.createQuery(cq).getResultList();
//    }
//}
