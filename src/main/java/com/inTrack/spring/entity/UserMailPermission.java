package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_mail_permission")
public class UserMailPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_daily")
    private boolean isDaily;

    @Column(name = "is_tomorrow")
    private boolean isTomorrow;

    @Column(name = "is_weekly")
    private boolean isWeekly;

    @Column(name = "is_monthly")
    private boolean isMonthly;

    @Column(name = "is_quarterly")
    private boolean isQuarterly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
