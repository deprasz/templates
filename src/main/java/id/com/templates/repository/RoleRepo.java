package id.com.templates.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.com.templates.model.main.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, String>{
	Role findByRoleId(String roleId);
	List<Role> findAllByRoleId(String roleId);
	List<Role> findAllByOrderByRoleId();
}
