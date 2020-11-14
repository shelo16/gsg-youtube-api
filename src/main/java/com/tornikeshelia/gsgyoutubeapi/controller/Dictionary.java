package com.tornikeshelia.gsgyoutubeapi.controller;

import com.tornikeshelia.gsgyoutubeapi.model.bean.country.CountryBean;
import com.tornikeshelia.gsgyoutubeapi.service.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/dictionary")
public class Dictionary {

    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/country-drop-down")
    public List<CountryBean> getCountryBeanListForDropDown() {
        return countryService.getCountryBeanListForDropDown();
    }

}
