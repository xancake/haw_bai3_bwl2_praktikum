package org.haw.bwl2.praktikum;

import java.util.List;

import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.loader.ProduktLoader_I;
import org.haw.bwl2.praktikum.produkt.loader.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.loader.db.DBConfigurationSingleton;
import org.haw.bwl2.praktikum.produkt.loader.db.oracle.OracleDBProduktLoader;

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
