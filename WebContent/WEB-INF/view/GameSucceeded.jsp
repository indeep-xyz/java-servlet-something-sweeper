<%@page import="controller.game.GameMaster"%>
<%@page import="java.util.Random"%>
<%@page import="model.Cell"%>
<%@page import="model.Field"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マインスイーパ (成功)</title>
<link rel="stylesheet" href="style/default.css" />
</head>
<body>

<%
Random rand = new Random();
Field field = (Field) session.getAttribute(GameMaster.SESSION_FIELD_DATA);
int width = field.getWidth();
int height = field.getHeight();
int difficulty = field.getDifficulty();
String[] colors = {"black", "red", "yellow", "green", "lightgreen", "skyblue", "magenta", "olieve"};
%>

<h1>成功</h1>

<form action="SomethingSweeper" method="get">
	<input type="hidden" name="width" value="<%= width %>">
	<input type="hidden" name="height" value="<%= height %>">
	<input type="hidden" name="difficulty" value="<%= difficulty %>">
	<p>
		<input type="submit" value="同じ設定で再挑戦">
	</p>
</form>

<form action="SomethingSweeper" method="get">
	<p>
		<input type="submit" value="設定を初期化して最初から">
	</p>
</form>

<div id="result">
	<div id="message">
		<% for(int i = 0; i < 20; i++) {
			String attr = (i % 2 == 0) ? "" : " direction=\"right\"";
		%>
			<marquee <%= attr %>>
			<% for(int j = 0; j < 16; j++) {
				String color = colors[rand.nextInt(colors.length)];
				%>
				
				<span style="color: <%= color %>">成功</span> 
			<% } %>
			</marquee>
		<% } %>
	</div>
	
    <jsp:include page="part/field-uncovered.jsp"></jsp:include>
</div>

</body>
</html>