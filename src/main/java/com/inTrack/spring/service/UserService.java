package com.inTrack.spring.service;

import com.inTrack.spring.dto.UserFacilityRelationDTO;
import com.inTrack.spring.dto.requestDTO.*;
import com.inTrack.spring.dto.responseDTO.EnquiryFormResDTO;
import com.inTrack.spring.dto.responseDTO.UserResDTO;
import com.inTrack.spring.dto.responseDTO.UserRoleResDTO;
import com.inTrack.spring.entity.*;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.*;
import com.inTrack.spring.repository.equipmentRepository.EquipmentRepository;
import com.inTrack.spring.security.JwtTokenUtil;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserInfoRepository userInfoRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final ValidatorService validatorService;
    private final EnquiryFormRepository enquiryFormRepository;
    private final UserFacilityRelationRepository userFacilityRelationRepository;
    private final FacilityRepository facilityRepository;
    private final CertificateOfFitnessRepository certificateOfFitnessRepository;
    private final UserMailPermissionRepository userMailPermissionRepository;
    private final BuildingRepository buildingRepository;
    private final EquipmentRepository equipmentRepository;
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Create a new user
     *
     * @param signUpReqDTO
     */
    @Transactional
    public void createUser(final SignUpReqDTO signUpReqDTO) {
        User user = this.userRepository.findByEmailOrLoginId(signUpReqDTO.getEmail(), signUpReqDTO.getLoginId());
        if (user != null) {
            throw new ValidationError(ApplicationMessageStore.USER_ALREADY_EXISTS);
        } else {
            user = new User();
        }
        user.setEmail(signUpReqDTO.getEmail().toUpperCase());
        user.setLoginId(signUpReqDTO.getLoginId().toUpperCase());
        user.setPassword(this.jwtTokenUtil.passwordEncoder().encode(signUpReqDTO.getPassword()));
        user.setDeletePermission(signUpReqDTO.isDeletePermission());
        user.setUpdatePermission(signUpReqDTO.isUpdatePermission());
        user.setAlternateMail(signUpReqDTO.getAlternateMail());
        /**
         * User type 1 indicates Internal
         * User type 2 indicates External
         * default user type value is External
         */
        user.setUserType(signUpReqDTO.getUserType() == 1 ? ConstantStore.INTERNAL : ConstantStore.EXTERNAL);
        user.setMustChangePassword(signUpReqDTO.isMustChangePassword());
        user.setRole(!ObjectUtils.isEmpty(signUpReqDTO.getUserRole()) ? new UserRole().setRoleId(signUpReqDTO.getUserRole()) : null);
        user.setIsProfileActive(signUpReqDTO.isProfileActive());
        user.setCreatedAt(System.currentTimeMillis());
        user.setProposal(signUpReqDTO.getProposal());
        user.setRegistration(signUpReqDTO.getRegistration());
        user.setAlternateMail(signUpReqDTO.getAlternateMail());
        if (signUpReqDTO.getSubscriptionId() != null) {
            user.setSubscription(new Subscription().setId(signUpReqDTO.getSubscriptionId()));
        }
        final User userObj = this.userRepository.save(user);
        /**
         * Save User info
         */
        final UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(signUpReqDTO.getFirstName());
        userInfo.setLastName(signUpReqDTO.getLastName());
        userInfo.setCountry(signUpReqDTO.getCountry());
        userInfo.setCity(signUpReqDTO.getCity());
        userInfo.setZipcode(signUpReqDTO.getZipcode());
        userInfo.setCountryCode(signUpReqDTO.getCountryCode());
        userInfo.setCreatedAt(System.currentTimeMillis());
        userInfo.setMobileNumber(signUpReqDTO.getMobileNumber());
        userInfo.setAlternateMobileNumber(signUpReqDTO.getAlternateMobileNumber());
        userInfo.setUser(user);
        this.userInfoRepository.save(userInfo);
//        if (userObj.getRole().getRole().equalsIgnoreCase(ApplicationRoleStore.ENGINEER)) {
//        this.saveCertificateOfFitness(signUpReqDTO.getCertificateOfFitnessReqDTOS(), userObj);
//        }
        /**
         * Create user facility relation
         */
        final List<UserFacilityRelation> userFacilityRelations = new LinkedList<>();
        long currentTimeMillis = System.currentTimeMillis();
        if (signUpReqDTO.getUserFacilityRelations() != null) {
            final List<UserFacilityRelationDTO> userFacilityRelationList = signUpReqDTO.getUserFacilityRelations();
            userFacilityRelationList.forEach(userFacilityRelationDto -> {
                final UserFacilityRelation userFacilityRelation = new UserFacilityRelation();
                final Facility facility = this.facilityRepository.findByFacilityId(userFacilityRelationDto.getFacility());
                if (facility == null) {
                    throw new ValidationError(ApplicationMessageStore.FACILITY_NOT_FOUND);
                }
                userFacilityRelation.setFacility(facility);
                userFacilityRelation.setUser(userObj);
                final Building building = this.buildingRepository.findByBuildingId(userFacilityRelationDto.getBuilding());
                if (building == null) {
                    throw new ValidationError(ApplicationMessageStore.BUILDING_NOT_FOUND);
                }
                userFacilityRelation.setBuilding(building);
                if (userFacilityRelationDto.getEquipmentName() != null) {
                    final Equipment equipment = this.equipmentRepository.findByEquipmentId(userFacilityRelationDto.getEquipmentName());
                    if (equipment == null) {
                        throw new ValidationError(ApplicationMessageStore.EQUIPMENT_NOT_FOUND);
                    }
                    userFacilityRelation.setEquipmentName(equipment);
                }
                userFacilityRelation.setCreatedAt(currentTimeMillis);
                userFacilityRelation.setUpdatedAt(currentTimeMillis);
                userFacilityRelations.add(userFacilityRelation);
            });
        }
        if (signUpReqDTO.getUserMailPermission() != null) {
            final UserMailPermission userMailPermission = signUpReqDTO.getUserMailPermission();
            userMailPermission.setUser(userObj);
            this.userMailPermissionRepository.save(userMailPermission);
        }
        this.userFacilityRelationRepository.saveAllAndFlush(userFacilityRelations);
    }

    public void createEnquiryForm(final EnquiryFormReqDTO enquiryFormReqDTO) {
        final EnquiryForm enquiryForm = new EnquiryForm();
        enquiryForm.setEmail(enquiryFormReqDTO.getEmail().toUpperCase());
        enquiryForm.setFirstName(enquiryFormReqDTO.getFirstName());
        enquiryForm.setLastName(enquiryFormReqDTO.getLastName());
        enquiryForm.setOrganization(enquiryForm.getOrganization());
        enquiryForm.setTitle(enquiryFormReqDTO.getTitle());
        enquiryForm.setCompliedWith(enquiryFormReqDTO.getCompliedWith());
        enquiryForm.setAddress(enquiryFormReqDTO.getAddress());
        enquiryForm.setZipcode(enquiryFormReqDTO.getZipCode());
        enquiryForm.setMobileNumber(enquiryFormReqDTO.getMobileNumber());
        enquiryForm.setAlternateMobileNumber(enquiryFormReqDTO.getAlterMobileNumber());
        enquiryForm.setCity(enquiryFormReqDTO.getCity());
        enquiryForm.setCreatedAt(System.currentTimeMillis());
        enquiryForm.setDesignation(enquiryFormReqDTO.getDesignation());
        this.enquiryFormRepository.save(enquiryForm);
    }

    public UserResDTO signInUser(final LoginReqDTO userReqVO) {
        final UserResDTO userResDTO = new UserResDTO();
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userReqVO.getUserName(), userReqVO.getPassword()));
            final User user = this.userRepository.findByEmailOrLoginId(userReqVO.getUserName(), userReqVO.getUserName());
            if (user == null) {
                throw new ValidationError("Invalid user");
            }
            final UserInfo userInfo = this.userInfoRepository.findByUser(user);
            userResDTO.setValue(this.jwtTokenUtil.generateToken(user));
            userResDTO.setType("Bearer");
            userResDTO.setUserId(user.getUserId());
            userResDTO.setEmail(user.getEmail());
            userResDTO.setLoginId(user.getLoginId());
            userResDTO.setRole(user.getRole().getRole());
            userResDTO.setDeletePermission(user.getDeletePermission());
            if (userInfo != null) {
                userResDTO.setCity(userInfo.getCity());
                userResDTO.setCountryCode(userInfo.getCountryCode());
                userResDTO.setLastName(userInfo.getLastName());
                userResDTO.setFirstName(userInfo.getFirstName());
                userResDTO.setZipcode(userInfo.getZipcode());
            }
            return userResDTO;
        } catch (final BadCredentialsException e) {
            throw new ValidationError(ApplicationMessageStore.BAD_CREDENTIALS);
        } catch (final AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    public UserResDTO updateUserDetails(final UserReqDTO userReqDTO) {
        final User user;
        if (userReqDTO.getUserId() != null) {
            user = this.userRepository.findByUserId(userReqDTO.getUserId());
        } else {
            user = this.validatorService.validateUser();
        }
        if (user == null) {
            throw new ValidationError(ApplicationMessageStore.USER_NOT_FOUND);
        }
        final UserRole userRole = new UserRole();
        userRole.setRoleId(userReqDTO.getUserRole().getRoleId());
        userRole.setRole(userReqDTO.getUserRole().getRole());
        UserInfo userInfo = this.userInfoRepository.findByUser(user);
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        user.setRole(userRole);
        user.setDeletePermission(userReqDTO.isDeletePermission());
        user.setIsProfileActive(userReqDTO.isProfileActive());
        user.setExpiryDate(userReqDTO.getExpiryDate());
        user.setUpdatedAt(System.currentTimeMillis());
        user.setUpdatePermission(userReqDTO.isUpdatePermission());
        user.setAlternateMail(userReqDTO.getAlternateMail());
        /**
         * User type 1 indicates Internal
         * User type 2 indicates External
         * default user type value is External
         */
        user.setUserType(userReqDTO.getUserType() == 2 ? ConstantStore.EXTERNAL : userReqDTO.getUserType() == 1 ? ConstantStore.INTERNAL : ConstantStore.EXTERNAL);
        this.userRepository.save(user);
        userInfo.setUser(user);
        userInfo.setFirstName(userReqDTO.getFirstName());
        userInfo.setLastName(userReqDTO.getLastName());
        userInfo.setCountry(userReqDTO.getCountry());
        userInfo.setCity(userReqDTO.getCity());
        userInfo.setZipcode(userReqDTO.getZipcode());
        userInfo.setCountryCode(userReqDTO.getCountryCode());
        userInfo.setUpdatedAt(System.currentTimeMillis());
        userInfo.setCreatedAt(System.currentTimeMillis());
        this.userInfoRepository.save(userInfo);
        return this.setUserDetails(user, userInfo);
    }


    public List<UserResDTO> getAllActiveUser() {
        final List<Object[]> users = this.userRepository.findByIsProfileActive(true);
        final List<UserResDTO> userResDTOS = new LinkedList<>();
        users.forEach(user -> {
            final UserResDTO userResDTO = new UserResDTO();
            userResDTO.setUserId(!ObjectUtils.isEmpty(user[0]) ? Long.parseLong(user[0].toString()) : null);
            userResDTO.setUserRole(!ObjectUtils.isEmpty(user[7]) ? Long.parseLong(user[7].toString()) : null);
            userResDTO.setEmail(!ObjectUtils.isEmpty(user[1]) ? user[1].toString() : null);
            userResDTO.setIsProfileActive(!ObjectUtils.isEmpty(user[4]) ? Boolean.parseBoolean(user[4].toString()) : null);
            userResDTO.setLoginId(!ObjectUtils.isEmpty(user[3]) ? user[3].toString() : null);
            userResDTO.setDeletePermission(!ObjectUtils.isEmpty(user[5]) && Boolean.parseBoolean(user[5].toString()));
            userResDTO.setFirstName(!ObjectUtils.isEmpty(user[19]) ? user[19].toString() : null);
            userResDTO.setLastName(!ObjectUtils.isEmpty(user[20]) ? user[20].toString() : null);
            userResDTO.setCountry(!ObjectUtils.isEmpty(user[23]) ? user[23].toString() : null);
            userResDTO.setCountryCode(!ObjectUtils.isEmpty(user[22]) ? user[22].toString() : null);
            userResDTO.setCity(!ObjectUtils.isEmpty(user[21]) ? user[21].toString() : null);
            userResDTO.setZipcode(!ObjectUtils.isEmpty(user[24]) ? user[24].toString() : null);
            userResDTO.setAddress(!ObjectUtils.isEmpty(user[25]) ? user[25].toString() : null);
            userResDTO.setExpiryDate(!ObjectUtils.isEmpty(user[6]) ? Long.parseLong(user[6].toString()) : null);
            userResDTO.setAlternateMail(!ObjectUtils.isEmpty(user[12]) ? user[12].toString() : null);
            userResDTO.setUserType(!ObjectUtils.isEmpty(user[10]) ? user[10].toString() : null);
            userResDTO.setSubscriptionId(!ObjectUtils.isEmpty(user[17]) ? Long.parseLong(user[17].toString()) : null);
            userResDTO.setDesignation(!ObjectUtils.isEmpty(user[14]) ? user[14].toString() : null);
            userResDTO.setRegistration(!ObjectUtils.isEmpty(user[15]) ? user[15].toString() : null);
            userResDTO.setProposal(!ObjectUtils.isEmpty(user[16]) ? Boolean.valueOf(user[16].toString()) : null);
            userResDTO.setMobileNumber(!ObjectUtils.isEmpty(user[26]) ? user[26].toString() : null);
            userResDTO.setAlternateMobileNumber(!ObjectUtils.isEmpty(user[27]) ? user[27].toString() : null);
            userResDTOS.add(userResDTO);
        });
        return userResDTOS;
    }

    public UserResDTO getUserByUserId(final Long userId) {
        final User user = this.userRepository.findByUserId(userId);
        final List<UserFacilityRelation> userFacilityRelation = this.userFacilityRelationRepository.findByUser(user);
        final UserMailPermission userMailPermission = this.userMailPermissionRepository.findByUser(user);
        final UserInfo userInfo = this.userInfoRepository.findByUser(user);
        final UserResDTO userResDTO = new UserResDTO();
        userResDTO.setUserId(user.getUserId());
        userResDTO.setUserRole(user.getRole().getRoleId());
        userResDTO.setEmail(user.getEmail());
        userResDTO.setIsProfileActive(user.getIsProfileActive());
        userResDTO.setLoginId(user.getLoginId());
        userResDTO.setDeletePermission(user.getDeletePermission());
        userResDTO.setFirstName(user.getUserType());
        userResDTO.setLastName(user.getUserType());
        userResDTO.setCountry(userInfo.getCountry());
        userResDTO.setCountryCode(userInfo.getCountryCode());
        userResDTO.setCity(userInfo.getCity());
        userResDTO.setZipcode(userInfo.getZipcode());
        userResDTO.setAddress(userInfo.getAddress());
        userResDTO.setExpiryDate(user.getExpiryDate());
        userResDTO.setAlternateMail(user.getAlternateMail());
        userResDTO.setUserType(user.getUserType());
        userResDTO.setSubscriptionId(user.getSubscription().getId());
        userResDTO.setDesignation(user.getDesignation());
        userResDTO.setRegistration(user.getRegistration());
        userResDTO.setProposal(user.getProposal());
        userResDTO.setMobileNumber(userInfo.getMobileNumber());
        userResDTO.setAlternateMobileNumber(userInfo.getAlternateMobileNumber());
        userResDTO.setUserMailPermission(userMailPermission);
        userResDTO.setUserFacilityRelations(this.setUserFacilityRelationDTOS(userFacilityRelation));
        return userResDTO;
    }

    public List<UserRoleResDTO> getUserRoles() {
        final List<UserRoleResDTO> userRoleResDTOList = new LinkedList<>();
        final List<UserRole> userRoleList = this.userRoleRepository.findAll();
        userRoleList.forEach(userRole -> {
            final UserRoleResDTO userRoleResDTO = new UserRoleResDTO();
            userRoleResDTO.setRoleId(userRole.getRoleId());
            userRoleResDTO.setRole(userRole.getRole());
            userRoleResDTOList.add(userRoleResDTO);
        });
        return userRoleResDTOList;
    }

    public List<EnquiryFormResDTO> getAllInActiveUser() {
        final List<EnquiryForm> enquiryForms = this.enquiryFormRepository.findByIsProfileActive(false);
        final List<EnquiryFormResDTO> enquiryFormResDTOS = new LinkedList<>();
        enquiryForms.forEach(enquiryForm -> {
            final EnquiryFormResDTO enquiryFormDTO = new EnquiryFormResDTO();
            enquiryFormDTO.setEnquiryId(enquiryForm.getEnquiryId());
            enquiryFormDTO.setEmail(enquiryForm.getEmail());
            enquiryFormDTO.setFirstName(enquiryForm.getFirstName());
            enquiryFormDTO.setLastName(enquiryForm.getLastName());
            enquiryFormDTO.setCompliedWith(enquiryForm.getCompliedWith());
            enquiryFormDTO.setTitle(enquiryForm.getTitle());
            enquiryFormDTO.setOrganization(enquiryForm.getOrganization());
            enquiryFormDTO.setIsProfileActive(enquiryForm.getIsProfileActive());
            enquiryFormDTO.setCity(enquiryForm.getCity());
            enquiryFormDTO.setAddress(enquiryForm.getAddress());
            enquiryFormDTO.setMobileNumber(enquiryForm.getMobileNumber());
            enquiryFormDTO.setAlterMobileNumber(enquiryForm.getMobileNumber());
            enquiryFormDTO.setZipCode(enquiryForm.getZipcode());
            enquiryFormDTO.setDesignation(enquiryForm.getDesignation());
            enquiryFormResDTOS.add(enquiryFormDTO);
        });
        return enquiryFormResDTOS;
    }

    public void updateEnquiryFormActiveStatus(final Boolean activeStatus, final Long id) {
        final User user = this.validatorService.validateUser();
        final EnquiryForm enquiryForm = this.enquiryFormRepository.findByEnquiryId(id);
        enquiryForm.setIsProfileActive(activeStatus ? true : null);
        enquiryForm.setUpdatedBy(user);
        this.enquiryFormRepository.save(enquiryForm);
    }

    public UserResDTO setUserDetails(final User user, final UserInfo userInfo) {
        final UserResDTO userResDTO = new UserResDTO();
        userResDTO.setUserId(user.getUserId());
        userResDTO.setEmail(user.getEmail());
        userResDTO.setLoginId(user.getLoginId());
        userResDTO.setDeletePermission(user.getDeletePermission());
        userResDTO.setIsProfileActive(user.getIsProfileActive());
        userResDTO.setRole(user.getRole().getRole());
        userResDTO.setFirstName(userInfo.getFirstName());
        userResDTO.setLastName(userInfo.getLastName());
        userResDTO.setCountry(userInfo.getCountry());
        userResDTO.setCity(userInfo.getCity());
        userResDTO.setZipcode(userInfo.getZipcode());
        userResDTO.setExpiryDate(user.getExpiryDate());
        return userResDTO;
    }

    @Transactional
    private void saveCertificateOfFitness(final List<CertificateOfFitnessReqDTO> certificateOfFitnessReqDTOList, final User user) {
        final List<String> certificateNumberList = certificateOfFitnessReqDTOList.stream()
                .map(CertificateOfFitnessReqDTO::getCertificateNumber)
                .toList();
        final List<String> certificateOfFitnesses = this.certificateOfFitnessRepository.findByCertificateNumberIn(certificateNumberList);
        if (!ObjectUtils.isEmpty(certificateOfFitnesses)) {
            throw new ValidationError(ApplicationMessageStore.placeHolderMessage(ApplicationMessageStore.CERTIFICATE_NUMBER_EXIST, certificateNumberList));
        }
        final List<CertificateOfFitness> certificateOfFitnessList = new LinkedList<>();
        final List<String> certificateNumbers = new LinkedList<>();
        certificateOfFitnessReqDTOList.forEach(certificateOfFitnessReqDTO -> {
            final CertificateOfFitness certificateOfFitness = new CertificateOfFitness();
            if (certificateNumbers.contains(certificateOfFitnessReqDTO.getCertificateNumber())) {
                throw new ValidationError(ApplicationMessageStore.placeHolderMessage(ApplicationMessageStore.CERTIFICATE_NUMBER_DUPLICATE, certificateOfFitnessReqDTO.getCertificateNumber()));
            }
            certificateNumbers.add(certificateOfFitnessReqDTO.getCertificateNumber());
            certificateOfFitness.setCertificateNumber(certificateOfFitnessReqDTO.getCertificateNumber());
            certificateOfFitness.setType(certificateOfFitnessReqDTO.getType());
            certificateOfFitness.setIssuedDate(certificateOfFitnessReqDTO.getIssuedDate());
            certificateOfFitness.setExpiryDate(certificateOfFitnessReqDTO.getExpiryDate());
            certificateOfFitness.setDescription(certificateOfFitnessReqDTO.getDescription());
            certificateOfFitness.setIsActive(certificateOfFitnessReqDTO.getIsActive());
            certificateOfFitness.setUser(user);
            certificateOfFitnessList.add(certificateOfFitness);
        });
        this.certificateOfFitnessRepository.saveAll(certificateOfFitnessList);
    }

    private List<UserFacilityRelationDTO> setUserFacilityRelationDTOS(final List<UserFacilityRelation> userFacilityRelations) {
        List<UserFacilityRelationDTO> userFacilityRelationDTOS = new LinkedList<>();
        if (!ObjectUtils.isEmpty(userFacilityRelations)) {
            userFacilityRelations.forEach(userFacilityRelation -> {
                final UserFacilityRelationDTO userFacilityRelationDTO = new UserFacilityRelationDTO();
                userFacilityRelationDTO.setFacility(!ObjectUtils.isEmpty(userFacilityRelation.getFacility()) ? userFacilityRelation.getFacility().getFacilityId() : null);
                userFacilityRelationDTO.setBuilding(!ObjectUtils.isEmpty(userFacilityRelation.getBuilding()) ? userFacilityRelation.getBuilding().getBuildingId() : null);
                userFacilityRelationDTO.setEquipmentName(!ObjectUtils.isEmpty(userFacilityRelation.getEquipmentName()) ? userFacilityRelation.getEquipmentName().getEquipmentId() : null);
                userFacilityRelationDTOS.add(userFacilityRelationDTO);
            });
        }
        return userFacilityRelationDTOS;
    }

    public List<Subscription> getUserSubscription() {
        return this.subscriptionRepository.findAll();
    }
}
