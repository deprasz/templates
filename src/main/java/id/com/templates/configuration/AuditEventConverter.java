package id.com.templates.configuration;



import id.com.templates.model.AuditEvent;
import id.com.templates.model.PersistentAuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class AuditEventConverter {

    public List<AuditEvent> convertToAuditEvent(Iterable<PersistentAuditEvent> persistentAuditEvents) {
        if (persistentAuditEvents == null) {
            return Collections.emptyList();
        }
        List<AuditEvent> auditEvents = new ArrayList<>();
        for (PersistentAuditEvent persistentAuditEvent : persistentAuditEvents) {
            auditEvents.add(convertToAuditEvent(persistentAuditEvent));
        }
        return auditEvents;
    }


    public AuditEvent convertToAuditEvent(PersistentAuditEvent persistentAuditEvent) {
        return new AuditEvent(new Date(), persistentAuditEvent.getPrincipal(),
            persistentAuditEvent.getAuditEventType(), convertDataToObjects(persistentAuditEvent.getData()));
    }


    public Map<String, Object> convertDataToObjects(Map<String, String> data) {
        Map<String, Object> results = new HashMap<>();

        if (data != null) {
            for (String key : data.keySet()) {
                results.put(key, data.get(key));
            }
        }
        return results;
    }


    public Map<String, String> convertDataToStrings(Map<String, Object> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (String key : data.keySet()) {
                Object object = data.get(key);
                if (object instanceof WebAuthenticationDetails) {
                    WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) object;
                    results.put("remoteAddress", authenticationDetails.getRemoteAddress());
                    results.put("sessionId", authenticationDetails.getSessionId());
                } else if (object != null) {
                    results.put(key, object.toString());
                } else {
                    results.put(key, "null");
                }
            }
        }

        return results;
    }
}
