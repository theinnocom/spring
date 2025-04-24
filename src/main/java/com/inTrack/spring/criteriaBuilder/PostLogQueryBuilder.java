package com.inTrack.spring.criteriaBuilder;

import com.inTrack.spring.EnumField.PostLogField;
import com.inTrack.spring.UtilService.PaginationUtil;
import com.inTrack.spring.dto.requestDTO.CommonListReqDTO;
import com.inTrack.spring.dto.responseDTO.CommonListResDTO;
import com.inTrack.spring.entity.PostLog;
import com.inTrack.spring.UtilService.CommonUtilService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostLogQueryBuilder {

    private final EntityManager entityManager;
    private final CommonUtilService commonUtilService;

    @Transactional
    public CommonListResDTO findByFacility(final Long facilityId, final CommonListReqDTO commonListReqDTO) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<PostLog> cq = cb.createQuery(PostLog.class);
        final Root<PostLog> root = cq.from(PostLog.class);
        Predicate mainPredicate = cb.equal(root.get(PostLogField.FACILITY.getColumnName()).get(PostLogField.FACILITY_ID.getColumnName()), facilityId);
        cq.select(root).where(mainPredicate);
        final List<PostLog> postLogs = entityManager.createQuery(cq).getResultList();

        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<PostLog> countRoot = countQuery.from(PostLog.class);
        Predicate countPredicate = cb.equal(countRoot.get(PostLogField.FACILITY.getColumnName()).get(PostLogField.FACILITY_ID.getColumnName()), facilityId);
        countQuery.select(cb.count(countRoot)).where(countPredicate);
        final Long count = entityManager.createQuery(countQuery).getSingleResult();

        final Pageable pageable = PaginationUtil.pageableWithoutShorting(commonListReqDTO.getPageNumber(), commonListReqDTO.getItemsPerPage());
        final Page<PostLog> postLogPage = new PageImpl<>(postLogs, pageable, count);
        return this.commonUtilService.setCommonListResDTO(postLogPage, postLogs, true);
    }
}
