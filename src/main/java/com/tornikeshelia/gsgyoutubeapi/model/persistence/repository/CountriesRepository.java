package com.tornikeshelia.gsgyoutubeapi.model.persistence.repository;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountriesRepository extends JpaRepository<Country, Long> {
}
