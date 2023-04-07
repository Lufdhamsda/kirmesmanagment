package de.johannes.kirmes.data.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.johannes.kirmes.data.entity.Role;
import de.johannes.kirmes.data.entity.User;
import de.johannes.kirmes.data.repositories.RoleRepository;
import de.johannes.kirmes.data.repositories.UserRepository;
import de.johannes.kirmes.data.specifications.UserSpecifications;
import jakarta.persistence.EntityManagerFactory;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public List<User> getUsersWithFilter(String filter){
		if (filter == null){
			return getAllUsers();
		} else {
			return userRepository.findAll(UserSpecifications.filterUsers(filter));
		}
	}

	public List<String> getAllUserRoles(){
		return roleRepository.getAllRoleName();
	}

	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}

	public Map<String, Long> getUserCountByRole(){
		List<Object[]> results = userRepository.getUserCountByRole();
		Map<String, Long> userCountByRole = new HashMap<>();
		for (Object[] result : results) {
			userCountByRole.put((String) result[0], (Long) result[1]);
		}
		return userCountByRole;
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void delete(User user){
		userRepository.delete(user);
	}

	public long getUserCount(){
		return userRepository.count();
	}
}
