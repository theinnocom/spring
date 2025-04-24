package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "battery_location")
@Accessors(chain = true)
public class BatteryLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_location_id")
    private Long batteryLocationId;

    @Column(name = "battery_location")
    private String batteryLocation;
}
