package com.tornikeshelia.gsgyoutubeapi.model.exception;

import com.tornikeshelia.gsgyoutubeapi.model.enums.GsgError;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GeneralException extends RuntimeException {
    private GsgError gsgError;

    public GeneralException(GsgError gsgError) {
        this.gsgError = gsgError;
    }

}
