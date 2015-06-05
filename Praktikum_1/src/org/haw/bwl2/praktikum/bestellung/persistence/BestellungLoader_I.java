package org.haw.bwl2.praktikum.bestellung.persistence;

import java.io.IOException;
import java.util.List;

import org.haw.bwl2.praktikum.bestellung.Bestellung;

public interface BestellungLoader_I extends AutoCloseable {
	/**
	 * Lädt alle Bestellungen.
	 * @return Eine Liste aller Bestellungen
	 * @throws IOException Wenn ein Fehler beim Laden aufgetreten ist
	 */
	List<Bestellung> loadAlleBestellungen() throws IOException;
	
	/**
	 * Lädt die Bestellung mit der übergebenen ID.
	 * @param id Die ID der Bestellung
	 * @return Die Bestellung zu der ID oder null, wenn es keine Bestellung mit der ID gibt
	 * @throws IOException Wenn ein Fehler beim Laden aufgetreten ist
	 */
	Bestellung loadBestellung(int id) throws IOException;
}
