<%@page import="java.util.Comparator"%>
<%@page import="java.util.TreeMap"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.haw.bwl2.praktikum.bestellung.Bestellung"%>
<%@page import="java.util.List"%>
<%@page import="org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader"%>
<%@page import="org.haw.bwl2.praktikum.bestellung.persistence.BestellungLoader_I"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	BestellungLoader_I loader = new BestellungLoader();
	
	List<Bestellung> bestellungen = loader.loadAlleBestellungen();
	double gesamtWert = 0;
	Map<Integer, Double> wertMapping = new TreeMap<>(new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return Double.compare(wertMapping.get(o1), wertMapping.get(o2));
		}
	});
	for(Bestellung bestellung : bestellungen){
		for(Produkt_I produkt : bestellung.getBestellteProdukte()){
			if(!wertMapping.containsKey(produkt.getID())){
				wertMapping.put(produkt.getID(), 0.0);
			}
			double aktuellerWert = produkt.getPreis()*bestellung.getBestellMenge(produkt);
			wertMapping.put(produkt.getID(),wertMapping.get(produkt.getID()) 
							+ aktuellerWert);
			gesamtWert += aktuellerWert;
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
			
		</div>
	</body>
</html>
<%
	loader.close();
%>