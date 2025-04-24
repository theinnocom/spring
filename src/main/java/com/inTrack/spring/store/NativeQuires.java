package com.inTrack.spring.store;


public class NativeQuires {

    public static final String GET_ALL_ACTIVE_USER = "select * from inTrack.`user` u join inTrack.user_info ui on ui.user_id = u.user_id join inTrack.user_role ur on ur.role_id = u.role_id left join inTrack.subscription s on s.id = u.subscription_id left JOIN inTrack.user_facility_relation ufr on ufr.user_id = u.user_id left join inTrack.user_mail_permission ump on ump.user_id = u.user_id where u.is_profile_active = TRUE";
    public static final String GET_USER = "select * from inTrack.`user` u join inTrack.user_info ui on ui.user_id = u.user_id join inTrack.user_role ur on ur.role_id = u.role_id left join inTrack.subscription s on s.id = u.subscription_id left JOIN inTrack.user_facility_relation ufr on ufr.user_id = u.user_id left join inTrack.user_mail_permission ump on ump.user_id = u.user_id where u.is_profile_active = TRUE";
    public static final String GET_PBS_CAPACITY = "SELECT SUM(pbs.capacity) AS total_capacity, CASE WHEN LOWER(tl.`type`) LIKE '%above%' THEN 'Above Ground' WHEN LOWER(tl.`type`) LIKE '%under%' THEN 'Under Ground' ELSE 'Unknown' END AS tank_location_type FROM inTrack.petroleum_bulk_storage pbs JOIN inTrack.tank_location tl ON tl.id = pbs.tank_location_id JOIN inTrack.building b on b.building_id = pbs.building_id  WHERE pbs.pbs_number = ?1 and b.facility_id = ?2 GROUP BY tank_location_type";
    public static final String GET_VIOLATION = "select v.violation_id, v.violation_no, v.issued_date, v.hearing_date, v.penalty_imposed, v.hearing_status_id, v.note from inTrack.violation v where hearing_date BETWEEN ?1 and ?2 and CASE when ?3 then hearing_status_id = ?5 else true end and case when ?4 then issuing_agency_id = ?6 else true end order by hearing_date DESC";
}
