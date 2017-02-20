package id.com.templates.repository;


import id.com.templates.model.PersistentAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;


public interface AuditEventRepo extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByAuditEventDateAfter(Date after);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, Date after);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principle, Date after, String type);

    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(Date fromDate, Date toDate, Pageable pageable);
}
