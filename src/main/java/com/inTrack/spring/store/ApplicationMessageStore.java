package com.inTrack.spring.store;

import java.text.MessageFormat;

/**
 * @author vijayan
 */

public class ApplicationMessageStore {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String GENERIC_ERROR = "Something went wrong, Please try again";
    public static final String BAD_REQUEST = "Bad request";
    public static final String BAD_CREDENTIALS = "Invalid email or password";
    public static final String ACCESS_DENIED = "Access denied for this request";

    public static final String ACCESS_DENIED_DUE_TO_EXPIRED_TOKEN = "Access denied for this request since the token is expired";
    public static final String USER_NOT_VERIFIED = "Your account has been deactivated or not activated. Please reach out to support for assistance.";
    public static final String USER_ALREADY_EXISTS = "User has been already registered";
    public static final MessageFormat CORRUPTED_ID = new MessageFormat("Given id is corrupted, Id: {0}");
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ELEVATOR_NOT_FOUND = "Elevator not found";

    /**
     * Success message
     */
    public static final String USER_CREATE_SUCCESS = "User created successfully";
    public static final String USER_UPDATE_SUCCESS = "User updated successfully";
    public static final String USER_FETCH_SUCCESS = "User fetched successfully";
    public static final String ENQUIRY_CREATE_SUCCESS = "Enquiry created successfully";
    public static final String LOGIN_SUCCESS = "Logged in successfully";
    public static final String USER_ROLE_SUCCESS = "User role fetched successfully";
    public static final String CERTIFICATE_FETCH_SUCCESS = "Certificate fetched successfully";
    public static final String CERTIFICATE_CREATE_SUCCESS = "Certificate created successfully";
    public static final String CERTIFICATE_UPDATE_SUCCESS = "Certificate updated successfully";
    public static final String CERTIFICATE_DELETE_SUCCESS = "Certificate removed successfully";
    public static final String FACILITY_CREATE_SUCCESS = "Facility created successfully";
    public static final String FACILITY_UPDATE_SUCCESS = "Facility updated successfully";
    public static final String FACILITY_FETCH_SUCCESS = "Facility fetched successfully";
    public static final String FACILITY_DELETE_SUCCESS = "Facility removed successfully";
    public static final String BUILDING_CREATE_SUCCESS = "Building created successfully";
    public static final String BUILDING_FETCH_SUCCESS = "Building fetched successfully";
    public static final String BUILDING_UPDATE_SUCCESS = "Building updated successfully";
    public static final String BUILDING_REMOVE_SUCCESS = "Building removed successfully";
    public static final String FUEL_CONSUMPTION_CREATE_SUCCESS = "Fuel consumption created successfully";
    public static final String FUEL_CONSUMPTION_FETCH_SUCCESS = "Fuel consumption fetched successfully";
    public static final String FUEL_CONSUMPTION_UPDATE_SUCCESS = "Fuel consumption updated successfully";
    public static final String FUEL_CONSUMPTION_DELETE_SUCCESS = "Fuel consumption deleted successfully";
    public static final String FUEL_TYPE_FETCH_SUCCESS = "Fuel type fetched successfully";
    public static final String VIOLATION_CREATE_SUCCESS = "Violation created successfully";
    public static final String VIOLATION_FETCH_SUCCESS = "Violation fetched successfully";
    public static final String VIOLATION_UPDATE_SUCCESS = "Violation update successfully";
    public static final String VIOLATION_DELETE_SUCCESS = "Violation delete successfully";
    public static final String FACADE_CREATE_SUCCESS = "Facade created successfully";
    public static final String FACADE_FETCH_SUCCESS = "Facade fetched successfully";
    public static final String FACADE_UPDATE_SUCCESS = "Facade update successfully";
    public static final String FACADE_DELETE_SUCCESS = "Facade delete successfully";
    public static final String AGENCIES_FETCH_SUCCESS = "Agencies fetched successfully";
    public static final String VIOLATION_COUNT_FETCH_SUCCESS = "Violation count fetched successfully";
    public static final String EQUIPMENT_FETCH_SUCCESS = "Equipment fetched successfully";


    /**
     * Error message
     */
    public static final String FACILITY_NOT_FOUND = "Facility not found";
    public static final String CERTIFICATE_NOT_FOUND = "Certificate not found";
    public static final String BOROUGH_NOT_FOUND = "Borough not found";
    public static final String DATA_ALREADY_EXIST = "Mandatory data fields already exists";
    public static final String BUILDING_NOT_FOUND = "Building not found";
    public static final String BOILER_NOT_FOUND = "Boiler not found";
    public static final String DATA_NOT_FOUND = "Data not found";
    public static final String FUEL_CONSUMPTION_NOT_FOUND = "Fuel consumption not found";
    public static final String VIOLATION_NOT_FOUND = "Violation not found";
    public static final String FACADE_NOT_FOUND = "Facade not found";
    public static final String FUEL_TYPE_NOT_FOUND = "Fuel type not found";
    public static final MessageFormat FUEL_CONSUMPTION_ALREADY_EXISTS = new MessageFormat("Fuel consumption record for {0} is already exists for this year: {1}");
    public static final String ISSUING_AGENCY_MANDATORY = "Issuing agency is mandatory";
    public static final String EQUIPMENT_TYPE_NOT_MATCH = "Equipment type not matched";
    public static final String UNIQUE_ID_EXISTS = "unique id is exists with another Equipment";
    public static final String JOB_FILLING_ID_EXISTS = "job filling id exists with another Equipment";
    public static final String UNIQUE_ID_AND_JOB_FILLING_ID_EXISTS = "unique id and job filling id exists with another Equipment";
    public static final String USER_IN_ACTIVE_SUCCESS = "User in-active successfully";

    public static String placeHolderMessage(final MessageFormat messageFormat, final Object... args) {
        return messageFormat.format(args);
    }
}
