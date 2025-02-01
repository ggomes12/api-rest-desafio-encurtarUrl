package com.ggomes.api_desafio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ggomes.api_desafio.entities.UrlEntity;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

	Optional<UrlEntity> findByShortUrl(String shortUrl);


}
