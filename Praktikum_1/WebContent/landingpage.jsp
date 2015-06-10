<%@page import="org.haw.bwl2.praktikum.analyse.persistence.Analyse"%>
<%@page import="org.haw.bwl2.praktikum.analyse.persistence.Analyse_I"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getRequestURI();
	Analyse_I analyse = new Analyse();
	analyse.incrementAufrufe(path.substring(path.lastIndexOf("/")+1));
	analyse.close();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="global.css" />
		<link rel="stylesheet" type="text/css" href="produkte.css" />
		<title>LuL Shop - Die besten Angebote aus LoL</title>
	</head>
	<body>
		
		<div id="content">
			
			<h2>Kaufen Kaufen Kaufen</h2>
			<h5>JETZT KAUFEN!</h5>
			<br />
			
			<p>
				Die neusten Gegenst&auml;nde, kaum in der PBE und schon bei uns erh&auml;ltlich!
				Wir haben wirklich alles auf Lager, vom "Abyssal Sceptre" f&uuml;r den erfolgreichen Magier, bis hin zu "Zz'Rot Portal", f&uuml;r verzweifelte Supports.
			</p>
			<p>Wenn sie noch keine Chance hatten den neuen Gegenstand "Luden's Echo" auzuprobieren, dann wird es jetzt h&ouml;chste Zeit!</p>
			<p>Seit seiner Einf&uuml;hrung vor einigen Wochen kam es zu st&auml;ndigen Engp&auml;ssen, da die Nachfrage s&auml;mtliche Erwartungen &uuml;bertraf.</p>
			<p>
				Trotz dessen ist er bereits in die TOP 10 der meistgekauften Gegenst&auml;nde aufgestiegen.
				Die L&ouml;sung f&uuml;r ihre Manaprobleme: Wir f&uuml;hren ab sofort wieder "Mana Crystal", welche extra f&uuml;r unsere Kunden von Minions in den Ruinen von Shurima gesammelt, und in unseren eigenen Einrichtungen in der Kluft der Beschw&ouml;rer geschnitten und auf Hochglanz poliert werden.
				All dies und noch viel mehr kriegen sie nat&uuml;rlich zu unserer bew&auml;hrten Kleinstpreis-Garantie!*
			</p>
			<br />
			
			<h5>Neu im Lager diese Woche:</h5>
			<p>Die neue, verbesserte und wesentlich stylischere Version des "Black Cleaver", f&uuml;r den alten Preis!**</p>
			<br />
			
			<h5>Angebote:</h5>
			<p>Noch bis zum 1.3.2037, gro&szlig;er 90s Kids-Ausverkauf: 20% auf alles, au&szlig;er Wards.***</p>
			<p>Jeder Teemo Spieler erh&auml;lt zu einem Einkauf von 50&euro; oder mehr kostenlos ein "Liandry's Torment"!****</p>
			<br />
			
			<p>Zum <a href="produkte.jsp">Shop</a>!</p>
			
			<hr />
			
			<p class="footnote">* Finden sie einen Shop mit besseren Preisen,  im Umkreis von 50km, und wir sorgen daf&uuml;r, dass er schlie&szlig;t.</p>
			<p class="footnote">** Nur solange der Vorrat reicht.</p>
			<p class="footnote">*** Nur f&uuml;r Kunden g&uuml;ltig, welche im Zeitraum 1990 - 1999 geboren wurden.</p>
			<p class="footnote">**** Nicht bei Eink&auml;ufen g&uuml;ltig, welche ein "Rylai's Crystal Scepter" beinhalten, und nur solange der Vorrat reicht.</p>
			
		</div>
		
	</body>
</html>