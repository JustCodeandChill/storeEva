package org.example.Securities;

public class SecurityContext {
    private static SecurityContext instance;
    private String username;
    private String role;

    private SecurityContext() {}

    public static synchronized SecurityContext getInstance() {
        if (instance == null) {
            instance = new SecurityContext();
        }
        return instance;
    }

    public void setAuthentication(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public boolean isAuthenticated() {
        return username != null;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void clear() {
        username = null;
        role = null;
    }
}