package org.haw.bwl2.praktikum.produkt.loader.db.oracle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.loader.db.DBConfiguration;
import org.haw.bwl2.praktikum.produkt.loader.db.ProduktStorer_I;

public class OracleDBProduktStorer implements ProduktStorer_I {
	private static final String SQL_UPDATE_PRODUKT = "UPDATE Produkt SET bestand=?, preis=?, name=?, bild=? WHERE id=?";
	
	private Connection myConnection;
	
	public OracleDBProduktStorer(DBConfiguration config) throws SQLException {
		this(config.getHost(), config.getUser(), config.getPassword());
	}
	
	public OracleDBProduktStorer(String url, String user, String password) throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		myConnection = DriverManager.getConnection(url, user, password);
	}
	
	@Override
	public void storeProdukt(Produkt_I produkt) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_UPDATE_PRODUKT)) {
			stmt.setInt(1, produkt.getBestand());
			stmt.setDouble(2, produkt.getPreis());
			stmt.setString(3, produkt.getName());
			stmt.setURL(4, produkt.getBildURL());
			stmt.setInt(5, produkt.getID());
			stmt.execute();
		} catch(SQLException e) {
			throw new IOException(e);
		}
	}
}
