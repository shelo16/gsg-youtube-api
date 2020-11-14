package com.tornikeshelia.gsgyoutubeapi.service.country;

import com.tornikeshelia.gsgyoutubeapi.model.bean.country.CountryBean;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.Country;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService{

    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public List<CountryBean> getCountryBeanListForDropDown() {
        List<Country> countryList = countriesRepository.findAll();

        return countryList.stream()
                .map(CountryBean::transformCountryEntityToBean)
                .collect(Collectors.toList());
    }
}
