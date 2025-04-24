package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author vijayan
 */

@Entity
@Getter
@Setter
@Table(name = "elevator_status")
@Accessors(chain = true)
public class ElevatorStatus {

    @Id
    @Column(name = "status_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long statusId;

    @Column(name = "status_name")
    private String statusName;
}
