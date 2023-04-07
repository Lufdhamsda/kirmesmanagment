package de.johannes.kirmes.data.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import de.johannes.kirmes.data.entity.User;
import jakarta.persistence.criteria.Predicate;

/**
 * @author j.henkel
 */
public class UserSpecifications {

	public static Specification<User> filterUsers(String filter) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (filter != null && !filter.isEmpty()) {
				String[] filters = filter.split(" ");
				for (String f : filters) {
					Predicate predicate = cb.or(
							cb.like(cb.lower(root.get("firstName")), "%" + f.toLowerCase() + "%"),
							cb.like(cb.lower(root.get("lastName")), "%" + f.toLowerCase() + "%"),
							cb.like(cb.lower(root.get("email")), "%" + f.toLowerCase() + "%"),
							cb.like(cb.lower(root.get("birthday")), "%" + f.toLowerCase() + "%"),
							cb.like(cb.lower(root.get("phoneNumber")), "%" + f.toLowerCase() + "%"),
							cb.like(cb.lower(root.get("location")), "%" + f.toLowerCase() + "%"),
							cb.like(cb.lower(root.join("role").get("name")), "%" + f.toLowerCase() + "%")
					);
					predicates.add(predicate);
				}
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
