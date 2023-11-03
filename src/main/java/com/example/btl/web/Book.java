package com.example.btl.web;

import java.sql.Date;

public class Book{
	private String title;
	private String author;
	private String description;
	private Date nph;
	private int nop;
	private String category;
	private String url;
	private int slb;
	
	public Book() {
		
	}
	public Book(String title, String author, String description, Date nph, int nop,String category, String url, int slb) {
		this.title=title;
		this.author=author;
		this.description=description;
		this.nph=nph;
		this.nop=nop;
		this.category=category;
		this.url=url;
		this.slb=slb;
	}
	public String getAuthor() {
		return author;
	}
	public String getDescription() {
		return description;
	}
	public int getNop() {
		return nop;
	}
	public Date getNph() {
		return nph;
	}
	public int getSlb() {
		return slb;
	}
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setNop(int nop) {
		this.nop = nop;
	}
	public void setNph(Date nph) {
		this.nph = nph;
	}
	public void setSlb(int slb) {
		this.slb = slb;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}