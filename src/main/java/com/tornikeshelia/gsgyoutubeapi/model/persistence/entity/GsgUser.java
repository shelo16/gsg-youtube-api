package com.tornikeshelia.gsgyoutubeapi.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "GSG_USER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GsgUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String bcryptedPassword;

    @OneToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @OneToOne
    @JoinColumn(name = "YOUTUBE_LINK_ID")
    private YoutubeLink youtubeLink;

    @Column(name = "JOB_TRIGGER_TIME")
    private int jobTriggerTime;

    @Column(name = "IS_AUTHENTICATED")
    private Long isAuthenticated;
}
