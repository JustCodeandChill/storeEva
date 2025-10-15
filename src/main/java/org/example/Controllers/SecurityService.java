package org.example.Controllers;

import org.example.Securities.SecurityContext;

import java.util.ArrayList;
import java.util.List;

public class SecurityService {
    private List<SecurityContext> securityContext;
    private SecurityContext currentSecurityContext;

    public SecurityService() {
        securityContext = new ArrayList<>();
    }


    public List<SecurityContext> getSecurityContext() {
        return securityContext;
    }

    public SecurityContext getCurrentSecurityContext() {
        return currentSecurityContext;
    }

    public void setCurrentSecurityContext(SecurityContext currentSecurityContext) {
        this.currentSecurityContext = currentSecurityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext.add(securityContext);
    }

    public SecurityContext getSecurityContextByUsername(String username) {
        return securityContext.stream().filter((SecurityContext ctx) ->
                ctx.getUsername().equalsIgnoreCase(username)
                ).findFirst().orElse(null);
    }
}
