package id.com.templates.service;

import java.util.Date;
import java.util.List;

import id.com.templates.model.auth.User;
import id.com.templates.model.main.Activity;
import id.com.templates.model.main.Role;
import id.com.templates.model.menu.Menu;
import id.com.templates.model.menu.Menus;
import id.com.templates.model.menu.RoleMenu;

public interface MainService {
	String createPassword();

	void saveRole(Role role);
	void deleteRole(Role role);
	Role findRoleById(String roleId);
	List<Role> findAllRole();
	List<Role> findAllRoleById(String roleId);

	void saveMenu(Menu menu);
	void deleteMenu(Menu menu);
	Menu findMenuById(String menuId);
	List<Menu> findAllMenu();
	List<Menu> findAllMenuById(String menuId);

	void saveRoleMenu(RoleMenu menu);
	void deleteAllRoleMenu(List<RoleMenu> menu);
	List<RoleMenu> findAllRoleMenuById(String roleId);
	List<Menus> findAllMenusById(String roleId);

	void saveUser(User user);
	User findUserById(String userId);

	void updateFailAttempts(Integer attempt,String userId);
	void lockedUserLogin(String userId);

	void saveActivity(Activity activity);
	void updateLogoutActivity(Date logout, String userId);
	List<Activity> findAllActivity(String userId);

}
