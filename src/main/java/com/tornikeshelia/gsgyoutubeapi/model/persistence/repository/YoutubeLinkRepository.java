package com.tornikeshelia.gsgyoutubeapi.model.persistence.repository;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeLinkRepository extends JpaRepository<YoutubeLink,Long> {
}
