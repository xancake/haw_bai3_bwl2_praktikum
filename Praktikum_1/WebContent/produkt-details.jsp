<%@page import="org.haw.bwl2.praktikum.Parameter"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader"%>
<%@page import="org.haw.bwl2.praktikum.produkt.persistence.ProduktLoader_I"%>
<%@page import="java.util.List"%>
<%@page import="org.haw.bwl2.praktikum.util.StringUtils"%>
<%@page import="org.haw.bwl2.praktikum.produkt.Produkt_I"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%!
	private String printUnterteile(List<Produkt_I> unterteile) {
		StringBuilder text = new StringBuilder();
		for(Produkt_I bauteil : unterteile) {
			text.append("<li>");
			text.append("<div class=\"produkt\">");
			text.append("<img src=\"").append(bauteil.getBildURL()).append("\" height=50 width=50 />");
			text.append("<div class=\"titel\">").append(bauteil.getName()).append("</div>");
			text.append("<p>Preis: ").append(bauteil.getPreis()).append(" &euro;</p>");
			text.append("<p>Bestand: ").append(bauteil.getBestand()).append("</p>");
			
			if(bauteil.getUnterteile() != null) {
				text.append("<ul class=\"produkte\">");
				text.append(printUnterteile(bauteil.getUnterteile()));
				text.append("</ul>");
			}
			
			text.append("</div>");
			text.append("</li>");
		}
		return text.toString();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="global.css" />
		<link rel="stylesheet" type="text/css" href="produkt-details.css" />
		<title>Produkt-Details</title>
	</head>
	<body>
		<div id="content">
			<%
				String produktParam = request.getParameter(Parameter.PRODUKT_ID);
				if(!StringUtils.isNullOrEmpty(produktParam)) {
					try(ProduktLoader_I loader = new ProduktLoader()) {
						Produkt_I produkt = loader.loadProdukt(produktParam);
			%>
			<div class="produkt">
				<img src="<%= produkt.getBildURL() %>" height=50 width=50>
				<div class="titel"><%= produkt.getName() %></div>
				<p>Preis: <%= produkt.getPreis() %> &euro;</p>
				<p>Bestand: <%= produkt.getBestand() %></p>
			</div>
			
			<ul class="produkte">
				<%= printUnterteile(produkt.getUnterteile()) %>
			</ul>
			
			<% 
					}
				} else {
			%>
			<p>Es wurde kein Produkt ausgew&auml;hlt. Zur&uuml;ck zur <a href="./produkte.jsp">Produkt-&Uuml;bersicht</a></p>
			<%  } %>
		</div>
	</body>
</html>