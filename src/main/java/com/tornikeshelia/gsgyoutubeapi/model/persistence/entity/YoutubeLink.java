package com.tornikeshelia.gsgyoutubeapi.model.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "YOUTUBE_LINK")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class YoutubeLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "TRENDING_VIDEO_URL")
    private String trendingVideoUrl;

    @Column(name = "COMMENT_URL")
    private String commentUrl;

    @OneToOne
    @JoinColumn(name = "GSG_USER_ID")
    private GsgUser gsgUser;


    @PreUpdate
    protected void onUpdate() {
        updateDate = new Date();
    }

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

}
