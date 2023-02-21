package com.server.repositories.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LoginResultProjection {
    Integer getId();
    String getUsername();
    @JsonIgnore
    String getPassword();
    boolean isAdmin();
}
