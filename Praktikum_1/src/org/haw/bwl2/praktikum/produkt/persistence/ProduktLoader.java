package org.haw.bwl2.praktikum.produkt.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfigurationSingleton;
import org.haw.bwl2.praktikum.produkt.persistence.db.oracle.OracleDBProduktLoader;

public class ProduktLoader implements ProduktLoader_I {
	private ProduktLoader_I myLoader;
	
	public ProduktLoader() throws IOException {
		try {
			DBConfiguration configDB = DBConfigurationSingleton.getInstance().getConfiguration();
			myLoader = new OracleDBProduktLoader(configDB);
		} catch (SQLException e) {
			throw new IOException("Fehler beim Instanzieren des Loaders", e);
		}
	}
	
	@Override
	public List<Produkt_I> loadAlleProdukte() throws IOException {
		return myLoader.loadAlleProdukte();
	}
	
	@Override
	public Produkt_I loadProdukt(String id) throws IOException {
		return myLoader.loadProdukt(id);
	}
	
	@Override
	public List<Produkt_I> loadProdukte(String name, double preisVon, double preisBis) throws IOException {
		return myLoader.loadProdukte(name, preisVon, preisBis);
	}
	
	@Override
	public void close() throws IOException {
		myLoader.close();
	}
}
