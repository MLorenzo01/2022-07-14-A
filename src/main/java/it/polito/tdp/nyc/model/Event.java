package it.polito.tdp.nyc.model;

public class Event {
	public enum EventType{
		SHARE,
		STOP
	}
	int giorno;
	EventType ev;
	int duration;
	nta nt;
	
	public Event(int giorno, EventType ev, nta nt, int duration) {
		super();
		this.giorno = giorno;
		this.ev = ev;
		this.duration = duration;
		this.nt = nt;
	}
	public int getGiorno() {
		return giorno;
	}
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}
	public EventType getEv() {
		return ev;
	}
	public void setEv(EventType ev) {
		this.ev = ev;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public nta getNt() {
		return nt;
	}
	public void setNt(nta nt) {
		this.nt = nt;
	}
	
	
	
	
}
