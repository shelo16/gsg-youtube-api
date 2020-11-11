package com.tornikeshelia.gsgyoutubeapi.model.persistence.repository;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GsgUserRepository extends JpaRepository<GsgUser, Long> {

    @Query(value = "FROM GsgUser user WHERE user.isAuthenticated = 1")
    List<GsgUser> getAuthenticatedUsers();

    @Query(value = "FROM GsgUser user WHERE user.userName = :username")
    GsgUser searchByUsername(String username);
}
