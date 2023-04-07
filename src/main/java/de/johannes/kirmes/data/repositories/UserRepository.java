package de.johannes.kirmes.data.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import de.johannes.kirmes.data.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	List<User> findAll(Specification<User> specification);
}
