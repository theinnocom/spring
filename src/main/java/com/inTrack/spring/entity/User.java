package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * @author vijayan
 */

@Getter
@Setter
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "alternate_mail")
    private String alternateMail;

    @Column(name = "password", nullable = false, unique = true, length = 200)
    private String password;

    @Column(name = "login_id", unique = true, length = 200)
    private String loginId;

    @Column(name = "designation")
    private String designation;

    @Column(name = "delete_permission", nullable = false)
    private Boolean deletePermission = false;

    @Column(name = "is_profile_active", nullable = false)
    private Boolean isProfileActive = false;

    @Column(name = "expiry_date")
    private Long expiryDate;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "update_permission", nullable = false)
    private Boolean updatePermission = false;

    @Column(name = "must_change_password", nullable = false)
    private Boolean mustChangePassword = true;

    @Column(name = "registration")
    private String registration;

    @Column(name = "proposal")
    private Boolean proposal;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private UserRole role;
}
