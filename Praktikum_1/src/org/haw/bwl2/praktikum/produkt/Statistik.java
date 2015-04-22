package org.haw.bwl2.praktikum.produkt;

public class Statistik {
	private String myBezeichnung;
	private int myValue;
	
	public String getBezeichnung() {
		return myBezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung) {
		myBezeichnung = bezeichnung;
	}
	
	public int getValue() {
		return myValue;
	}
	
	public void setWert(int value) {
		myValue = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((myBezeichnung == null) ? 0 : myBezeichnung.hashCode());
		result = prime * result + myValue;
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
		Statistik other = (Statistik) obj;
		if (myBezeichnung == null) {
			if (other.myBezeichnung != null)
				return false;
		} else if (!myBezeichnung.equals(other.myBezeichnung))
			return false;
		if (myValue != other.myValue)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return myValue + (myBezeichnung.startsWith("%") ? "" : " ") + myBezeichnung;
	}
}
