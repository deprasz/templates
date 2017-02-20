package id.com.templates.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import id.com.templates.model.menu.Menus;

public interface MenusRepo extends Repository<Menus, Long>{
	@Query(value="select a.id, a.menu_id, a.menu_name, a.parent, a.url, case when a.menu_id = b.menu_id then '1' else '0' end as pick, b.role_id"
			+ " from menu a left join role_menu b on b.menu_id = a.menu_id  and b.role_id = :roleId order by a.menu_id", nativeQuery=true)
	List<Menus> findAllItemsMenuByRoleId(@Param("roleId") String roleId);
}
