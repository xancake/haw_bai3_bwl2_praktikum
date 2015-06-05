package org.haw.bwl2.praktikum.produkt.persistence;

import java.io.IOException;
import java.sql.SQLException;

import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfigurationSingleton;
import org.haw.bwl2.praktikum.produkt.persistence.db.oracle.OracleDBProduktStorer;

public class ProduktStorer implements ProduktStorer_I {
	private ProduktStorer_I myStorer;
	
	public ProduktStorer() throws IOException {
		try {
			DBConfiguration configDB = DBConfigurationSingleton.getInstance().getConfiguration();
			myStorer = new OracleDBProduktStorer(configDB);
		} catch (SQLException e) {
			throw new IOException("Fehler beim Instanzieren des Loaders", e);
		}
	}
	
	@Override
	public void storeProdukt(Produkt_I produkt) throws IOException {
		myStorer.storeProdukt(produkt);
	}
	
	@Override
	public void close() throws Exception {
		myStorer.close();
	}
}
