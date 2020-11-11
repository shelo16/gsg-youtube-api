package com.tornikeshelia.gsgyoutubeapi.security.service.registration;

import com.tornikeshelia.gsgyoutubeapi.model.enums.GsgError;
import com.tornikeshelia.gsgyoutubeapi.model.exception.GeneralException;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.Country;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.CountriesRepository;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.register.RegisterUserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private GsgUserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CountriesRepository countriesRepository;


    /**
     * register :
     *
     * @param registerUserBean
     **/
    @Override
    @Transactional
    public void register(RegisterUserBean registerUserBean) {

        GsgUser gsgUser = userRepository.searchByUsername(registerUserBean.getUsername());
        if (gsgUser != null) {
            throw new GeneralException(GsgError.USER_ALREADY_EXISTS);
        } else {
            gsgUser = new GsgUser();
        }

        BeanUtils.copyProperties(registerUserBean, gsgUser);
        gsgUser.setIsAuthenticated(1L);
        gsgUser.setBcryptedPassword(bCryptPasswordEncoder.encode(registerUserBean.getPassword()));
        // TODO : set countries
        Country country = countriesRepository.findById(registerUserBean.getCountryId()).orElseThrow(() -> new GeneralException(GsgError.COULDNT_FIND_COUNTRY));
        gsgUser.setCountry(country);
        userRepository.save(gsgUser);

    }


}
