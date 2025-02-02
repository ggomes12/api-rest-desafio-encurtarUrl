package com.ggomes.api_desafio.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ggomes.api_desafio.entities.UrlEntity;
import com.ggomes.api_desafio.exceptions.InvalidUrlException;
import com.ggomes.api_desafio.repositories.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {
	private final UrlRepository urlRepository;
	private final Random random = new Random();
	private final String Base_URL = "https://xxx.com/";
	
	public String shortenUrl(String originalUrl) {
		if(!isValidUrl(originalUrl)) {
			log.info("Invalid URL");
			throw new InvalidUrlException("Invalid URL format.");
		}
		
		String shortUrl;
		
		do {
			shortUrl = generateShortUrl();
		} while(urlRepository.findByShortUrl(shortUrl).isPresent());
		
		UrlEntity urlEntity = new UrlEntity();
		urlEntity.setOriginalUrl(originalUrl);
		urlEntity.setShortUrl(shortUrl);
		urlEntity.setExpirationDate(LocalDateTime.now().plusDays(7));
		
		
		urlRepository.save(urlEntity);
		
		log.info("Shortened URL: {} -> {}", originalUrl, shortUrl);
		return Base_URL + shortUrl;
	}
	
	
	private boolean isValidUrl(String url) {
	    try {
	        URI uri = new URI(url);
	        return uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https"));
	    } catch (URISyntaxException e) {
	        return false;
	    }
	}
	
	
	
	private String generateShortUrl() {
        int length = random.nextInt(6) + 5; 
        return random.ints('0', 'z' + 1)
                .filter(i -> Character.isLetterOrDigit(i))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
	
	
	
	public String getOriginalUrl(String shortUrl) {
        Optional<UrlEntity> entity = urlRepository.findByShortUrl(shortUrl);

        if (entity.isEmpty() || entity.get().getExpirationDate().isBefore(LocalDateTime.now())) {
        	log.info("Short URL not found or expired");
            throw new NoSuchElementException("Short URL not found or expired.");
        }

        log.info("Original URL: {} -> {}", shortUrl, entity.get().getOriginalUrl());
        return entity.get().getOriginalUrl();
    }
	
	
}
