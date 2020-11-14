package com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.Video;

import com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.Video.VideoItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeVideoResponse {

    private List<VideoItems> items;

}
