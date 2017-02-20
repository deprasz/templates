package id.com.templates.enumz;

/**
 * Created by edsarp on 2/18/17.
 */
public enum  Activity {
    LOGIN("login"),
    LOGOUT("logout"),
    TRANSACTION("transaction");

    private final String value;

    Activity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
