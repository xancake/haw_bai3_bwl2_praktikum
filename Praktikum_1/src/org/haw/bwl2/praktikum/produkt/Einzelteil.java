package org.haw.bwl2.praktikum.produkt;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Einzelteil implements Produkt_I {
	private int myID;
	private String myName;
	private URL myBildURL;
	private int myBestand;
	private double myPreis;
	private List<Statistik> myStatistiken;
	
	public Einzelteil() {
		myStatistiken = new ArrayList<>();
	}
	
	@Override
	public int getID() {
		return myID;
	}
	
	@Override
	public void setID(int id) {
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
	public List<Produkt_I> getUnterteile() { return null; }
	
	@Override
	public void addUnterteil(Produkt_I produkt) {}

	@Override
	public void removeUnterteil(Produkt_I produkt) {}
	
	@Override
	public List<Statistik> getStatistiken() {
		return Collections.unmodifiableList(myStatistiken);
	}
	
	@Override
	public void addStatistik(Statistik stat) {
		myStatistiken.add(stat);
	}
	
	@Override
	public void removeStatistik(Statistik stat) {
		myStatistiken.remove(stat);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + myBestand;
		result = prime * result + myID;
		result = prime * result + ((myName == null) ? 0 : myName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(myPreis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Einzelteil other = (Einzelteil) obj;
		if (myBestand != other.myBestand)
			return false;
		if (myID != other.myID)
			return false;
		if (myName == null) {
			if (other.myName != null)
				return false;
		} else if (!myName.equals(other.myName))
			return false;
		if (Double.doubleToLongBits(myPreis) != Double
				.doubleToLongBits(other.myPreis))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getID() + "|" + getName() + "|" + getBestand() + "|" + getPreis();
	}
}
