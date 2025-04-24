package com.inTrack.spring.controller;


import com.inTrack.spring.dto.requestDTO.EnquiryFormReqDTO;
import com.inTrack.spring.dto.requestDTO.LoginReqDTO;
import com.inTrack.spring.dto.requestDTO.SignUpReqDTO;
import com.inTrack.spring.dto.requestDTO.UserReqDTO;
import com.inTrack.spring.dto.responseDTO.ResponseDTO;
import com.inTrack.spring.service.UserService;
import com.inTrack.spring.store.ApplicationMessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author vijayan
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    private static final ResponseDTO responseDTO = new ResponseDTO();

    @PostMapping(value = "/user")
    public ResponseDTO createUser(@RequestBody final SignUpReqDTO signUpReqDTO) {
        this.userService.createUser(signUpReqDTO);
        return super.success(responseDTO, null, ApplicationMessageStore.USER_CREATE_SUCCESS);
    }

    @PostMapping(value = "/user/enquiry-form")
    public ResponseDTO createEnquiryForm(@RequestBody final EnquiryFormReqDTO enquiryFormReqDTO) {
        this.userService.createEnquiryForm(enquiryFormReqDTO);
        return super.success(responseDTO, null, ApplicationMessageStore.ENQUIRY_CREATE_SUCCESS);
    }

    @PostMapping(value = "/user/authenticate")
    public ResponseDTO authenticateUser(@RequestBody final LoginReqDTO userReqVO) {
        return super.success(responseDTO, userService.signInUser(userReqVO), ApplicationMessageStore.LOGIN_SUCCESS);
    }

    @PutMapping(value = "/user")
    public ResponseDTO updateUserDetails(@RequestBody final UserReqDTO userReqDTO) {
        return super.success(responseDTO, this.userService.updateUserDetails(userReqDTO), ApplicationMessageStore.USER_UPDATE_SUCCESS);

    }

    @GetMapping(value = "/user/roles")
    public ResponseDTO getUserRoles() {
        return super.success(responseDTO, this.userService.getUserRoles(), ApplicationMessageStore.USER_ROLE_SUCCESS);
    }

    @GetMapping(value = "/user/enquiry/in-active")
    public ResponseDTO getAllInActiveUser() {
        return super.success(responseDTO, this.userService.getAllInActiveUser(), ApplicationMessageStore.USER_FETCH_SUCCESS);
    }

    @PutMapping(value = "/user/enquiry-form/{id}")
    public ResponseDTO updateEnquiryFormActiveStatus(@RequestParam final Boolean activeStatus, @PathVariable final Long id) {
        this.userService.updateEnquiryFormActiveStatus(activeStatus, id);
        return super.success(responseDTO, null, ApplicationMessageStore.USER_UPDATE_SUCCESS);
    }

    @GetMapping(value = "/user")
    public ResponseDTO getAllActiveUser() {
        return super.success(responseDTO, this.userService.getAllActiveUser(), ApplicationMessageStore.USER_FETCH_SUCCESS);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseDTO getActiveUser(@PathVariable final Long userId) {
        return super.success(responseDTO, this.userService.getUserByUserId(userId), ApplicationMessageStore.USER_FETCH_SUCCESS);
    }

    @GetMapping(value = "/user/subscription")
    public ResponseDTO getUserSubscription() {
        return super.success(responseDTO, this.userService.getUserSubscription(), ApplicationMessageStore.USER_FETCH_SUCCESS);
    }
}
