package com.tornikeshelia.gsgyoutubeapi.security.model.bean.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserBean {

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    private Long countryId;

    @NotNull
    @Min(1)
    @Max(60)
    private Integer jobTriggerTime;

}
