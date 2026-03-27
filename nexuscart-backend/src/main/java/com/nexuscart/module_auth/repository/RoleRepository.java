package com.nexuscart.module_auth.repository;

import com.nexuscart.module_auth.entity.Role;
import com.nexuscart.module_auth.entity.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
