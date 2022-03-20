package com.capillary.webcrawler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capillary.webcrawler.model.webPage;
import com.capillary.webcrawler.repository.webPageRepository;
import com.capillary.webcrawler.util.WebScraper;

@Service
public class WebCrawlerService {
	private WebScraper scraper;
	private webPageRepository repo;
	
	public WebCrawlerService(WebScraper scraper, webPageRepository wRepo) {
		super();
		this.scraper = scraper;
		this.repo = wRepo;
	}

	public List<webPage> scrape(String url) {
		List<webPage> webPages = scraper.scrape(url);
		repo.saveAll(webPages);
		return webPages;
	}
	
	public List<webPage> getAllPages() {
		return (List<webPage>) repo.findAll();
	}
}
