package org.haw.bwl2.praktikum.analyse.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.haw.bwl2.praktikum.analyse.persistence.db.oracle.OracleDBAnalyse;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfigurationSingleton;

public class Analyse implements Analyse_I {
	private Analyse_I myAnalyse;

	public Analyse() throws IOException {
		try {
			DBConfiguration configDB = DBConfigurationSingleton.getInstance().getConfiguration();
			myAnalyse = new OracleDBAnalyse(configDB);
		} catch (SQLException e) {
			throw new IOException("Fehler beim Instanzieren des Loaders", e);
		}
	}

	@Override
	public int getAufrufe(String website) throws IOException {
		return myAnalyse.getAufrufe(website);
	}

	@Override
	public void incrementAufrufe(String website) throws IOException {
		myAnalyse.incrementAufrufe(website);
	}

	@Override
	public void close() throws Exception {
		myAnalyse.close();
	}

	@Override
	public Map<String, Integer> getAufrufeFuerAlleWebsites() throws IOException {
		return myAnalyse.getAufrufeFuerAlleWebsites();
	}
}
