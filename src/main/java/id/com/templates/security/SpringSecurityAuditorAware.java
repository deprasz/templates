package id.com.templates.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;


@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Autowired
    private AuthService authService;

    @Override
    public String getCurrentAuditor() {
        String userId = authService.userDetails().getUserId();
        return (userId != null ? userId : "system");
    }
}
