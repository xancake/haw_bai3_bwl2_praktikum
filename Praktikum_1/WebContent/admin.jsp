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
	BestellungLoader_I loader = new BestellungLoader();

	List<Bestellung> bestellungen = loader.loadAlleBestellungen();
	double gesamtWert = 0;
	final Map<Integer, Double> wertMapping = new HashMap<>();
	NavigableMap<Integer, Double> sortedWertMapping = new TreeMap<>(new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return Double.compare(wertMapping.get(o1), wertMapping.get(o2));
		}
	});
	for (Bestellung bestellung : bestellungen) {
		for (Produkt_I produkt : bestellung.getBestellteProdukte()) {
			if (!wertMapping.containsKey(produkt.getID()))
				wertMapping.put(produkt.getID(), 0.0);

			double aktuellerWert = produkt.getPreis() * bestellung.getBestellMenge(produkt);
			wertMapping.put(produkt.getID(), wertMapping.get(produkt.getID()) + aktuellerWert);
			gesamtWert += aktuellerWert;
		}
	}
	sortedWertMapping.putAll(wertMapping);

	List<Integer> aKlasseProdukte = new ArrayList<>();
	List<Integer> bKlasseProdukte = new ArrayList<>();
	List<Integer> cKlasseProdukte = new ArrayList<>();

	//TODO: Koennte fuer groeﬂere Flexibiltaet durch Methodenaufruf uebergeben werden.
	Double aKlasseProzentual = 0.75; 
	Double bKlasseProzentual = 0.95;

	Double summe = 0.0;
	Entry<Integer, Double> mapEntry;
	while ((mapEntry = sortedWertMapping.pollLastEntry()) != null) {
		if (summe < gesamtWert * aKlasseProzentual) {
			aKlasseProdukte.add(mapEntry.getKey());
			summe += mapEntry.getValue();
		} else if (summe < gesamtWert * bKlasseProzentual) {
			bKlasseProdukte.add(mapEntry.getKey());
			summe += mapEntry.getValue();
		} else {
			cKlasseProdukte.add(mapEntry.getKey());
		}
	}
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
		<table>
			<tr>
				<td>A-Klasse</td>
				<% //Da nur die ProduktID durchgegeben wird m¸ssten produktdaten zuerst wieder aus der Datenbank geladen werden.
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
</body>
</html>
<%
	loader.close();
%>