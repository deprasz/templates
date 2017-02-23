package id.com.templates.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.com.templates.model.account.AccountUser;
import id.com.templates.model.pk.AccountUserPK;

public interface AccountUserRepo extends JpaRepository<AccountUser, AccountUserPK>{
	AccountUser findByUserIdAndAccountNumber(String userId, String account);
	List<AccountUser> findAllByUserId(String userId);
	
}
