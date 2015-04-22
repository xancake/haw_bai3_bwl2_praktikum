package org.haw.bwl2.praktikum.produkt;

import java.net.URL;
import java.util.List;

public interface Produkt_I {
	String getID();
	void setID(String id);
	
	String getName();
	void setName(String name);
	
	URL getBildURL();
	void setBildURL(URL url);
	
	int getBestand();
	void setBestand(int bestand);
	
	double getPreis();
	void setPreis(double preis);
	
	List<Produkt_I> getUnterteile();
	void addUnterteil(Produkt_I produkt);
	void removeUnterteil(Produkt_I produkt);
	
	List<Statistik> getStatistiken();
	void addStatistik(Statistik stat);
	void removeStatistik(Statistik stat);
}
