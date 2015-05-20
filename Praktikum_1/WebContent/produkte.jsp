<%@page import="org.apache.jasper.tagplugins.jstl.core.Param"%>
<%@page import="org.haw.bwl2.praktikum.Parameter"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader_I"%>
<%@page import="org.haw.bwl2.praktikum.util.StringUtils"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	ProduktLoader_I loader = new ProduktLoader();
	List<Produkt_I> produkte;
	
	String pName = request.getParameter(Parameter.PRODUKT_SUCHE_NAME);
	String pPreisVon = request.getParameter(Parameter.PRODUKT_SUCHE_PREIS_VON);
	String pPreisBis = request.getParameter(Parameter.PRODUKT_SUCHE_PREIS_BIS);
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
					Name: <input type="text" name="<%= Parameter.PRODUKT_SUCHE_NAME %>" value="<%= pName!=null ? pName : "" %>" />
					Preis von: <input type="text" name="<%= Parameter.PRODUKT_SUCHE_PREIS_VON %>" value="<%= pPreisVon!=null ? pPreisVon : "" %>" />
					Preis bis: <input type="text" name="<%= Parameter.PRODUKT_SUCHE_PREIS_BIS %>" value="<%= pPreisBis!=null ? pPreisBis : "" %>" />
					<input type="submit" value="<%= Parameter.PRODUKT_MODE_SUCHE %>" />
				</form>
			</div>
			
			<ul id="produkte">
				<% for(Produkt_I produkt : produkte) { %>
				<li>
				<form action="warenkorb.jsp" method="get">
					<div class="produkt">
						<input type="hidden" name="<%= Parameter.PRODUKT_ID %>" value="<%= produkt.getID() %>" />
						<img src="<%= produkt.getBildURL() %>" height=50 width=50>
						<div class="titel"><a href="produkt-details.jsp?<%= Parameter.PRODUKT_ID %>=<%= produkt.getID() %>"><%= produkt.getName() %></a></div>
						<p>Preis: <%= produkt.getPreis() %> &euro;</p>
						<p>Bestand: <%= produkt.getBestand() %></p>
						<input type="submit" name="<%= Parameter.WARENKORB_MODE %>" value="<%= Parameter.WARENKORB_MODE_KAUFEN %>" />
					</div>
				</form>
				</li>
				<% } %>
			</ul>
		</div>
		
	</body>
</html>