package com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.full;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YoutubeFullBean {

    private String videoLink;
    private String comment;

    public static YoutubeFullBean transformYoutubeLinkEntityToBean(YoutubeLink youtubeLink) {
        return YoutubeFullBean.builder()
                .videoLink(youtubeLink.getTrendingVideoUrl())
                .comment(youtubeLink.getCommentUrl())
                .build();
    }

}
