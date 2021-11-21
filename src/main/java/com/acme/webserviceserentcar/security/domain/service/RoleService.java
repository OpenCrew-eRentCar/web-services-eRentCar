package com.acme.webserviceserentcar.security.domain.service;

import com.acme.webserviceserentcar.security.domain.model.entity.Role;

import java.util.List;

public interface RoleService {

    void seed();

    List<Role> getAll();
}
