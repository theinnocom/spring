package com.inTrack.spring.entity.equipmentEntity.agencyEntity;

import com.inTrack.spring.entity.InspectionDurationType;
import com.inTrack.spring.entity.ManagementType;
import com.inTrack.spring.entity.equipmentEntity.*;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.BoilerCogenAgency;
import com.inTrack.spring.entity.equipmentEntity.agencyEntity.agencyDropDown.InspectionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "boiler_cogen_agency_info")
@Getter
@Setter
public class BoilerCogenAgencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "agency_number")
    private String agencyNumber;

    @Column(name = "issue_date")
    private Long issueDate;

    @Column(name = "expiration_date")
    private Long expirationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "submitted_date")
    private Long submittedDate;

    @Column(name = "note")
    private String note;

    @Column(name = "last_inspection_date")
    private Long lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @Column(name = "inspected_by")
    private String inspectedBy;

    @Column(name = "fd_date")
    private Long fdDate;

    @Column(name = "date_performed")
    private Long datePerformed;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "is_record_kept")
    private Boolean isRecordKept;

    @Column(name = "issued_date")
    private Long issuedDate;  //state permit

    @Column(name = "is_paint_spray_booth")
    private Boolean isPaintSprayBooth;

    @Column(name = "is_inspection_tag")
    private Boolean isInspectionTag;

    @Column(name = "DOB_issue_date")
    private Long dobIssueDate;

    @Column(name = "last_test_date")
    private Long lastTestDate;

    @Column(name = "next_test_date")
    private Long nextTestDate;

    @Column(name = "test_done_by")
    private String testDoneBy;

    @Column(name = "fdny_certificate_no")
    private String fdnyCertificateNo;

    @Column(name = "firm_inspected")
    private String firmInspected;

    @ManyToOne
    @JoinColumn(name = "inspection_duration_type_id")
    private InspectionDurationType inspectionDurationType;

    @ManyToOne
    @JoinColumn(name = "boiler_cogen_agency_id")
    private BoilerCogenAgency boilerCogenAgency;

    @ManyToOne
    @JoinColumn(name = "inspection_type_id")
    private InspectionType inspectionType;

    //Equipment mapping

    @ManyToOne
    @JoinColumn(name = "cogen_turbine_id")
    private CogenEngineTurbine cogenEngineTurbine; //complete

    @ManyToOne
    @JoinColumn(name = "fire_alarm_id")
    private FireAlarm fireAlarm; //complete

    @ManyToOne
    @JoinColumn(name = "fume_hood_id")
    private FumeHood fumeHood; //complete

    @ManyToOne
    @JoinColumn(name = "paint_spray_booth_id")
    private PaintSprayBooth paintSprayBooth; //complete

    @ManyToOne
    @JoinColumn(name = "boiler_id")
    private Boiler boiler; //complete

    @ManyToOne
    @JoinColumn(name = "kitchen_hood_id")
    private KitchenHoodFireSuppression kitchenHoodFireSuppression; //complete

    @ManyToOne
    @JoinColumn(name = "rpz_id")
    private RPZ rpz; //complete

    @ManyToOne
    @JoinColumn(name = "elevator_id")
    private Elevator elevator; //complete

    @ManyToOne
    @JoinColumn(name = "emergency_egress_id")
    private EmergencyEgressAndLighting emergencyEgress; //complete

    @ManyToOne
    @JoinColumn(name = "hydraulic_id")
    private HydraulicStorageTank hydraulicStorageTank;  //complete

    @ManyToOne
    @JoinColumn(name = "land_fill_id")
    private LandFill landFill; //complete

    @ManyToOne
    @JoinColumn(name = "printing_press_id")
    private PrintingPress printingPress; //complete

    @ManyToOne
    @JoinColumn(name = "portable_fire_id")
    private PortableFireExtinguisher portableFireExtinguisher;

}
