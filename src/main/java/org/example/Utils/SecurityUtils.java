package org.example.Utils;

import org.example.Controllers.SecurityService;
import org.example.Securities.SecurityContext;

public class SecurityUtils {
    SecurityService securityService;

    public SecurityUtils() {
        securityService = new SecurityService();
    }

    public boolean isAuthenticated() {
        SecurityContext ctx = securityService.getCurrentSecurityContext();
        if (ctx != null && ctx.isAuthenticated()) {
            System.out.println("You are authenticated as " + ctx.getUsername() + " Redirect to other page");
            return true;
        } else  {
            System.out.println("You are not authenticated");
            return false;
        }
    }
}
