package com.capillary.webcrawler.model;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class webPage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	public String url;
	@ElementCollection(targetClass=String.class)
	public Set<String> allNumbers;
	
	public webPage() {
		super();
	}

	public webPage(String uRL2, Set<String> allNumbersInPage) {
		url = uRL2;
		allNumbers = allNumbersInPage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<String> getAllNumbers() {
		return allNumbers;
	}

	public void setAllNumbers(Set<String> allNumbers) {
		this.allNumbers = allNumbers;
	}
}
