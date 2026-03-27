package com.nexuscart.module_auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    public enum RoleName implements GrantedAuthority {
        ROLE_CUSTOMER,
        ROLE_ADMIN,
        ROLE_VENDOR;

        @Override
        public String getAuthority() {
            return name();
        }
    }

    public Role(RoleName name) {
        this.name = name;
    }
}
