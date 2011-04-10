<?xml version="1.0" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="ewaMemory.memoryTable.gui.UrlFactory"%>
<%@page import="com.sun.org.omg.SendingContext.CodeBasePackage.URLHelper"%>
<%@page import="ewaMemory.memoryTable.gui.MemoryTableParams"%>
<%@page import="ewaMemory.memoryTable.gui.SessionKeys"%>
<%@page import="ewaMemory.memoryTable.beans.MemoryTable"%>
<%@page import="java.util.List"%>
<%@page import="ewaMemory.memoryTable.beans.MemoryCard"%>
<%@ page language="java" contentType="application/xhtml+xml; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%
    //TODO check for NULL?
    MemoryTable memory = (MemoryTable) session.getAttribute(SessionKeys.MEMORY_TABLE);
    %>
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="de" lang="de">
	<head>
		<title>EWA Memory :: Spiel 1</title>
		<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/>
		<meta name="description" content="Das EWA Memory, ein Spass der niemals endet."/>
		<meta name="keywords" content="Spiel EWA memory"/>
		<meta name="language" content="de-AT"/>
		<link rel="stylesheet" type="text/css" href="styles/screen.css"/>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<div id="teaser"></div>
				<div id="logo"></div>
				<h1>EWA Memory</h1>
			</div>
			<ul class="accessibility">
				<li><a href="#navigation" accesskey="1">Navigation</a></li>
				<li><a href="#board" accesskey="0">Spielbrett</a></li>
				<li><a href="#score" accesskey="2">Spielstand</a></li>
			</ul>
			<div id="body">
				<div id="navigation">
					<h2 class="accessibility">Navigation</h2>
					<ul>
						<li><a href="#">Userdaten &auml;ndern</a></li>
						<li><a href="#">Zur&uuml;ck zur Lounge</a></li>
						<li><a href="#">Ausloggen</a></li>
					</ul>
				</div>
				<hr class="accessibility" />				
				<div id="info_area">
					<h2>Spielstand</h2>
					<table id="score" summary="Diese Tabelle zeigt den aktuellen Spielstand">
						<tbody>
							<tr>
								<th class="accessibility">Label</th>
								<th>Du</th>
								<th>Gegner</th>
							</tr>
							<tr>
								<th class="label">Paare</th>
								<td><%= memory.getPoints()%></td>
								<td>0</td>
							</tr>
							<tr>
								<th class="label">Versuche</th>
								<td><%= memory.getAttempts()%></td>
								<td>2</td>
							</tr>
							<tr>
								<th class="label">Zeit</th>
								<td><%= memory.getPlayTime()%></td>
								<td>0:50</td>
							</tr>
						</tbody>
					</table>
					<h2>Spielinformationen</h2>
					<table id="game_info" summary="Diese Tabelle zeigt weitere Informationen zum aktuellen Spiel">
						<tbody>
							<tr class="accessibility">
								<th>Information</th>
								<th>Wert</th>
							</tr>
							<tr>
								<th>Restliche Paare</th>
								<td><%= memory.getRemainingPairs()%></td>
							</tr>
						</tbody>
					</table>
					<h2>Highscore</h2>
					<ul id="unknown">
						<li>Petra: 165 Punkte</li>
						<li>Manuel: 128 Punkte</li>
						<li>Lena: 115 Punkte</li>
						<li>Andi: 99 Punkte</li>
					</ul>
				</div>
				<div id="play_area">
					<hr class="accessibility" />
					<div id="board">
						<h2 class="accessibility">Spielbrett</h2>
						
						<%
						int rowCount = 0;
						for(List<MemoryCard> row : memory.getRows()) {
							int column = 0;
						%>
						<ol>
							<%
							for(MemoryCard card : row) {
								if(card.isVisible()) {
							%><li title="Zeile <%= rowCount %>, Spalte <%= column %>" class="card visible"><img src="<%= card.getImagePath() %>" alt="<%= card.getAltText() %>"/></li>			
							<%
								}
								else {
							%><li title="Zeile <%= rowCount %>, Spalte <%= column %>" class="card"><a href="<%= UrlFactory.generateMemoryCardClickUrl(rowCount, column) %>"><img src="img/card_background.png" alt="zugedeckte Karte"/></a></li>
							<%
								}
								column++;
							}
							rowCount++;
						%></ol>
						<%
						}
						%>
						
						<div class="clearer"></div>
					</div>
				</div>				
			</div>
			<div id="footer">
				<p>&copy; 2011 EWA Memory.</p>
			</div>
		</div>
	</body>
</html>
