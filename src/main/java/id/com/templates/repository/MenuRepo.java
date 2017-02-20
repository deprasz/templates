package id.com.templates.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.com.templates.model.menu.Menu;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Long>{
	Menu findByMenuId(String menu);
	List<Menu> findAllByMenuId(String menu);
	List<Menu> findAllByOrderByMenuId();
	
	@Query(value="select a.id, a.menu_id, a.menu_name, a.parent, a.url from menu a, role_menu b "
			+ " where b.menu_id = a.menu_id and b.role_id = :roleId order by a.menu_id, a.parent", nativeQuery=true)
	List<Menu> findMenuByRoleId(@Param("roleId") String roleId);
}
