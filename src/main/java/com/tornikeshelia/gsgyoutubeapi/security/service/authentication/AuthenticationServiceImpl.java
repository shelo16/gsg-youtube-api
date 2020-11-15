package com.tornikeshelia.gsgyoutubeapi.security.service.authentication;

import com.tornikeshelia.gsgyoutubeapi.model.enums.GsgError;
import com.tornikeshelia.gsgyoutubeapi.model.exception.GeneralException;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.Country;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.CountriesRepository;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.YoutubeLinkRepository;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.reset.ResetUserData;
import com.tornikeshelia.gsgyoutubeapi.security.service.JwtUtilService;
import com.tornikeshelia.gsgyoutubeapi.security.service.MyUserDetailsService;
import com.tornikeshelia.gsgyoutubeapi.service.youtube.YoutubeService;
import org.springframework.beans.BeanUtils;
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
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private GsgUserRepository userRepository;

    @Autowired
    private YoutubeService youtubeService;

    @Autowired
    private YoutubeLinkRepository youtubeLinkRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    /**
     * AuthenticateUser method :
     *
     * @param authenticationRequest -> username,password
     *                              1 ) Authenticates User if username and password are correct
     *                              2 ) Creates JWT token and stores it as HttpOnly cookie
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
        YoutubeLink youtubeLink = youtubeLinkRepository.getByUser(gsgUser);
        youtubeService.saveByUserAndYoutube(gsgUser, youtubeLink);

        final String jwt = jwtUtilService.generateToken(userDetails);
        Cookie cookie = new Cookie("token", jwt);
        cookie.setPath("/");
        cookie.setMaxAge((int) (System.currentTimeMillis() + 1000 * 60 * 60));
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return new CheckUserAuthResponse(true, gsgUser.getUserName(), gsgUser.getJobTriggerTime());
    }

    /**
     * checkIfUserIsAuthenticated method :
     *
     * @param request HttpServletRequest -> To check the cookie
     *                1 ) check cookies for token
     *                2 ) if token exists :
     * @return checkUserAuthResponse -> isAuthenticated =null , email = null
     **/
    @Override
    public CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request) {
        String jwtToken = null;
        Boolean isAuthenticated = false;
        Cookie[] cookies = request.getCookies();
        GsgUser gsgUser = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    isAuthenticated = jwtUtilService.validateToken(jwtToken);
                    gsgUser = userRepository.searchByUsername(jwtUtilService.extractUsername(jwtToken));
                    break;
                }
            }
        }
        return new CheckUserAuthResponse(isAuthenticated, gsgUser.getUserName(), gsgUser.getJobTriggerTime());
    }

    /**
     * Logout Logic :
     *
     * @param request  HttpServletRequest -> To check the cookie
     * @param response HttpServletResponse -> to set the cookie expiration
     *                 Checks if Cookie has a "token" value inside (which is a JWT token)
     *                 if true -> gets the token and sets its expiration date as NOW, which basically deletes the cookie
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

    /**
     * resetUserData Logic :
     *
     * @param request       HttpServletRequest -> To check the cookie
     * @param resetUserData ResetUserData -> to update userData
     *                      Checks if Cookie has a "token" value inside (which is a JWT token)
     *                      if true -> gets the token and user
     *                      if country is different (is updated ) -> update youtube link as well
     *                      Updates user
     **/
    @Override
    public void resetUserData(ResetUserData resetUserData, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        GsgUser gsgUser = null;
        String jwtToken = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    gsgUser = userRepository.searchByUsername(jwtUtilService.extractUsername(jwtToken));
                    if (gsgUser == null) {
                        throw new GeneralException(GsgError.COULDNT_FIND_USER_BY_USERNAME);
                    }
                    Country country = countriesRepository.findById(resetUserData.getCountryId())
                            .orElseThrow(() -> new GeneralException(GsgError.COULDNT_FIND_COUNTRY));
                    BeanUtils.copyProperties(resetUserData, gsgUser);

                    if (country != gsgUser.getCountry()) {
                        YoutubeLink youtubeLink = youtubeLinkRepository.getByUser(gsgUser);
                        gsgUser.setCountry(country);
                        youtubeService.saveByUserAndYoutube(gsgUser,youtubeLink);
                    }
                    userRepository.save(gsgUser);
                    break;
                }
            }
            if (jwtToken == null) {
                throw new GeneralException(GsgError.MUST_BE_AUTHENTICATED);
            }
        }
    }

    /**
     * getResetUserdata Logic :
     *
     * @param request HttpServletRequest -> To check the cookie
     *                Checks if Cookie has a "token" value inside (which is a JWT token)
     *                if true -> gets the token and user
     *                creates new ResetUserData and returns it :
     * @return resetUserData ResetUserData -> to update userData
     **/
    @Override
    public ResetUserData getResetUserdata(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        GsgUser gsgUser = null;
        String jwtToken = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    gsgUser = userRepository.searchByUsername(jwtUtilService.extractUsername(jwtToken));
                    if (gsgUser == null) {
                        throw new GeneralException(GsgError.COULDNT_FIND_USER_BY_USERNAME);
                    }
                    Country country = gsgUser.getCountry();
                    int jobTriggerTime = gsgUser.getJobTriggerTime();
                    return new ResetUserData(country.getId(), jobTriggerTime);
                }
            }
        }
        return null;
    }
}
