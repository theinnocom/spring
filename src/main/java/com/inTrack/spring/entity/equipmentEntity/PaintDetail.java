package com.inTrack.spring.entity.equipmentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "paint_detail")
public class PaintDetail {

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

    @Column(name = "voc")
    private String voc;

    @ManyToOne
    @JoinColumn(name = "paint_id")
    private PaintSprayBooth paintSprayBooth;
}
