package id.com.templates.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.com.templates.model.account.Account;
import id.com.templates.model.account.AccountUser;
import id.com.templates.model.account.Transaction;
import id.com.templates.model.auth.User;
import id.com.templates.model.main.Activity;
import id.com.templates.model.main.Role;
import id.com.templates.model.menu.Menu;
import id.com.templates.model.menu.Menus;
import id.com.templates.model.menu.RoleMenu;
import id.com.templates.repository.AccountRepo;
import id.com.templates.repository.AccountUserRepo;
import id.com.templates.repository.ActivityRepo;
import id.com.templates.repository.MenuRepo;
import id.com.templates.repository.MenusRepo;
import id.com.templates.repository.RoleMenuRepo;
import id.com.templates.repository.RoleRepo;
import id.com.templates.repository.TransactionRepo;
import id.com.templates.repository.UserRepo;
import id.com.templates.service.MainService;
@Service
public class MainServiceImpl implements MainService{
	@Autowired RoleRepo roleRepo;
	@Autowired MenuRepo menuRepo;
	@Autowired MenusRepo menusRepo;
	@Autowired RoleMenuRepo roleMenuRepo;
	@Autowired UserRepo userRepo;
	@Autowired ActivityRepo activityRepo;
	@Autowired AccountRepo accountRepo;
	@Autowired AccountUserRepo accountUserRepo;
	@Autowired TransactionRepo transactionRepo;
	
	@Override
	public void saveRole(Role role) {
		roleRepo.save(role);
	}

	@Override
	public void deleteRole(Role role) {
		roleRepo.delete(role);
	}

	@Override
	public Role findRoleById(String roleId) {
		return roleRepo.findByRoleId(roleId);
	}

	@Override
	public List<Role> findAllRole() {
		return roleRepo.findAllByOrderByRoleId();
	}

	@Override
	public List<Role> findAllRoleById(String roleId) {
		return roleRepo.findAllByRoleId(roleId);
	}

	@Override
	public void saveMenu(Menu menu) {
		menuRepo.save(menu);
	}

	@Override
	public void deleteMenu(Menu menu) {
		menuRepo.delete(menu);
	}

	@Override
	public Menu findMenuById(String menuId) {
		return menuRepo.findByMenuId(menuId);
	}

	@Override
	public List<Menu> findAllMenu() {
		return menuRepo.findAllByOrderByMenuId();
	}

	@Override
	public List<Menu> findAllMenuById(String menuId) {
		return menuRepo.findAllByMenuId(menuId);
	}

	@Override
	public void saveRoleMenu(RoleMenu menu) {
		roleMenuRepo.save(menu);
	}

	@Override
	public void deleteAllRoleMenu(List<RoleMenu> menu) {
		roleMenuRepo.delete(menu);
	}

	@Override
	public List<RoleMenu> findAllRoleMenuById(String roleId) {
		return roleMenuRepo.findAllByRoleId(roleId);
	}

	@Override
	public List<Menus> findAllMenusById(String roleId) {
		return menusRepo.findAllItemsMenuByRoleId(roleId);
	}

	@Override
	public void saveUser(User user) {
		userRepo.save(user);
	}

	@Override
	public User findUserById(String userId) {
		return userRepo.findByUserId(userId);
	}

	@Override
	public String createPassword() {
		String alpha = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder builder = new StringBuilder(6);
    	for(int i = 0; i < 6; i++){
    		builder.append(alpha.charAt(new Random().nextInt(alpha.length())));
    	}
//   		return builder.toString();
   		return "1";
	}

	@Override
	public void saveActivity(Activity activity) {
		activityRepo.save(activity);
	}

	@Override
	public List<Activity> findAllActivity(String userId) {
		return activityRepo.findAllByUserIdAndStatusOrderByTimes(userId, 0);
	}

	@Override
	public void updateLogoutActivity(Date logout, String userId) {
		activityRepo.updateLogoutByUserId(logout, userId);
		activityRepo.updateByUserId(userId);
	}

	@Override
	public void updateFailAttempts(Integer attempt, String userId) {
		userRepo.updateFailedLoginByUserId(attempt,userId);
	}

	@Override
	public void lockedUserLogin(String userId) {
		userRepo.updateLockedByUserId(userId);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public Account findAccountById(String accnbr) {
		return accountRepo.findByAccountNumber(accnbr);
	}

	@Override
	public AccountUser findAccountUserById(String userId, String account) {
		return accountUserRepo.findByUserIdAndAccountNumber(userId, account);
	}

	@Override
	public void saveAccountUser(AccountUser accountUser) {
		accountUserRepo.save(accountUser);
	}

	@Override
	public List<AccountUser> findAllAccountUserById(String userId) {
		return accountUserRepo.findAllByUserId(userId);
	}

	@Override
	public BigDecimal findBalanceAccount(String account) {
		return transactionRepo.balanceTransaction(account);
	}

	@Override
	public List<Transaction> findAllTransactionByLimit(String account, String limit) {
		if (limit.equals("5")) {
			return transactionRepo.findTop5AllByAccountNumberOrderByTimestampDesc(account);
		}else if (limit.equals("10")) {
			return transactionRepo.findTop10AllByAccountNumberOrderByTimestampDesc(account);
		}else{
			return transactionRepo.findTop15AllByAccountNumberOrderByTimestampDesc(account);
		}
	}

	@Override
	public List<Transaction> findAllTransactionByPeriode(String account, String from, String end) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFrom = sdf.parse(from);
			Date dateTo = sdf.parse(end);
			return transactionRepo.findAllByAccountNumberAndTransactionDateBetweenOrderByTimestamp(account, dateFrom, dateTo);
		} catch (Exception e) {
			return null;
		}
	}
}
