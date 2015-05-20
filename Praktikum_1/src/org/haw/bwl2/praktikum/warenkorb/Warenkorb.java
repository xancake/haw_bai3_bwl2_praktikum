package org.haw.bwl2.praktikum.warenkorb;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.haw.bwl2.praktikum.produkt.Produkt_I;

public class Warenkorb implements Iterable<Entry<Produkt_I, Integer>> {
	private Map<Produkt_I, Integer> myProdukteMengeMapping;
	
	public Warenkorb() {
		myProdukteMengeMapping = new HashMap<>();
	}
	
	public int getMengeVonProdukt(Produkt_I produkt) {
		Integer integer = myProdukteMengeMapping.get(produkt);
		return integer == null ? 0 : integer;
	}
	
	public void trageProdukteEin(Produkt_I produkt, int menge) {
		if(menge > 0) {
			myProdukteMengeMapping.put(produkt, menge);
		}
	}
	
	public void entferneProdukt(Produkt_I produkt) {
		myProdukteMengeMapping.remove(produkt);
	}
	
	public int getAnzahlProdukte() {
		return myProdukteMengeMapping.size();
	}
	
	public Set<Entry<Produkt_I, Integer>> getEntrySet() {
		return Collections.unmodifiableSet(myProdukteMengeMapping.entrySet());
	}
	
	@Override
	public Iterator<Entry<Produkt_I, Integer>> iterator() {
		return getEntrySet().iterator();
	}
}
