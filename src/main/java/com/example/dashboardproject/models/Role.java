package com.example.dashboardproject.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
