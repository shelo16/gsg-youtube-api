package com.tornikeshelia.gsgyoutubeapi.security.service.authentication;


import com.tornikeshelia.gsgyoutubeapi.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.reset.ResetUserData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    CheckUserAuthResponse authenticateUser(AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception;
    CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request);
    void logout(HttpServletResponse response, HttpServletRequest request);
    void resetUserData(ResetUserData resetUserData, HttpServletRequest request);

    ResetUserData getResetUserdata(HttpServletRequest request);
}
