<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader_I"%>
<%@page import="org.haw.bwl2.praktikum.util.StringUtils"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	ProduktLoader_I loader = new ProduktLoader();
	List<Produkt_I> produkte;
	
	String pName = request.getParameter("name");
	String pPreisVon = request.getParameter("preis-von");
	String pPreisBis = request.getParameter("preis-bis");
	if(pName != null || pPreisVon != null || pPreisBis != null) {
		double preisVon = !StringUtils.isNullOrEmpty(pPreisVon) ? Double.parseDouble(pPreisVon) : 0;
		double preisBis = !StringUtils.isNullOrEmpty(pPreisBis) ? Double.parseDouble(pPreisBis) : Double.MAX_VALUE;
		produkte = loader.loadProdukte(pName, preisVon, preisBis);
	} else {
		produkte = loader.loadAlleProdukte();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="global.css" />
		<link rel="stylesheet" type="text/css" href="produkte.css" />
		<title>Produkte</title>
	</head>
	<body>
		
		<div id="content">
			<div id="suche">
				<form action="produkte.jsp" method="get">
					Name: <input type="text" name="name" value="<%= pName!=null ? pName : "" %>" />
					Preis von: <input type="text" name="preis-von" value="<%= pPreisVon!=null ? pPreisVon : "" %>" />
					Preis bis: <input type="text" name="preis-bis" value="<%= pPreisBis!=null ? pPreisBis : "" %>" />
					<input type="submit" value="Suchen" />
				</form>
			</div>
			
			
			<ul id="produkte">
				<% for(Produkt_I produkt : produkte) { %>
				<li>
				<form action="warenkorb.jsp" method="get">
					<input type="hidden" name="produktID" value="<%= produkt.getID() %>" />
					<div class="produkt">
						<img src="<%= produkt.getBildURL() %>" height=50 width=50>
						<div class="titel"><a href="produkt-details.jsp?produkt=<%= produkt.getID() %>"><%= produkt.getName() %></a></div>
						<p>Preis: <%= produkt.getPreis() %> &euro;</p>
						<p>Bestand: <%= produkt.getBestand() %></p>
					</div>
					<input type="submit" name="mode" value="Kaufen" />
				</form>
				</li>
				<% } %>
			</ul>
		</div>
		
	</body>
</html>