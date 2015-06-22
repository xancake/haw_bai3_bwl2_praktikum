<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.haw.bwl2.praktikum.analyse.assoziation.AssoziationsAnalyse.AnalyseEintrag"%>
<%@page import="org.haw.bwl2.praktikum.analyse.assoziation.AssoziationsAnalyse"%>
<%@page import="org.haw.bwl2.praktikum.analyse.persistence.Analyse_I"%>
<%@page import="org.haw.bwl2.praktikum.analyse.persistence.Analyse"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.TreeMap"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.haw.bwl2.praktikum.bestellung.Bestellung"%>
<%@page import="java.util.List"%>
<%@page import="java.util.NavigableMap"%>
<%@page
	import="org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader"%>
<%@page
	import="org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader_I"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Analyse_I analyse = new Analyse();
	Map<String, Integer> siteZuAufrufeMapping = analyse.getAufrufeFuerAlleWebsites();

	BestellungLoader_I loader = new BestellungLoader();

	List<Bestellung> bestellungen = loader.loadAlleBestellungen();
	double gesamtWert = 0;
	final Map<Integer, Double> wertMapping = new HashMap<>();
	for (Bestellung bestellung : bestellungen) {
		for (Produkt_I produkt : bestellung.getBestellteProdukte()) {
			if (!wertMapping.containsKey(produkt.getID()))
				wertMapping.put(produkt.getID(), 0.0);

			double aktuellerWert = produkt.getPreis() * bestellung.getBestellMenge(produkt);
			wertMapping.put(produkt.getID(), wertMapping.get(produkt.getID()) + aktuellerWert);
			gesamtWert += aktuellerWert;
		}
	}
	
	List<Integer> produkteSortiertNachWert = new ArrayList<>();
	produkteSortiertNachWert.addAll(wertMapping.keySet());
	Collections.sort(produkteSortiertNachWert, new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			// Der zweite Übergabeparameter wird zuerst in die Compare-Methode von Double übergeben,
			// um eine absteigende Sortierung zu gewährleisten (gegenüberstehend einer aufsteigenden).
			return Double.compare(wertMapping.get(o2), wertMapping.get(o1));
		}
	});
	
	List<Integer> aKlasseProdukte = new ArrayList<>();
	List<Integer> bKlasseProdukte = new ArrayList<>();
	List<Integer> cKlasseProdukte = new ArrayList<>();

	//TODO: Koennte fuer groeßere Flexibiltaet durch Methodenaufruf uebergeben werden.
	double aKlasseProzentual = 0.75; 
	double bKlasseProzentual = 0.95;

	double summe = 0.0;
	for(Integer produktID : produkteSortiertNachWert) {
		double wert = wertMapping.get(produktID);
		if (summe < gesamtWert * aKlasseProzentual) {
			aKlasseProdukte.add(produktID);
		} else if (summe < gesamtWert * bKlasseProzentual) {
			bKlasseProdukte.add(produktID);
		} else {
			cKlasseProdukte.add(produktID);
		}
		summe += wert;
	}
	int minSupport = 10;
	int minKonfidenz = 30;
	AssoziationsAnalyse assAnal = new AssoziationsAnalyse(minSupport, minKonfidenz);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="global.css" />
<link rel="stylesheet" type="text/css" href="admin.css" />
<title>Admin</title>
</head>
<body>
	<div id="content">
		<div id="abc-analyse">
			<h1>ABC-Analyse</h1>
			<table>
				<tr>
					<td>A-Klasse</td>
					<% //Da nur die ProduktID durchgegeben wird müssten produktdaten zuerst wieder aus der Datenbank geladen werden.
					   //TODO: Problem mit Produkten als Schleussel beheben, und dann das Produkt durchgeben.
						for (Integer produktID : aKlasseProdukte) {
					%>
					<td><%=produktID%></td>
					<%
						}
					%>
				</tr>
				<tr>
					<td>B-Klasse</td>
					<%
						for (Integer produktID : bKlasseProdukte) {
					%>
					<td><%=produktID%></td>
					<%
						}
					%>
				</tr>
				<tr>
					<td>C-Klasse</td>
					<%
						for (Integer produktID : cKlasseProdukte) {
					%>
					<td><%=produktID%></td>
					<%
						}
					%>
				</tr>
			</table>
		</div>
		<br />
		
		<div id="assoziations-analyse">
			<h1>Assoziation-Analyse bei <%= minSupport %> % Support und <%= minKonfidenz %> % Konfidenz</h1>
			<table>
				<tr>
					<td>
						Produktassoziation
					</td>
					<td>
						erfüllende Bestellungen
					</td>
					<td>
						Support
					</td>
					<td>
						Konfidenz
					</td>
				</tr>
				<%
					for(Produkt_I produkt : assAnal.getAllProdukte()){
						List<AnalyseEintrag> analListe = assAnal.getEintraegeFuer(produkt);
						for(AnalyseEintrag analEintrag : analListe){
				%>
				<tr>
					<td>
						<%= produkt.getName() %> => <%= analEintrag.getAssoziiertesProdukt().getName() %>
					</td>
					<td>
						<%= analEintrag.getBestellungen() %>
					</td>
					<td>
						<%= analEintrag.getSupport() %> &#37;
					</td>
					<td>
						<%= analEintrag.getKonfidenz() %> &#37;
					</td>
				</tr>
				<%
						}
					}
				%>
			</table>
		</div>
		<br />
		
		<div id="web-analyse">
			<h1>Web-Analyse der Zugriffe</h1>
			<table>
				<tr>
					<td>Seite</td>
					<td>Zugriffe</td>
				</tr>
				<%
					for(Map.Entry<String, Integer> mapEntry : siteZuAufrufeMapping.entrySet()){
				%>
						<tr>
							<td><%= mapEntry.getKey() %></td>
							<td><%= mapEntry.getValue() %></td>
						</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>
<%
	loader.close();
	analyse.close();
%>