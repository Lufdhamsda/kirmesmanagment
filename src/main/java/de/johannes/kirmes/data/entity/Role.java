package de.johannes.kirmes.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.johannes.kirmes.data.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", columnDefinition = "BINARY(16)", updatable = false, unique = true)
	private UUID id;

	@Column(name = "role_name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "role")
	private List<User> users = new ArrayList<>();

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		this.users.add(user);
		user.setRole(this);
	}

	public void removeUser(User user) {
		this.users.remove(user);
		user.setRole(null);
	}
}

