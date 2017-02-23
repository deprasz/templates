package id.com.templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import id.com.templates.model.account.Account;

public interface AccountRepo extends JpaRepository<Account, String>{
	Account findByAccountNumber(String accnbr);
}
