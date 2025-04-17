package com.inTrack.spring.controller;



import com.inTrack.spring.dto.common.ResponseDTO;
import com.inTrack.spring.dto.request.LoginReqDTO;
import com.inTrack.spring.dto.request.SignUpReqDTO;
import com.inTrack.spring.dto.request.UserReqDTO;
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

    @PostMapping(value = "/user")
    public ResponseDTO createUser(@RequestBody final SignUpReqDTO signUpReqDTO) {
        this.userService.createUser(signUpReqDTO);
        return super.success(new ResponseDTO(), null, ApplicationMessageStore.USER_CREATE_SUCCESS);
    }

    @GetMapping(value = "/user")
    public ResponseDTO getUser(final Long userId) {
        return super.success(new ResponseDTO(), this.userService.getUserById(userId), ApplicationMessageStore.USER_FETCH_SUCCESS);
    }

    @PostMapping(value = "/user/authenticate")
    public ResponseDTO authenticateUser(@RequestBody final LoginReqDTO userReqVO) {
        return super.success(new ResponseDTO(), userService.signInUser(userReqVO), ApplicationMessageStore.LOGIN_SUCCESS);
    }

    @PutMapping(value = "/user")
    public ResponseDTO updateUserDetails(@RequestBody final UserReqDTO userReqDTO) {
        return super.success(new ResponseDTO(), this.userService.updateUserDetails(userReqDTO), ApplicationMessageStore.USER_UPDATE_SUCCESS);

    }

    @GetMapping(value = "/user/roles")
    public ResponseDTO getUserRoles() {
        return super.success(new ResponseDTO(), this.userService.getUserRoles(), ApplicationMessageStore.USER_ROLE_SUCCESS);
    }

    @DeleteMapping(value = "/user")
    public ResponseDTO deleteUser(@RequestBody final Long userId) {
        this.userService.deleteUser(userId);
        return success(new ResponseDTO(), ApplicationMessageStore.USER_IN_ACTIVE_SUCCESS);
    }

}
