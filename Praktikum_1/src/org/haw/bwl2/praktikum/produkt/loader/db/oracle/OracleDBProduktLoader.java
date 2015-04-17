package org.haw.bwl2.praktikum.produkt.loader.db.oracle;

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

import org.haw.bwl2.praktikum.produkt.Baugruppe;
import org.haw.bwl2.praktikum.produkt.Einzelteil;
import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.loader.ProduktLoader_I;
import org.haw.bwl2.praktikum.produkt.loader.db.DBConfiguration;

public class OracleDBProduktLoader implements ProduktLoader_I {
	private static final String SQL_ALLE_PRODUKTE = "SELECT p.id FROM Produkt p WHERE p.id NOT IN (SELECT DISTINCT unterteil FROM Baugruppe)";
	private static final String SQL_EIN_PRODUKT = "SELECT * FROM Produkt p WHERE p.id=?";
	private static final String SQL_GET_UNTERTEILE = "SELECT * FROM Baugruppe b WHERE b.oberteil=?";
	
	private Connection myConnection;
	
	public OracleDBProduktLoader(DBConfiguration config) throws SQLException {
		this(config.getHost(), config.getUser(), config.getPassword());
	}
	
	public OracleDBProduktLoader(String url, String user, String password) throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		myConnection = DriverManager.getConnection(url, user, password);
	}
	
	@Override
	public List<Produkt_I> loadAlleProdukte() throws IOException {
		List<Produkt_I> produkte = new ArrayList<Produkt_I>();
		try(
				Statement stmt = myConnection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL_ALLE_PRODUKTE)
		) {
			while(rs.next()) {
				produkte.add(loadProdukt(rs.getString("id")));
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
		return produkte;
	}
	
	@Override
	public Produkt_I loadProdukt(String id) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_EIN_PRODUKT)) {
			stmt.setString(1, id);
			try(ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					String ident = rs.getString("id");
					boolean isEinzelteil = isEinzelteil(ident);
					Produkt_I produkt = isEinzelteil ? new Einzelteil() : new Baugruppe();
					produkt.setID(rs.getString("id"));
					produkt.setName(rs.getString("name"));
					produkt.setBestand(rs.getInt("bestand"));
					produkt.setPreis(rs.getDouble("preis"));
					produkt.setBildURL(rs.getURL("bild"));
					if(!isEinzelteil) {
						loadUnterteile(produkt);
					}
					return produkt;
				} else {
					throw new IOException("Das Produkt " + id + " existiert nicht");
				}
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
	
	private void loadUnterteile(Produkt_I produkt) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_GET_UNTERTEILE)) {
			stmt.setString(1, produkt.getID());
			try(ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					produkt.addUnterteil(loadProdukt(rs.getString("unterteil")));
				}
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
	
	private boolean isEinzelteil(String id) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_GET_UNTERTEILE)) {
			stmt.setString(1, id);
			try(ResultSet rs = stmt.executeQuery()) {
				return !rs.next();
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
}
