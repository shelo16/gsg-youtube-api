package com.tornikeshelia.gsgyoutubeapi.service.youtube;

import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.full.YoutubeFullBean;
import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.video.YoutubeVideoResponse;
import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.comment.YoutubeCommentReponse;
import com.tornikeshelia.gsgyoutubeapi.model.enums.GsgError;
import com.tornikeshelia.gsgyoutubeapi.model.exception.GeneralException;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.YoutubeLinkRepository;
import com.tornikeshelia.gsgyoutubeapi.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.gsgyoutubeapi.security.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class YoutubeServiceImpl implements YoutubeService {

    @Autowired
    private GsgUserRepository userRepository;

    @Value("${youtube-video-api-url}")
    private String videoApiUrl;

    @Value("${youtube-comment-api-url}")
    private String commentApiUrl;

    @Autowired
    private YoutubeLinkRepository youtubeLinkRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GsgUserRepository userRepositoryr;

    @Override
    public void saveByUserAndYoutube(GsgUser user, YoutubeLink youtubeLink) {

        if (user == null) {
            throw new GeneralException(GsgError.COULDNT_FIND_USER_BY_PROVIDED_ID);
        }
        String regionCode = user.getCountry().getCountryCode();
        String videoApiUrl = this.videoApiUrl + "&regionCode=" + regionCode;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<YoutubeVideoResponse> responseVideo = restTemplate
                .exchange(videoApiUrl, HttpMethod.GET, null, YoutubeVideoResponse.class);

        System.out.println(responseVideo);
        String trendingVideo = responseVideo.getStatusCode() == HttpStatus.OK ? responseVideo.getBody().getItems().get(0).getId() : "";


        String commentApiUrl = this.commentApiUrl + "&videoId=" + trendingVideo;
        ResponseEntity<YoutubeCommentReponse> responseComment = restTemplate
                .exchange(commentApiUrl, HttpMethod.GET, null, YoutubeCommentReponse.class);
        String youtubeVideoUrl = responseVideo.getBody().getItems().get(0).getId();
        String comment = responseComment.getBody().getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getTextOriginal();

        if (youtubeLink == null) {
            youtubeLink = YoutubeLink.builder()
                    .gsgUser(user)
                    .trendingVideoUrl("https://youtube.com/embed/" + youtubeVideoUrl)
                    .commentUrl(comment)
                    .build();
        } else {
            youtubeLink.setTrendingVideoUrl("https://youtube.com/embed/" + youtubeVideoUrl);
            youtubeLink.setCommentUrl(comment);
        }

        youtubeLinkRepository.save(youtubeLink);
    }

    @Override
    public YoutubeFullBean getYoutubeForUser(HttpServletRequest request) {

        CheckUserAuthResponse checkUserAuthResponse = authenticationService.checkIfUserIsAuthenticated(request);
        if (checkUserAuthResponse == null) {
            throw new GeneralException(GsgError.USER_IS_NOT_AUTHENTICATED);
        }
        GsgUser gsgUser = userRepository.searchByUsername(checkUserAuthResponse.getUsername());
        if (gsgUser == null){
            throw new GeneralException(GsgError.COULDNT_FIND_USER_BY_PROVIDED_ID);
        }
        YoutubeLink youtubeLink = youtubeLinkRepository.getByUser(gsgUser);

        return YoutubeFullBean.transformYoutubeLinkEntityToBean(youtubeLink);
    }
}
