package id.com.templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.com.templates.model.auth.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	User findByUserId(String userId);
	User findByEmail(String email);

	@Transactional
	@Modifying
	@Query(value="update sys_user set login_failed = :loginFailed where user_id = :userId", nativeQuery=true)
	void updateFailedLoginByUserId(@Param("loginFailed") Integer loginFailed, @Param("userId") String userId);

	@Transactional
	@Modifying
	@Query(value="update sys_user set locked = 'true' where user_id = :userId", nativeQuery=true)
	void updateLockedByUserId(@Param("userId") String userId);


	@Query(value = "select a.user_id, username, password, email, login_failed, limit_failed, " +
			"locked, activated, role, lang_key, activation_key, reset_key, " +
			"created_by, created_date, reset_date, updated_by, updated_date FROM sys_user a " +
			"inner join ( " +
			"select distinct on(user_id)user_id, timestamp, activity " +
			"from user_activity where activity=:activity order by user_id,timestamp desc " +
			")as b on b.user_id=a.user_id " +
			"where login_failed > 0 and locked='false' and activated='true' and b.timestamp < :timestamp",nativeQuery = true)
	List<User> findAllByLastLoginTimestameLessThan(@Param("timestamp") Date timestamp,@Param("activity") String activity);

}
