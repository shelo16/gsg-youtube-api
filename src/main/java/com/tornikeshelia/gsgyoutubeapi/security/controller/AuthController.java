package com.tornikeshelia.gsgyoutubeapi.security.controller;

import com.tornikeshelia.gsgyoutubeapi.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.reset.ResetUserData;
import com.tornikeshelia.gsgyoutubeapi.security.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public CheckUserAuthResponse authenticateUser(@RequestBody @Valid @NotNull AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {

        return authenticationService.authenticateUser(authenticationRequest, res);

    }

    @RequestMapping(value = "/checkAuth", method = RequestMethod.GET)
    public CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request) {

        return authenticationService.checkIfUserIsAuthenticated(request);

    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public void resetUser(@RequestBody @NotNull @Valid ResetUserData resetUserData, HttpServletRequest request) {
        authenticationService.resetUserData(resetUserData, request);
    }


    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        authenticationService.logout(response, request);
    }

    @RequestMapping(value = "/resetUserData", method = RequestMethod.GET)
    public ResetUserData getResetUserData(HttpServletRequest request) {
        return authenticationService.getResetUserdata(request);
    }

}
