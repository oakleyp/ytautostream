package com.oakleyp.youtubeautostream.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.AccessOptions.SetOptions.Propagation;
import org.springframework.util.StringUtils;

import com.oakleyp.youtubeautostream.data.model.LiveStream;
import com.oakleyp.youtubeautostream.data.model.LiveStream_;
import com.oakleyp.youtubeautostream.data.model.LiveStreamStatus;

public class LiveStreamSpecifications {
    private LiveStreamSpecifications() {
    }

    @Transactional
    public static Specification<LiveStream> filterByKeywordAndStatus(
        final String keyword,
        final LiveStreamStatus status
    ) {
        return (Root<LiveStream> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            root.fetch("sources");

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(keyword)) {
                predicates.add(
                    cb.or(
                        cb.like(root.get(LiveStream_.description), "%" + keyword + "%"),
                        cb.like(root.get(LiveStream_.name), "%" + keyword + "%")
                    )
                );
            }

            if (status != null) {
                predicates.add(cb.equal(root.get(LiveStream_.liveStreamStatus), status));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));

        };
    }

}
