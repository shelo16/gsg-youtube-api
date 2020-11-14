package com.tornikeshelia.gsgyoutubeapi.model.persistence.repository;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YoutubeLinkRepository extends JpaRepository<YoutubeLink,Long> {

    @Query(value = "FROM YoutubeLink ul WHERE ul.gsgUser =:gsgUser")
    YoutubeLink getByUser(GsgUser gsgUser);

}
