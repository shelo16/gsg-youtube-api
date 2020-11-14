package com.tornikeshelia.gsgyoutubeapi.service.youtube;

import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.Video.YoutubeVideoResponse;
import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.comment.YoutubeCommentReponse;
import com.tornikeshelia.gsgyoutubeapi.model.enums.GsgError;
import com.tornikeshelia.gsgyoutubeapi.model.exception.GeneralException;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.YoutubeLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public void saveByUserAndYoutube(GsgUser user, YoutubeLink youtubeLink) {

        if (user == null){
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
                    .trendingVideoUrl("youtube.com/watch?v=" + youtubeVideoUrl)
                    .commentUrl(comment)
                    .build();
        } else {
            youtubeLink.setTrendingVideoUrl("youtube.com/watch?v=" + youtubeVideoUrl);
            youtubeLink.setCommentUrl(comment);
        }

        youtubeLinkRepository.save(youtubeLink);
    }
}
