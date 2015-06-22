package org.haw.bwl2.praktikum.analyse.assoziation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.haw.bwl2.praktikum.bestellung.Bestellung;
import org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader;
import org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader_I;
import org.haw.bwl2.praktikum.produkt.Produkt_I;

public class AssoziationsAnalyse {
	private int myAnzahlBestellungen;
	private Map<Produkt_I, List<AnalyseEintrag>> myAnalyseMap;
	
	public AssoziationsAnalyse(int minSupport, int minKonfidenz) throws IOException {
		try (BestellungLoader_I bestellungLoader = new BestellungLoader()) {
			List<Bestellung> bestellungen = bestellungLoader.loadAlleBestellungen();
			
			// Vorkommnisse ermitteln
			Map<Produkt_I, List<Bestellung>> produktVorkommnisse = new HashMap<>();
			Map<Produkt_I, Map<Produkt_I, Integer>> produktAssoziation = new HashMap<>();
			for(Bestellung bestellung : bestellungen) {
				for(Produkt_I produkt : bestellung.getBestellteProdukte()) {
					List<Bestellung> vorkommnis = produktVorkommnisse.get(produkt);
					if(vorkommnis == null) {
						vorkommnis = new ArrayList<Bestellung>();
						produktVorkommnisse.put(produkt, vorkommnis);
					}
					vorkommnis.add(bestellung);
					
					Map<Produkt_I, Integer> assoziation = produktAssoziation.get(produkt);
					if(assoziation == null) {
						assoziation = new HashMap<Produkt_I, Integer>();
						produktAssoziation.put(produkt, assoziation);
					}
					for (Produkt_I assoziertesProdukt : bestellung.getBestellteProdukte()) {
						if(!assoziertesProdukt.equals(produkt)){
							Integer menge = assoziation.containsKey(assoziertesProdukt) ? assoziation.get(assoziertesProdukt) : 0;
							assoziation.put(assoziertesProdukt, menge+1);
						}
					}
				}
			}
			
			myAnzahlBestellungen = bestellungen.size();
			myAnalyseMap = new HashMap<>();
			for(Produkt_I produkt : produktVorkommnisse.keySet()) {
				Map<Produkt_I, Integer> assoziationMap = produktAssoziation.get(produkt);
				List<AnalyseEintrag> analyseListe = new ArrayList<>();
				for(Entry<Produkt_I, Integer> assoziation : assoziationMap.entrySet()) {
					Produkt_I assoziiertesProdukt = assoziation.getKey();
					List<Integer> bestellungsIdenten = new ArrayList<>();
					
					//select orders with both products
					for(Bestellung bestellung : produktVorkommnisse.get(produkt)) {
						if(bestellung.getBestellteProdukte().contains(assoziiertesProdukt))
							bestellungsIdenten.add(bestellung.getID());
					}
					int support = (assoziation.getValue()*100) / myAnzahlBestellungen;
					int konfidenz = (assoziation.getValue()*100) / produktVorkommnisse.get(produkt).size();
					
					if(support > minSupport && konfidenz > minKonfidenz)
						analyseListe.add(new AnalyseEintrag(assoziiertesProdukt, bestellungsIdenten, support, konfidenz));
				}
				myAnalyseMap.put(produkt, analyseListe);
			}
		}
	}
	
	public int getAnzahlBestellungen() {
		return myAnzahlBestellungen;
	}
	
	public Set<Produkt_I> getAllProdukte() {
		return Collections.unmodifiableSet(myAnalyseMap.keySet());
	}
	
	public List<AnalyseEintrag> getEintraegeFuer(Produkt_I produkt) {
		return myAnalyseMap.get(produkt);
	}
	
	public class AnalyseEintrag {
		private Produkt_I myAssoziiertesProdukt;
		private List<Integer> myBestellungen;
		private int mySupport;
		private int myKonfidenz;
		
		private AnalyseEintrag(Produkt_I assoziiertesProdukt, List<Integer> bestellungen, int support, int konfidenz) {
			myAssoziiertesProdukt = assoziiertesProdukt;
			myBestellungen = bestellungen;
			mySupport = support;
			myKonfidenz = konfidenz;
		}
		
		public Produkt_I getAssoziiertesProdukt() {
			return myAssoziiertesProdukt;
		}
		
		public List<Integer> getBestellungen() {
			return myBestellungen;
		}
		
		public int getSupport() {
			return mySupport;
		}
		
		public int getKonfidenz() {
			return myKonfidenz;
		}
	}
}
