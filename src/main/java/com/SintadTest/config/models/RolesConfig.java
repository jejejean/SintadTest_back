package com.SintadTest.config.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolesConfig {
    @Value("${role.admin}")
    private String adminRole;

    public String getAdminRole() {
        return adminRole;
    }
}
