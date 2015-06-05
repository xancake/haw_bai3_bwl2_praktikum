package org.haw.bwl2.praktikum.bestellung.persistence.db.oracle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleDriver;

import org.haw.bwl2.praktikum.bestellung.Bestellung;
import org.haw.bwl2.praktikum.bestellung.persistence.BestellungStorer_I;
import org.haw.bwl2.praktikum.produkt.Produkt_I;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;

public class OracleDBBestellungStorer implements BestellungStorer_I {
	private static final String SQL_NEXT_BESTELL_ID        = "SELECT Bestellung_seq.nextval FROM DUAL";
	private static final String SQL_INSERT_BESTELLUNG      = "INSERT INTO Bestellung (id, datum) VALUES (?, ?)";
	private static final String SQL_INSERT_BESTELLPOSITION = "INSERT INTO Bestellposition (bestellung, produkt, menge) VALUES (?, ?, ?)";
	
	private Connection myConnection;
	
	public OracleDBBestellungStorer(DBConfiguration config) throws SQLException {
		this(config.getHost(), config.getUser(), config.getPassword());
	}
	
	public OracleDBBestellungStorer(String url, String user, String password) throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		myConnection = DriverManager.getConnection(url, user, password);
	}
	
	@Override
	public void createBestellung(Bestellung bestellung) throws IOException {
		try(
				Statement stmt = myConnection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL_NEXT_BESTELL_ID);
		) {
			rs.next();
			bestellung.setID(rs.getInt(1));
			
			try(PreparedStatement pstmt = myConnection.prepareStatement(SQL_INSERT_BESTELLUNG)) {
				pstmt.setInt(1, bestellung.getID());
				pstmt.setDate(2, new Date(bestellung.getBestelldatum().getTime()));
				pstmt.execute();
				for(Produkt_I produkt : bestellung.getBestellteProdukte()) {
					createBestellposition(bestellung, produkt, bestellung.getBestellMenge(produkt));
				}
			}
		} catch(SQLException e) {
			throw new IOException(e);
		}
	}
	
	private void createBestellposition(Bestellung bestellung, Produkt_I produkt, int menge) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_INSERT_BESTELLPOSITION)) {
			stmt.setInt(1, bestellung.getID());
			stmt.setInt(2, produkt.getID());
			stmt.setInt(3, menge);
			stmt.execute();
		} catch(SQLException e) {
			throw new IOException(e);
		}
	}
	
	@Override
	public void close() throws Exception {
		myConnection.close();
	}
}
