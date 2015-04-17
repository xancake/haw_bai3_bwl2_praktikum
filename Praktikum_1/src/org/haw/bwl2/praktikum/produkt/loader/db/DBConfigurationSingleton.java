package org.haw.bwl2.praktikum.produkt.loader.db;

import java.io.IOException;
import java.io.InputStream;

public class DBConfigurationSingleton {
	private static final String DEFAULT_CONFIG = "db.properties";
	
	private DBConfiguration myConfiguration;
	
	public DBConfigurationSingleton() {
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG);
			if(in == null) {
				throw new RuntimeException("Die Datenbank-Konfigurations-Datei konnte nicht gefunden werden");
			}
			myConfiguration = new DBConfiguration(in);
		} catch (IOException e) {
			throw new RuntimeException("Fehler beim Laden der Datenbank-Konfigurations-Datei", e);
		}
	}
	
	public DBConfiguration getConfiguration() {
		return myConfiguration;
	}
	
	public static DBConfigurationSingleton getInstance() {
		return Holder.INSTANCE;
	}
	
	private static class Holder {
		private static final DBConfigurationSingleton INSTANCE = new DBConfigurationSingleton();
	}
}
