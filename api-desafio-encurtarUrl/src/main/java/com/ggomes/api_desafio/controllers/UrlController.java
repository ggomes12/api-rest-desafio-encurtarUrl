package com.ggomes.api_desafio.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggomes.api_desafio.dtos.ShortenUrlRequest;
import com.ggomes.api_desafio.dtos.ShortenUrlResponse;
import com.ggomes.api_desafio.services.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {
	
	private final UrlService urlService;
	
	@PostMapping("/shorten")
	public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request) {
		String shortUrl = urlService.shortenUrl(request.url());
		return ResponseEntity.ok(new ShortenUrlResponse(shortUrl));
	}
	
	@GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        response.sendRedirect(originalUrl);
        return ResponseEntity.status(302).build();
    }
	
}
