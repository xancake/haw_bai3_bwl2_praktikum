package org.haw.bwl2.praktikum.bestellung.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.haw.bwl2.praktikum.bestellung.Bestellung;
import org.haw.bwl2.praktikum.bestellung.persistence.db.oracle.OracleDBBestellungLoader;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfigurationSingleton;

public class BestellungLoader implements BestellungLoader_I {
	private BestellungLoader_I myLoader;

	public BestellungLoader() throws IOException {
		try {
			DBConfiguration configDB = DBConfigurationSingleton.getInstance().getConfiguration();
			myLoader = new OracleDBBestellungLoader(configDB);
		} catch (SQLException e) {
			throw new IOException("Fehler beim Instanzieren des Loaders", e);
		}
	}

	@Override
	public List<Bestellung> loadAlleBestellungen() throws IOException {
		return myLoader.loadAlleBestellungen();
	}

	@Override
	public Bestellung loadBestellung(int id) throws IOException {
		return myLoader.loadBestellung(id);
	}

	@Override
	public void close() throws Exception {
		myLoader.close();
	}
}