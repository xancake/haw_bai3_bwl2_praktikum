package org.haw.bwl2.praktikum.bestellung.persistence.db.oracle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleDriver;

import org.haw.bwl2.praktikum.bestellung.Bestellung;
import org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader_I;
import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader;
import org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader_I;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;

public class OracleDBBestellungLoader implements BestellungLoader_I {
	private static final String SQL_ALLE_BESTELLUNGEN = "SELECT id FROM Bestellung";
	private static final String SQL_EINE_BESTELLUNG   = "SELECT * FROM Bestellung WHERE id=?";
	private static final String SQL_BESTELLPOSITIONEN = "SELECT * FROM Bestellposition WHERE bestellung=?";
	
	private Connection myConnection;
	private ProduktLoader_I myProduktLoader;
	
	public OracleDBBestellungLoader(DBConfiguration config) throws IOException, SQLException {
		this(config.getHost(), config.getUser(), config.getPassword());
	}
	
	public OracleDBBestellungLoader(String url, String user, String password) throws IOException, SQLException {
		DriverManager.registerDriver(new OracleDriver());
		myConnection = DriverManager.getConnection(url, user, password);
		myProduktLoader = new ProduktLoader();
	}
	
	@Override
	public List<Bestellung> loadAlleBestellungen() throws IOException {
		List<Bestellung> bestellungen = new ArrayList<>();
		try(
				Statement stmt = myConnection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL_ALLE_BESTELLUNGEN);
		) {
			while(rs.next()) {
				bestellungen.add(loadBestellung(rs.getInt("id")));
			}
		} catch(SQLException e) {
			throw new IOException(e);
		}
		return bestellungen;
	}
	
	@Override
	public Bestellung loadBestellung(int id) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_EINE_BESTELLUNG)) {
			stmt.setInt(0, id);
			try(ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					Bestellung bestellung = new Bestellung();
					bestellung.setID(rs.getInt("id"));
					bestellung.setBestelldatum(rs.getDate("datum"));
					appendBestellpositionen(bestellung);
					return bestellung;
				} else {
					throw new IOException("Die Bestellung " + id + " existiert nicht!");
				}
			}
		} catch(SQLException e) {
			throw new IOException(e);
		}
	}
	
	private void appendBestellpositionen(Bestellung bestellung) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_BESTELLPOSITIONEN)) {
			stmt.setInt(0, bestellung.getID());
			try(ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					Produkt_I produkt = myProduktLoader.loadProdukt(rs.getString("produkt"));
					int menge = rs.getInt("menge");
					bestellung.addBestelltesProdukt(produkt, menge);
				}
			}
		} catch(SQLException e) {
			throw new IOException(e);
		}
	}
	
	@Override
	public void close() throws Exception {
		myConnection.close();
		myProduktLoader.close();
	}
}
