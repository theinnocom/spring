package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author vijayan
 */

@Getter
@Setter
@Entity
@Table(name = "user_role")
@Accessors(chain = true)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "role")
    private String role;

    @Column(name = "is_cof")
    private boolean isCof;
}
