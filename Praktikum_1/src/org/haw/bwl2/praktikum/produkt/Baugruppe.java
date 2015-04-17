package org.haw.bwl2.praktikum.produkt;

import java.util.ArrayList;
import java.util.List;

public class Baugruppe extends Einzelteil implements Produkt_I {
	private List<Produkt_I> myUnterteile;
	
	public Baugruppe() {
		myUnterteile = new ArrayList<Produkt_I>();
	}
	
	@Override
	public double getPreis() {
		double preiseAllerUnterteile = 0;
		for(Produkt_I unterteil : myUnterteile) {
			preiseAllerUnterteile += unterteil.getPreis();
		}
		return super.getPreis() + preiseAllerUnterteile;
	}
	
	@Override
	public void addUnterteil(Produkt_I produkt) {
		myUnterteile.add(produkt);
	}
	
	@Override
	public void removeUnterteil(Produkt_I produkt) {
		myUnterteile.remove(produkt);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		for(Produkt_I unterteil : myUnterteile) {
			sb.append("\n[" + getID() + "] ");
			sb.append(unterteil);
		}
		return sb.toString();
	}
}
