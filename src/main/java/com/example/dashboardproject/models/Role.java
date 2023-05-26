package com.example.dashboardproject.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
   ROLE_ADMIN,ROLE_USER,ROLE_VISITOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
