package id.com.templates.enumz;

/**
 * Created by edsarp on 2/19/17.
 */
public enum StatusUser {
    DEFAULT(0),LOGON(1),LOCKED(2);

    private Integer value;

    StatusUser(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
