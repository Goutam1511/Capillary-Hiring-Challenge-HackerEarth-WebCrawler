package com.capillary.webcrawler.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capillary.webcrawler.model.webPage;
import com.capillary.webcrawler.service.WebCrawlerService;

@RestController
public class webCrawlerController {
	private final WebCrawlerService wbService;

	public webCrawlerController(WebCrawlerService wbService) {
		super();
		this.wbService = wbService;
	}
	
	@GetMapping("/scrape")
	List<webPage> scrapeUrl(@RequestParam String url) {
		return wbService.scrape(url);
	}
	
	@GetMapping("/webPages")
	List<webPage> getAllWebPages() {
		return wbService.getAllPages();
	}
}
