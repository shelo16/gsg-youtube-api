package com.tornikeshelia.gsgyoutubeapi.controller;

import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.full.YoutubeFullBean;
import com.tornikeshelia.gsgyoutubeapi.service.youtube.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/youtube")
public class YoutubeController {

    @Autowired
    private YoutubeService youtubeService;

    @GetMapping
    public YoutubeFullBean getYoutubeForUser(HttpServletRequest request) {
        return youtubeService.getYoutubeForUser(request);
    }


}
