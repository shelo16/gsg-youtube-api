package com.tornikeshelia.gsgyoutubeapi.security.controller;

import com.tornikeshelia.gsgyoutubeapi.security.model.bean.register.RegisterUserBean;
import com.tornikeshelia.gsgyoutubeapi.security.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/reg")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/register")
    public void register(@Valid @NotNull @RequestBody RegisterUserBean registerUserBean) {
        registrationService.register(registerUserBean);
    }

}
