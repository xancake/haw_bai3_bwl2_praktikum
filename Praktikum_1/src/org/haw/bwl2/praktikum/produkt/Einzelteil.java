package org.haw.bwl2.praktikum.produkt;

import java.net.URL;

public class Einzelteil implements Produkt_I {
	private String myID;
	private String myName;
	private URL myBildURL;
	private int myBestand;
	private double myPreis;
	
	@Override
	public String getID() {
		return myID;
	}
	
	@Override
	public void setID(String id) {
		myID = id;
	}
	
	@Override
	public String getName() {
		return myName;
	}

	@Override
	public void setName(String name) {
		myName = name;
	}
	
	@Override
	public URL getBildURL() {
		return myBildURL;
	}

	@Override
	public void setBildURL(URL url) {
		myBildURL = url;
	}
	
	@Override
	public int getBestand() {
		return myBestand;
	}

	@Override
	public void setBestand(int bestand) {
		myBestand = bestand;
	}
	
	@Override
	public double getPreis() {
		return myPreis;
	}
	
	@Override
	public void setPreis(double preis) {
		myPreis = preis;
	}
	
	@Override
	public void addUnterteil(Produkt_I produkt) {}

	@Override
	public void removeUnterteil(Produkt_I produkt) {}
	
	@Override
	public String toString() {
		return getID() + "|" + getName() + "|" + getBestand() + "|" + getPreis();
	}
}
