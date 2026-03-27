package com.nexuscart.module_auth.service;

import com.nexuscart.module_auth.entity.Role;
import com.nexuscart.module_auth.entity.Role.RoleName;
import com.nexuscart.module_auth.entity.User;
import com.nexuscart.module_auth.repository.RoleRepository;
import com.nexuscart.module_auth.repository.UserRepository;
import com.nexuscart.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName.name()));
    }

    @Transactional
    public Set<Role> getDefaultCustomerRoles() {
        Set<Role> roles = new HashSet<>();
        Role customerRole = findByName(RoleName.ROLE_CUSTOMER);
        roles.add(customerRole);
        return roles;
    }

    @Transactional
    public Set<Role> getRolesByNames(String[] roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            try {
                RoleName name = RoleName.valueOf(roleName.toUpperCase());
                Role role = findByName(name);
                roles.add(role);
            } catch (IllegalArgumentException e) {
                // Skip invalid role names
            }
        }
        if (roles.isEmpty()) {
            roles.addAll(getDefaultCustomerRoles());
        }
        return roles;
    }
}
