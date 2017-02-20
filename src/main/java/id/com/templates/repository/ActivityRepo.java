package id.com.templates.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import id.com.templates.model.main.Activity;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long>{
	Activity findTop1ByUserIdOrderByTimesDesc(String userId);
	List<Activity> findAllByUserIdAndStatusOrderByTimes(String userId, Integer status);

	@Transactional
	@Modifying
	@Query(value="update activity set status = 1 where user_id = :userId and status = 0", nativeQuery=true)
	void updateByUserId(@Param("userId") String userId);

	@Transactional
	@Modifying
	@Query(value="update activity set logout = :logout where user_id = :userId and status = 0", nativeQuery=true)
	void updateLogoutByUserId(@Param("logout") Date logout, @Param("userId") String userId);


}
