package id.com.templates.model.main;

import id.com.templates.model.pk.RoleMenuPK;
import id.com.templates.model.pk.UserActivityPK;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_activity")
@IdClass(UserActivityPK.class)
public class UserActivity {

    @Id
    @Column(name="user_id")
    private String userId;

    @Id
    @Column(name="timestamp")
    private Date timestamp;

    @Column(name = "activity")
    private String activity;

    @Column(name = "ip_address")
    private String ipAdress;

    @Column(name = "description")
    private String description;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
