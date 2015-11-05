package com.contacts;

import org.apache.log4j.Logger;

public class ContactsHeader {
	private String title;
	private String font;
	private String size;
	private String color;
	static final Logger logger = Logger.getLogger(ContactsHeader.class);
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSize() {
		return size;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
	public String toString() {
        return("Title="+this.title + " , Font=" +  this.font + " , Size=" +  this.size+ " & Color=" +  this.color);
    }
}
