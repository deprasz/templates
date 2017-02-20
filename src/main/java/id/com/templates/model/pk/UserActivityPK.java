package id.com.templates.model.pk;

import java.io.Serializable;
import java.util.Date;


public class UserActivityPK implements Serializable{

    private static final long serialVersionUID = -1238350673852884418L;

    private String userId;

    private Date timestamp;

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
}
