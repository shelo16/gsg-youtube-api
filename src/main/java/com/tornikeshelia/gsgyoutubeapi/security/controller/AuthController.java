package com.tornikeshelia.gsgyoutubeapi.security.controller;

import com.tornikeshelia.gsgyoutubeapi.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.register.RegisterUserBean;
import com.tornikeshelia.gsgyoutubeapi.security.service.authentication.AuthenticationService;
import com.tornikeshelia.gsgyoutubeapi.security.service.registration.RegistrationService;
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

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/register")
    public void generateRegisterShortLink(@Valid @NotNull @RequestBody RegisterUserBean registerUserBean) {
        registrationService.register(registerUserBean);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public CheckUserAuthResponse authenticateUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {

        return authenticationService.authenticateUser(authenticationRequest, res);

    }

    @RequestMapping(value = "/checkAuth", method = RequestMethod.GET)
    public CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request) {

        return authenticationService.checkIfUserIsAuthenticated(request);

    }


    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void logout(HttpServletResponse response, HttpServletRequest request) {

        authenticationService.logout(response, request);

    }

}
