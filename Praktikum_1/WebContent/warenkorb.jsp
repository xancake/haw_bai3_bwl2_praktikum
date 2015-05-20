<%@page import="java.util.Map.Entry"%>
<%@page import="org.haw.bwl2.praktikum.warenkorb.Warenkorb"%>
<%@page import="org.haw.bwl2.praktikum.produkt.loader.db.oracle.OracleDBProduktLoader"%>
<%@page import="org.haw.bwl2.praktikum.produkt.loader.db.DBConfigurationSingleton"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@page import="java.util.List"%>
<%@page import="org.haw.bwl2.praktikum.produkt.loader.ProduktLoader_I"%>
<%@page import="org.haw.bwl2.praktikum.produkt.loader.db.DBConfiguration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	DBConfiguration configDB = DBConfigurationSingleton.getInstance().getConfiguration();
	ProduktLoader_I loader = new OracleDBProduktLoader(configDB);
	
	Warenkorb warenkorb = (Warenkorb)session.getAttribute("Warenkorb");
	if(warenkorb == null) {
		warenkorb = new Warenkorb();
		session.setAttribute("Warenkorb", warenkorb);
	}
	
	String fehlermeldung = null;
	
	String pMode = request.getParameter("mode");
	if(pMode != null) {
		switch(pMode) {
			case "Kaufen": {
				String pProdukt = request.getParameter("produktID");
				Produkt_I produkt = loader.loadProdukt(pProdukt);
				if(warenkorb.getMengeVonProdukt(produkt) == 0) {
					warenkorb.trageProdukteEin(produkt, 1);
				} else {
					fehlermeldung = produkt.getName() + " ist schon im Warenkorb enthalten";
				}
				break;
			}
			case "Menge Aktualisieren": {
				String pProdukt = request.getParameter("produktID");
				String pMenge   = request.getParameter("menge");
				Produkt_I produkt = loader.loadProdukt(pProdukt);
				int menge = 0;
				try {
					menge = Integer.parseInt(pMenge);
				} catch(NumberFormatException e) {
					fehlermeldung = "Sie müssen eine Zahl als Menge eingeben!";
				}
				if(menge <= produkt.getBestand()) {
					warenkorb.trageProdukteEin(produkt, menge);
				} else {
					fehlermeldung = produkt.getName() + " ist in der von Ihnen gewünschten Menge nicht vorhanden.";
				}
				break;
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="global.css" />
		<link rel="stylesheet" type="text/css" href="produkte.css" />
		<title>Warenkorb</title>
	</head>
	<body>
		
		<div id="content">
			<% if(fehlermeldung != null) { %>
			<div class="error">
				<p class="error_msg"><%= fehlermeldung %></p>
			</div>
			<% } %>
			
			<ul id="produkte">
				<% for(Entry<Produkt_I, Integer> entry : warenkorb) { 
						Produkt_I produkt = entry.getKey();
						Integer menge = entry.getValue();
				%>
				<li>
				<form action="warenkorb.jsp" method="get">
					<input type="hidden" name="produktID" value="<%= produkt.getID() %>" />
					<div class="produkt">
						<img src="<%= produkt.getBildURL() %>" height=50 width=50>
						<div class="titel"><a href="produkt-details.jsp?produkt=<%= produkt.getID() %>"><%= produkt.getName() %></a></div>
						<p>Einzelpreis: <%= produkt.getPreis() %> &euro;</p>
						<p>Gesamtpreis: <%= produkt.getPreis()*menge %> &euro;</p>
						<p>Bestand: <%= produkt.getBestand() %></p>
						<p>Menge: <input type="text" name="menge" value="<%= menge %>" /></p>
					</div>
					<input type="submit" name="mode" value="Menge Aktualisieren" />
				</form>
				</li>
				<% } %>
			</ul>
			
			<div class="">
				<form action="produkte.jsp">
					<input type="submit" value="Weiter einkaufen" />
				</form>
				<form action="kasse.jsp">
					<input type="submit" name="Kaufen" value="Zur Kasse" />
				</form>
			</div>
		</div>
		
	</body>
</html>