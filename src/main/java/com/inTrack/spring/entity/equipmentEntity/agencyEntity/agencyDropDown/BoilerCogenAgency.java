package com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "boiler_cogen_agency")
public class BoilerCogenAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "is_boiler")
    private Boolean isBoiler;

    @Column(name = "is_kitchen")
    private Boolean isKitchen;

    @Column(name = "is_cogen")
    private Boolean isCogen;

    @Column(name = "is_fire_alarm")
    private Boolean isFireAlarm;

    @Column(name = "is_fume_hood")
    private Boolean isFumeHood;

    @Column(name = "is_paint_spray_booth")
    private Boolean isPaintSprayBooth;

    @Column(name = "is_rpz")
    private Boolean isRpz;

    @Column(name = "is_hydraulic")
    private Boolean isHydraulic;

    @Column(name = "is_portable")
    private Boolean isPortable;

    @Column(name = "is_elevator")
    private Boolean isElevator;

    @Column(name = "is_emergency")
    private Boolean isEmergency;

    @Column(name = "is_land_fill")
    private Boolean isLandFill;

    @Column(name = "is_printing_press")
    private Boolean isPrintingPress;
}
