package com.inTrack.spring.service;

import com.inTrack.spring.dto.request.LoginReqDTO;
import com.inTrack.spring.dto.request.SignUpReqDTO;
import com.inTrack.spring.dto.request.UserReqDTO;
import com.inTrack.spring.dto.response.UserResDTO;
import com.inTrack.spring.dto.response.UserRoleResDTO;
import com.inTrack.spring.entity.User;
import com.inTrack.spring.entity.UserInfo;
import com.inTrack.spring.entity.UserRole;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.UserInfoRepository;
import com.inTrack.spring.repository.UserRepository;
import com.inTrack.spring.repository.UserRoleRepository;
import com.inTrack.spring.security.JwtTokenUtil;
import com.inTrack.spring.store.ApplicationMessageStore;
import com.inTrack.spring.store.ConstantStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vijayan
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ValidatorService validatorService;

    @Transactional
    public void createUser(final SignUpReqDTO signUpReqDTO) {
        User user = this.userRepository.findByEmailOrUserName(signUpReqDTO.getEmail(), signUpReqDTO.getUserName());
        if (user != null) {
            throw new ValidationError(ApplicationMessageStore.USER_ALREADY_EXISTS);
        } else {
            user = new User();
        }
        user.setEmail(signUpReqDTO.getEmail().toUpperCase());
        user.setUserName(signUpReqDTO.getUserName().toUpperCase());
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
        final UserRole userRole = new UserRole();
        userRole.setRoleId(signUpReqDTO.getUserRole().getRoleId());
        userRole.setRole(signUpReqDTO.getUserRole().getRole());
        user.setRole(userRole);
        user.setIsProfileActive(signUpReqDTO.isProfileActive());
        user.setCreatedAt(System.currentTimeMillis());
        this.userRepository.save(user);
        final UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(signUpReqDTO.getFirstName());
        userInfo.setLastName(signUpReqDTO.getLastName());
        userInfo.setCountry(signUpReqDTO.getCountry());
        userInfo.setCity(signUpReqDTO.getCity());
        userInfo.setZipcode(signUpReqDTO.getZipcode());
        userInfo.setCountryCode(signUpReqDTO.getCountryCode());
        userInfo.setCreatedAt(System.currentTimeMillis());
        userInfo.setUser(user);
        this.userInfoRepository.save(userInfo);
    }

    public UserResDTO signInUser(final LoginReqDTO userReqVO) {
        final UserResDTO userResDTO = new UserResDTO();
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userReqVO.getUserName(), userReqVO.getPassword()));
            final User user = this.userRepository.findByEmailOrUserName(userReqVO.getUserName(), userReqVO.getUserName());
            if (user == null) {
                throw new ValidationError("Invalid user");
            }
            final UserInfo userInfo = this.userInfoRepository.findByUser(user);
            userResDTO.setValue(this.jwtTokenUtil.generateToken(user));
            userResDTO.setType("Bearer");
            userResDTO.setUserId(user.getUserId());
            userResDTO.setEmail(user.getEmail());
            userResDTO.setUserName(user.getUserName());
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

    public void deleteUser(final Long userId) {
        final User validateUser = this.validatorService.validateUser();
        final User user = this.validatorService.validateUser(userId);
        user.setIsProfileActive(false);
        this.userRepository.save(user);
    }

    public UserResDTO getUserById(final Long userId) {
        final User user = this.validatorService.validateUser(userId);
        final UserInfo userInfo = this.userInfoRepository.findByUser(user);
        return this.setUserDetails(user, userInfo);
    }

    private UserResDTO setUserDetails(final User user, final UserInfo userInfo) {
        final UserResDTO userResDTO = new UserResDTO();
        userResDTO.setUserId(user.getUserId());
        userResDTO.setEmail(user.getEmail());
        userResDTO.setUserName(user.getUserName());
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
}
