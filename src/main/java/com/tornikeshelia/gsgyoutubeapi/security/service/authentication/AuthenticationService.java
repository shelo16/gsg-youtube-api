package com.tornikeshelia.gsgyoutubeapi.security.service.authentication;


import com.tornikeshelia.gsgyoutubeapi.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    CheckUserAuthResponse authenticateUser(AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception;
    CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request);
    void logout(HttpServletResponse response, HttpServletRequest request);

}
