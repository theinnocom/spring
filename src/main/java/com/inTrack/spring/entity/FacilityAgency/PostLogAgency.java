package com.inTrack.spring.entity.FacilityAgency;


import com.inTrack.spring.entity.PostLog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post_log_agency")
public class PostLogAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annual_log_record_inspected")
    private boolean annualLogRecordInspected;

    @Column(name = "last_inspection_date")
    private Long lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Long nextInspectionDate;

    @Column(name = "inspected_by")
    private String inspectedBy;

    @Column(name = "note")
    private String note;

    @Column(name = "five_year_log_record_available")
    private boolean fiveYearLogRecordAvailable;

    @ManyToOne
    @JoinColumn(name = "post_log_id")
    private PostLog postLog;
}
