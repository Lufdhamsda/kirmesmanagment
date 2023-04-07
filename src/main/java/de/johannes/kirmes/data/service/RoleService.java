package de.johannes.kirmes.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.johannes.kirmes.data.entity.Role;
import de.johannes.kirmes.data.repositories.RoleRepository;

/**
 * @author j.henkel
 */
@Service
public class RoleService {

	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository){
		this.roleRepository = roleRepository;
	}

	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
}
