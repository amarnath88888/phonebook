package com.contacts;

public class ContactsBody {
	
	private String begin;
	private String index;
	
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String toString() {
        return("Begin="+this.begin + " , Index=" +  this.index);
    }
}
