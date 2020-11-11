package com.tornikeshelia.gsgyoutubeapi.model.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "YOUTUBE_LINK")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class YoutubeLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRENDING_VIDEO_URL")
    private String trendingVideoUrl;

    @Column(name = "COMMENT_URL")
    private String commentUrl;

}
