package com._chaumedia.GroupLottery.service.specification;

import org.springframework.data.jpa.domain.Specification;

import com._chaumedia.GroupLottery.model.User;
import com._chaumedia.GroupLottery.model.User_;

public class UserSpecs {
	public static Specification<User> hasRole(Long roleId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.ROLE).get("id"), roleId);
	};

	public static Specification<User> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.STATUS), status);
	};

	public static Specification<User> hasName(String name) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(User_.NAME)),
				"%" + name.toLowerCase() + "%");
	}
}
