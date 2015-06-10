package org.haw.bwl2.praktikum.analyse.persistence;

import java.io.IOException;
import java.util.Map;

public interface Analyse_I extends AutoCloseable{

	int getAufrufe(String website) throws IOException;
	
	Map<String, Integer> getAufrufeFuerAlleWebsites() throws IOException;
	
	void incrementAufrufe(String website) throws IOException;
}
