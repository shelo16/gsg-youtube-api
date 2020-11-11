package com.tornikeshelia.gsgyoutubeapi.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralExceptionResponse {

    private String message;
    private List<ValidationException> violations;

    public GeneralExceptionResponse(String description) {
        this.message = description;
    }
}
