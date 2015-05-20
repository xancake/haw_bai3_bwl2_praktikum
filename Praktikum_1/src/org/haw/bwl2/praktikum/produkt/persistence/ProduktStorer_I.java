package org.haw.bwl2.praktikum.produkt.persistence;

import java.io.IOException;

import org.haw.bwl2.praktikum.produkt.Produkt_I;

public interface ProduktStorer_I {
	void storeProdukt(Produkt_I produkt) throws IOException;
}
