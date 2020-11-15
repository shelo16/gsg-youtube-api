package com.tornikeshelia.gsgyoutubeapi.youtube;

import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.comment.YoutubeCommentReponse;
import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.video.YoutubeVideoResponse;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import com.tornikeshelia.gsgyoutubeapi.service.youtube.YoutubeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class YoutubeTest {

    @Autowired
    private YoutubeService youtubeService;

    /**
     *
     * Testing to get Trending video on region Code;
     *
     * **/
    @Test
    public void testGetYoutubeByUser() {
        YoutubeLink youtubeLink = new YoutubeLink();
        String regionCode = "GE";
        String videoApiUrl = "https://www.googleapis.com/youtube/v3/videos?chart=mostPopular&part=contentDetails&maxResults=1&key=AIzaSyDNbm7Isq58-2Ec9mxTR0nCnfxjpQhuxIM" + "&regionCode=" + regionCode;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<YoutubeVideoResponse> responseVideo = restTemplate
                .exchange(videoApiUrl, HttpMethod.GET, null, YoutubeVideoResponse.class);

        System.out.println(responseVideo);
        String trendingVideo = responseVideo.getStatusCode() == HttpStatus.OK ? responseVideo.getBody().getItems().get(0).getId() : "";

        String youtubeVideoUrl = responseVideo.getBody().getItems().get(0).getId();

        assertEquals(youtubeVideoUrl,"brq7LfhHEjo");

    }

}
