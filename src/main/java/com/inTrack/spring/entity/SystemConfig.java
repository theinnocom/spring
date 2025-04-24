package com.inTrack.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "system_config")
public class SystemConfig {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;
}
