package org.haw.bwl2.praktikum.analyse.persistence.db.oracle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleDriver;

import org.haw.bwl2.praktikum.analyse.persistence.Analyse_I;
import org.haw.bwl2.praktikum.produkt.persistence.db.DBConfiguration;

public class OracleDBAnalyse implements Analyse_I{


	private final static String SQL_AUFRUFE_FUER_ALLE_WEBSITES = "SELECT * FROM Analytics";
	private final static String SQL_AUFRUFE_ZU_WEBSITE = "SELECT aufrufe FROM Analytics WHERE website =?";
	private final static String SQL_UPDATE_AUFRUFE_ZU_WEBSITE = "UPDATE Analytics SET aufrufe = aufrufe+1 WHERE website =?";
	private final static String SQL_INSERT_AUFRUFE_ZU_WEBSITE = "INSERT INTO Analytics VALUES(?,1)";
	
	private Connection myConnection;
	
	public OracleDBAnalyse(DBConfiguration config) throws SQLException {
		this(config.getHost(), config.getUser(), config.getPassword());
	}
	
	public OracleDBAnalyse(String url, String user, String password) throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		myConnection = DriverManager.getConnection(url, user, password);
	}
	
	
	@Override
	public int getAufrufe(String website) throws IOException {
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_AUFRUFE_ZU_WEBSITE)){
			stmt.setString(1, website);
			try(ResultSet rs = stmt.executeQuery()){
				int aufrufe = 0;
				if(rs.next()){
					aufrufe = rs.getInt("aufrufe");
				}
				return aufrufe;
			}
		} catch(SQLException e){
			throw new IOException(e);
		}
	}

	@Override
	public void incrementAufrufe(String website) throws IOException{
		try(PreparedStatement stmt = myConnection.prepareStatement(SQL_AUFRUFE_ZU_WEBSITE)){
			stmt.setString(1, website);
			try(ResultSet rs = stmt.executeQuery()) {
				PreparedStatement incrementStmt = rs.next()
						? myConnection.prepareStatement(SQL_UPDATE_AUFRUFE_ZU_WEBSITE)
						: myConnection.prepareStatement(SQL_INSERT_AUFRUFE_ZU_WEBSITE);
				try {
					incrementStmt.setString(1, website);
					incrementStmt.execute();
				} finally {
					incrementStmt.close();
				}
			}
		} catch(SQLException e){
			throw new IOException(e);
		}
	}

	@Override
	public void close() throws Exception {
		myConnection.close();
	}

	@Override
	public Map<String, Integer> getAufrufeFuerAlleWebsites() throws IOException {
		try(Statement stmt = myConnection.createStatement()){
			try(ResultSet rs = stmt.executeQuery(SQL_AUFRUFE_FUER_ALLE_WEBSITES)){
				Map<String, Integer> map = new HashMap<>();
				while(rs.next()){
					map.put(rs.getString("website"), rs.getInt("aufrufe"));
				}
				return map;
			}
		} catch(SQLException e){
			throw new IOException(e);
		}
	}

}
