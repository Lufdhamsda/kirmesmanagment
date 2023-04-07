package de.johannes.kirmes.data.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.johannes.kirmes.data.entity.Role;

/**
 * @author j.henkel
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {

	@Query("select r.name from Role r")
	List<String> getAllRoleName();
}
