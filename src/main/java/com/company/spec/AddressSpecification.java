package com.company.spec;

import com.company.entity.AddressEntity;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {
    public static Specification<AddressEntity> secForString (String requiredRoot, String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(requiredRoot), field));
    }

}
