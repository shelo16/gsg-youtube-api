package com.tornikeshelia.gsgyoutubeapi.model.bean.youtube.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeCommentReponse {

    private List<CommentItems> items;

}
