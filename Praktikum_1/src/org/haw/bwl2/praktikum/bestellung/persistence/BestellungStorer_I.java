package org.haw.bwl2.praktikum.bestellung.persistence;

import java.io.IOException;

import org.haw.bwl2.praktikum.bestellung.Bestellung;

public interface BestellungStorer_I extends AutoCloseable {
	/**
	 * Legt eine Bestellung in der Datenbank an.
	 * @param bestellung Die anzulegende Bestellung
	 * @throws IOException Wenn ein Fehler beim Speichern auftritt
	 */
	void createBestellung(Bestellung bestellung) throws IOException;
}
