package com.tornikeshelia.gsgyoutubeapi.security.model.bean.reset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetUserData {

    private Long countryId;

    @Min(1)
    @Max(60)
    private int jobTriggerTime;

}
