package id.com.templates.repository;

import id.com.templates.model.main.UserActivity;
import id.com.templates.model.pk.UserActivityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserActitvityRepo extends JpaRepository<UserActivity,UserActivityPK> {

    @Query(value="select distinct on(user_id)user_id, timestamp, activity, ip_address,description " +
            "from user_activity where user_id=:userId and activity=:activity order by user_id,timestamp desc;", nativeQuery=true)
    UserActivity findOneLastActivityByUserIdAndActiviyKey(@Param("userId") String userId,@Param("activity") String activity);
}
