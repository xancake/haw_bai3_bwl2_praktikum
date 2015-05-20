package org.haw.bwl2.praktikum;

import java.util.List;

import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader_I;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfigurationSingleton;
import org.haw.bwl2.praktikum.produkt.persistence.db.oracle.OracleDBProduktLoader;

public class TestMain {
	public static void main(String[] args) throws Exception {
		DBConfiguration config = DBConfigurationSingleton.getInstance().getConfiguration();
		ProduktLoader_I loader = new OracleDBProduktLoader(config);
		List<Produkt_I> produkte = loader.loadAlleProdukte();
		
		for(Produkt_I produkt : produkte) {
			System.out.println(produkt + "\n");
		}
	}
}
