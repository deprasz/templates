package id.com.templates.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.com.templates.model.menu.RoleMenu;
import id.com.templates.model.pk.RoleMenuPK;

@Repository
public interface RoleMenuRepo extends JpaRepository<RoleMenu, RoleMenuPK>{
	List<RoleMenu> findAllByRoleId(String roleId);
}
