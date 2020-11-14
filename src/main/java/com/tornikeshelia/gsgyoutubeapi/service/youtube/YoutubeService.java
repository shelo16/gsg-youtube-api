package com.tornikeshelia.gsgyoutubeapi.service.youtube;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;

public interface YoutubeService {

    void saveByUserAndYoutube(GsgUser user, YoutubeLink youtubeLink);

}
