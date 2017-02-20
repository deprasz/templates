package id.com.templates.service.impl;


import id.com.templates.configuration.AuditEventConverter;
import id.com.templates.model.PersistentAuditEvent;
import id.com.templates.repository.AuditEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class AuditEventService {

    private AuditEventRepo persistenceAuditEventRepository;

    private AuditEventConverter auditEventConverter;

    @Autowired
    public AuditEventService(
        AuditEventRepo persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    public Page<PersistentAuditEvent> findAll(Pageable pageable) {
        return  persistenceAuditEventRepository.findAll(pageable);

    }


}
