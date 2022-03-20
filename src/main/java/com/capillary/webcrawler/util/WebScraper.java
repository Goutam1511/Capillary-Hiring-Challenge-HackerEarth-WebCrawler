package com.capillary.webcrawler.util;

// import exception and collection classes    
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.capillary.webcrawler.model.webPage;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;  

@Component
public class WebScraper {
	private int maxUrlQueueSize = 50;
	private String[] selectors = {"p", "div", "label"};
	
	public List<String> extractPhoneNumber(String input){
		List<String> allNumbers = new ArrayList<String>();

	    Iterator<PhoneNumberMatch> existsPhone=PhoneNumberUtil.getInstance().findNumbers(input, "IN").iterator();

	    while (existsPhone.hasNext()){
	    	PhoneNumber num = existsPhone.next().number();
	        allNumbers.add(num.getCountryCode() + "-" + num.getNationalNumber());
	    }
	    return allNumbers;
	}
    // create getPageLink() method that finds all the page link in the given URL  
	public void getPageLinks(String URL, Set<String> urlLink, List<webPage> webPages) {  
      
        // we use the conditional statement to check whether we have already crawled the URL or not.  
		if (!urlLink.contains(URL) && urlLink.size() < maxUrlQueueSize) {   
			try {   
				// if the URL is not present in the set, we add it to the set 
				if (urlLink.add(URL)) {   
					System.out.println(URL);   
				}
				Set<String> allNumbersInPage = new HashSet<String>();
				// fetch the HTML code of the given URL by using the connect() and get() method and store the result in Document  
				Document doc = Jsoup.connect(URL).get(); 
				for (String selector : selectors) {
					Elements availableTextOnPage = doc.select(selector);
					for (Element ele : availableTextOnPage) {
						allNumbersInPage.addAll(extractPhoneNumber(ele.text()));
					}	
				}
				if (allNumbersInPage.size() > 0)
					webPages.add(new webPage(URL, allNumbersInPage));
				
				// we use the select() method to parse the HTML code for extracting links of other URLs and store them into Elements 
				
				Elements availableLinksOnPage = doc.select("a[href]");   
				// for each extracted URL, we repeat process   
				for (Element ele : availableLinksOnPage) {   
					// call getPageLinks() method and pass the extracted URL to it as an argument  
					getPageLinks(ele.attr("abs:href"), urlLink, webPages);   
				}   
			}   
			// handle exception  
			catch (IOException e) {   
				// print exception messages  
				System.err.println("For '" + URL + "': " + e.getMessage());   
			}   
		} 
	}
	
	public List<webPage> scrape(String url) {
		Set<String> urlLinks = new HashSet<String>();
		List<webPage> webPages = new ArrayList<webPage>();
		getPageLinks(url, urlLinks, webPages);
		return webPages;
	}
}
