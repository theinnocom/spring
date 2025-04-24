package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "fiber_optic_connection")
@Accessors(chain = true)
public class FiberOpticConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fiber_optic_connection_id")
    private Long fiberOpticConnectionId;

    @Column(name = "connection_status")
    private String connectionStatus;
}
