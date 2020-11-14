package com.tornikeshelia.gsgyoutubeapi.model.enums;

import lombok.Getter;

@Getter
public enum GsgError {

    // ============= GENERAL ERRORS =========================

    OK("Success"),
    PROVIDED_ID_WAS_NULL("Provided Id was null"),
    INVALID_REQUEST("Invalid Request"),

    // ================= USER ERRORS =======================
    USER_ALREADY_EXISTS("User with provided username already exists"),
    COULDNT_FIND_USER_BY_PROVIDED_ID("User with provided id doesn't exist"),

    // ================= COUNTRY ERRORS ===================
    COULDNT_FIND_COUNTRY("Couldn't find country by provided id");

    private String description;

    GsgError(String description) {
        this.description = description;
    }

}
