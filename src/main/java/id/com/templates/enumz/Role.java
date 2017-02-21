package id.com.templates.enumz;

/**
 * Created by edsarp on 2/18/17.
 */
public enum  Role {
    SUPERUSER("XX"),
    CUSTOMER("00"),
    ADMINISTRATOR("01"),
    SUPERVISOR("02");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
