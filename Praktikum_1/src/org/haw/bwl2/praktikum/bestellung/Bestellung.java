package org.haw.bwl2.praktikum.bestellung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.haw.bwl2.praktikum.produkt.Produkt_I;

public class Bestellung {
	private int myID;
	private Date myBestelldatum;
	private List<Produkt_I> myBestellteProdukte;
	private Map<Integer, Integer> myMengeMapping;
	
	public Bestellung() {
		myID = -1;
		myBestelldatum = new Date();
		myBestellteProdukte = new ArrayList<>();
		myMengeMapping = new HashMap<>();
	}
	
	public int getID() {
		return myID;
	}
	
	public void setID(int id) {
		myID = id;
	}
	
	public Date getBestelldatum() {
		return myBestelldatum;
	}
	
	public void setBestelldatum(Date date) {
		myBestelldatum = date;
	}
	
	public List<Produkt_I> getBestellteProdukte() {
		return Collections.unmodifiableList(myBestellteProdukte);
	}
	
	public void addBestelltesProdukt(Produkt_I produkt, int menge) {
		myBestellteProdukte.add(produkt);
		setBestellMenge(produkt, menge);
	}
	
	public void removeBestelltesProdukt(Produkt_I produkt) {
		myBestellteProdukte.remove(produkt);
		myMengeMapping.remove(produkt.getID());
	}
	
	public Integer getBestellMenge(Produkt_I produkt) {
		return myMengeMapping.get(produkt.getID());
	}
	
	public void setBestellMenge(Produkt_I produkt, int menge) {
		myMengeMapping.put(produkt.getID(), menge);
	}
}
