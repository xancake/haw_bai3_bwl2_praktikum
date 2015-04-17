package org.haw.bwl2.praktikum.produkt.loader.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfiguration {
	private static final String DB_HOST = "db.host";
	private static final String DB_USER = "db.user";
	private static final String DB_PASS = "db.pass";
	
	private Properties myProperties;
	
	public DBConfiguration(InputStream config) throws IOException {
		myProperties = new Properties();
		myProperties.load(config);
	}
	
	public String getHost() {
		return myProperties.getProperty(DB_HOST);
	}
	
	public String getUser() {
		return myProperties.getProperty(DB_USER);
	}
	
	public String getPassword() {
		return myProperties.getProperty(DB_PASS);
	}
}
