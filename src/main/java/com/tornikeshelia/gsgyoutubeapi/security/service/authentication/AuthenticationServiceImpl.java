package com.tornikeshelia.gsgyoutubeapi.security.service.authentication;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.gsgyoutubeapi.security.service.JwtUtilService;
import com.tornikeshelia.gsgyoutubeapi.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private GsgUserRepository userRepository;

    /**
     * AuthenticateUser method :
     * @param authenticationRequest -> email,password
     * 1 ) Authenticates User if username and password are correct
     * 2 ) Creates JWT token and stores it as HttpOnly cookie
     * @return checkUserAuthReponse -> boolean isAuthenticated , email
     **/
    @Override
    public CheckUserAuthResponse authenticateUser(AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken
                            (authenticationRequest.getUsername(),
                                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        GsgUser gsgUser = userRepository.searchByUsername(authenticationRequest.getUsername());
        gsgUser.setIsAuthenticated(1L);
        userRepository.save(gsgUser);

        // TODO :: Get youtube Link for best video and best comment -> update youtube Link;

        final String jwt = jwtUtilService.generateToken(userDetails);
        Cookie cookie = new Cookie("token", jwt);
        cookie.setPath("/");
        cookie.setMaxAge((int) (System.currentTimeMillis() + 1000 * 60 * 60));
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return new CheckUserAuthResponse(true, authenticationRequest.getUsername());
    }

    /**
     * checkIfUserIsAuthenticated method :
     * @param request HttpServletRequest -> To check the cookie
     * 1 ) check cookies for token
     * 2 ) if token exists :
     *          @return checkUserAuthReponse -> boolean isAuthenticated , email
     *    if token Doesnt exist :
     *          @return checkUserAuthResponse -> isAuthenticated =null , email = null
     **/
    @Override
    public CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request) {
        String jwtToken = null;
        String email = null;
        Boolean isAuthenticated = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    email = jwtUtilService.extractUsername(jwtToken);
                    isAuthenticated = jwtUtilService.validateToken(jwtToken);
                    break;
                }
            }
        }
        return new CheckUserAuthResponse(isAuthenticated, email);
    }

    /**
     * Logout Logic :
     * @param request HttpServletRequest -> To check the cookie
     * @param response HttpServletResponse -> to set the cookie expiration
     * Checks if Cookie has a "token" value inside (which is a JWT token)
     * if true -> gets the token and sets its expiration date as NOW, which basically deletes the cookie
     **/
    @Override
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    String username = jwtUtilService.extractUsername(jwtToken);
                    GsgUser gsgUser = userRepository.searchByUsername(username);
                    gsgUser.setIsAuthenticated(0L);
                    userRepository.save(gsgUser);
                    break;
                }
            }
        }

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}
