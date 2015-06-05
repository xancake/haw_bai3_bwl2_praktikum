package org.haw.bwl2.praktikum.bestellung.persistence;

import java.io.IOException;
import java.sql.SQLException;

import org.haw.bwl2.praktikum.bestellung.Bestellung;
import org.haw.bwl2.praktikum.bestellung.persistence.db.oracle.OracleDBBestellungStorer;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfigurationSingleton;

public class BestellungStorer implements BestellungStorer_I {
	private BestellungStorer_I myStorer;
	
	public BestellungStorer() throws IOException {
		try {
			DBConfiguration configDB = DBConfigurationSingleton.getInstance().getConfiguration();
			myStorer = new OracleDBBestellungStorer(configDB);
		} catch (SQLException e) {
			throw new IOException("Fehler beim Instanzieren des Loaders", e);
		}
	}
	
	@Override
	public void createBestellung(Bestellung bestellung) throws IOException {
		myStorer.createBestellung(bestellung);
	}
	
	@Override
	public void close() throws Exception {
		myStorer.close();
	}
}
