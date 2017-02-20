package id.com.templates.security;

import id.com.templates.model.PersistentAuditEvent;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

/**
 * Created by edsarp on 2/19/17.
 */
@Component
public class AuditLogEventListener implements PostUpdateEventListener, PostInsertEventListener, PostDeleteEventListener {

    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {
        System.out.println("Post Delete");
    }

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        System.out.println("Post Insert");
    }

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        System.out.println("Post Update");
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
