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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
@Tag(name = "URL Shortener", description = "API for URL shortening and redirection")
public class UrlController {
	
	private final UrlService urlService;
	
	@Operation(summary = "Shorten a URL", description = "Receives a long URL and returns a shortened version")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully shortened the URL"),
        @ApiResponse(responseCode = "400", description = "Invalid URL provided")
    })
	@PostMapping("/shorten")
	public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request) {
		String shortUrl = urlService.shortenUrl(request.url());
		return ResponseEntity.ok(new ShortenUrlResponse(shortUrl));
	}
	
	@Operation(summary = "Redirect to Original URL", description = "Redirects the user to the original URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to the original URL"),
            @ApiResponse(responseCode = "404", description = "Short URL not found")
        })
	@GetMapping("/{shortUrl}")
	public ResponseEntity<ShortenUrlResponse> getOriginalUrl(@PathVariable String shortUrl) {
	    String originalUrl = urlService.getOriginalUrl(shortUrl);
	    return ResponseEntity.ok(new ShortenUrlResponse(originalUrl));
	}
	
}
