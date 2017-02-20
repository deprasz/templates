package id.com.templates.model;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by edsarp on 2/19/17.
 */
public class AuditEvent implements Serializable {

    private final Date timestamp;
    private final String principal;
    private final String type;
    private final Map<String, Object> data;

    public AuditEvent(String principal, String type, Map<String, Object> data) {
        this(new Date(), principal, type, data);
    }

    public AuditEvent(String principal, String type, String... data) {
        this(new Date(), principal, type, convert(data));
    }

    public AuditEvent(Date timestamp, String principal, String type, Map<String, Object> data) {
        Assert.notNull(timestamp, "Timestamp must not be null");
        Assert.notNull(principal, "Principal must not be null");
        Assert.notNull(type, "Type must not be null");
        this.timestamp = timestamp;
        this.principal = principal;
        this.type = type;
        this.data = Collections.unmodifiableMap(data);
    }

    private static Map<String, Object> convert(String[] data) {
        HashMap result = new HashMap();
        String[] var2 = data;
        int var3 = data.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String entry = var2[var4];
            if(entry.contains("=")) {
                int index = entry.indexOf("=");
                result.put(entry.substring(0, index), entry.substring(index + 1));
            } else {
                result.put(entry, (Object)null);
            }
        }

        return result;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public String toString() {
        return "AuditEvent [timestamp=" + this.timestamp + ", principal=" + this.principal + ", type=" + this.type + ", data=" + this.data + "]";
    }
}
