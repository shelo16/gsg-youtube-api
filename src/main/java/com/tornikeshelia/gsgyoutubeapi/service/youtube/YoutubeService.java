package com.tornikeshelia.gsgyoutubeapi.service.youtube;

import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.full.YoutubeFullBean;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;

import javax.servlet.http.HttpServletRequest;

public interface YoutubeService {

    void saveByUserAndYoutube(GsgUser user, YoutubeLink youtubeLink);

    YoutubeFullBean getYoutubeForUser(HttpServletRequest request);
}
