package org.haw.bwl2.praktikum.produkt.persistence;

import java.io.IOException;
import java.util.List;

import org.haw.bwl2.praktikum.produkt.Produkt_I;

public interface ProduktLoader_I extends AutoCloseable {
	List<Produkt_I> loadAlleProdukte() throws IOException;
	
	Produkt_I loadProdukt(String id) throws IOException;
	
	List<Produkt_I> loadProdukte(String name, double preisVon, double preisBis) throws IOException;
}
