<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Random rand = new Random();
String[] colors = {"black", "red", "yellow", "green", "lightgreen", "skyblue", "magenta", "olieve"};
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Something Sweeper (Succeeded)</title>
<link rel="stylesheet" href="style/default.css" />

<script type="text/javascript" src="script/Game/game-result.js"></script>
<script type="text/javascript" src="script/Game/field-manager.js"></script>
<script type="text/javascript" src="script/Game/field-loader.js"></script>
<script type="text/javascript" src="script/Game/field-object.js"></script>
<script type="text/javascript" src="script/Game/history-loader.js"></script>
<script type="text/javascript" src="script/Game/history-viewer.js"></script>
<script type="text/javascript" src="script/Game/cell-object.js"></script>
</head>
<body>

<header class="succeeded">
    <h1>Something Sweeper</h1>
</header>

<h2>SUCCEEDED</h2>

<jsp:include page="part/RetryForm.jsp"></jsp:include>

<div id="result">
	<div id="message">
		<% for(int i = 0; i < 20; i++) {
			String attr = (i % 2 == 0) ? "" : " direction=\"right\"";
		%>
			<marquee <%= attr %>>
			<% for(int j = 0; j < 16; j++) {
				String color = colors[rand.nextInt(colors.length)];
				%>

				<span style="color: <%= color %>">Yeah!</span>
			<% } %>
			</marquee>
		<% } %>
	</div>

	<section id="field"></section>
</div>

</body>
</html>