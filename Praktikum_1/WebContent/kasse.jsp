<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktStorer"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktStorer_I"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader_I"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="org.haw.bwl2.praktikum.warenkorb.Warenkorb"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	ProduktLoader_I loader = new ProduktLoader();
	ProduktStorer_I storer = new ProduktStorer();
	
	Warenkorb warenkorb = (Warenkorb)session.getAttribute("Warenkorb");
	if(warenkorb == null) {
		warenkorb = new Warenkorb();
		session.setAttribute("Warenkorb", warenkorb);
	}
	
	for(Entry<Produkt_I, Integer> entry : warenkorb) {
		Produkt_I produkt = entry.getKey();
		produkt.setBestand(produkt.getBestand()-entry.getValue());
		storer.storeProdukt(produkt);
	}
	
	session.setAttribute("Warenkorb", new Warenkorb());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="global.css" />
		<title>Kasse</title>
	</head>
	<body>
		
		<div id="content">
			<p>Viel Spa&szlig; mit ihren gekaufen Produkten!</p>
			<br/>
			<a href="produkte.jsp">Zurück zur Produktübersicht</a>
		</div>
		
	</body>
</html>