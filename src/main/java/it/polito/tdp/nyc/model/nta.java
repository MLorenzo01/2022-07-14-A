package it.polito.tdp.nyc.model;

import java.util.HashSet;
import java.util.Set;

public class nta {
	private String code;
	private String name;
	private Set<String> ssid;
	
	public nta(String code, String name, String ss) {
		this.code = code;
		this.name = name;
		this.ssid = new HashSet<>();
		ssid.add(ss);
	}
	
	public Set<String> getSsid() {
		return ssid;
	}

	public void setSsid(String ss) {
		ssid.add(ss);
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
