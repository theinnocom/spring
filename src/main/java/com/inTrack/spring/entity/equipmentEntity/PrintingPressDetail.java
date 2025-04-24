package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "printing_press_detail")
public class PrintingPressDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "volume")
    private String volume;

    @Column(name = "density")
    private String density;

    @Column(name = "voc_wt")
    private String vocWt;

    @Column(name = "voc_lb")
    private String vocLb;

    @ManyToOne
    @JoinColumn(name = "printing_press_id")
    private PrintingPress printingPress;
}
