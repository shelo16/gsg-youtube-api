package com.tornikeshelia.gsgyoutubeapi.model.bean.country;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.Country;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryBean {

    private Long id;
    private String countryName;
    private String countryCode;

    public static CountryBean transformCountryEntityToBean(Country country) {
        return CountryBean.builder()
                .id(country.getId())
                .countryCode(country.getCountryCode())
                .countryName(country.getCountryName())
                .build();
    }

}
