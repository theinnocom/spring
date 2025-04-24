package com.inTrack.spring.entity.equipmentEntity.equipmentPermits;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipment_permit")
public class EquipmentPermit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //permits

    @Column(name = "dob")
    private String dob;

    @Column(name = "dep")
    private String dep;

    @Column(name = "fd")
    private String fd;

    @Column(name = "dec")
    private String dec;

    @Column(name = "dot")
    private String dot;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "rcdoh")
    private String rcdoh;

    //Test

    @Column(name = "pressure_valve")
    private String pressureValve;

    @Column(name = "stack")
    private String stack;

    @Column(name = "annual_cathodic_protection")
    private String annualCathodicProtection;

    @Column(name = "tank_tightness")
    private String tankTightness;

    //Consumption

    @Column(name = "annual_fuel")
    private String annualFuel;

    @Column(name = "natural_gas")
    private String naturalGas;

    @Column(name = "fuel_oil")
    private String fuelOil;

    @Column(name = "diesel_oil")
    private String dieselOil;

    @Column(name = "inks")
    private String inks;

    @Column(name = "fountain_solution")
    private String fountainSolution;

    @Column(name = "cleaning_agent")
    private String cleaningAgent;

    @Column(name = "voc")
    private String voc;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "spill_compliance")
    private String spillCompliance;

    @Column(name = "annual_waste_burned")
    private String annualWasteBurned;

    @Column(name = "annual_tune_up")
    private String annualTuneUp;

    @ManyToOne
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

    @ManyToOne
    @JoinColumn(name = "certification_id")
    private Certification certification;
}
